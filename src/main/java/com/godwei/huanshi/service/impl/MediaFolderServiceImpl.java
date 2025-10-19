package com.godwei.huanshi.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.godwei.huanshi.exception.BusinessException;
import com.godwei.huanshi.exception.ErrorCode;
import com.godwei.huanshi.model.entity.MediaFolder;
import com.godwei.huanshi.mapper.MediaFolderMapper;
import com.godwei.huanshi.service.MediaFolderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author godwei
 * @description 针对表【media_folder(媒体文件夹表，用于归纳媒体文件)】的数据库操作Service实现
 * @createDate 2025-10-19 19:15:06
 */
@Service
public class MediaFolderServiceImpl extends ServiceImpl<MediaFolderMapper, MediaFolder>
        implements MediaFolderService {

    @Override
    public MediaFolder addMediaFolder(String folderName, Long parentId) {
        // 1. 校验
        if (StrUtil.hasBlank(folderName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (parentId == null) {
            parentId = 0L; // ⬅️ 问题的根源在这里
        }

        // 2. 插入数据
        MediaFolder mediaFolder = new MediaFolder();
        mediaFolder.setFolder_name(folderName);
        mediaFolder.setParent_id(parentId); // 直接设置 (可以为 null)

        boolean saveResult = this.save(mediaFolder);
        if (!saveResult) {
            // "注册失败" 最好改成 "创建失败"
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建失败，数据库错误");
        }

        // 3. 返回数据
        // `this.save()` 成功后, `mediaFolder.getId()` 已经有值了。
        // TODO  为了获取由数据库自动生成的 created_at, updated_at 等完整信息，这里多查了一次
        return this.getById(mediaFolder.getId());
    }

    @Override
    public List<MediaFolder> listAllFolders() {
        return this.list();
    }

    @Override
    @Transactional
    public boolean deleteFolderAndChildren(Long folderId) {
        if (folderId == null || folderId <= 0) {
            // 这里你可以抛出你的 BusinessException
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的ID");
        }

        // 1. 创建一个列表，用于存放所有需要被删除的ID
        List<Long> idsToDelete = new ArrayList<>();

        // 2. 创建一个队列，用于广度优先搜索 (BFS)
        Queue<Long> queue = new LinkedList<>();
        queue.add(folderId); // 将要删除的根ID放入队列

        // 3. 循环，直到队列为空
        while (!queue.isEmpty()) {
            Long currentId = queue.poll(); // 取出一个ID
            idsToDelete.add(currentId);      // 将它加入到“待删除列表”

            // 4. 查找这个ID的所有 *直接* 子文件夹
            QueryWrapper<MediaFolder> childrenWrapper = new QueryWrapper<>();
            childrenWrapper.eq("parent_id", currentId)
                    .select("id"); // 我们只需要ID

            // listObjs 会返回一个只包含ID的列表 (List<Object> -> List<Long>)
            List<Long> childrenIds = this.listObjs(childrenWrapper, id -> (Long) id);

            // 5. 将所有找到的子ID加入队列，等待下一轮处理
            if (childrenIds != null && !childrenIds.isEmpty()) {
                queue.addAll(childrenIds);
            }
        }

        // 6. 此时 idsToDelete 包含了 folderId 及其所有子孙ID
        //    使用 MyBatis Plus 的批量逻辑删除
        return this.removeByIds(idsToDelete);
    }
}




