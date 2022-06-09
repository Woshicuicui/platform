package com.bt.course.security.dto;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author limenglin
 */
@Data
public class JWTTokenDto implements AuthenticationToken {

    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
