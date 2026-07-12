package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.Statistics;
import com.healthinnova.portal.mapper.StatisticsMapper;
import com.healthinnova.portal.service.StatisticsService;
import org.springframework.stereotype.Service;

/**
 * 平台统计服务实现
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {

    @Override
    public Statistics getCurrent() {
        // 返回第一条记录（设计上只有一条）
        return list().stream().findFirst().orElse(new Statistics());
    }

}
