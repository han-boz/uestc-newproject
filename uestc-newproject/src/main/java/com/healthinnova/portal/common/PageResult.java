package com.healthinnova.portal.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应体
 *
 * @param <T> 列表数据类型
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Long pageNum;

    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 从 MyBatis-Plus 分页结果构建
     */
    public static <T> PageResult<T> of(IPage<T> page) {
        PageResult<T> result = new PageResult<>();
        result.total = page.getTotal();
        result.pageNum = page.getCurrent();
        result.pageSize = page.getSize();
        result.pages = page.getPages();
        result.list = page.getRecords();
        return result;
    }

    /**
     * 手动构建分页结果
     */
    public static <T> PageResult<T> of(List<T> list, Long total, Long pageNum, Long pageSize) {
        PageResult<T> result = new PageResult<>();
        result.total = total;
        result.pageNum = pageNum;
        result.pageSize = pageSize;
        result.pages = (total + pageSize - 1) / pageSize;
        result.list = list;
        return result;
    }

}
