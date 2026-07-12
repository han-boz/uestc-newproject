package com.healthinnova.portal.controller;

import com.healthinnova.portal.entity.News;
import com.healthinnova.portal.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 新闻 Controller 测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsService newsService;

    private Long newsId1;
    private Long newsId2;

    @BeforeEach
    void setUp() {
        News news1 = new News();
        news1.setTitle("测试新闻1");
        news1.setCategory("政策动态");
        news1.setSummary("摘要1");
        news1.setContent("<p>正文1</p>");
        news1.setStatus("published");
        news1.setViewCount(100);
        news1.setPublishTime(java.time.LocalDateTime.now());
        newsService.save(news1);
        newsId1 = news1.getId();

        News news2 = new News();
        news2.setTitle("测试新闻2");
        news2.setCategory("中心动态");
        news2.setSummary("摘要2");
        news2.setContent("<p>正文2</p>");
        news2.setStatus("published");
        news2.setViewCount(50);
        news2.setPublishTime(java.time.LocalDateTime.now());
        newsService.save(news2);
        newsId2 = news2.getId();
    }

    @Test
    @DisplayName("获取新闻列表 - 默认分页")
    void testListNews() throws Exception {
        mockMvc.perform(get("/api/v1/news/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.total", is(2)))
                .andExpect(jsonPath("$.data.list.length()", is(2)));
    }

    @Test
    @DisplayName("获取新闻列表 - 按分类筛选")
    void testListNewsByCategory() throws Exception {
        mockMvc.perform(get("/api/v1/news/list")
                        .param("category", "政策动态"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.total", is(1)));
    }

    @Test
    @DisplayName("获取新闻列表 - 按关键词搜索")
    void testListNewsByKeyword() throws Exception {
        mockMvc.perform(get("/api/v1/news/list")
                        .param("keyword", "测试新闻1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.total", is(1)));
    }

    @Test
    @DisplayName("获取新闻详情 - 成功")
    void testGetNewsDetail() throws Exception {
        mockMvc.perform(get("/api/v1/news/detail/" + newsId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.title", is("测试新闻1")))
                .andExpect(jsonPath("$.data.viewCount", is(101))); // 浏览量 +1
    }

    @Test
    @DisplayName("获取新闻详情 - 不存在返回 404")
    void testGetNewsDetailNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/news/detail/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(404)));
    }

}
