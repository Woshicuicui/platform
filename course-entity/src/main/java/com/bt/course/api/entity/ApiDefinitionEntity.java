package com.bt.course.api.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *
 * @param <T> ApiDefinitionDto/DubboDefinitionDto
 */
@Data
@TableName("course_api_definition")
public class ApiDefinitionEntity<T> {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    /**
     * 接口名称
     */
    private String definitionName;

    /**
     * 请求参数
     * 不同种类型的请求转为String去保存
     * http: host\uri\params\rest\header\method
     * dubbo:zk\serviceName\group\zkVersion\function\className
     * tcp: ....
     * 用bean转为json保存到数据库里，后续使用取出后根据类型做一次转换即可
     */
    private String requestData;

    /**
     * 协议，
     * HTTP、HTTPS、DUBBO、TCP.......等等
     * 用来标记接口的类型
     */
    private String protocol;

    /**
     * 请求体
     */
    private String body;

    /**
     * 接口的用例数
     * 两种方式：
     * 1、每次查询接口的时候，单独计算
     * 2、每次添加、删除接口的时候，直接把总数写入到caseCount
     */
    private Integer caseCount;

    // 为什么要把超时时间放到这个接口表里？
    /**
     * 连接超时时间
     */
    private Long connectTimeout = 6000L;
    /**
     * 响应超时时间
     */
    private Long responseTimeout = 6000L;

    /**
     * 状态: 做逻辑删除使用
     * 0 在用， 1 删除
     */
    private Integer status;

    @TableField(value = "create_user")
    private String createUser;

    @TableField(value = "update_user")
    private String updateUser;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private Date createTime;

    // 2022-03-13 10:01:10
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     *
     * exist = false 时此字段不会作为sql转换时的字段，仅作为bean属性使用
     * request 字段的类型，基于 protocol 来决定
     */
    @TableField(exist = false)
    private T request;
}
