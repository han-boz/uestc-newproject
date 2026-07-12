package com.healthinnova.portal.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.entity.KnowledgeArticle;
import com.healthinnova.portal.entity.KnowledgeCategory;
import com.healthinnova.portal.service.KnowledgeArticleService;
import com.healthinnova.portal.service.KnowledgeCategoryService;
import org.springframework.web.bind.annotation.*;

/**
 * 后台 - 知识库管理 Controller
 */
@RestController
@RequestMapping("/api/v1/admin/knowledge")
public class AdminKnowledgeController {

    private final KnowledgeCategoryService categoryService;
    private final KnowledgeArticleService articleService;

    public AdminKnowledgeController(KnowledgeCategoryService categoryService,
                                     KnowledgeArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    // ===== 分类管理 =====

    @GetMapping("/categories")
    public Result<?> categories() {
        LambdaQueryWrapper<KnowledgeCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(KnowledgeCategory::getSortOrder);
        return Result.ok(categoryService.list(wrapper));
    }

    @GetMapping("/category/{id}")
    public Result<KnowledgeCategory> getCategory(@PathVariable Long id) {
        return Result.ok(categoryService.getById(id));
    }

    @PostMapping("/category")
    public Result<?> createCategory(@RequestBody KnowledgeCategory category) {
        categoryService.save(category);
        return Result.ok();
    }

    @PutMapping("/category/{id}")
    public Result<?> updateCategory(@PathVariable Long id, @RequestBody KnowledgeCategory category) {
        category.setId(id);
        categoryService.updateById(category);
        return Result.ok();
    }

    @DeleteMapping("/category/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        long count = articleService.count(new LambdaQueryWrapper<KnowledgeArticle>()
                .eq(KnowledgeArticle::getCategoryId, id));
        if (count > 0) {
            throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "该分类下还有文章，无法删除");
        }
        categoryService.removeById(id);
        return Result.ok();
    }

    // ===== 文章管理 =====

    @GetMapping("/articles")
    public Result<PageResult<KnowledgeArticle>> articles(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                          @RequestParam(required = false) Long categoryId) {
        Page<KnowledgeArticle> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) wrapper.eq(KnowledgeArticle::getCategoryId, categoryId);
        wrapper.orderByDesc(KnowledgeArticle::getCreateTime);
        IPage<KnowledgeArticle> result = articleService.page(page, wrapper);
        return Result.ok(PageResult.of(result));
    }

    @GetMapping("/article/{id}")
    public Result<KnowledgeArticle> getArticle(@PathVariable Long id) {
        return Result.ok(articleService.getById(id));
    }

    @PostMapping("/article")
    public Result<?> createArticle(@RequestBody KnowledgeArticle article) {
        articleService.save(article);
        return Result.ok();
    }

    @PutMapping("/article/{id}")
    public Result<?> updateArticle(@PathVariable Long id, @RequestBody KnowledgeArticle article) {
        article.setId(id);
        articleService.updateById(article);
        return Result.ok();
    }

    @DeleteMapping("/article/{id}")
    public Result<?> deleteArticle(@PathVariable Long id) {
        articleService.removeById(id);
        return Result.ok();
    }

}
