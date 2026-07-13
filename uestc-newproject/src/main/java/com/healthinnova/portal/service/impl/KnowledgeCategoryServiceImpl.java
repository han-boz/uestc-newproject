package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.KnowledgeCategory;
import com.healthinnova.portal.mapper.KnowledgeCategoryMapper;
import com.healthinnova.portal.service.KnowledgeCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 知识库分类服务实现
 */
@Service
public class KnowledgeCategoryServiceImpl extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory>
        implements KnowledgeCategoryService {

    private final KnowledgeCategoryMapper knowledgeCategoryMapper;

    public KnowledgeCategoryServiceImpl(KnowledgeCategoryMapper knowledgeCategoryMapper) {
        this.knowledgeCategoryMapper = knowledgeCategoryMapper;
    }

    @Override
    public List<Map<String, Object>> getCategoryListWithArticleInfo() {
        // 一次 SQL 查询带 LEFT JOIN + 子查询，替代原 N+1 次循环查询
        return knowledgeCategoryMapper.selectCategoryListWithArticleInfo();
    }

}
