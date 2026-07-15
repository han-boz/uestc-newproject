package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.KnowledgeArticle;

public interface KnowledgeArticleService extends IService<KnowledgeArticle> {

    /**
     * 按分类分页查询文章（支持搜索和排序）
     */
    IPage<KnowledgeArticle> getPageByCategory(Long categoryId, Integer pageNum, Integer pageSize, String keyword, String sortBy, String sortOrder);

    /**
     * 获取文章详情并增加浏览次数
     */
    KnowledgeArticle getDetail(Long id);

}
