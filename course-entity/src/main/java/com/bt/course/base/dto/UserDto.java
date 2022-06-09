package com.bt.course.base.dto;

import lombok.Data;

@Data
public class UserDto {

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 手机号
     */
    private String userPhone;

    private Integer userStatus;

}
