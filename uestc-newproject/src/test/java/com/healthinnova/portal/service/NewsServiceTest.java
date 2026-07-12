package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.healthinnova.portal.dto.request.NewsQueryRequest;
import com.healthinnova.portal.entity.News;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 新闻 Service 层测试
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class NewsServiceTest {

    @Autowired
    private NewsService newsService;

    @BeforeEach
    void setUp() {
        // 程序化创建测试新闻，保证数据独立性
        News news1 = new News();
        news1.setTitle("测试新闻1");
        news1.setCategory("政策动态");
        news1.setSummary("摘要1");
        news1.setContent("<p>正文1</p>");
        news1.setStatus("published");
        news1.setViewCount(100);
        news1.setPublishTime(java.time.LocalDateTime.now());
        newsService.save(news1);

        News news2 = new News();
        news2.setTitle("测试新闻2");
        news2.setCategory("中心动态");
        news2.setSummary("摘要2");
        news2.setContent("<p>正文2</p>");
        news2.setStatus("published");
        news2.setViewCount(50);
        news2.setPublishTime(java.time.LocalDateTime.now());
        newsService.save(news2);

        News news3 = new News();
        news3.setTitle("测试新闻3");
        news3.setCategory("政策动态");
        news3.setSummary("摘要3");
        news3.setContent("<p>正文3</p>");
        news3.setStatus("draft");
        news3.setViewCount(0);
        news3.setPublishTime(java.time.LocalDateTime.now());
        newsService.save(news3);
    }

    @Test
    @DisplayName("分页查询已发布新闻")
    void testGetPublishedPage() {
        NewsQueryRequest request = new NewsQueryRequest();
        request.setPageNum(1);
        request.setPageSize(10);

        IPage<News> page = newsService.getPublishedPage(request);
        assertNotNull(page);
        assertEquals(2, page.getTotal()); // 只有 2 条 published
        assertEquals(2, page.getRecords().size());
    }

    @Test
    @DisplayName("按分类筛选新闻")
    void testGetPublishedPageByCategory() {
        NewsQueryRequest request = new NewsQueryRequest();
        request.setPageNum(1);
        request.setPageSize(10);
        request.setCategory("政策动态");

        IPage<News> page = newsService.getPublishedPage(request);
        assertNotNull(page);
        assertEquals(1, page.getTotal());
        assertEquals("测试新闻1", page.getRecords().get(0).getTitle());
    }

    @Test
    @DisplayName("获取新闻详情 - 浏览量自增")
    void testGetDetailIncrementViewCount() {
        // 直接通过查询条件定位新闻
        NewsQueryRequest req = new NewsQueryRequest();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setKeyword("测试新闻1");
        IPage<News> page = newsService.getPublishedPage(req);
        Long newsId = page.getRecords().get(0).getId();

        News news = newsService.getDetail(newsId);
        assertNotNull(news);
        assertEquals("测试新闻1", news.getTitle());
        // 初始 view_count=100，调用一次后应变为 101
        assertEquals(101, news.getViewCount());
    }

    @Test
    @DisplayName("获取不存在的新闻详情 - 抛出异常")
    void testGetDetailNotFound() {
        assertThrows(Exception.class, () -> newsService.getDetail(999L));
    }

}
