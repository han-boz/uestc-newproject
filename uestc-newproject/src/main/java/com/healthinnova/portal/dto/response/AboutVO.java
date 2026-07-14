package com.healthinnova.portal.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AboutVO {

    private String centerName;

    private String description;

    private String organization;

    private List<MilestoneVO> milestones;

    private String contactAddress;

    private String contactPhone;

    private String contactEmail;

    private String workHours;

}
