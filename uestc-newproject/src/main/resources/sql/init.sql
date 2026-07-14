-- =============================================
-- 健康大数据应用创新研发中心 - 门户系统
-- 数据库初始化脚本
-- =============================================

-- 1. 系统用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username`        VARCHAR(50)  NOT NULL COMMENT '登录用户名',
  `password`        VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
  `real_name`       VARCHAR(50)  DEFAULT NULL COMMENT '真实姓名',
  `avatar`          VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `department`      VARCHAR(100) DEFAULT NULL COMMENT '部门',
  `role`            VARCHAR(50)  DEFAULT 'user' COMMENT '角色：admin/editor/user',
  `email`           VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone`           VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
  `status`          TINYINT      DEFAULT 1 COMMENT '状态：1启用 0禁用',
  `last_login_time` DATETIME     DEFAULT NULL COMMENT '最后登录时间',
  `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`         TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0正常 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
);

-- 初始化管理员账号（密码：admin123，BCrypt加密）
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `role`)
VALUES ('admin', '$2a$10$8cq2.pOOYTV0QXg4IcVTIu3UDgH7zdlMUhDxVtasi8yczjEJduIuq', '系统管理员', 'admin');

-- 初始化普通测试用户（密码：test1234，BCrypt加密）
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `department`, `role`)
VALUES ('testuser', '$2a$10$YRrF/d1HUa6BK5EA/3W3e..wTMw26pWpNgyRp4ElvPYhN28NIAClS', '测试用户', '数据管理部', 'user');

-- 2. 用户操作日志表
CREATE TABLE IF NOT EXISTS `sys_user_log` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT       NOT NULL COMMENT '用户ID',
  `username`    VARCHAR(50)  NOT NULL COMMENT '用户名',
  `action`      VARCHAR(100) NOT NULL COMMENT '操作描述',
  `target`      VARCHAR(255) DEFAULT NULL COMMENT '操作对象',
  `ip_address`  VARCHAR(50)  DEFAULT NULL COMMENT '操作IP',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
);

-- 3. 平台统计表
CREATE TABLE IF NOT EXISTS `sys_statistics` (
  `id`                    BIGINT   NOT NULL AUTO_INCREMENT,
  `data_source_count`     INT      NOT NULL DEFAULT 0 COMMENT '数据源接入数量',
  `data_entry_count`      BIGINT   NOT NULL DEFAULT 0 COMMENT '数据条目总数',
  `analysis_model_count`  INT      NOT NULL DEFAULT 0 COMMENT '分析模型数量',
  `availability_rate`     DECIMAL(5,2) NOT NULL DEFAULT 100.00 COMMENT '平台可用率(%)',
  `update_time`           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
);

INSERT INTO `sys_statistics` (`data_source_count`, `data_entry_count`, `analysis_model_count`, `availability_rate`)
VALUES (128, 520000, 23, 99.96);

