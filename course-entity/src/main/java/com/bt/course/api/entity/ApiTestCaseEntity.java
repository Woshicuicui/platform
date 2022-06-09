package com.bt.course.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course_api_test_case")
public class ApiTestCaseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 接口id
     */
    private Integer definitionId;

    /**
     * 用例名称
     */
    private String caseName;
    /**
     * 用例优先级别
     */
    private String priority;

    /**
     * 请求体
     */
    private String body;

    /**
     * 其他参数
     */
    private String otherParams;

    /**
     * 断言
     */
    private String asserts;
    /**
     * 参数提取
     */
    private String extraction;

    /**
     * 响应超时时间
     */
    private Long responseTimeout = 6000L;
    /**
     * 连接超时时间
     */
    private Long connectionTimeout = 6000L;
}
