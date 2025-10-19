create database huanshi;

-- 使用 BEGIN...COMMIT 确保所有语句作为一个整体执行
BEGIN;

-- ----------------------------
-- 1. 创建 媒体文件夹表 (media_folder)
-- (已移除 user_id 和 外键约束)
-- ----------------------------
CREATE TABLE IF NOT EXISTS media_folder
(
    id          bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    folder_name varchar(255) NOT NULL,

    -- 核心归纳功能字段 (NULL 表示根目录)
    parent_id   bigint       NULL,

    -- 附加信息
    cover_url   varchar(1024) NULL,

    -- 时间戳与软删除
    created_at  timestamptz  NOT NULL DEFAULT now(),
    updated_at  timestamptz  NOT NULL DEFAULT now(),
    is_deleted  boolean      NOT NULL DEFAULT false,

    -- 优化：唯一约束
    -- 即使没有外键，也应防止在同一个父文件夹下创建同名文件夹
    -- (允许 parent_id 为 NULL 的情况下有多个同名根目录)
    CONSTRAINT uk_parent_name
        UNIQUE (parent_id, folder_name)
);


-- ----------------------------
-- 2. 添加注释 (PostgreSQL 标准语法)
-- ----------------------------
COMMENT ON TABLE media_folder IS '媒体文件夹表，用于归纳媒体文件';

COMMENT ON COLUMN media_folder.id IS '主键ID (自增)';
COMMENT ON COLUMN media_folder.folder_name IS '文件夹名称';
COMMENT ON COLUMN media_folder.parent_id IS '父文件夹ID (NULL表示根目录)';
COMMENT ON COLUMN media_folder.cover_url IS '文件夹封面URL';
COMMENT ON COLUMN media_folder.created_at IS '创建时间 (带时区)';
COMMENT ON COLUMN media_folder.updated_at IS '更新时间 (带时区)';
COMMENT ON COLUMN media_folder.is_deleted IS '是否逻辑删除';


-- ----------------------------
-- 3. 创建索引 (优化查询性能)
-- ----------------------------
-- 移除外键后，为 parent_id 创建索引对于查询树形结构至关重要
CREATE INDEX IF NOT EXISTS idx_media_folder_parent_id ON media_folder (parent_id);

-- 提交事务
COMMIT;

-- 确保在你的表上运行
-- 注意：这些数据假设 id 从 1 开始自增

-- ----------------------------
-- 1. 插入根目录 (parent_id 为 NULL)
-- ----------------------------
-- 假设 id=1
INSERT INTO media_folder (folder_name, parent_id, cover_url)
VALUES ('我的照片', NULL, 'https://example.com/covers/photos.jpg');

-- 假设 id=2
INSERT INTO media_folder (folder_name, parent_id, cover_url)
VALUES ('工作文档', NULL, 'https://example.com/covers/work.jpg');

-- 假设 id=3
INSERT INTO media_folder (folder_name, parent_id)
VALUES ('游戏截图', NULL);


-- ----------------------------
-- 2. 插入子目录 (引用 1, 2, 3)
-- ----------------------------
-- 假设 '2025年假期' 的 id=4。 它的父目录是 '我的照片' (id=1)
INSERT INTO media_folder (folder_name, parent_id, cover_url)
VALUES ('2025年假期', 1, 'https://example.com/covers/vacation.jpg');

-- 假设 '日常生活' 的 id=5。 它的父目录是 '我的照片' (id=1)
INSERT INTO media_folder (folder_name, parent_id)
VALUES ('日常生活', 1);

-- 假设 '项目A' 的 id=6。 它的父目录是 '工作文档' (id=2)
INSERT INTO media_folder (folder_name, parent_id)
VALUES ('项目A', 2);

-- 假设 '项目B' 的 id=7。 它的父目录是 '工作文档' (id=2)
INSERT INTO media_folder (folder_name, parent_id)
VALUES ('项目B', 2);

-- 假设 '艾尔登法环' 的 id=8。 它的父目录是 '游戏截图' (id=3)
INSERT INTO media_folder (folder_name, parent_id)
VALUES ('《艾尔登法环》', 3);


-- ----------------------------
-- 3. 插入孙子目录 (引用 4)
-- ----------------------------
-- 假设 '风景照' 的 id=9。 它的父目录是 '2025年假期' (id=4)
INSERT INTO media_folder (folder_name, parent_id)
VALUES ('风景照', 4);

-- 假设 '人物照' 的 id=10。 它的父目录是 '2025年假期' (id=4)
INSERT INTO media_folder (folder_name, parent_id)
VALUES ('人物照', 4);


-- ----------------------------
-- 4. 插入一个已删除的文件夹
-- ----------------------------
-- 假设 '旧资料' 的 id=11。 它的父目录是 '工作文档' (id=2)
INSERT INTO media_folder (folder_name, parent_id, is_deleted)
VALUES ('旧资料(已删除)', 2, true);