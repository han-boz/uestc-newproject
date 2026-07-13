package com.healthinnova.portal.config.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.healthinnova.portal.entity.User;
import com.healthinnova.portal.entity.UserLog;
import com.healthinnova.portal.mapper.UserMapper;
import com.healthinnova.portal.mapper.UserLogMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * 操作日志 AOP 切面 —— 自动记录管理员的后台写操作
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private final UserLogMapper userLogMapper;
    private final UserMapper userMapper;

    public LogAspect(UserLogMapper userLogMapper, UserMapper userMapper) {
        this.userLogMapper = userLogMapper;
        this.userMapper = userMapper;
    }

    /**
     * 拦截所有 admin controller 的写操作（增/删/改）
     */
    @AfterReturning("execution(* com.healthinnova.portal.controller.admin.*.*(..)) && " +
            "(@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void logAdminOperation(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes()).getRequest();

            // 提取操作信息
            String method = request.getMethod();
            String path = request.getRequestURI();
            String ip = getClientIp(request);
            String username = getCurrentUsername();

            if (username == null) {
                return; // 未登录不记录
            }

            // 操作描述
            String action;
            if ("POST".equals(method)) {
                action = "新增";
            } else if ("PUT".equals(method)) {
                action = "修改";
            } else if ("DELETE".equals(method)) {
                action = "删除";
            } else {
                return;
            }

            // 提取目标类型（从 URL 路径中取）
            String target = extractTarget(path);

            // 获取用户 ID
            LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
            userQuery.eq(User::getUsername, username);
            User user = userMapper.selectOne(userQuery);
            if (user == null) return;

            // 保存日志
            UserLog logEntry = new UserLog();
            logEntry.setUserId(user.getId());
            logEntry.setUsername(username);
            logEntry.setAction(action);
            logEntry.setTarget(target);
            logEntry.setIpAddress(ip);
            logEntry.setCreateTime(LocalDateTime.now());
            userLogMapper.insert(logEntry);

            log.debug("操作日志: {} {} {} (IP: {})", username, action, target, ip);
        } catch (Exception e) {
            log.warn("操作日志记录失败: {}", e.getMessage());
        }
    }

    private String extractTarget(String path) {
        // /api/v1/admin/news/123 → news
        // /api/v1/admin/notice → notice
        // /api/v1/admin/knowledge/category → knowledge/category
        String prefix = "/api/v1/admin/";
        if (path.startsWith(prefix)) {
            return path.substring(prefix.length()).replaceAll("/\\d+$", "");
        }
        return path;
    }

    private String getCurrentUsername() {
        try {
            org.springframework.security.core.Authentication auth =
                    org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()
                    && !"anonymousUser".equals(auth.getName())) {
                return auth.getName();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip != null ? ip.split(",")[0].trim() : "unknown";
    }
}
