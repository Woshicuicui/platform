package com.bt.course.api.dto;


import lombok.Data;

@Data
public class ApiDefinitionDto {

    /**
     * 请求类型
     */
    private String apiMethod;

    /**
     * 请求地址
     */
    private String host;

    /**
     * URI
     */
    private String uri;
    /**
     * header头
     */
    private String header;
    /**
     * param参数
     * map: {
     *     "name":"xxx",
     *     "age":"xxx"
     * }
     */
    private String params;
    /**
     * rest参数
     */
    private String rest;


}
