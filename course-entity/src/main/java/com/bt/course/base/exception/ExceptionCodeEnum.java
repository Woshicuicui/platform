package com.bt.course.base.exception;

public enum ExceptionCodeEnum {
    /**
     * 错误码的规范，code必须 5位数
     */
    params_error(10001,"参数错误"),
    user_not_exist(10100,"用户不存在"),
    user_exist(10101,"用户已存在"),
    user_password_error(10102,"用户名或密码错误"),
    user_disable(10103,"用户已禁用"),
    project_not_exist(10200,"项目/服务不存在"),
    project_env_not_exist(10201,"环境不存在"),
    project_env_exist(10202,"环境已存在，无法重复添加"),
    project_host_exist(10203,"HOST已存在，无法重复添加"),
    project_host_not_exist(10203,"HOST不存在，无法重复添加"),
    api_definition_not_exist(10300,"接口不存在"),
    api_definition_exist(10300,"接口已存在"),
    ;


    private int code;
    private String msg;

    ExceptionCodeEnum(int code, String msg) {
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
