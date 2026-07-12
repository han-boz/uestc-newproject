package com.healthinnova.portal.common.enums;

import lombok.Getter;

/**
 * 公告重要程度枚举
 */
@Getter
public enum NoticeLevel {

    NORMAL("normal", "普通"),
    IMPORTANT("important", "重要"),
    URGENT("urgent", "紧急");

    private final String code;
    private final String label;

    NoticeLevel(String code, String label) {
        this.code = code;
        this.label = label;
    }

}
