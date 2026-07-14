package com.healthinnova.portal.service.impl;

import com.healthinnova.portal.common.Constants;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.dto.response.FileUploadVO;
import com.healthinnova.portal.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.path:uploads}")
    private String uploadPath;

    @Override
    public FileUploadVO upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "上传文件不能为空");
        }

        // 获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        }

        // 验证文件类型
        validateFileType(extension, file.getSize());

        // 生成存储路径
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String newFilename = UUID.randomUUID().toString() + "." + extension;
        String relativePath = "/" + dateDir + "/" + newFilename;

        // 保存文件
        try {
            Path targetDir = Paths.get(uploadPath, dateDir);
            Files.createDirectories(targetDir);
            Path targetPath = Paths.get(uploadPath + relativePath);
            file.transferTo(targetPath.toFile());
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), "文件上传失败");
        }

        String fileUrl = "/uploads" + relativePath;
        return new FileUploadVO(originalFilename, fileUrl, file.getSize());
    }

    /**
     * 验证文件类型和大小
     */
    private void validateFileType(String extension, long fileSize) {
        boolean isImage = Arrays.asList(Constants.ALLOWED_IMAGE_TYPES).contains(extension);
        boolean isDoc = Arrays.asList(Constants.ALLOWED_DOC_TYPES).contains(extension);

        if (!isImage && !isDoc) {
            throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),
                    "不支持的文件类型: " + extension);
        }

        if (isImage && fileSize > Constants.MAX_IMAGE_SIZE) {
            throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "图片大小不能超过 5MB");
        }

        if (isDoc && fileSize > Constants.MAX_DOC_SIZE) {
            throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "文档大小不能超过 20MB");
        }
    }

}
