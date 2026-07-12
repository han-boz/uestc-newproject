package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 平台统计 VO
 */
@Data
public class StatisticsVO {

    private Integer dataSourceCount;

    private Long dataEntryCount;

    private Integer analysisModelCount;

    private BigDecimal availabilityRate;

    private LocalDateTime updateTime;

}
