package com.healthinnova.portal.controller.notice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.response.NoticeVO;
import com.healthinnova.portal.entity.Notice;
import com.healthinnova.portal.service.NoticeService;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告 Controller
 */
@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * 公告分页列表
     */
    @GetMapping("/list")
    public Result<PageResult<NoticeVO>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "4") Integer pageSize) {
        IPage<Notice> page = noticeService.getPublishedPage(pageNum, pageSize);

        PageResult<NoticeVO> pageResult = PageResult.of(page.convert(notice -> {
            NoticeVO vo = new NoticeVO();
            vo.setId(notice.getId());
            vo.setTitle(notice.getTitle());
            vo.setPublishTime(notice.getPublishTime());
            vo.setIsTop(notice.getIsTop() == 1);
            vo.setLevel(notice.getLevel());
            return vo;
        }));

        return Result.ok(pageResult);
    }

    /**
     * 公告详情
     */
    @GetMapping("/{id}")
    public Result<NoticeVO> detail(@PathVariable Long id) {
        Notice notice = noticeService.getById(id);
        if (notice == null) {
            return Result.fail(404, "公告不存在");
        }
        NoticeVO vo = new NoticeVO();
        vo.setId(notice.getId());
        vo.setTitle(notice.getTitle());
        vo.setContent(notice.getContent());
        vo.setLevel(notice.getLevel());
        vo.setIsTop(notice.getIsTop() == 1);
        vo.setPublishTime(notice.getPublishTime());
        return Result.ok(vo);
    }

}
