package com.healthinnova.portal.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.dto.request.PasswordChangeRequest;
import com.healthinnova.portal.dto.response.UserLogVO;
import com.healthinnova.portal.dto.response.UserVO;
import com.healthinnova.portal.entity.User;
import com.healthinnova.portal.entity.UserLog;
import com.healthinnova.portal.security.SecurityUtils;
import com.healthinnova.portal.service.UserLogService;
import com.healthinnova.portal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 个人中心 Controller
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserLogService userLogService;

    public UserController(UserService userService, UserLogService userLogService) {
        this.userService = userService;
        this.userLogService = userLogService;
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/current")
    public Result<UserVO> current() {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.getByUsername(username);

        if (user == null) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }

        UserVO vo = new UserVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setAvatar(user.getAvatar());
        vo.setDepartment(user.getDepartment());
        vo.setRole(user.getRole());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setLastLoginTime(user.getLastLoginTime());

        return Result.ok(vo);
    }

    /**
     * 获取用户操作日志
     */
    @GetMapping("/activities")
    public Result<PageResult<UserLogVO>> activities(@RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.getByUsername(username);

        if (user == null) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }

        IPage<UserLog> page = userLogService.getPageByUserId(user.getId(), pageNum, pageSize);

        PageResult<UserLogVO> pageResult = PageResult.of(page.convert(log -> {
            UserLogVO vo = new UserLogVO();
            vo.setId(log.getId());
            vo.setAction(log.getAction());
            vo.setTarget(log.getTarget());
            vo.setIpAddress(log.getIpAddress());
            vo.setCreateTime(log.getCreateTime());
            return vo;
        }));

        return Result.ok(pageResult);
    }

    /**
     * 修改密码（普通用户只能改自己的密码；管理员可指定 targetUsername 改他人密码）
     */
    @PutMapping("/password")
    public Result<Void> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User currentUser = userService.getByUsername(currentUsername);

        // 如果指定了 targetUsername 且当前用户是管理员，则修改他人的密码
        if (request.getTargetUsername() != null && !request.getTargetUsername().isEmpty()
                && !request.getTargetUsername().equals(currentUsername)) {
            if (!"admin".equals(currentUser.getRole())) {
                throw new ServiceException(GlobalErrorCodeConstants.FORBIDDEN.getCode(), "仅管理员可修改他人密码");
            }
            userService.adminChangePassword(request.getTargetUsername(), request.getOldPassword(), request.getNewPassword());
        } else {
            // 普通用户修改自己的密码
            userService.changePassword(currentUsername, request.getOldPassword(), request.getNewPassword());
        }

        return Result.ok(null);
    }

}
