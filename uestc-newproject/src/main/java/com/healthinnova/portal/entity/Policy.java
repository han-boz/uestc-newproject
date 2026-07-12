package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 卫生政策表 cms_policy
 */
@Data
@TableName("cms_policy")
public class Policy implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 政策标题 */
    private String title;

    /** 简要描述 */
    private String description;

    /** 正文（富文本 HTML） */
    private String content;

    /** 标签 */
    private String tag;

    /** 发文单位 */
    private String source;

    /** 附件文件名 */
    private String fileName;

    /** 附件下载 URL */
    private String fileUrl;

    /** 发布日期 */
    private LocalDate publishTime;

    /** 状态 */
    private String status;

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
