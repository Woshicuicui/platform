package com.bt.course.base.response;

public enum ResponseCode {
    /**
     * 标准返回code
     */
    success(200,"操作成功"),
    error(400,"很抱歉，系统发生故障"),
    please_login(401,"没有权限,请重新登录"),
    closuer(301,"用户被禁用"),
    param_error(414,"参数不存在"),
    failure(500,"操作失败")
    ;


    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