-- 4. 关于我们表
CREATE TABLE IF NOT EXISTS `sys_about` (
  `id`              BIGINT   NOT NULL AUTO_INCREMENT,
  `center_name`     VARCHAR(100) NOT NULL COMMENT '中心名称',
  `description`     TEXT COMMENT '中心简介（富文本HTML）',
  `organization`    TEXT COMMENT '组织架构（富文本HTML）',
  `milestones`      TEXT DEFAULT NULL COMMENT '发展历程JSON',
  `contact_address` VARCHAR(255) DEFAULT NULL COMMENT '联系地址',
  `contact_phone`   VARCHAR(30)  DEFAULT NULL COMMENT '联系电话',
  `contact_email`   VARCHAR(100) DEFAULT NULL COMMENT '联系邮箱',
  `work_hours`      VARCHAR(50)  DEFAULT NULL COMMENT '工作时间',
  `update_time`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

INSERT INTO `sys_about` (`center_name`, `description`, `organization`, `milestones`, `contact_address`, `contact_phone`, `contact_email`, `work_hours`)
VALUES ('健康大数据应用创新研发中心',
'<p>健康大数据应用创新研发中心（以下简称"中心"）是依托电子科技大学建设的省级科研平台，专注于健康医疗大数据领域的技术研发、应用创新和成果转化。</p><p>中心以"数据驱动健康，创新引领未来"为发展理念，围绕健康医疗数据的采集汇聚、治理融合、智能分析、安全共享等关键环节，开展核心技术攻关和产业化应用。</p><p>中心拥有一支由计算机科学、医学信息学、生物统计学等多学科交叉的高水平研发团队，现有固定研究人员50余人，其中高级职称占比60%以上。中心设有数据治理、智能分析、隐私计算、可视化、健康管理五个研究室。</p><p>中心承担了多项国家级、省部级科研项目，累计申请发明专利30余项，发表高水平学术论文100余篇，制定行业标准5项。中心建设了数据治理平台、安全共享平台、智能分析平台、可视化平台、知识图谱平台、健康服务平台六大平台，为全省30余家医疗机构提供数据服务。</p>',
'<h4>组织架构</h4><ul><li><strong>学术委员会</strong> — 由国内外知名专家学者组成，负责中心科研方向的学术指导</li><li><strong>数据治理研究室</strong> — 负责多源异构健康医疗数据的采集、清洗、标准化和质量管理</li><li><strong>智能分析研究室</strong> — 负责AI驱动的健康大数据分析与预测建模</li><li><strong>隐私计算研究室</strong> — 负责联邦学习、差分隐私等隐私保护技术研发</li><li><strong>可视化研究室</strong> — 负责健康数据可视化与大屏展示技术研发</li><li><strong>健康管理研究室</strong> — 负责面向公众的健康管理与服务平台研发</li><li><strong>综合管理办公室</strong> — 负责中心日常行政、科研管理和对外交流合作</li></ul>',
'[{"year":"2020","title":"中心成立","desc":"健康大数据应用创新研发中心正式挂牌成立"},{"year":"2021","title":"数据治理平台上线","desc":"中心首个自主研发的健康医疗数据治理平台正式上线运行"},{"year":"2022","title":"获评省级重点实验室","desc":"中心被省科技厅评为省级重点实验室"},{"year":"2023","title":"安全共享平台通过等保三级","desc":"数据安全共享平台通过国家网络安全等级保护三级认证"},{"year":"2024","title":"六大平台全面建成","desc":"数据治理、安全共享、智能分析、可视化、知识图谱、健康服务六大平台全面投入运行"},{"year":"2025","title":"承担国家级重大项目","desc":"承担国家重点研发计划项目2项，科研经费突破2000万元"},{"year":"2026","title":"服务覆盖30+医疗机构","desc":"中心平台服务覆盖全省30余家医疗机构，累计治理数据超过10亿条"}]',
'四川省成都市高新区西源大道2006号电子科技大学清水河校区',
'028-61830000',
'contact@healthinnova.edu.cn',
'周一至周五 8:30 - 17:30（法定节假日除外）');

-- 5. 新闻表
CREATE TABLE IF NOT EXISTS `cms_news` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '新闻ID',
  `title`        VARCHAR(255) NOT NULL COMMENT '标题',
  `category`     VARCHAR(50)  NOT NULL COMMENT '分类',
  `summary`      VARCHAR(500) DEFAULT NULL COMMENT '摘要',
  `content`      TEXT         NOT NULL COMMENT '正文内容（富文本HTML）',
  `cover_image`  VARCHAR(255) DEFAULT NULL COMMENT '封面图URL',
  `source`       VARCHAR(100) DEFAULT NULL COMMENT '来源',
  `is_hot`       TINYINT      DEFAULT 0 COMMENT '是否热门',
  `is_new`       TINYINT      DEFAULT 1 COMMENT '是否新增',
  `is_top`       TINYINT      DEFAULT 0 COMMENT '是否置顶',
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft' COMMENT '状态',
  `view_count`   INT          NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `publish_time` DATETIME     DEFAULT NULL COMMENT '发布时间',
  `create_by`    BIGINT       DEFAULT NULL COMMENT '创建人ID',
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
);

