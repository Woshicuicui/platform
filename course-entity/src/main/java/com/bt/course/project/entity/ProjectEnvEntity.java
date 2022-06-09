package com.bt.course.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("course_project_env")
public class ProjectEnvEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private String name;

    private String envDesc;

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
}
