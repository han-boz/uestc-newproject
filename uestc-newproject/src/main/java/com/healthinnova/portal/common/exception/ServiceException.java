package com.healthinnova.portal.common.exception;

import com.healthinnova.portal.common.enums.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务逻辑异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ServiceException extends RuntimeException {

    /**
     * 业务错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    public ServiceException() {
    }

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
