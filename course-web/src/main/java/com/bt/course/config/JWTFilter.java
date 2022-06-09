package com.bt.course.config;

import com.alibaba.fastjson.JSON;
import com.bt.course.base.response.ResponseCode;
import com.bt.course.base.response.ResponseFactory;
import com.bt.course.security.dto.JWTTokenDto;
import com.bt.course.security.utils.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 检查用户是否登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(SecurityUtils.REQUEST_AUTH_HEADER);
        return header != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(SecurityUtils.REQUEST_AUTH_HEADER);
        JWTTokenDto jwtTokenDto = new JWTTokenDto();
        jwtTokenDto.setToken(header);
        getSubject(request,response).login(jwtTokenDto);
        return true;
    }

    /**
     * 是否允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean loginAttempt = isLoginAttempt(request, response);
        if (loginAttempt){
            executeLogin(request,response);
            return true;
        }
        response401(response);
        return true;
    }

    // 如果没有登录(没有auth在header中) 则会给你拦截，给你一个401的返回
    private Boolean response401(ServletResponse resp) {
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(ResponseFactory.getError(ResponseCode.please_login)));
            out.flush();
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return false;
    }
}

