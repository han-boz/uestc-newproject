package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.About;

/**
 * 关于我们服务
 */
public interface AboutService extends IService<About> {

    /**
     * 获取关于我们信息
     */
    About getCurrent();

}
