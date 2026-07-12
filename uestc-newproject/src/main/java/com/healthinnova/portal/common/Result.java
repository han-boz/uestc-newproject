package com.healthinnova.portal.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一响应体
 *
 * @param <T> 数据泛型
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回总数（分页时使用）
     */
    private Long total;

    // ========== 成功响应 ==========

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
        result.message = "success";
        result.data = data;
        return result;
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data, Long total) {
        Result<T> result = ok(data);
        result.total = total;
        return result;
    }

    // ========== 失败响应 ==========

    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> Result<T> fail(com.healthinnova.portal.common.enums.ErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMsg());
    }

    // ========== 状态判断 ==========

    @JsonIgnore
    public boolean isSuccess() {
        return Objects.equals(code, GlobalErrorCodeConstants.SUCCESS.getCode());
    }

    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }

}
