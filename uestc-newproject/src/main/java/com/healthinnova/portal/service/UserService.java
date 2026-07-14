package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.dto.response.LoginVO;
import com.healthinnova.portal.entity.User;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     */
    LoginVO login(String username, String password);

    /**
     * 根据用户名查询
     */
    User getByUsername(String username);

    /**
     * 修改密码（用户修改自己的密码）
     */
    void changePassword(String username, String oldPassword, String newPassword);

    /**
     * 管理员修改指定用户的密码（需验证原密码）
     */
    void adminChangePassword(String targetUsername, String oldPassword, String newPassword);

}
