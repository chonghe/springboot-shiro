package com.cping.shirospringboot.config;

import com.cping.shirospringboot.mapper.UserMapper;
import com.cping.shirospringboot.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm 需要继承 extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了==>授权doGetAuthorizationInfo");

        //授权给用户
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //所有用户将被授权
        //info.addStringPermission("user:add");

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();   //拿到user对象

        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());

        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了==>认证doGetAuthorizationInfo");

        //临时创建一个账号密码
        /*String username = "root";
        String password = "root";*/


        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        /*if (!userToken.getUsername().equals(username)) {
            return null;    //抛出异常  UnknownAccountException
        }*/

        //连接真实的数据库
        User user = userMapper.queryUserByName(userToken.getUsername());
        if (user == null) { //没有这个人
            return null;    // UnknownAccountException
        }

        //密码认证 shiro做... 而且加密了
        return new SimpleAuthenticationInfo(user, userToken.getPassword(), "");
    }
}
