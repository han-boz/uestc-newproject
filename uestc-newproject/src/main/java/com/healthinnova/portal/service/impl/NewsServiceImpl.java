package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.dto.request.NewsQueryRequest;
import com.healthinnova.portal.entity.News;
import com.healthinnova.portal.mapper.NewsMapper;
import com.healthinnova.portal.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public IPage<News> getPublishedPage(NewsQueryRequest request) {
        Page<News> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(News::getStatus, "published");

        if (StringUtils.hasText(request.getCategory())) {
            wrapper.eq(News::getCategory, request.getCategory());
        }
        if (StringUtils.hasText(request.getKeyword())) {
            wrapper.like(News::getTitle, request.getKeyword());
        }

        // 动态排序
        applySort(wrapper, request.getSortBy(), request.getSortOrder());
        return page(page, wrapper);
    }

    /**
     * 动态排序：置顶优先 → 所选字段 → ID
     */
    private void applySort(LambdaQueryWrapper<News> wrapper, String sortBy, String sortOrder) {
        wrapper.orderByDesc(News::getIsTop);
        if ("id".equals(sortBy)) {
            wrapper.orderByAsc(News::getId);
        } else if ("updateTime".equals(sortBy)) {
            wrapper.orderByDesc(News::getUpdateTime).orderByDesc(News::getId);
        } else {
            wrapper.orderByDesc(News::getPublishTime).orderByDesc(News::getId);
        }
    }

    @Override
    public News getDetail(Long id) {
        News news = getById(id);
        if (news == null) {
            throw new ServiceException(GlobalErrorCodeConstants.NOT_FOUND);
        }
        // 浏览量 +1
        news.setViewCount(news.getViewCount() + 1);
        updateById(news);
        return news;
    }

}
