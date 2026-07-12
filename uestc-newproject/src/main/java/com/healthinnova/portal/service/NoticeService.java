package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.Notice;

/**
 * 通知公告服务
 */
public interface NoticeService extends IService<Notice> {

    /**
     * 分页查询已发布公告
     */
    IPage<Notice> getPublishedPage(Integer pageNum, Integer pageSize);

}
