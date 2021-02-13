package com.cdshop.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 统一异常处理
 *
 * @author wangjn
 * @date 2020-12-30
 */
public class BadRequestException extends RuntimeException {
    private Integer status = BAD_REQUEST.value();

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }
}
