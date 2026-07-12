package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.Statistics;

/**
 * 平台统计服务
 */
public interface StatisticsService extends IService<Statistics> {

    /**
     * 获取当前统计数据
     */
    Statistics getCurrent();

}
