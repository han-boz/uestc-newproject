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

    private LocalDateTime publishTime;

    private Boolean isTop;

    private String level;

}
