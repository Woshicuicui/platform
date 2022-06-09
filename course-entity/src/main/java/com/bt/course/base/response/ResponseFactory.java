package com.bt.course.base.response;

import com.bt.course.base.exception.ExceptionCodeEnum;

import java.util.HashMap;
import java.util.Map;

public class ResponseFactory {
    public ResponseFactory() {
    }

    public static <T> Response<T> getResponse(int code, String msg, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> getSuccess() {
        return getResponse(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), null);
    }

    public static <T> Response<T> getSuccess(T data) {
        return getResponse(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), data);
    }

    public static <T> Response<Map<String, T>> getSuccess(String k1, T v1, String k2, T v2) {
        Map<String, T> map = new HashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return getResponse(getSuccess().getCode(), getSuccess().getMsg(), map);
    }

    public static <T> Response<Map<String, T>> getSuccess(String k1, T v1) {
        Map<String, T> map = new HashMap<>(2);
        map.put(k1, v1);
        return getResponse(getSuccess().getCode(), getSuccess().getMsg(), map);
    }

    public static Response getError() {
        return getResponse(ResponseCode.error.getCode(), ResponseCode.error.getMsg(), null);
    }

    public static Response getError(String msg) {
        return getResponse(ResponseCode.error.getCode(), msg, null);
    }

    //  不一定所有的失败都是400, 通过定义一个标准的返回code枚举类来规范
    public static <T> Response<T> getError(ResponseCode responseCode) {
        return getResponse(responseCode.getCode(), responseCode.getMsg(), null);
    }


    //  不一定所有的失败都是400, 通过定义一个标准的返回code枚举类来规范
    public static <T> Response<T> getError(ExceptionCodeEnum responseCode) {
        return getResponse(responseCode.getCode(), responseCode.getMsg(), null);
    }



    //  不一定所有的失败都是400, 通过定义一个标准的返回code枚举类来规范
    public static <T> Response<T> getError(Integer code,String msg) {
        return getResponse(code, msg, null);
    }


}
