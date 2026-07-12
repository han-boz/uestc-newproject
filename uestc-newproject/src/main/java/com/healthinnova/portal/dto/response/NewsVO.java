package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 新闻 VO
 */
@Data
public class NewsVO {

    private Long id;

    private String title;

    private String category;

    private String summary;

    private String content;

    private Boolean isHot;

    private Boolean isNew;

    private Boolean isTop;

    private String coverImage;

    private LocalDateTime publishTime;

    private String source;

    private Integer viewCount;

    /** 新闻详情中的附件列表 */
    private List<AttachmentVO> attachments;

}
