package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.util.List;

/**
 * 服务目录 VO
 */
@Data
public class ServiceItemVO {

    private Long id;

    private String name;

    private String description;

    private String icon;

    private String color;

    private List<String> features;

    private Integer sortOrder;

    private String linkUrl;

    private Boolean enabled;

}
