package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户表 sys_user
 */
@Data
@TableName("sys_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 登录用户名 */
    private String username;

    /** 密码（BCrypt 加密） */
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 头像 URL */
    private String avatar;

    /** 部门 */
    private String department;

    /** 角色：admin/editor/user */
    private String role;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 状态：1启用 0禁用 */
    private Integer status;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除：0正常 1已删除 */
    @TableLogic
    private Integer deleted;

}
