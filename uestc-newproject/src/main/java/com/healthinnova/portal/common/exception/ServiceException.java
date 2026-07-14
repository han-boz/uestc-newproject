package com.healthinnova.portal.common.exception;

import com.healthinnova.portal.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public final class ServiceException extends RuntimeException {

    /**
     * 业务错误码
     */
    private final Integer code;

    public ServiceException() {
        super();
        this.code = null;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
