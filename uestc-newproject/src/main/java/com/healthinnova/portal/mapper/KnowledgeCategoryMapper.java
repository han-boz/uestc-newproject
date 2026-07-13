package com.healthinnova.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthinnova.portal.entity.KnowledgeCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 知识库分类 Mapper
 */
public interface KnowledgeCategoryMapper extends BaseMapper<KnowledgeCategory> {

    /**
     * 查询分类列表，附带文章数和最新文章标题（1 次 SQL 代替 N+1 次查询）
     */
    @Select("SELECT c.id, c.name, c.icon, c.sort_order, " +
            "COUNT(a.id) AS article_count, " +
            "(SELECT a2.title FROM cms_knowledge_article a2 " +
            " WHERE a2.category_id = c.id AND a2.status = 'published' " +
            " ORDER BY a2.create_time DESC LIMIT 1) AS latest_title " +
            "FROM cms_knowledge_category c " +
            "LEFT JOIN cms_knowledge_article a ON a.category_id = c.id AND a.status = 'published' " +
            "GROUP BY c.id, c.name, c.icon, c.sort_order " +
            "ORDER BY c.sort_order")
    List<Map<String, Object>> selectCategoryListWithArticleInfo();

}
