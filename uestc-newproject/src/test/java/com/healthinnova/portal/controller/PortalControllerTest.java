package com.healthinnova.portal.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 门户首页 Controller 测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PortalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("获取首页统计数据 - 成功")
    void testGetStatistics() throws Exception {
        mockMvc.perform(get("/api/v1/portal/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.dataSourceCount", is(128)))
                .andExpect(jsonPath("$.data.dataEntryCount", is(520000)));
    }

    @Test
    @DisplayName("获取关于我们信息 - 成功")
    void testGetAbout() throws Exception {
        mockMvc.perform(get("/api/v1/portal/about"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.centerName", is("健康大数据应用创新研发中心")));
    }

}
