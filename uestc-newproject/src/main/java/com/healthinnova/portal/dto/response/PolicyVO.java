package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * 卫生政策 VO
 */
@Data
public class PolicyVO {

    private Long id;

    private String title;

    private String description;

    private String tag;

    private LocalDate publishTime;

    private String source;

    private String fileUrl;

    private String fileName;

}
