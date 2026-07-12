package com.healthinnova.portal.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求参数
 */
@Data
public class PageRequest implements Serializable {

    /** 页码，默认 1 */
    private Integer pageNum = 1;

    /** 每页条数，默认 10 */
    private Integer pageSize = 10;

}
