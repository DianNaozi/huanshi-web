package com.godwei.huanshi.controller;

import com.godwei.huanshi.common.BaseResponse;
import com.godwei.huanshi.common.ResultUtils;
import com.godwei.huanshi.exception.ErrorCode;
import com.godwei.huanshi.exception.ThrowUtils;
import com.godwei.huanshi.model.dto.mediafolder.AddMediaFolderRequest;
import com.godwei.huanshi.model.entity.MediaFolder;
import com.godwei.huanshi.service.MediaFolderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mediaFolder")
public class MediaFolderController {

    @Resource
    private MediaFolderService mediaFolderService;

    /**
     * 添加媒体文件夹
     * @param addMediaFolderRequest
     * @return MediaFolder
     */
    @PostMapping("/add")
    public BaseResponse<MediaFolder> add(@RequestBody AddMediaFolderRequest addMediaFolderRequest) {
        ThrowUtils.throwIf(addMediaFolderRequest == null, ErrorCode.PARAMS_ERROR);
        String folderName = addMediaFolderRequest.getFolder_name();
        Long parentId = addMediaFolderRequest.getParent_id();
        MediaFolder mediaFolder = mediaFolderService.addMediaFolder(folderName, parentId);
        return ResultUtils.success(mediaFolder);
    }

    /**
     * 查询所有
     */
    @GetMapping("/list")
    public BaseResponse<List<MediaFolder>> getAll() {
        List<MediaFolder> list = mediaFolderService.listAllFolders();
        return ResultUtils.success(list);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteFolder(@PathVariable("id") Long id) {
        boolean result = mediaFolderService.deleteFolderAndChildren(id);
        return ResultUtils.success(result);
    }


}


