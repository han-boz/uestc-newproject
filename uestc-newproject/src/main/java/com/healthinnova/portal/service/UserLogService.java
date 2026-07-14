package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.UserLog;

public interface UserLogService extends IService<UserLog> {

    /**
     * 分页查询用户日志
     */
    IPage<UserLog> getPageByUserId(Long userId, Integer pageNum, Integer pageSize);

}
