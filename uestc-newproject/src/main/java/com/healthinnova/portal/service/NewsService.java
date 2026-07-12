package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.dto.request.NewsQueryRequest;
import com.healthinnova.portal.entity.News;

/**
 * 新闻服务
 */
public interface NewsService extends IService<News> {

    /**
     * 分页查询新闻（前台）
     */
    IPage<News> getPublishedPage(NewsQueryRequest request);

    /**
     * 获取新闻详情并增加浏览量
     */
    News getDetail(Long id);

}
