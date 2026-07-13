package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知公告 VO
 */
@Data
public class NoticeVO {

    private Long id;

    private String title;

    /** 正文内容（仅在详情接口返回） */
    private String content;

    private LocalDateTime publishTime;

    private Boolean isTop;

    private String level;

}
