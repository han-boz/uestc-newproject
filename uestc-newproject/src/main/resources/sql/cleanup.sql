-- =============================================
-- 数据库清理脚本
-- 用途：清除所有重复数据，确保每人数据库一致
-- 运行方式：mysql -u root -p < src/main/resources/sql/cleanup.sql
-- 安全：可重复运行，不会破坏已有数据
-- =============================================

USE health_portal;

-- ===== 1. 新闻：保留每个标题第一条 =====
DELETE n1 FROM cms_news n1
INNER JOIN cms_news n2
WHERE n1.title = n2.title AND n1.id > n2.id;

-- ===== 2. 公告：保留每个标题第一条 =====
DELETE n1 FROM cms_notice n1
INNER JOIN cms_notice n2
WHERE n1.title = n2.title AND n1.id > n2.id;

-- ===== 3. 政策：保留每个标题第一条 =====
DELETE n1 FROM cms_policy n1
INNER JOIN cms_policy n2
WHERE n1.title = n2.title AND n1.id > n2.id;

-- ===== 4. 服务：保留每个名称第一条 =====
DELETE s1 FROM cms_service s1
INNER JOIN cms_service s2
WHERE s1.name = s2.name AND s1.id > s2.id;

-- ===== 5. 关于我们：只保留第一条 =====
DELETE FROM sys_about WHERE id > 1;

-- ===== 6. 知识文章：保留每个标题第一条 =====
DELETE a1 FROM cms_knowledge_article a1
INNER JOIN cms_knowledge_article a2
WHERE a1.title = a2.title AND a1.id > a2.id;

-- ===== 7. 用户日志/收藏：清空测试数据 =====
DELETE FROM sys_user_log;
DELETE FROM sys_favorite;

-- ===== 8. 重置自增ID =====
ALTER TABLE cms_news AUTO_INCREMENT = 21;
ALTER TABLE cms_notice AUTO_INCREMENT = 12;
ALTER TABLE cms_policy AUTO_INCREMENT = 11;
ALTER TABLE cms_service AUTO_INCREMENT = 8;
ALTER TABLE cms_knowledge_article AUTO_INCREMENT = 11;
ALTER TABLE sys_about AUTO_INCREMENT = 2;

-- ===== 9. 验证结果 =====
SELECT '清理完成' AS '';
SELECT '新闻' AS 表名, COUNT(*) AS 条数 FROM cms_news
UNION ALL SELECT '公告', COUNT(*) FROM cms_notice
UNION ALL SELECT '政策', COUNT(*) FROM cms_policy
UNION ALL SELECT '服务', COUNT(*) FROM cms_service
UNION ALL SELECT '关于我们', COUNT(*) FROM sys_about
UNION ALL SELECT '知识分类', COUNT(*) FROM cms_knowledge_category
UNION ALL SELECT '知识文章', COUNT(*) FROM cms_knowledge_article;
