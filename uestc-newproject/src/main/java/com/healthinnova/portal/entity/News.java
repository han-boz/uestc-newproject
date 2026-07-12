package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 新闻表 cms_news
 */
@Data
@TableName("cms_news")
public class News implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 标题 */
    private String title;

    /** 分类 */
    private String category;

    /** 摘要 */
    private String summary;

    /** 正文内容（富文本 HTML） */
    private String content;

    /** 封面图 URL */
    private String coverImage;

    /** 来源 */
    private String source;

    /** 是否热门 */
    private Integer isHot;

    /** 是否新增 */
    private Integer isNew;

    /** 是否置顶 */
    private Integer isTop;

    /** 状态：draft/published/offline */
    private String status;

    /** 浏览次数 */
    private Integer viewCount;

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
