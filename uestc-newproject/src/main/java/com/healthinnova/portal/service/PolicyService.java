package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.Policy;

/**
 * 卫生政策服务
 */
public interface PolicyService extends IService<Policy> {

    /**
     * 分页查询已发布政策
     */
    IPage<Policy> getPublishedPage(Integer pageNum, Integer pageSize, String tag, String keyword, String sortBy, String sortOrder);

}
