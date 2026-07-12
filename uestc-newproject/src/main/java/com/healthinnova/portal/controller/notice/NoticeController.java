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

}
