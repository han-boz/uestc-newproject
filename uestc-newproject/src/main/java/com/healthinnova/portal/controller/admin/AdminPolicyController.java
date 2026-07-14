package com.healthinnova.portal.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.entity.Policy;
import com.healthinnova.portal.service.PolicyService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 后台 - 政策管理 Controller
 */
@RestController
@RequestMapping("/api/v1/admin/policy")
public class AdminPolicyController {

    private final PolicyService policyService;

    public AdminPolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/list")
    public Result<PageResult<Policy>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "8") Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String sortBy,
                                            @RequestParam(defaultValue = "desc") String sortOrder) {
        Page<Policy> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Policy> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Policy::getTitle, keyword);
        applySortPolicy(wrapper, sortBy, sortOrder);
        IPage<Policy> result = policyService.page(page, wrapper);
        return Result.ok(PageResult.of(result));
    }

    private void applySortPolicy(LambdaQueryWrapper<Policy> wrapper, String sortBy, String sortOrder) {
        boolean asc = "asc".equalsIgnoreCase(sortOrder);
        if ("id".equals(sortBy)) {
            wrapper.orderByAsc(Policy::getId);
        } else if ("updateTime".equals(sortBy)) {
            wrapper.orderByDesc(Policy::getUpdateTime).orderByDesc(Policy::getId);
        } else {
            wrapper.orderByDesc(Policy::getPublishTime).orderByDesc(Policy::getId);
        }
    }

    @GetMapping("/{id}")
    public Result<Policy> detail(@PathVariable Long id) {
        return Result.ok(policyService.getById(id));
    }

    @PostMapping
    public Result<?> create(@RequestBody Policy policy) {
        policyService.save(policy);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Policy policy) {
        policy.setId(id);
        policyService.updateById(policy);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        policyService.removeById(id);
        return Result.ok();
    }

}
