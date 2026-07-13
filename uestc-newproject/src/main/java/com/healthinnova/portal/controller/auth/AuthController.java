package com.healthinnova.portal.controller.auth;

import com.healthinnova.portal.common.Constants;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.request.LoginRequest;
import com.healthinnova.portal.dto.response.LoginVO;
import com.healthinnova.portal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证 Controller
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    public AuthController(UserService userService,
                          ObjectProvider<RedisTemplate<String, Object>> redisTemplateProvider) {
        this.userService = userService;
        this.redisTemplate = redisTemplateProvider.getIfAvailable();
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        LoginVO loginVO = userService.login(request.getUsername(), request.getPassword());
        return Result.ok(loginVO);
    }

    /**
     * 用户退出登录（清除 Redis 中的 Token）
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(Constants.TOKEN_PREFIX)) {
            String token = bearerToken.substring(Constants.TOKEN_PREFIX.length());
            if (redisTemplate != null) {
                redisTemplate.delete(Constants.REDIS_TOKEN_PREFIX + token);
            }
        }
        return Result.ok(null);
    }

}
