package com.bt.course.rest.security;


import com.bt.course.base.response.Response;
import com.bt.course.base.response.ResponseFactory;
import com.bt.course.security.entity.User;
import com.bt.course.security.service.UserServiceImpl;
import com.bt.course.security.utils.JwtUtils;
import com.bt.course.security.utils.SecurityUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("tokens")
    public Response login(@RequestParam(value = "uid") String username,
                          @RequestParam(value = "pwd") String password,
                          @RequestHeader(value = "Authorization", required = false) String authorization,
                          HttpServletResponse response,
                          HttpSession session) {

        Assert.notNull(username, "用户名不能为空");
        Assert.notNull(password, "密码不能为空");

        // 获取用户密码混淆值
        User user = userService.getByAccount(username);
        if (null == user) {
            throw new IncorrectCredentialsException("用户名或密码错误");
        }

        // 加密当前用户输入密码
        String encodePassword = SecurityUtils.md5(password, user.getUserActivationKey());
        if (!encodePassword.equals(user.getUserPass())) {
            throw new IncorrectCredentialsException("用户名或密码错误");
        }

        String token = JwtUtils.sign(username, user.getId(), encodePassword);

        //写入header
        response.setHeader(SecurityUtils.REQUEST_AUTH_HEADER, token);
        response.setHeader("Access-Control-Expose-Headers", SecurityUtils.REQUEST_AUTH_HEADER);

        //不要显示敏感信息
        user.setUserPass("");
        user.setUserActivationKey("");
        return ResponseFactory.getSuccess("user", user, "token", token);

    }

    @PostMapping("register")
    public Response register(@RequestParam(value = "account") String account,
                             @RequestParam(value = "pwd") String pwd,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "code") String code) {

        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(name) || StringUtils.isEmpty(code)) {
            return ResponseFactory.getError("请完善注册信息");
        }
        //todo 检查验证码错误或已过期
        if (userService.isExist(account)) {
            return ResponseFactory.getError("该账号已注册");
        }
        User user = userService.register(account, pwd, name);

        return ResponseFactory.getSuccess(user);
    }


    @PostMapping("/logout")
    public Response logout() {
        Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
        return ResponseFactory.getSuccess();
    }

}
