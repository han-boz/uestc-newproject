package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {

    private Long userId;

    private String username;

    private String realName;

    private String avatar;

    private String department;

    private String role;

    private String email;

    private String phone;

    private LocalDateTime lastLoginTime;

}
