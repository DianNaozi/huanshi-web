package com.godwei.huanshi;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
@MapperScan("com.godwei.huanshi.mapper")
public class HuanshiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuanshiApiApplication.class, args);
    }

    /**
     * 媒体文件夹表，用于归纳媒体文件
     * @TableName media_folder
     */
    @TableName(value ="media_folder")
    public static class MediaFolder {
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
        private Boolean is_deleted;

        /**
         * 主键ID (自增)
         */
        public Long getId() {
            return id;
        }

        /**
         * 主键ID (自增)
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * 文件夹名称
         */
        public String getFolder_name() {
            return folder_name;
        }

        /**
         * 文件夹名称
         */
        public void setFolder_name(String folder_name) {
            this.folder_name = folder_name;
        }

        /**
         * 父文件夹ID (NULL表示根目录)
         */
        public Long getParent_id() {
            return parent_id;
        }

        /**
         * 父文件夹ID (NULL表示根目录)
         */
        public void setParent_id(Long parent_id) {
            this.parent_id = parent_id;
        }

        /**
         * 文件夹封面URL
         */
        public String getCover_url() {
            return cover_url;
        }

        /**
         * 文件夹封面URL
         */
        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        /**
         * 创建时间 (带时区)
         */
        public Date getCreated_at() {
            return created_at;
        }

        /**
         * 创建时间 (带时区)
         */
        public void setCreated_at(Date created_at) {
            this.created_at = created_at;
        }

        /**
         * 更新时间 (带时区)
         */
        public Date getUpdated_at() {
            return updated_at;
        }

        /**
         * 更新时间 (带时区)
         */
        public void setUpdated_at(Date updated_at) {
            this.updated_at = updated_at;
        }

        /**
         * 是否逻辑删除
         */
        public Boolean getIs_deleted() {
            return is_deleted;
        }

        /**
         * 是否逻辑删除
         */
        public void setIs_deleted(Boolean is_deleted) {
            this.is_deleted = is_deleted;
        }
    }
}