-- 6. 通知公告表
CREATE TABLE IF NOT EXISTS `cms_notice` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `title`        VARCHAR(255) NOT NULL COMMENT '标题',
  `content`      TEXT         NOT NULL COMMENT '正文（富文本HTML）',
  `level`        VARCHAR(20)  NOT NULL DEFAULT 'normal' COMMENT '重要程度',
  `is_top`       TINYINT      DEFAULT 0 COMMENT '是否置顶',
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft' COMMENT '状态',
  `publish_time` DATETIME     DEFAULT NULL COMMENT '发布时间',
  `create_by`    BIGINT       DEFAULT NULL,
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 7. 服务目录表
CREATE TABLE IF NOT EXISTS `cms_service` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50)  NOT NULL COMMENT '服务名称',
  `description` VARCHAR(255) NOT NULL COMMENT '服务描述',
  `icon`        VARCHAR(30)  DEFAULT NULL COMMENT '图标标识',
  `color`       VARCHAR(10)  DEFAULT NULL COMMENT '主题色',
  `features`    TEXT         DEFAULT NULL COMMENT '功能特性列表JSON',
  `link_url`    VARCHAR(100) DEFAULT NULL COMMENT '跳转链接',
  `sort_order`  INT          NOT NULL DEFAULT 0 COMMENT '排序序号',
  `enabled`     TINYINT      NOT NULL DEFAULT 1 COMMENT '是否启用',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`     TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 8. 卫生政策表
CREATE TABLE IF NOT EXISTS `cms_policy` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `title`        VARCHAR(255) NOT NULL COMMENT '政策标题',
  `description`  VARCHAR(500) DEFAULT NULL COMMENT '简要描述',
  `content`      TEXT COMMENT '正文（富文本HTML）',
  `tag`          VARCHAR(30)  NOT NULL COMMENT '标签',
  `source`       VARCHAR(100) DEFAULT NULL COMMENT '发文单位',
  `file_name`    VARCHAR(255) DEFAULT NULL COMMENT '附件文件名',
  `file_url`     VARCHAR(255) DEFAULT NULL COMMENT '附件下载URL',
  `publish_time` DATE         DEFAULT NULL COMMENT '发布日期',
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft' COMMENT '状态',
  `create_by`    BIGINT       DEFAULT NULL,
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 9. 知识库分类表
CREATE TABLE IF NOT EXISTS `cms_knowledge_category` (
  `id`          BIGINT      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) NOT NULL COMMENT '分类名称',
  `icon`        VARCHAR(30) DEFAULT NULL COMMENT '图标标识',
  `sort_order`  INT         NOT NULL DEFAULT 0 COMMENT '排序序号',
  `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`     TINYINT     NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
);

INSERT INTO `cms_knowledge_category` (`name`, `icon`, `sort_order`) VALUES
('疾病防治', 'medicine', 1), ('健康生活', 'diet', 2), ('公共卫生', 'shield', 3),
('妇幼保健', 'baby', 4), ('老年健康', 'elder', 5), ('心理健康', 'psychology', 6);

-- 10. 知识库文章表
CREATE TABLE IF NOT EXISTS `cms_knowledge_article` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `category_id`  BIGINT       NOT NULL COMMENT '所属分类ID',
  `title`        VARCHAR(255) NOT NULL COMMENT '文章标题',
  `summary`      VARCHAR(500) DEFAULT NULL COMMENT '摘要',
  `content`      TEXT         NOT NULL COMMENT '正文（富文本HTML）',
  `view_count`   INT          NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `status`       VARCHAR(20)  NOT NULL DEFAULT 'draft' COMMENT '状态',
  `publish_time` DATETIME     DEFAULT NULL COMMENT '发布时间',
  `create_by`    BIGINT       DEFAULT NULL,
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted`      TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- 11. 用户收藏表
CREATE TABLE IF NOT EXISTS `sys_favorite` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT,
  `user_id`      BIGINT       NOT NULL COMMENT '用户ID',
  `type`         VARCHAR(20)  NOT NULL COMMENT '收藏类型：news / app',
  `target_id`    BIGINT       NOT NULL COMMENT '收藏目标ID（新闻ID或服务ID）',
  `target_title` VARCHAR(255) DEFAULT NULL COMMENT '收藏时的标题（冗余，便于展示）',
  `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`, `type`, `target_id`)
);

-- 初始化服务列表（6个现有服务 + 1个测试应用）
INSERT INTO `cms_service` (`name`, `description`, `icon`, `color`, `features`, `sort_order`, `enabled`) VALUES
('数据治理平台', '提供医疗健康数据的清洗、标准化、质量管理等服务', 'datalake', '#1a6fb5', '["多源数据接入","数据清洗与标注","数据质量评估","数据血缘追踪"]', 1, 1),
('安全共享平台', '基于隐私计算的数据安全共享与交换', 'shield', '#e67e22', '["联邦学习","差分隐私","数据沙箱","审计追溯"]', 2, 1),
('智能分析平台', 'AI驱动的健康大数据分析与预测建模', 'brain', '#27ae60', '["智能问数","预测建模","异常检测","报告自动生成"]', 3, 1),
('可视化平台', '低代码拖拽式健康数据可视化大屏', 'chart', '#8e44ad', '["拖拽式编辑","大屏模板","实时数据刷新","多端适配"]', 4, 1),
('知识图谱平台', '构建医疗健康知识图谱与智能问答系统', 'graph', '#2c3e50', '["实体关系抽取","图数据库存储","智能问答","关联推荐"]', 5, 1),
('健康服务平台', '面向公众的健康管理一站式服务平台', 'heart', '#2980b9', '["健康档案","风险评估","在线咨询","报告解读"]', 6, 1),
('测试应用', '用于收藏功能测试的示例应用', 'test', '#95a5a6', '["功能测试","收藏演示","展示用途"]', 99, 1);

