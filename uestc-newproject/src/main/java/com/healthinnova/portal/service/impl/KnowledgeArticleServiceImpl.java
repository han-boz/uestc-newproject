package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.KnowledgeArticle;
import com.healthinnova.portal.mapper.KnowledgeArticleMapper;
import com.healthinnova.portal.service.KnowledgeArticleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 知识库文章服务实现
 */
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

    private void applySort(LambdaQueryWrapper<KnowledgeArticle> wrapper, String sortBy, String sortOrder) {
        boolean asc = "asc".equalsIgnoreCase(sortOrder);
        if ("id".equals(sortBy)) {
            wrapper.orderByAsc(KnowledgeArticle::getId);
        } else if ("updateTime".equals(sortBy)) {
            wrapper.orderByDesc(KnowledgeArticle::getUpdateTime).orderByDesc(KnowledgeArticle::getId);
        } else {
            wrapper.orderByDesc(KnowledgeArticle::getPublishTime).orderByDesc(KnowledgeArticle::getId);
        }
    }

}
