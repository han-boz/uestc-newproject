package com.healthinnova.portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 文件上传 VO
 */
@Data
@AllArgsConstructor
public class FileUploadVO {

    private String fileName;

    private String fileUrl;

    private Long fileSize;

}