-- ========== 测试数据（新闻、公告、政策、知识库文章） ==========

-- 新闻测试数据（20条）
INSERT INTO `cms_news` (`title`, `category`, `summary`, `content`, `source`, `is_hot`, `is_new`, `is_top`, `status`, `view_count`, `publish_time`) VALUES
('国家卫健委发布2026年医疗大数据应用新规划', '政策动态', '国家卫健委近日印发《2026-2028年医疗健康大数据应用发展规划》，明确了未来三年医疗大数据发展的重点方向和目标任务。', '<h3>政策亮点</h3><p>国家卫健委近日印发《2026-2028年医疗健康大数据应用发展规划》，明确了未来三年医疗大数据发展的重点方向和目标任务。</p><p>规划提出，到2028年，全国二级以上医疗机构数据互联互通率将达到90%以上，建成3-5个国家级医疗健康大数据中心。</p>', '国家卫健委', 1, 1, 1, 'published', 3280, '2026-07-10 10:00:00'),
('中心成功举办2026年健康大数据学术研讨会', '中心动态', '健康大数据应用创新研发中心主办的"2026健康大数据学术研讨会"于7月8日在成都圆满落幕。', '<p>本次研讨会汇聚了来自全国各地的200余名专家学者，围绕医疗健康大数据的前沿技术、应用实践和产业发展进行了深入交流。</p><p>会议共收到学术论文156篇，评选出优秀论文30篇。</p>', '中心办公室', 1, 1, 1, 'published', 2156, '2026-07-08 09:00:00'),
('人工智能辅助诊断系统通过国家医疗器械认证', '行业资讯', '由中心参与研发的AI辅助诊断系统正式通过国家药品监督管理局三类医疗器械认证。', '<p>该AI辅助诊断系统基于深度学习技术，可对CT影像、病理切片等进行智能分析，辅助医生进行疾病诊断。</p><p>临床试验表明，该系统对肺结节的检出率达到98.5%，高于传统人工阅片水平。</p>', '科技日报', 1, 0, 0, 'published', 4520, '2026-07-05 14:30:00'),
('健康医疗大数据标准体系建设取得阶段性成果', '政策动态', '国家卫生健康委统计信息中心发布了健康医疗大数据标准体系建设的阶段性成果报告。', '<p>报告显示，我国已累计制定健康医疗大数据国家标准87项、行业标准156项，涵盖数据采集、存储、共享、安全等各个环节。</p><p>标准体系的完善为医疗健康大数据的有序流通和合规使用提供了重要保障。</p>', '国家卫健委', 0, 0, 0, 'published', 1890, '2026-07-03 11:00:00'),
('中心与华西医院签署战略合作协议', '中心动态', '健康大数据应用创新研发中心与四川大学华西医院正式签署战略合作协议。', '<p>双方将在医疗健康大数据领域开展深度合作，重点围绕临床数据治理、智能分析模型构建、真实世界研究等方面进行联合攻关。</p><p>此次合作将整合中心的平台技术优势与华西医院的临床资源优势，推动科研成果转化应用。</p>', '中心办公室', 1, 0, 0, 'published', 3120, '2026-07-01 16:00:00'),
('区块链技术在医疗数据共享中的应用探索', '技术前沿', '区块链技术为医疗健康数据的安全共享提供了全新的技术路径和解决方案。', '<p>区块链的分布式账本、智能合约、共识机制等特性，天然适合解决医疗数据共享中的信任问题和权限控制问题。</p><p>目前，国内已有多个省市开展了基于区块链的医疗数据共享试点项目。</p>', '健康报', 0, 0, 0, 'published', 1350, '2026-06-28 09:30:00'),
('隐私计算技术在健康大数据领域应用指南发布', '技术前沿', '中国信通院联合多家单位发布了《隐私计算健康大数据应用指南（2026）》。', '<p>指南系统梳理了联邦学习、安全多方计算、差分隐私等隐私计算技术在医疗健康场景中的适用性和实施路径。</p><p>指南的发布将为医疗机构和科技企业在隐私保护前提下开展数据价值挖掘提供重要参考。</p>', '中国信通院', 0, 0, 0, 'published', 980, '2026-06-25 15:00:00'),
('中心数据治理平台获评2026年度优秀案例', '中心动态', '中心自主研发的健康医疗数据治理平台入选2026年度四川省大数据产业发展优秀案例。', '<p>四川省经济和信息化厅公布了2026年度大数据产业发展优秀案例评选结果，中心自主研发的"健康医疗数据治理平台"成功入选。</p><p>该平台已为全省30余家医疗机构提供数据治理服务，累计治理数据超过10亿条。</p>', '中心办公室', 1, 1, 0, 'published', 1560, '2026-07-12 09:00:00'),
('国家数据局印发健康医疗数据要素市场建设方案', '政策动态', '国家数据局联合国家卫健委印发《健康医疗数据要素市场建设实施方案》，推动数据合规高效流通。', '<p>方案提出，到2027年将建成3-5个国家级健康医疗数据要素市场试点，形成数据确权、定价、交易全链条制度体系。</p><p>方案强调要在保障数据安全的前提下，充分释放健康医疗数据要素价值。</p>', '国家数据局', 1, 0, 1, 'published', 2780, '2026-07-11 14:00:00'),
('中心举办2026年数据安全专题培训', '中心动态', '中心组织全体人员开展数据安全专题培训，提升数据安全防护能力。', '<p>本次培训邀请了国内知名数据安全专家授课，内容涵盖数据安全法律法规、数据分类分级、隐私保护技术等方面。</p><p>通过培训，全体人员的数据安全意识和防护能力得到了显著提升。</p>', '中心办公室', 0, 0, 0, 'published', 890, '2026-07-09 15:30:00'),
('智能问数系统在多家医院上线运行', '技术前沿', '中心研发的智能问数系统已在省内5家三甲医院完成部署并正式上线运行。', '<p>智能问数系统基于自然语言处理技术，医生可以通过语音或文字直接查询患者数据、统计指标等信息，无需手动编写SQL语句。</p><p>系统上线后，临床数据查询效率提升了80%以上。</p>', '中心技术部', 1, 0, 0, 'published', 2340, '2026-07-07 11:00:00'),
('健康大数据创新应用高峰论坛在蓉举行', '行业资讯', '由中心和四川省大数据发展研究会联合主办的2026健康大数据创新应用高峰论坛在成都举行。', '<p>论坛以"数智融合·健康未来"为主题，吸引了来自全国各地的400余名专家学者、行业代表参加。</p><p>论坛设立了数据治理、智能分析、隐私计算、健康管理四个分论坛，共举办专题报告50余场。</p>', '中心办公室', 1, 0, 0, 'published', 3120, '2026-07-04 08:30:00'),
('中心牵头编制的健康数据标准正式发布', '中心动态', '由中心牵头编制的两项健康医疗数据团体标准正式发布实施。', '<p>《健康医疗数据分类分级指南》《健康医疗数据质量管理规范》由中心联合四川大学华西医院、省标准化研究院等单位共同编制，填补了省内健康医疗数据标准的空白。</p><p>标准的发布将有效推动全省健康医疗数据的规范化管理和高效利用。</p>', '中心办公室', 0, 0, 0, 'published', 1230, '2026-07-02 10:00:00'),
('隐私计算技术助力多中心临床研究', '技术前沿', '中心运用联邦学习技术，实现了多家医院间的协同临床研究。', '<p>中心联合省内外6家三甲医院，利用联邦学习技术开展多中心临床研究。在数据不出院区的前提下，完成了基于10万例患者数据的疾病预测模型训练。</p><p>研究成果已发表在国际知名学术期刊上。</p>', '中心研发部', 0, 0, 0, 'published', 1670, '2026-06-30 16:00:00'),
('数据安全共享平台通过等保三级认证', '中心动态', '中心建设的数据安全共享平台正式通过国家网络安全等级保护三级测评认证。', '<p>等保三级是国家对非银行金融机构的最高信息安全认证等级。通过认证标志着中心在数据安全防护能力方面达到了行业领先水平。</p><p>平台目前已为多家医疗机构和科研单位提供安全数据共享服务。</p>', '中心办公室', 1, 0, 0, 'published', 980, '2026-06-26 13:00:00'),
('健康知识图谱平台用户突破10万', '行业资讯', '中心开发的健康知识图谱平台注册用户已突破10万人。', '<p>健康知识图谱平台自上线以来，已覆盖疾病防治、健康生活、公共卫生等6大领域，收录健康知识文章超过1000篇。</p><p>平台提供智能问答、健康知识检索、个性化推荐等功能，受到广大用户的欢迎。</p>', '中心技术部', 0, 0, 0, 'published', 2450, '2026-06-23 09:00:00'),
('省卫健委领导莅临中心调研指导', '中心动态', '四川省卫健委主要领导一行莅临中心调研指导工作。', '<p>调研组实地参观了中心的数据治理平台、智能分析平台和可视化平台，听取了中心建设运行情况的汇报。</p><p>委领导要求中心继续发挥技术优势，为全省卫生健康事业高质量发展提供有力支撑。</p>', '中心办公室', 0, 0, 0, 'published', 780, '2026-06-21 15:00:00'),
('中心与重庆医科大学签署战略合作协议', '中心动态', '中心与重庆医科大学签署战略合作协议，深化成渝健康大数据合作。', '<p>双方将在健康大数据标准研制、数据资源共享、人才培养等方面开展深入合作，共同推动成渝地区健康大数据产业发展。</p><p>此次合作是落实成渝双城经济圈建设国家战略的具体举措。</p>', '中心办公室', 0, 0, 0, 'published', 1120, '2026-06-18 11:30:00'),
('可视化平台新增多款健康数据大屏模板', '技术前沿', '中心可视化平台新增多项健康数据大屏模板，降低可视化搭建门槛。', '<p>新增的模板涵盖了公共卫生监测、医院运营分析、疾病流行趋势、健康管理看板等多个场景。</p><p>用户可通过拖拽式编辑快速搭建专业的数据大屏，无需编写代码。</p>', '中心技术部', 0, 0, 0, 'published', 1450, '2026-06-16 10:00:00'),
('2026年上半年中心工作成果显著', '中心动态', '2026年上半年，中心在平台建设、科研项目、标准编制等方面取得了一系列重要成果。', '<p>上半年，中心新增数据源接入32个，累计数据条目突破60万条；承担国家级科研项目2项、省级项目5项；发布团体标准3项；申请发明专利8项。</p><p>中心将继续保持良好发展势头，力争下半年再创佳绩。</p>', '中心办公室', 1, 0, 0, 'published', 1890, '2026-06-14 08:00:00');

