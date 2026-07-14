package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.KnowledgeCategory;

import java.util.List;
import java.util.Map;

public interface KnowledgeCategoryService extends IService<KnowledgeCategory> {

    /**
     * 获取所有分类（含文章计数和最新文章标题）
     */
    List<Map<String, Object>> getCategoryListWithArticleInfo();

}
