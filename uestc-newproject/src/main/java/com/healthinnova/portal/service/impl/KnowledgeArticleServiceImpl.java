package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.KnowledgeArticle;
import com.healthinnova.portal.mapper.KnowledgeArticleMapper;
import com.healthinnova.portal.service.KnowledgeArticleService;
import org.springframework.stereotype.Service;

/**
 * 知识库文章服务实现
 */
@Service
public class KnowledgeArticleServiceImpl extends ServiceImpl<KnowledgeArticleMapper, KnowledgeArticle>
        implements KnowledgeArticleService {

    @Override
    public IPage<KnowledgeArticle> getPageByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
        Page<KnowledgeArticle> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgeArticle::getCategoryId, categoryId)
                .eq(KnowledgeArticle::getStatus, "published")
                .orderByDesc(KnowledgeArticle::getPublishTime);
        return page(page, wrapper);
    }

}