-- 通知公告测试数据（11条）
INSERT INTO `cms_notice` (`title`, `content`, `level`, `is_top`, `status`, `publish_time`) VALUES
('关于2026年暑假工作安排的通知', '<p>根据中心统一安排，2026年暑假放假时间为7月20日至8月20日。请各部门做好假期值班安排和安全检查工作。</p>', 'important', 1, 'published', '2026-07-10 08:00:00'),
('中心数据平台维护升级公告', '<p>兹定于2026年7月15日00:00至06:00对数据平台进行维护升级，届时部分服务将暂时无法访问，请各位用户提前做好工作安排。</p>', 'urgent', 1, 'published', '2026-07-08 10:00:00'),
('关于开展2026年度科研项目申报的通知', '<p>2026年度中心科研项目申报工作现已启动，请各部门组织相关人员积极申报。申报截止日期为2026年8月31日。</p>', 'normal', 0, 'published', '2026-07-01 09:00:00'),
('新入职员工培训通知', '<p>2026年第三季度新入职员工培训将于7月22日至7月24日在中心会议室举行，请各部门通知相关人员按时参加。</p>', 'normal', 0, 'published', '2026-06-28 14:00:00'),
('关于征集健康大数据典型案例的通知', '<p>为总结推广健康大数据应用经验，现面向各单位征集健康大数据典型案例，优秀案例将编入《2026健康大数据应用案例集》。</p>', 'normal', 0, 'published', '2026-06-25 11:00:00'),
('关于2026年国庆节放假安排的通知', '<p>根据国务院办公厅通知精神，中心2026年国庆节放假安排如下：10月1日至7日放假调休，共7天。请各部门做好节前安全检查和假期值班工作。</p>', 'important', 0, 'published', '2026-09-25 08:00:00'),
('中心网站系统升级公告', '<p>为进一步提升网站服务质量和用户体验，中心定于2026年8月20日22:00至8月21日06:00对门户网站系统进行升级维护，届时网站将暂停访问。</p>', 'normal', 0, 'published', '2026-08-18 10:00:00'),
('关于开展数据安全自查工作的通知', '<p>根据上级部门要求，中心决定开展2026年度数据安全自查工作。请各部门于8月15日前完成自查并提交自查报告。</p>', 'urgent', 1, 'published', '2026-08-01 09:00:00'),
('2026年中秋节福利发放通知', '<p>中秋佳节将至，中心工会将为全体教职工发放中秋节福利，请各部门于9月10日前到办公室领取。</p>', 'normal', 0, 'published', '2026-09-05 14:00:00'),
('中心学术委员会2026年度会议通知', '<p>中心学术委员会2026年度会议定于10月15日在中心会议室召开，请各位委员准时参加。</p>', 'normal', 0, 'published', '2026-10-08 11:00:00'),
('关于征集2027年度科研课题建议的通知', '<p>为做好2027年度中心科研课题立项工作，现面向各部门征集科研课题建议。请各部门于11月30日前将课题建议书提交至科研管理部。</p>', 'important', 0, 'published', '2026-11-01 08:00:00');

