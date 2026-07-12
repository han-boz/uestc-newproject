package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 平台统计数据表 sys_statistics
 */
@Data
@TableName("sys_statistics")
public class Statistics implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 数据源接入数量 */
    private Integer dataSourceCount;

    /** 数据条目总数 */
    private Long dataEntryCount;

    /** 分析模型数量 */
    private Integer analysisModelCount;

    /** 平台可用率(%) */
    private BigDecimal availabilityRate;

    /** 数据更新时间 */
    private LocalDateTime updateTime;

}
