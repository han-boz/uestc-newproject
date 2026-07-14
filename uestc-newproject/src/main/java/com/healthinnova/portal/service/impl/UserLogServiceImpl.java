package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.UserLog;
import com.healthinnova.portal.mapper.UserLogMapper;
import com.healthinnova.portal.service.UserLogService;
import org.springframework.stereotype.Service;

@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {

    @Override
    public IPage<UserLog> getPageByUserId(Long userId, Integer pageNum, Integer pageSize) {
        Page<UserLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UserLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserLog::getUserId, userId)
                .orderByDesc(UserLog::getCreateTime);
        return page(page, wrapper);
    }

}
