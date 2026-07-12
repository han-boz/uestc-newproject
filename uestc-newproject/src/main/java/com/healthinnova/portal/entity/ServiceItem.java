package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务目录表 cms_service
 */
@Data
@TableName("cms_service")
public class ServiceItem implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 服务名称 */
    private String name;

    /** 服务描述 */
    private String description;

    /** 图标标识 */
    private String icon;

    /** 主题色 */
    private String color;

    /** 功能特性列表 JSON */
    private String features;

    /** 跳转链接 */
    private String linkUrl;

    /** 排序序号 */
    private Integer sortOrder;

    /** 是否启用 */
    private Integer enabled;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;

}
