package com.healthinnova.portal.controller.admin;

import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.entity.About;
import com.healthinnova.portal.service.AboutService;
import org.springframework.web.bind.annotation.*;

/**
 * 后台 - 关于我们管理 Controller
 */
@RestController
@RequestMapping("/api/v1/admin/about")
public class AdminAboutController {

    private final AboutService aboutService;

    public AdminAboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public Result<About> get() {
        return Result.ok(aboutService.getCurrent());
    }

    @PutMapping
    public Result<?> update(@RequestBody About about) {
        About current = aboutService.getCurrent();
        if (current.getId() != null) {
            about.setId(current.getId());
            aboutService.updateById(about);
        } else {
            aboutService.save(about);
        }
        return Result.ok();
    }

}
