package com.healthinnova.portal.controller.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.response.ServiceItemVO;
import com.healthinnova.portal.entity.ServiceItem;
import com.healthinnova.portal.service.ServiceItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用中心 / 服务目录 Controller
 */
@RestController
@RequestMapping("/api/v1/service")
public class ServiceController {

    private final ServiceItemService serviceItemService;
    private final ObjectMapper objectMapper;

    public ServiceController(ServiceItemService serviceItemService, ObjectMapper objectMapper) {
        this.serviceItemService = serviceItemService;
        this.objectMapper = objectMapper;
    }

    /**
     * 获取已启用的服务列表
     */
    @GetMapping("/list")
    public Result<List<ServiceItemVO>> list() {
        List<ServiceItem> items = serviceItemService.getEnabledList();

        List<ServiceItemVO> vos = items.stream().map(item -> {
            ServiceItemVO vo = new ServiceItemVO();
            vo.setId(item.getId());
            vo.setName(item.getName());
            vo.setDescription(item.getDescription());
            vo.setIcon(item.getIcon());
            vo.setColor(item.getColor());
            vo.setSortOrder(item.getSortOrder());
            vo.setLinkUrl(item.getLinkUrl());
            vo.setEnabled(item.getEnabled() == 1);
            try {
                vo.setFeatures(objectMapper.readValue(
                        item.getFeatures(), new TypeReference<List<String>>() {}));
            } catch (Exception e) {
                vo.setFeatures(Collections.emptyList());
            }
            return vo;
        }).collect(Collectors.toList());

        return Result.ok(vos);
    }

}
