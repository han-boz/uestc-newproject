package com.healthinnova.portal.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.entity.ServiceItem;
import com.healthinnova.portal.service.ServiceItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 后台 - 服务管理 Controller
 */
@RestController
@RequestMapping("/api/v1/admin/service")
public class AdminServiceController {

    private final ServiceItemService serviceItemService;

    public AdminServiceController(ServiceItemService serviceItemService) {
        this.serviceItemService = serviceItemService;
    }

    @GetMapping("/list")
    public Result<List<ServiceItem>> list() {
        LambdaQueryWrapper<ServiceItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(ServiceItem::getSortOrder);
        return Result.ok(serviceItemService.list(wrapper));
    }

    @GetMapping("/{id}")
    public Result<ServiceItem> detail(@PathVariable Long id) {
        return Result.ok(serviceItemService.getById(id));
    }

    @PostMapping
    public Result<?> create(@RequestBody ServiceItem item) {
        serviceItemService.save(item);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody ServiceItem item) {
        item.setId(id);
        serviceItemService.updateById(item);
        return Result.ok();
    }

    @PutMapping("/sort")
    public Result<?> updateSort(@RequestBody List<Map<String, Object>> sortList) {
        for (Map<String, Object> entry : sortList) {
            Long id = ((Number) entry.get("id")).longValue();
            Integer sortOrder = ((Number) entry.get("sortOrder")).intValue();
            ServiceItem item = new ServiceItem();
            item.setId(id);
            item.setSortOrder(sortOrder);
            serviceItemService.updateById(item);
        }
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        serviceItemService.removeById(id);
        return Result.ok();
    }

}
