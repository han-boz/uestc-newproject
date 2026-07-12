package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.Notice;
import com.healthinnova.portal.mapper.NoticeMapper;
import com.healthinnova.portal.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * 通知公告服务实现
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public IPage<Notice> getPublishedPage(Integer pageNum, Integer pageSize) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getStatus, "published")
                .orderByDesc(Notice::getIsTop)
                .orderByDesc(Notice::getPublishTime);
        return page(page, wrapper);
    }

}
