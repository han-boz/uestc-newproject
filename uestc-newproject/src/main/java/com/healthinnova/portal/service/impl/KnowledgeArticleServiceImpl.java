package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.entity.KnowledgeArticle;
import com.healthinnova.portal.mapper.KnowledgeArticleMapper;
import com.healthinnova.portal.service.KnowledgeArticleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class KnowledgeArticleServiceImpl extends ServiceImpl<KnowledgeArticleMapper, KnowledgeArticle>
        implements KnowledgeArticleService {

    @Override
    public IPage<KnowledgeArticle> getPageByCategory(Long categoryId, Integer pageNum, Integer pageSize, String keyword, String sortBy, String sortOrder) {
        Page<KnowledgeArticle> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgeArticle::getCategoryId, categoryId)
                .eq(KnowledgeArticle::getStatus, "published");
        if (StringUtils.hasText(keyword)) {
            wrapper.like(KnowledgeArticle::getTitle, keyword);
        }
        applySort(wrapper, sortBy, sortOrder);
        return page(page, wrapper);
    }

    @Override
    public KnowledgeArticle getDetail(Long id) {
        KnowledgeArticle article = getById(id);
        if (article == null) {
            throw new ServiceException(GlobalErrorCodeConstants.NOT_FOUND);
        }
        article.setViewCount(article.getViewCount() + 1);
        updateById(article);
        return article;
    }

    private void applySort(LambdaQueryWrapper<KnowledgeArticle> wrapper, String sortBy, String sortOrder) {
        if ("id".equals(sortBy)) {
            wrapper.orderByAsc(KnowledgeArticle::getId);
        } else if ("updateTime".equals(sortBy)) {
            wrapper.orderByDesc(KnowledgeArticle::getUpdateTime).orderByDesc(KnowledgeArticle::getId);
        } else {
            wrapper.orderByDesc(KnowledgeArticle::getPublishTime).orderByDesc(KnowledgeArticle::getId);
        }
    }

}
