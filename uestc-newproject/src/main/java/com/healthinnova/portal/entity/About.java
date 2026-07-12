package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 关于我们信息表 sys_about
 */
@Data
@TableName("sys_about")
public class About implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 中心名称 */
    private String centerName;

    /** 中心简介（富文本 HTML） */
    private String description;

    /** 组织架构（富文本 HTML） */
    private String organization;

    /** 发展历程 JSON: [{year, title, desc}] */
    private String milestones;

    /** 联系地址 */
    private String contactAddress;

    /** 联系电话 */
    private String contactPhone;

    /** 联系邮箱 */
    private String contactEmail;

    /** 工作时间 */
    private String workHours;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
