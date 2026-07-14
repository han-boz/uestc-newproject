package com.healthinnova.portal.controller.policy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.healthinnova.portal.common.PageResult;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.response.PolicyVO;
import com.healthinnova.portal.entity.Policy;
import com.healthinnova.portal.service.PolicyService;
import org.springframework.web.bind.annotation.*;

/**
 * 卫生政策 Controller
 */
@RestController
@RequestMapping("/api/v1/policy")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * 政策分页列表
     */
    @GetMapping("/list")
    public Result<PageResult<PolicyVO>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "8") Integer pageSize,
                                             @RequestParam(required = false) String tag,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String sortBy,
                                             @RequestParam(defaultValue = "desc") String sortOrder) {
        IPage<Policy> page = policyService.getPublishedPage(pageNum, pageSize, tag, keyword, sortBy, sortOrder);

        PageResult<PolicyVO> pageResult = PageResult.of(page.convert(policy -> {
            PolicyVO vo = new PolicyVO();
            vo.setId(policy.getId());
            vo.setTitle(policy.getTitle());
            vo.setDescription(policy.getDescription());
            vo.setTag(policy.getTag());
            vo.setPublishTime(policy.getPublishTime());
            vo.setSource(policy.getSource());
            vo.setFileUrl(policy.getFileUrl());
            vo.setFileName(policy.getFileName());
            return vo;
        }));

        return Result.ok(pageResult);
    }

}
