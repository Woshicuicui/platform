package com.bt.course.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userNicename;

    private String userEmail;

    private String userUrl;

    private String userPhone;

    private Integer userStatus;
}
