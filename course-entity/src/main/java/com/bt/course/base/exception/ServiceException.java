package com.bt.course.base.exception;

import com.bt.course.base.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 12345678123456L;

    private Integer code;
    private String message;


    public ServiceException(Integer code) {
        this.code = code;
    }

    public ServiceException(String message) {
        this.code = 400;
        this.message = message;
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
        this.code = code;
    }


    public ServiceException(ExceptionCodeEnum exceptionCodeEnum) {
        log.info("exception:{}", exceptionCodeEnum.getCode());
        log.info("exception:{}", exceptionCodeEnum.getMsg());
        String data = exceptionCodeEnum.getMsg();
        this.code = exceptionCodeEnum.getCode();
        this.message = data;
        log.info("message:{}",this.message);
    }

    public ServiceException(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
