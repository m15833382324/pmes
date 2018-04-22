package com.example.demo.security;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Const;


public class AuthRealm extends AuthorizingRealm{
    @Autowired
    private UserService userService;
    
    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;//获取用户输入的token
        String username = utoken.getUsername();
        User user = userService.findUserByUserName(username);
        SecurityUtils.getSubject().getSession().setAttribute(Const.LOGIN_USER, user);
        return new SimpleAuthenticationInfo(user, user.getLoginpswd(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
    }
    //此为授权方法，暂时不做授权操作；
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
    	return null;
    }

}