-- 卫生政策测试数据（10条）
INSERT INTO `cms_policy` (`title`, `description`, `content`, `tag`, `source`, `publish_time`, `status`) VALUES
('"健康中国2030"规划纲要', '中共中央、国务院印发的《"健康中国2030"规划纲要》，是推进健康中国建设的宏伟蓝图和行动纲领。', '<p>规划纲要明确了健康中国建设的目标任务：到2030年，人均预期寿命达到79岁，重大慢性病过早死亡率降低30%，健康产业规模显著扩大。</p>', '国家政策', '国务院', '2026-06-15', 'published'),
('医疗健康大数据安全管理办法', '国家卫健委发布的《医疗健康大数据安全管理办法》，规范医疗健康数据的采集、存储、使用和共享。', '<p>办法明确了医疗健康大数据的分类分级保护制度，对敏感个人健康信息实行重点保护。</p><p>要求数据使用单位建立数据安全管理制度，定期开展安全评估。</p>', '部门规章', '国家卫健委', '2026-05-20', 'published'),
('关于促进互联网+医疗健康发展的意见', '国务院办公厅印发《关于促进"互联网+医疗健康"发展的意见》，推动互联网与医疗健康深度融合。', '<p>意见提出允许依托医疗机构发展互联网医院，支持在线开具处方，推动远程医疗服务覆盖所有医联体。</p>', '国家政策', '国务院办公厅', '2026-04-10', 'published'),
('四川省健康医疗大数据应用管理办法', '四川省卫健委发布的地方性健康医疗大数据管理规范。', '<p>办法结合四川实际，对健康医疗大数据的采集汇聚、开放共享、开发利用等环节进行了详细规定。</p>', '地方政策', '四川省卫健委', '2026-03-01', 'published'),
('数据安全法实施条例', '国务院发布的《数据安全法实施条例》，对数据安全法的具体实施进行详细规定。', '<p>实施条例进一步明确了数据分类分级保护制度的具体要求，规定了重要数据目录的制定程序和更新机制。</p><p>条例还增加了对数据跨境传输的合规要求，明确了相关法律责任。</p>', '国家政策', '国务院', '2026-07-01', 'published'),
('四川省健康医疗大数据共享管理办法', '四川省人民政府办公厅印发的《四川省健康医疗大数据共享管理办法（试行）》。', '<p>办法明确了健康医疗大数据的共享原则、共享范围、共享流程和安全管理要求。</p><p>办法提出建立省级健康医疗大数据共享平台，实现全省医疗机构间的数据互联互通。</p>', '地方政策', '四川省政府办公厅', '2026-06-15', 'published'),
('互联网诊疗监管细则（试行）', '国家卫健委发布的互联网诊疗监管细则，进一步规范互联网诊疗活动。', '<p>细则对互联网诊疗的准入条件、诊疗范围、病历管理、数据安全等方面提出了明确要求。</p><p>细则强调互联网诊疗必须依托实体医疗机构开展，严禁AI自动生成处方。</p>', '指导意见', '国家卫健委', '2026-05-30', 'published'),
('健康医疗大数据标准体系表（2026版）', '国家卫生健康委统计信息中心发布的健康医疗大数据标准体系表更新版本。', '<p>标准体系表包含基础类标准、数据类标准、技术类标准、管理类标准、应用类标准五大类，共计200余项标准。</p><p>新版标准体系表增加了人工智能、区块链等新兴技术的标准内容。</p>', '技术标准', '国家卫健委统计信息中心', '2026-05-01', 'published'),
('个人信息保护合规审计办法', '国家网信办发布的《个人信息保护合规审计办法》，要求数据处理者定期开展合规审计。', '<p>办法规定，处理超过100万人个人信息的处理者，应当每年至少开展一次合规审计。</p><p>审计内容包括个人信息的收集、存储、使用、加工、传输等全生命周期活动。</p>', '管理办法', '国家网信办', '2026-04-20', 'published'),
('成都市健康医疗大数据产业发展规划', '成都市人民政府印发的健康医疗大数据产业发展三年行动计划。', '<p>规划提出，到2028年成都将建成2-3个健康医疗大数据产业集聚区，培育50家以上骨干企业，产业规模达到500亿元。</p><p>规划明确了数据资源汇聚、技术创新突破、应用场景拓展、产业生态培育四个方面重点任务。</p>', '地方政策', '成都市人民政府', '2026-03-15', 'published');

