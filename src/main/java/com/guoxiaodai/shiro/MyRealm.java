package com.guoxiaodai.shiro;

import com.guoxiaodai.service.UserService;
import com.guoxiaodai.service.impl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {

    private static final Logger log= LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private UserService userService =new UserServiceImpl();


    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1、获得用户名
        String username=principalCollection.getPrimaryPrincipal().toString();

        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        //2、从数据库中获得用户角色数据
        simpleAuthorizationInfo.setRoles(userService.getRolesByUserName(username));
        //3、从数据库中获得用户权限
        simpleAuthorizationInfo.setStringPermissions(userService.getPermissionsByUserName(username));
        System.out.println(userService.getRolesByUserName(username).toString());
        System.out.println(userService.getPermissionsByUserName(username).toString());
        return simpleAuthorizationInfo;
    }

    @Override
    //用户名密码认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、从主体传过来的认证信息中，获得用户名
        String username = (String) authenticationToken.getPrincipal();

        //2、通过用户名到数据库中获取凭证
        String password= userService.getPasswordByUserName(username);
        if (password==null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo=
                new SimpleAuthenticationInfo(username,password,"myRealm");
        return simpleAuthenticationInfo;
    }

}
