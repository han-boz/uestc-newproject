package com.healthinnova.portal.controller.knowledge;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.dto.response.KnowledgeArticleVO;
import com.healthinnova.portal.dto.response.KnowledgeCategoryVO;
import com.healthinnova.portal.entity.KnowledgeArticle;
import com.healthinnova.portal.service.KnowledgeArticleService;
import com.healthinnova.portal.service.KnowledgeCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 健康知识库 Controller
 */
@RestController
@RequestMapping("/api/v1/knowledge")
public class KnowledgeController {

    private final KnowledgeCategoryService categoryService;
    private final KnowledgeArticleService articleService;

    public KnowledgeController(KnowledgeCategoryService categoryService,
                               KnowledgeArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    /**
     * 获取分类列表
     */
    @GetMapping("/categories")
    public Result<List<KnowledgeCategoryVO>> categories() {
        List<Map<String, Object>> list = categoryService.getCategoryListWithArticleInfo();

        List<KnowledgeCategoryVO> vos = list.stream().map(map -> {
            KnowledgeCategoryVO vo = new KnowledgeCategoryVO();
            vo.setId((Long) map.get("id"));
            vo.setName((String) map.get("name"));
            vo.setIcon((String) map.get("icon"));
            vo.setLatestTitle((String) map.getOrDefault("latestTitle", ""));
            vo.setArticleCount((Long) map.getOrDefault("articleCount", 0L));
            vo.setSortOrder((Integer) map.getOrDefault("sortOrder", 0));
            return vo;
        }).collect(Collectors.toList());

        return Result.ok(vos);
    }

    /**
     * 按分类获取文章列表（支持搜索和排序）
     */
    @GetMapping("/articles")
    public Result<PageResult<KnowledgeArticleVO>> articles(@RequestParam Long categoryId,
                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "8") Integer pageSize,
                                                            @RequestParam(required = false) String keyword,
                                                            @RequestParam(required = false) String sortBy,
                                                            @RequestParam(defaultValue = "desc") String sortOrder) {
        IPage<KnowledgeArticle> page = articleService.getPageByCategory(categoryId, pageNum, pageSize, keyword, sortBy, sortOrder);

        PageResult<KnowledgeArticleVO> pageResult = PageResult.of(page.convert(article -> {
            KnowledgeArticleVO vo = new KnowledgeArticleVO();
            vo.setId(article.getId());
            vo.setTitle(article.getTitle());
            vo.setSummary(article.getSummary());
            vo.setCategoryId(article.getCategoryId());
            vo.setViewCount(article.getViewCount());
            vo.setCreateTime(article.getCreateTime());
            vo.setPublishTime(article.getPublishTime());
            return vo;
        }));

        return Result.ok(pageResult);
    }

    /**
     * 文章详情（浏览量增量更新）
     */
    @GetMapping("/article/{id}")
    public Result<KnowledgeArticleVO> articleDetail(@PathVariable Long id) {
        KnowledgeArticle article = articleService.getById(id);
        if (article == null) {
            throw new ServiceException(GlobalErrorCodeConstants.NOT_FOUND);
        }
        // 浏览量 +1（增量更新）
        articleService.lambdaUpdate()
                .setSql("view_count = view_count + 1")
                .eq(KnowledgeArticle::getId, id)
                .update();
        article.setViewCount(article.getViewCount() + 1);

        KnowledgeArticleVO vo = new KnowledgeArticleVO();
        vo.setId(article.getId());
        vo.setTitle(article.getTitle());
        vo.setSummary(article.getSummary());
        vo.setContent(article.getContent());
        vo.setCategoryId(article.getCategoryId());
        vo.setViewCount(article.getViewCount());
        vo.setCreateTime(article.getCreateTime());
        return Result.ok(vo);
    }

}
