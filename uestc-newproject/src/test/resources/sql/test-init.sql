-- =============================================
-- 测试数据库初始化脚本 (H2)
-- =============================================

-- 清空所有表
DROP TABLE IF EXISTS `sys_user_log`;
DROP TABLE IF EXISTS `sys_about`;
DROP TABLE IF EXISTS `sys_statistics`;
DROP TABLE IF EXISTS `cms_notice`;
DROP TABLE IF EXISTS `cms_service`;
DROP TABLE IF EXISTS `cms_policy`;
DROP TABLE IF EXISTS `cms_knowledge_article`;
DROP TABLE IF EXISTS `cms_knowledge_category`;
DROP TABLE IF EXISTS `cms_news`;
DROP TABLE IF EXISTS `sys_user`;

-- 1. 系统用户表
CREATE TABLE `sys_user` (
  `id`              BIGINT       NOT NULL AUTO_INCREMENT,
  `username`        VARCHAR(50)  NOT NULL,
  `password`        VARCHAR(255) NOT NULL,
  `real_name`       VARCHAR(50)  DEFAULT NULL,
  `avatar`          VARCHAR(255) DEFAULT NULL,
  `department`      VARCHAR(100) DEFAULT NULL,
  `role`            VARCHAR(50)  DEFAULT 'user',
  `email`           VARCHAR(100) DEFAULT NULL,
  `phone`           VARCHAR(20)  DEFAULT NULL,
  `status`          TINYINT      DEFAULT 1,
  `last_login_time` DATETIME     DEFAULT NULL,
  `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`         TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
);

-- 2. 平台统计表
CREATE TABLE `sys_statistics` (
  `id`                    BIGINT   NOT NULL AUTO_INCREMENT,
  `data_source_count`     INT      NOT NULL DEFAULT 0,
  `data_entry_count`      BIGINT   NOT NULL DEFAULT 0,
  `analysis_model_count`  INT      NOT NULL DEFAULT 0,
  `availability_rate`     DECIMAL(5,2) NOT NULL DEFAULT 100.00,
  `update_time`           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

INSERT INTO `sys_statistics` (`data_source_count`, `data_entry_count`, `analysis_model_count`, `availability_rate`)
VALUES (128, 520000, 23, 99.96);

-- 3. 关于我们表
CREATE TABLE `sys_about` (
  `id`              BIGINT   NOT NULL AUTO_INCREMENT,
  `center_name`     VARCHAR(100) NOT NULL,
  `description`     TEXT,
  `organization`    TEXT,
  `milestones`      TEXT DEFAULT NULL,
  `contact_address` VARCHAR(255) DEFAULT NULL,
  `contact_phone`   VARCHAR(30)  DEFAULT NULL,
  `contact_email`   VARCHAR(100) DEFAULT NULL,
  `work_hours`      VARCHAR(50)  DEFAULT NULL,
  `update_time`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

INSERT INTO `sys_about` (`center_name`) VALUES ('健康大数据应用创新研发中心');

-- 4. 新闻表
CREATE TABLE `cms_news` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `title`        VARCHAR(255) NOT NULL,
  `category`     VARCHAR(50)  NOT NULL,
  `summary`      VARCHAR(500) DEFAULT NULL,
  `content`      TEXT         NOT NULL,
  `cover_image`  VARCHAR(255) DEFAULT NULL,
  `source`       VARCHAR(100) DEFAULT NULL,
  `is_hot`       TINYINT      DEFAULT 0,
  `is_new`       TINYINT      DEFAULT 1,
  `is_top`       TINYINT      DEFAULT 0,
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft',
  `view_count`   INT          NOT NULL DEFAULT 0,
  `publish_time` DATETIME     DEFAULT NULL,
  `create_by`    BIGINT       DEFAULT NULL,
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 5. 通知公告表
CREATE TABLE `cms_notice` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `title`        VARCHAR(255) NOT NULL,
  `content`      TEXT         NOT NULL,
  `level`        VARCHAR(20)  NOT NULL DEFAULT 'normal',
  `is_top`       TINYINT      DEFAULT 0,
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft',
  `publish_time` DATETIME     DEFAULT NULL,
  `create_by`    BIGINT       DEFAULT NULL,
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 6. 服务目录表
CREATE TABLE `cms_service` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50)  NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `icon`        VARCHAR(30)  DEFAULT NULL,
  `color`       VARCHAR(10)  DEFAULT NULL,
  `features`    TEXT         DEFAULT NULL,
  `link_url`    VARCHAR(100) DEFAULT NULL,
  `sort_order`  INT          NOT NULL DEFAULT 0,
  `enabled`     TINYINT      NOT NULL DEFAULT 1,
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`     TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

INSERT INTO `cms_service` (`name`, `description`, `icon`, `color`, `features`, `sort_order`, `enabled`)
VALUES ('数据查询分析', '多维度医疗健康数据检索', 'chart', '#1a6fb5', '["多条件组合查询","统计分析图表"]', 1, 1);

-- 7. 卫生政策表
CREATE TABLE `cms_policy` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `title`        VARCHAR(255) NOT NULL,
  `description`  VARCHAR(500) DEFAULT NULL,
  `content`      TEXT,
  `tag`          VARCHAR(30)  NOT NULL,
  `source`       VARCHAR(100) DEFAULT NULL,
  `file_name`    VARCHAR(255) DEFAULT NULL,
  `file_url`     VARCHAR(255) DEFAULT NULL,
  `publish_time` DATE         DEFAULT NULL,
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft',
  `create_by`    BIGINT       DEFAULT NULL,
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 8. 知识库分类表
CREATE TABLE `cms_knowledge_category` (
  `id`          BIGINT      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) NOT NULL,
  `icon`        VARCHAR(30) DEFAULT NULL,
  `sort_order`  INT         NOT NULL DEFAULT 0,
  `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`     TINYINT     NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

INSERT INTO `cms_knowledge_category` (`name`, `icon`, `sort_order`) VALUES
('疾病防治', 'medicine', 1);

-- 9. 知识库文章表
CREATE TABLE `cms_knowledge_article` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `category_id`  BIGINT       NOT NULL,
  `title`        VARCHAR(255) NOT NULL,
  `summary`      VARCHAR(500) DEFAULT NULL,
  `content`      TEXT         NOT NULL,
  `view_count`   INT          NOT NULL DEFAULT 0,
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft',
  `publish_time` DATETIME     DEFAULT NULL,
  `create_by`    BIGINT       DEFAULT NULL,
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 10. 用户操作日志表
CREATE TABLE `sys_user_log` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT       NOT NULL,
  `username`    VARCHAR(50)  NOT NULL,
  `action`      VARCHAR(100) NOT NULL,
  `target`      VARCHAR(255) DEFAULT NULL,
  `ip_address`  VARCHAR(50)  DEFAULT NULL,
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);
