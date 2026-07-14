package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.ServiceItem;

import java.util.List;

public interface ServiceItemService extends IService<ServiceItem> {

    /**
     * 获取所有已启用的服务（按排序字段排列）
     */
    List<ServiceItem> getEnabledList();

}
