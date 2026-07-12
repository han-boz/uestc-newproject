package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.KnowledgeArticle;
import com.healthinnova.portal.entity.KnowledgeCategory;
import com.healthinnova.portal.mapper.KnowledgeArticleMapper;
import com.healthinnova.portal.mapper.KnowledgeCategoryMapper;
import com.healthinnova.portal.service.KnowledgeCategoryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 知识库分类服务实现
 */
@Service
public class KnowledgeCategoryServiceImpl extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory>
        implements KnowledgeCategoryService {

    private final KnowledgeArticleMapper articleMapper;

    public KnowledgeCategoryServiceImpl(KnowledgeArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public List<Map<String, Object>> getCategoryListWithArticleInfo() {
        LambdaQueryWrapper<KnowledgeCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(KnowledgeCategory::getSortOrder);
        List<KnowledgeCategory> categories = list(wrapper);

        // 按 name 去重（保留每组第一条），防止数据库重复数据导致显示重复
        Set<String> seen = new HashSet<>();
        List<KnowledgeCategory> deduplicated = categories.stream()
                .filter(c -> seen.add(c.getName()))
                .collect(Collectors.toList());

        List<Map<String, Object>> result = new ArrayList<>();
        for (KnowledgeCategory category : deduplicated) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", category.getId());
            map.put("name", category.getName());
            map.put("icon", category.getIcon());
            map.put("sortOrder", category.getSortOrder());

            // 统计文章数
            LambdaQueryWrapper<KnowledgeArticle> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(KnowledgeArticle::getCategoryId, category.getId())
                    .eq(KnowledgeArticle::getStatus, "published");
            long articleCount = articleMapper.selectCount(articleWrapper);
            map.put("articleCount", articleCount);

            // 最新文章标题
            LambdaQueryWrapper<KnowledgeArticle> latestWrapper = new LambdaQueryWrapper<>();
            latestWrapper.eq(KnowledgeArticle::getCategoryId, category.getId())
                    .eq(KnowledgeArticle::getStatus, "published")
                    .orderByDesc(KnowledgeArticle::getCreateTime)
                    .last("LIMIT 1");
            KnowledgeArticle latest = articleMapper.selectOne(latestWrapper);
            map.put("latestTitle", latest != null ? latest.getTitle() : "");

            result.add(map);
        }
        return result;
    }

}
