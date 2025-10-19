package com.godwei.huanshi.service;

import com.godwei.huanshi.model.dto.mediafolder.AddMediaFolderRequest;
import com.godwei.huanshi.model.entity.MediaFolder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author godwei
* @description 针对表【media_folder(媒体文件夹表，用于归纳媒体文件)】的数据库操作Service
* @createDate 2025-10-19 19:15:06
*/
public interface MediaFolderService extends IService<MediaFolder> {

    MediaFolder addMediaFolder(String folderName, Long parentId);

    List<MediaFolder> listAllFolders();

    boolean deleteFolderAndChildren(Long folderId);
}