-- 知识库文章测试数据
INSERT INTO `cms_knowledge_article` (`category_id`, `title`, `summary`, `content`, `status`, `view_count`, `publish_time`) VALUES
(1, '高血压的预防与管理', '高血压是最常见的慢性病之一，本文详细介绍高血压的预防措施和日常管理方法。', '<p>高血压是指以体循环动脉血压持续升高为主要特征的慢性病，是心脑血管病最主要的危险因素。</p><p>预防高血压要做到：限盐减重、规律运动、戒烟限酒、保持心理平衡。</p>', 'published', 5680, '2026-07-01 09:00:00'),
(1, '糖尿病早期症状识别', '了解糖尿病的早期症状，有助于早发现、早治疗，避免并发症的发生。', '<p>糖尿病早期症状包括：多饮、多食、多尿、体重减轻（三多一少）。此外还可能出现视力模糊、手脚麻木等症状。</p><p>建议45岁以上人群每年进行一次血糖检测。</p>', 'published', 4320, '2026-06-20 10:00:00'),
(1, '冠心病防治知识科普', '冠心病是危害人类健康的主要疾病之一，掌握防治知识至关重要。', '<p>冠心病全称冠状动脉粥样硬化性心脏病，是由于冠状动脉血管发生动脉粥样硬化病变而引起血管腔狭窄或阻塞，造成心肌缺血、缺氧或坏死而导致的心脏病。</p><p>防治冠心病需要控制血压、血脂、血糖，保持健康生活方式。</p>', 'published', 3210, '2026-06-10 14:00:00'),
(2, '合理膳食搭配指南', '科学合理的膳食搭配是健康生活的基础，本文为您介绍日常饮食搭配原则。', '<p>合理膳食应遵循食物多样、谷类为主的原则。每天摄入的食物应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等。</p><p>建议每人每天摄入12种以上食物，每周25种以上。</p>', 'published', 7890, '2026-06-15 08:30:00'),
(2, '运动健身的正确方式', '科学运动是保持健康的重要手段，本文介绍运动健身的注意事项和方法。', '<p>成年人每周应进行至少150分钟中等强度有氧运动，或75分钟高等强度有氧运动。</p><p>运动前要做好热身，运动后做好拉伸，循序渐进，避免运动损伤。</p>', 'published', 6540, '2026-05-20 10:00:00'),
(3, '常见传染病预防指南', '了解常见传染病的传播途径和预防措施，保护自己和家人的健康。', '<p>常见传染病包括流感、肺结核、病毒性肝炎等。预防传染病要注意个人卫生，勤洗手、多通风、戴口罩。</p><p>接种疫苗是预防传染病最经济有效的手段。</p>', 'published', 9870, '2026-05-10 09:00:00'),
(3, '疫苗接种常见问题解答', '关于疫苗接种的常见疑问，本文为您一一解答。', '<p>疫苗是预防控制疾病最有效的手段之一。我国目前实施国家免疫规划，为适龄儿童免费提供多种疫苗。</p><p>成年人也应根据自身情况接种流感疫苗、肺炎疫苗、HPV疫苗等。</p>', 'published', 5430, '2026-04-15 11:00:00'),
(4, '孕期营养与保健指南', '孕期科学营养和保健对母婴健康至关重要，本文为孕妈妈提供全面指导。', '<p>孕期营养应遵循均衡、全面、适量的原则。孕期应增加蛋白质、钙、铁、叶酸等营养素的摄入。</p><p>孕期还应保持适度运动，定期产检，保持良好的心态。</p>', 'published', 8760, '2026-04-01 08:00:00'),
(5, '老年人健康管理要点', '随着年龄增长，老年人需要更加关注自身健康管理，本文介绍管理要点。', '<p>老年人应定期进行健康体检，重点关注血压、血糖、血脂等指标。</p><p>合理饮食、适度运动、保持社交活动、按时服药是老年人健康管理的关键。</p>', 'published', 6540, '2026-03-20 09:30:00'),
(6, '压力管理与心理健康', '长期压力会影响身心健康，学会科学的压力管理方法非常重要。', '<p>压力管理的方法包括：规律作息、适度运动、正念冥想、社交支持等。</p><p>当出现持续的情绪低落、焦虑不安等症状时，应及时寻求专业心理帮助。</p>', 'published', 7890, '2026-03-10 10:00:00');
