package com.healthinnova.portal.dto.response;

import lombok.Data;

/**
 * 知识库分类 VO
 */
@Data
public class KnowledgeCategoryVO {

    private Long id;

    private String name;

    private String icon;

    /** 最新文章标题 */
    private String latestTitle;

    /** 文章数量 */
    private Long articleCount;

    private Integer sortOrder;

}
