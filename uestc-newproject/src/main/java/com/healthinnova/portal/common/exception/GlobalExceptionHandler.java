package com.healthinnova.portal.common.exception;

import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> handleServiceException(ServiceException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * @Valid 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        log.warn("参数校验异常: {}", message);
        return Result.fail(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), message);
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("参数校验异常: {}", e.getMessage());
        return Result.fail(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 系统异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.fail(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
    }

}
