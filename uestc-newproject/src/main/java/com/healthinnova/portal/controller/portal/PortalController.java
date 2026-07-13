package com.healthinnova.portal.controller.portal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.dto.response.AboutVO;
import com.healthinnova.portal.dto.response.MilestoneVO;
import com.healthinnova.portal.dto.response.StatisticsVO;
import com.healthinnova.portal.entity.About;
import com.healthinnova.portal.entity.Statistics;
import com.healthinnova.portal.service.AboutService;
import com.healthinnova.portal.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * 门户首页 Controller
 */
@RestController
@RequestMapping("/api/v1/portal")
public class PortalController {

    private final StatisticsService statisticsService;
    private final AboutService aboutService;
    private final ObjectMapper objectMapper;

    public PortalController(StatisticsService statisticsService, AboutService aboutService,
                            ObjectMapper objectMapper) {
        this.statisticsService = statisticsService;
        this.aboutService = aboutService;
        this.objectMapper = objectMapper;
    }

    /**
     * 获取首页统计数据
     */
    @GetMapping("/statistics")
    public Result<StatisticsVO> statistics() {
        Statistics stats = statisticsService.getCurrent();

        StatisticsVO vo = new StatisticsVO();
        vo.setDataSourceCount(stats.getDataSourceCount());
        vo.setDataEntryCount(stats.getDataEntryCount());
        vo.setAnalysisModelCount(stats.getAnalysisModelCount());
        vo.setAvailabilityRate(stats.getAvailabilityRate());
        vo.setUpdateTime(stats.getUpdateTime());

        return Result.ok(vo);
    }

    /**
     * 获取关于我们
     */
    @GetMapping("/about")
    public Result<AboutVO> about() {
        About about = aboutService.getCurrent();

        AboutVO vo = new AboutVO();
        vo.setCenterName(about.getCenterName());
        vo.setDescription(about.getDescription());
        vo.setOrganization(about.getOrganization());
        vo.setContactAddress(about.getContactAddress());
        vo.setContactPhone(about.getContactPhone());
        vo.setContactEmail(about.getContactEmail());
        vo.setWorkHours(about.getWorkHours());

        // 解析 JSON 发展历程
        try {
            List<MilestoneVO> milestones = objectMapper.readValue(
                    about.getMilestones(), new TypeReference<List<MilestoneVO>>() {});
            vo.setMilestones(milestones != null ? milestones : Collections.emptyList());
        } catch (Exception e) {
            vo.setMilestones(Collections.emptyList());
        }

        return Result.ok(vo);
    }

}
