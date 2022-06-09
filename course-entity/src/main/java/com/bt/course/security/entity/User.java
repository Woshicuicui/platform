package com.bt.course.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("course_user")
public class User extends Model<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    private Long id;

    @TableField("username")
    private String userLogin;

    @TableField("password")
    private String userPass;

    @TableField("nick_name")
    private String userNickname;

    @TableField("user_email")
    private String userEmail;

    @TableField("user_phone")
    private String userPhone;

    @TableField("create_time")
    private Date userRegistered;

    @TableField("update_time")
    private Date updateTime;

    @TableField("user_activation_key")
    private String userActivationKey;

    @TableField("user_status")
    private Integer userStatus;



    @Override
    public Serializable pkVal() {
        return id;
    }
}
