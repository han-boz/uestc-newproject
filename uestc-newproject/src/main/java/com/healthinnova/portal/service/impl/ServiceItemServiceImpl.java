package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.ServiceItem;
import com.healthinnova.portal.mapper.ServiceItemMapper;
import com.healthinnova.portal.service.ServiceItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemServiceImpl extends ServiceImpl<ServiceItemMapper, ServiceItem> implements ServiceItemService {

    @Override
    public List<ServiceItem> getEnabledList() {
        LambdaQueryWrapper<ServiceItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ServiceItem::getEnabled, 1)
                .orderByAsc(ServiceItem::getSortOrder);
        return list(wrapper);
    }

}
