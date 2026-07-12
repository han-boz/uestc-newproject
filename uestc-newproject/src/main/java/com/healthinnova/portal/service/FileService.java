package com.healthinnova.portal.service;

import com.healthinnova.portal.dto.response.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 */
public interface FileService {

    /**
     * 上传文件
     */
    FileUploadVO upload(MultipartFile file);

}
