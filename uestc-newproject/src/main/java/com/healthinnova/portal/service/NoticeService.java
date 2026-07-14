package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.Notice;

public interface NoticeService extends IService<Notice> {

    /**
     * 分页查询已发布公告
     */
    IPage<Notice> getPublishedPage(Integer pageNum, Integer pageSize);

}
