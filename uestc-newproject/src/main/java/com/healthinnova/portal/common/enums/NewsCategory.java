package com.healthinnova.portal.common.enums;

import lombok.Getter;

/**
 * 新闻分类枚举
 */
@Getter
public enum NewsCategory {

    POLICY_TREND("政策动态", "政策相关新闻"),
    CENTER_NEWS("中心动态", "中心内部新闻"),
    TECH_PROGRESS("技术进展", "技术研发进展"),
    DATA_RELEASE("数据发布", "数据发布相关"),
    HONOR_QUALIFICATION("荣誉资质", "荣誉资质相关");

    private final String label;
    private final String description;

    NewsCategory(String label, String description) {
        this.label = label;
        this.description = description;
    }

}
