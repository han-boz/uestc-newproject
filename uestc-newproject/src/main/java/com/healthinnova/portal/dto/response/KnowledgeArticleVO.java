package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库文章 VO
 */
@Data
public class KnowledgeArticleVO {

    private Long id;

    private String title;

    private String summary;

    private Long categoryId;

    private String categoryName;

    private Integer viewCount;

    private LocalDateTime createTime;

}
