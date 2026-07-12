package com.healthinnova.portal.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.entity.Notice;
import com.healthinnova.portal.service.NoticeService;
import org.springframework.web.bind.annotation.*;

/**
 * 后台 - 公告管理 Controller
 */
@RestController
@RequestMapping("/api/v1/admin/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    public AdminNoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/list")
    public Result<PageResult<Notice>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Notice::getIsTop).orderByDesc(Notice::getCreateTime);
        IPage<Notice> result = noticeService.page(page, wrapper);
        return Result.ok(PageResult.of(result));
    }

    @GetMapping("/{id}")
    public Result<Notice> detail(@PathVariable Long id) {
        return Result.ok(noticeService.getById(id));
    }

    @PostMapping
    public Result<?> create(@RequestBody Notice notice) {
        noticeService.save(notice);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        noticeService.updateById(notice);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        noticeService.removeById(id);
        return Result.ok();
    }

}
