package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户操作日志 VO
 */
@Data
public class UserLogVO {

    private Long id;

    private String action;

    private String target;

    private String ipAddress;

    private LocalDateTime createTime;

}
