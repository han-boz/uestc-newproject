package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.common.Constants;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.dto.response.LoginVO;
import com.healthinnova.portal.dto.response.UserVO;
import com.healthinnova.portal.entity.User;
import com.healthinnova.portal.mapper.UserMapper;
import com.healthinnova.portal.security.JwtTokenProvider;
import com.healthinnova.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginVO login(String username, String password) {
        // 用户名去前后空格（密码保持原样）
        username = username.trim();
        // 查询用户
        User user = getByUsername(username);
        if (user == null) {
            throw new ServiceException(GlobalErrorCodeConstants.LOGIN_ERROR);
        }

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ServiceException(GlobalErrorCodeConstants.LOGIN_ERROR);
        }

        // 检查用户状态
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new ServiceException(GlobalErrorCodeConstants.FORBIDDEN.getCode(), "账号已被禁用");
        }

        // 生成 Token
        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole());

        // Token 存入 Redis（如果可用）
        if (redisTemplate != null) {
            redisTemplate.opsForValue().set(
                    Constants.REDIS_TOKEN_PREFIX + token, user.getUsername(),
                    jwtTokenProvider.parseToken(token).getExpiration().getTime() - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS);
        }

        // 更新最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);

        // 构建 VO
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setRealName(user.getRealName());
        userVO.setAvatar(user.getAvatar());
        userVO.setDepartment(user.getDepartment());
        userVO.setRole(user.getRole());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setLastLoginTime(user.getLastLoginTime());

        Date expireDate = jwtTokenProvider.parseToken(token).getExpiration();
        LocalDateTime expireTime = expireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return new LoginVO(token, "Bearer", expireTime, userVO);
    }

    @Override
    public User getByUsername(String username) {
        username = username.trim();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username).last("LIMIT 1");
        return getOne(wrapper, false);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = getByUsername(username);
        if (user == null) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "原密码错误");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
    }

    @Override
    public void adminChangePassword(String targetUsername, String oldPassword, String newPassword) {
        // 管理员也需要验证原密码
        changePassword(targetUsername, oldPassword, newPassword);
    }

}
