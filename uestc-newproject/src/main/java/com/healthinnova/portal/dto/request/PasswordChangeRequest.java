package com.healthinnova.portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 修改密码请求 DTO
 */
@Data
public class PasswordChangeRequest {

    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    /** 管理员修改他人密码时指定目标用户名 */
    private String targetUsername;
}
