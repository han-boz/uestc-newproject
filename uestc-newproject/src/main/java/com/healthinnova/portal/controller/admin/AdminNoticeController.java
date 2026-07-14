package com.healthinnova.portal.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.entity.Notice;
import com.healthinnova.portal.service.NoticeService;
import org.springframework.util.StringUtils;
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
                                            @RequestParam(defaultValue = "8") Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String sortBy,
                                            @RequestParam(defaultValue = "desc") String sortOrder) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Notice::getTitle, keyword);
        applySortNotice(wrapper, sortBy, sortOrder);
        IPage<Notice> result = noticeService.page(page, wrapper);
        return Result.ok(PageResult.of(result));
    }

    private void applySortNotice(LambdaQueryWrapper<Notice> wrapper, String sortBy, String sortOrder) {
        boolean asc = "asc".equalsIgnoreCase(sortOrder);
        wrapper.orderByDesc(Notice::getIsTop);
        if ("id".equals(sortBy)) {
            wrapper.orderByAsc(Notice::getId);
        } else if ("updateTime".equals(sortBy)) {
            wrapper.orderByDesc(Notice::getUpdateTime).orderByDesc(Notice::getId);
        } else {
            wrapper.orderByDesc(Notice::getPublishTime).orderByDesc(Notice::getId);
        }
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
