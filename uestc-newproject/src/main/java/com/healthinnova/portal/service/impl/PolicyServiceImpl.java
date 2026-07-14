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

@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements PolicyService {

    @Override
    public IPage<Policy> getPublishedPage(Integer pageNum, Integer pageSize, String tag, String keyword, String sortBy, String sortOrder) {
        Page<Policy> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Policy> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Policy::getStatus, "published");

        if (StringUtils.hasText(tag)) {
            wrapper.eq(Policy::getTag, tag);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Policy::getTitle, keyword);
        }

        applySort(wrapper, sortBy, sortOrder);
        return page(page, wrapper);
    }

    private void applySort(LambdaQueryWrapper<Policy> wrapper, String sortBy, String sortOrder) {
        if ("id".equals(sortBy)) {
            wrapper.orderByAsc(Policy::getId);
        } else if ("updateTime".equals(sortBy)) {
            wrapper.orderByDesc(Policy::getUpdateTime).orderByDesc(Policy::getId);
        } else {
            wrapper.orderByDesc(Policy::getPublishTime).orderByDesc(Policy::getId);
        }
    }

}
