package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.time.LocalDate;

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
