package com.godwei.huanshi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 媒体文件夹表，用于归纳媒体文件
 * @TableName media_folder
 */
@TableName(value ="media_folder")
@Data
public class MediaFolder implements Serializable {
    /**
     * 主键ID (自增)
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件夹名称
     */
    private String folder_name;

    /**
     * 父文件夹ID (NULL表示根目录)
     */
    private Long parent_id;

    /**
     * 文件夹封面URL
     */
    private String cover_url;

    /**
     * 创建时间 (带时区)
     */
    private Date created_at;

    /**
     * 更新时间 (带时区)
     */
    private Date updated_at;

    /**
     * 是否逻辑删除
     */
    @TableLogic
    private Boolean is_deleted;
}