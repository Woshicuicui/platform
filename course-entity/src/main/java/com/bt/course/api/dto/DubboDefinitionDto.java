package com.bt.course.api.dto;


import lombok.Data;

@Data
public class DubboDefinitionDto {

    /**
     * zk地址(注册中心)
     */
    private String zkAddress;

    /**
     * 被测服务的名字
     */
    private String serviceName;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 函数名称
     */
    private String functionName;

    /**
     * 类名称
     */
    private String className;


}
