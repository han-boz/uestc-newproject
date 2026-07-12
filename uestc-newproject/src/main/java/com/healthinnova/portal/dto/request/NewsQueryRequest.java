package com.healthinnova.portal.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新闻查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NewsQueryRequest extends PageRequest {

    /** 新闻分类筛选 */
    private String category;

    /** 标题关键词搜索 */
    private String keyword;

}
