package com.healthinnova.portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录响应 VO
 */
@Data
@AllArgsConstructor
public class LoginVO {

    /** JWT Token */
    private String token;

    /** Token 类型 */
    private String tokenType;

    /** 过期时间 */
    private LocalDateTime expireTime;

    /** 用户信息 */
    private UserVO user;

}
