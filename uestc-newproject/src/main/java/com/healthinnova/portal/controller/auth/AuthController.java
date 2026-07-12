package com.healthinnova.portal.controller.auth;

import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.request.LoginRequest;
import com.healthinnova.portal.dto.response.LoginVO;
import com.healthinnova.portal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证 Controller
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        LoginVO loginVO = userService.login(request.getUsername(), request.getPassword());
        return Result.ok(loginVO);
    }

}
