package com.healthinnova.portal.service;

import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.dto.response.LoginVO;
import com.healthinnova.portal.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户 Service 层测试
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // 使用程序化方式创建测试用户，避免 BCrypt 哈希不一致问题
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRealName("系统管理员");
        admin.setRole("admin");
        admin.setStatus(1);
        userService.save(admin);

        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setRealName("张三");
        user.setRole("user");
        user.setStatus(1);
        userService.save(user);
    }

    @Test
    @DisplayName("正确用户名密码 - 登录成功")
    void testLoginSuccess() {
        LoginVO loginVO = userService.login("admin", "admin123");
        assertNotNull(loginVO);
        assertNotNull(loginVO.getToken());
        assertEquals("Bearer", loginVO.getTokenType());
        assertEquals("admin", loginVO.getUser().getUsername());
        assertEquals("系统管理员", loginVO.getUser().getRealName());
    }

    @Test
    @DisplayName("错误密码 - 登录失败")
    void testLoginFailWrongPassword() {
        assertThrows(ServiceException.class, () -> userService.login("admin", "wrongpwd"));
    }

    @Test
    @DisplayName("不存在用户 - 登录失败")
    void testLoginFailUserNotFound() {
        assertThrows(ServiceException.class, () -> userService.login("nonexistent", "admin123"));
    }

    @Test
    @DisplayName("根据用户名查询用户")
    void testGetByUsername() {
        User user = userService.getByUsername("admin");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("系统管理员", user.getRealName());

        User notFound = userService.getByUsername("notexist");
        assertNull(notFound);
    }

}
