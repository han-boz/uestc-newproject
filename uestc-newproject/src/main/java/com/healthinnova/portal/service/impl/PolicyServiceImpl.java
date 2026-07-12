package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.Policy;
import com.healthinnova.portal.mapper.PolicyMapper;
import com.healthinnova.portal.service.PolicyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 卫生政策服务实现
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements PolicyService {

    @Override
    public IPage<Policy> getPublishedPage(Integer pageNum, Integer pageSize, String tag) {
        Page<Policy> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Policy> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Policy::getStatus, "published");

        if (StringUtils.hasText(tag)) {
            wrapper.eq(Policy::getTag, tag);
        }

        wrapper.orderByDesc(Policy::getPublishTime);
        return page(page, wrapper);
    }

}
