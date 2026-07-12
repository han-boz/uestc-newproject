package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.KnowledgeArticle;

/**
 * 知识库文章服务
 */
public interface KnowledgeArticleService extends IService<KnowledgeArticle> {

    /**
     * 按分类分页查询文章
     */
    IPage<KnowledgeArticle> getPageByCategory(Long categoryId, Integer pageNum, Integer pageSize);

}
