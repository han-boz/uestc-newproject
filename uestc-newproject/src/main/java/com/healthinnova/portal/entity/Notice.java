package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知公告表 cms_notice
 */
@Data
@TableName("cms_notice")
public class Notice implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 标题 */
    private String title;

    /** 正文（富文本 HTML） */
    private String content;

    /** 重要程度：normal/important/urgent */
    private String level;

    /** 是否置顶 */
    private Integer isTop;

    /** 状态：draft/published/offline */
    private String status;

    /** 发布时间 */
    private LocalDateTime publishTime;

    /** 创建人 ID */
    private Long createBy;

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
