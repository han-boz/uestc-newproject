package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.entity.About;
import com.healthinnova.portal.mapper.AboutMapper;
import com.healthinnova.portal.service.AboutService;
import org.springframework.stereotype.Service;

/**
 * 关于我们服务实现
 */
@Service
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements AboutService {

    @Override
    public About getCurrent() {
        return list().stream().findFirst().orElse(new About());
    }

}
