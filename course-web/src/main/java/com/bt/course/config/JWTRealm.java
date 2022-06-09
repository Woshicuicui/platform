package com.bt.course.config;

import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.exception.ServiceException;
import com.bt.course.base.response.ResponseCode;
import com.bt.course.security.dto.JWTTokenDto;
import com.bt.course.security.entity.User;
import com.bt.course.security.service.UserServiceImpl;

import com.bt.course.security.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 判断用户是否存在，如果存在给用户授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 先从token中取出来username(登录名)
        String userAccount = JwtUtils.getUserAccount(principalCollection.toString());
        User user = userService.getByAccount(userAccount);
        if (user == null) {
            throw new ServiceException(ResponseCode.please_login);
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // todo 用户所属角色
        authorizationInfo.setRoles(null);
        // todo 用户所拥有的权限(在Menu中会加入)，权限如何生效、如何绑定到接口上(在Menu讲完以后会去讲)
        // todo @RequiresPermissions("权限名称")
        authorizationInfo.setStringPermissions(null);
        return authorizationInfo;
    }

    /**
     * 判断用户的的状态，如果为-1 返回禁用，如果正常的检查密码和token是否匹配，如果不匹配则认为需要重新登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String userAccount = JwtUtils.getUserAccount(token);
        User user = userService.getByAccount(userAccount);
        if (user == null) {
            throw new UnknownAccountException(ExceptionCodeEnum.user_not_exist.getMsg());
        }
        if ("-1".equals(String.valueOf((user.getUserStatus())))) {
            throw new DisabledAccountException(ExceptionCodeEnum.user_disable.getMsg());
        }
        // 用户名密码错误的
        if (!JwtUtils.verify(token,userAccount,user.getUserPass())){
            throw new UnauthenticatedException(ExceptionCodeEnum.user_password_error.getMsg());
        }
        return new SimpleAuthenticationInfo(token,token,"JwtRealm");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTTokenDto;
    }
}
