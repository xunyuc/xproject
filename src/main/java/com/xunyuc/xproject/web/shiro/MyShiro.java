package com.xunyuc.xproject.web.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.HashSet;

/**
 * Created by Xunyuc on 2017/6/4.
 */
public class MyShiro  extends AuthorizingRealm {

    /**
     * 权限认证，获取登录用户的权限
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String loginName=(String) principalCollection.fromRealm(getName()).iterator().next();

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        java.util.Set<String> roles = new HashSet<String>();
        java.util.Set<String> permissions = new HashSet<String>();
        if ("admin".equals(loginName)){
            roles.add("admin");
            permissions.add("add");
            permissions.add("query");
        } else {
            roles.add("guest");
            permissions.add("query");
        }
        // 设置角色
        info.setRoles(roles);
        // 设置权限
        info.addStringPermissions(permissions);

        //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址
        //详见applicationContext.xml中的<bean id="shiroFilter">的配置
        return info;
    }

    /**
     * 登录认证，创建用户的登录信息
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;

        //      User user = userService.getByUsername(token.getUsername());
//      if(null != user){
//          AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), user.getNickname());
//          this.setSession("currentUser", user);
//          return authcInfo;
//      }else{
//          return null;
//      }
        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息
        //说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)
        //这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证
        if("admin".equals(token.getUsername())){
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("admin", "password", this.getName());
            this.setSession("currentUser", token.getUsername());
            return authcInfo;
        }else if ( "guest".equals(token.getUsername())){
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("guest", "password", this.getName());
            this.setSession("currentUser", token.getUsername());
            return authcInfo;
        }
        // 没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
       return null;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     */
    private void setSession(Object key, Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }
}
