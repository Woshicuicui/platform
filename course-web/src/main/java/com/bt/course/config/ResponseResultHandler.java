package com.bt.course.config;


import com.bt.course.base.exception.ServiceException;
import com.bt.course.base.response.Response;
import com.bt.course.base.response.ResponseCode;
import com.bt.course.base.response.ResponseFactory;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice {

    /**
     * 决定beforBodyWrite是否工作
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getMethod().getReturnType().isAssignableFrom(Void.TYPE);
    }

    /**
     * 全局重写返回值
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }


    /**
     * 全局拦截异常，此处定义的是ServiceException
     */
    @ExceptionHandler(ServiceException.class)
    public Response handleServiceException(ServiceException e, HttpServletRequest request) {
        return ResponseFactory.getError(e.getCode(), e.getMsg());
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     *
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Response unauthenticatedException(ServiceException e) {
        return ResponseFactory.getError(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(DisabledAccountException.class)
    public Response disabledAccountException(ServiceException e) {
        return ResponseFactory.getError(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(UnknownAccountException.class)
    public Response unknownAccountException(ServiceException e) {
        return ResponseFactory.getError(e.getCode(),e.getMsg());
    }

}
