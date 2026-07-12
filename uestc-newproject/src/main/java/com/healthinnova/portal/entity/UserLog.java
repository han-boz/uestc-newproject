package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户操作日志表 sys_user_log
 */
@Data
@TableName("sys_user_log")
public class UserLog implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户 ID */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 操作描述 */
    private String action;

    /** 操作对象 */
    private String target;

    /** 操作 IP */
    private String ipAddress;

    /** 操作时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
