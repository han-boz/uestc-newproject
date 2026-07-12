package com.healthinnova.portal.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.entity.News;
import com.healthinnova.portal.service.NewsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 后台 - 新闻管理 Controller
 */
@RestController
@RequestMapping("/api/v1/admin/news")
public class AdminNewsController {

    private final NewsService newsService;

    public AdminNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /** 新闻列表（含所有状态） */
    @GetMapping("/list")
    public Result<PageResult<News>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(required = false) String category,
                                         @RequestParam(required = false) String keyword) {
        Page<News> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(category)) wrapper.eq(News::getCategory, category);
        if (StringUtils.hasText(keyword)) wrapper.like(News::getTitle, keyword);
        wrapper.orderByDesc(News::getIsTop).orderByDesc(News::getCreateTime);
        IPage<News> result = newsService.page(page, wrapper);
        return Result.ok(PageResult.of(result));
    }

    @GetMapping("/{id}")
    public Result<News> detail(@PathVariable Long id) {
        return Result.ok(newsService.getById(id));
    }

    @PostMapping
    public Result<?> create(@RequestBody News news) {
        newsService.save(news);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody News news) {
        news.setId(id);
        newsService.updateById(news);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        newsService.removeById(id);
        return Result.ok();
    }

    @PutMapping("/{id}/top")
    public Result<?> toggleTop(@PathVariable Long id) {
        News news = newsService.getById(id);
        if (news != null) {
            news.setIsTop(news.getIsTop() == 1 ? 0 : 1);
            newsService.updateById(news);
        }
        return Result.ok();
    }

    @PutMapping("/{id}/status")
    public Result<?> changeStatus(@PathVariable Long id, @RequestParam String status) {
        News news = newsService.getById(id);
        if (news != null) {
            news.setStatus(status);
            newsService.updateById(news);
        }
        return Result.ok();
    }

}
