package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 知识库文章表 cms_knowledge_article
 */
@Data
@TableName("cms_knowledge_article")
public class KnowledgeArticle implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属分类 ID */
    private Long categoryId;

    /** 文章标题 */
    private String title;

    /** 摘要 */
    private String summary;

    /** 正文（富文本 HTML） */
    private String content;

    /** 浏览次数 */
    private Integer viewCount;

    /** 状态 */
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
