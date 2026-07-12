package com.healthinnova.portal.controller.admin;

import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.response.FileUploadVO;
import com.healthinnova.portal.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 后台 - 文件上传 Controller
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminUploadController {

    private final FileService fileService;

    public AdminUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 通用文件上传
     */
    @PostMapping("/upload")
    public Result<FileUploadVO> upload(@RequestParam("file") MultipartFile file) {
        FileUploadVO vo = fileService.upload(file);
        return Result.ok(vo);
    }

}
