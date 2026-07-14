package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLogVO {

    private Long id;

    private String action;

    private String target;

    private String ipAddress;

    private LocalDateTime createTime;

}
