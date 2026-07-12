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

INSERT INTO `sys_about` (`center_name`, `description`, `organization`)
VALUES ('健康大数据应用创新研发中心', '<p>中心简介...</p>', '<p>组织架构...</p>');

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

-- 新闻测试数据
INSERT INTO `cms_news` (`title`, `category`, `summary`, `content`, `source`, `is_hot`, `is_new`, `is_top`, `status`, `view_count`, `publish_time`) VALUES
('国家卫健委发布2026年医疗大数据应用新规划', '政策动态', '国家卫健委近日印发《2026-2028年医疗健康大数据应用发展规划》，明确了未来三年医疗大数据发展的重点方向和目标任务。', '<h3>政策亮点</h3><p>国家卫健委近日印发《2026-2028年医疗健康大数据应用发展规划》，明确了未来三年医疗大数据发展的重点方向和目标任务。</p><p>规划提出，到2028年，全国二级以上医疗机构数据互联互通率将达到90%以上，建成3-5个国家级医疗健康大数据中心。</p>', '国家卫健委', 1, 1, 1, 'published', 3280, '2026-07-10 10:00:00'),
('中心成功举办2026年健康大数据学术研讨会', '中心动态', '健康大数据应用创新研发中心主办的"2026健康大数据学术研讨会"于7月8日在成都圆满落幕。', '<p>本次研讨会汇聚了来自全国各地的200余名专家学者，围绕医疗健康大数据的前沿技术、应用实践和产业发展进行了深入交流。</p><p>会议共收到学术论文156篇，评选出优秀论文30篇。</p>', '中心办公室', 1, 1, 1, 'published', 2156, '2026-07-08 09:00:00'),
('人工智能辅助诊断系统通过国家医疗器械认证', '行业资讯', '由中心参与研发的AI辅助诊断系统正式通过国家药品监督管理局三类医疗器械认证。', '<p>该AI辅助诊断系统基于深度学习技术，可对CT影像、病理切片等进行智能分析，辅助医生进行疾病诊断。</p><p>临床试验表明，该系统对肺结节的检出率达到98.5%，高于传统人工阅片水平。</p>', '科技日报', 1, 0, 0, 'published', 4520, '2026-07-05 14:30:00'),
('健康医疗大数据标准体系建设取得阶段性成果', '政策动态', '国家卫生健康委统计信息中心发布了健康医疗大数据标准体系建设的阶段性成果报告。', '<p>报告显示，我国已累计制定健康医疗大数据国家标准87项、行业标准156项，涵盖数据采集、存储、共享、安全等各个环节。</p><p>标准体系的完善为医疗健康大数据的有序流通和合规使用提供了重要保障。</p>', '国家卫健委', 0, 0, 0, 'published', 1890, '2026-07-03 11:00:00'),
('中心与华西医院签署战略合作协议', '中心动态', '健康大数据应用创新研发中心与四川大学华西医院正式签署战略合作协议。', '<p>双方将在医疗健康大数据领域开展深度合作，重点围绕临床数据治理、智能分析模型构建、真实世界研究等方面进行联合攻关。</p><p>此次合作将整合中心的平台技术优势与华西医院的临床资源优势，推动科研成果转化应用。</p>', '中心办公室', 1, 0, 0, 'published', 3120, '2026-07-01 16:00:00'),
('区块链技术在医疗数据共享中的应用探索', '技术前沿', '区块链技术为医疗健康数据的安全共享提供了全新的技术路径和解决方案。', '<p>区块链的分布式账本、智能合约、共识机制等特性，天然适合解决医疗数据共享中的信任问题和权限控制问题。</p><p>目前，国内已有多个省市开展了基于区块链的医疗数据共享试点项目。</p>', '健康报', 0, 0, 0, 'published', 1350, '2026-06-28 09:30:00'),
('隐私计算技术在健康大数据领域应用指南发布', '技术前沿', '中国信通院联合多家单位发布了《隐私计算健康大数据应用指南（2026）》。', '<p>指南系统梳理了联邦学习、安全多方计算、差分隐私等隐私计算技术在医疗健康场景中的适用性和实施路径。</p><p>指南的发布将为医疗机构和科技企业在隐私保护前提下开展数据价值挖掘提供重要参考。</p>', '中国信通院', 0, 0, 0, 'published', 980, '2026-06-25 15:00:00');

-- 通知公告测试数据
INSERT INTO `cms_notice` (`title`, `content`, `level`, `is_top`, `status`, `publish_time`) VALUES
('关于2026年暑假工作安排的通知', '<p>根据中心统一安排，2026年暑假放假时间为7月20日至8月20日。请各部门做好假期值班安排和安全检查工作。</p>', 'important', 1, 'published', '2026-07-10 08:00:00'),
('中心数据平台维护升级公告', '<p>兹定于2026年7月15日00:00至06:00对数据平台进行维护升级，届时部分服务将暂时无法访问，请各位用户提前做好工作安排。</p>', 'urgent', 1, 'published', '2026-07-08 10:00:00'),
('关于开展2026年度科研项目申报的通知', '<p>2026年度中心科研项目申报工作现已启动，请各部门组织相关人员积极申报。申报截止日期为2026年8月31日。</p>', 'normal', 0, 'published', '2026-07-01 09:00:00'),
('新入职员工培训通知', '<p>2026年第三季度新入职员工培训将于7月22日至7月24日在中心会议室举行，请各部门通知相关人员按时参加。</p>', 'normal', 0, 'published', '2026-06-28 14:00:00'),
('关于征集健康大数据典型案例的通知', '<p>为总结推广健康大数据应用经验，现面向各单位征集健康大数据典型案例，优秀案例将编入《2026健康大数据应用案例集》。</p>', 'normal', 0, 'published', '2026-06-25 11:00:00');

-- 卫生政策测试数据
INSERT INTO `cms_policy` (`title`, `description`, `content`, `tag`, `source`, `publish_time`, `status`) VALUES
('"健康中国2030"规划纲要', '中共中央、国务院印发的《"健康中国2030"规划纲要》，是推进健康中国建设的宏伟蓝图和行动纲领。', '<p>规划纲要明确了健康中国建设的目标任务：到2030年，人均预期寿命达到79岁，重大慢性病过早死亡率降低30%，健康产业规模显著扩大。</p>', '国家政策', '国务院', '2026-06-15', 'published'),
('医疗健康大数据安全管理办法', '国家卫健委发布的《医疗健康大数据安全管理办法》，规范医疗健康数据的采集、存储、使用和共享。', '<p>办法明确了医疗健康大数据的分类分级保护制度，对敏感个人健康信息实行重点保护。</p><p>要求数据使用单位建立数据安全管理制度，定期开展安全评估。</p>', '部门规章', '国家卫健委', '2026-05-20', 'published'),
('关于促进互联网+医疗健康发展的意见', '国务院办公厅印发《关于促进"互联网+医疗健康"发展的意见》，推动互联网与医疗健康深度融合。', '<p>意见提出允许依托医疗机构发展互联网医院，支持在线开具处方，推动远程医疗服务覆盖所有医联体。</p>', '国家政策', '国务院办公厅', '2026-04-10', 'published'),
('四川省健康医疗大数据应用管理办法', '四川省卫健委发布的地方性健康医疗大数据管理规范。', '<p>办法结合四川实际，对健康医疗大数据的采集汇聚、开放共享、开发利用等环节进行了详细规定。</p>', '地方政策', '四川省卫健委', '2026-03-01', 'published');

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
