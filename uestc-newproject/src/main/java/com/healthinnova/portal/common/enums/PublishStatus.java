package com.healthinnova.portal.common.enums;

import lombok.Getter;

/**
 * 发布状态枚举
 */
@Getter
public enum PublishStatus {

    DRAFT("draft", "草稿"),
    PUBLISHED("published", "已发布"),
    OFFLINE("offline", "已下架");

    private final String code;
    private final String label;

    PublishStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

}
