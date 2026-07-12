package com.healthinnova.portal.common.enums;

import lombok.Data;

/**
 * 错误码对象
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误提示
     */
    private final String msg;

    public ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
