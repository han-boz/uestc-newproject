package com.healthinnova.portal.controller.news;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.request.NewsQueryRequest;
import com.healthinnova.portal.dto.response.NewsVO;
import com.healthinnova.portal.entity.News;
import com.healthinnova.portal.service.NewsService;
import org.springframework.web.bind.annotation.*;

/**
 * 新闻中心 Controller
 */
@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * 新闻分页列表
     */
    @GetMapping("/list")
    public Result<PageResult<NewsVO>> list(NewsQueryRequest request) {
        IPage<News> page = newsService.getPublishedPage(request);

        PageResult<NewsVO> pageResult = PageResult.of(page.convert(news -> {
            NewsVO vo = new NewsVO();
            vo.setId(news.getId());
            vo.setTitle(news.getTitle());
            vo.setCategory(news.getCategory());
            vo.setSummary(news.getSummary());
            vo.setIsHot(news.getIsHot() == 1);
            vo.setIsNew(news.getIsNew() == 1);
            vo.setIsTop(news.getIsTop() == 1);
            vo.setCoverImage(news.getCoverImage());
            vo.setPublishTime(news.getPublishTime());
            vo.setSource(news.getSource());
            vo.setViewCount(news.getViewCount());
            return vo;
        }));

        return Result.ok(pageResult);
    }

    /**
     * 新闻详情
     */
    @GetMapping("/detail/{id}")
    public Result<NewsVO> detail(@PathVariable Long id) {
        News news = newsService.getDetail(id);

        NewsVO vo = new NewsVO();
        vo.setId(news.getId());
        vo.setTitle(news.getTitle());
        vo.setCategory(news.getCategory());
        vo.setContent(news.getContent());
        vo.setPublishTime(news.getPublishTime());
        vo.setSource(news.getSource());
        vo.setViewCount(news.getViewCount());

        return Result.ok(vo);
    }

}
