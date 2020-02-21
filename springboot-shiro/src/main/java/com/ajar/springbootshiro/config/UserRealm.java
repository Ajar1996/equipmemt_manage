package com.ajar.springbootshiro.config;

import com.ajar.springbootshiro.enums.ForbiddenEnum;
import com.ajar.springbootshiro.model.Resource;
import com.ajar.springbootshiro.model.User;
import com.ajar.springbootshiro.service.ResourceService;
import com.ajar.springbootshiro.service.UserService;
import com.ajar.springbootshiro.utils.ShiroUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.net.PasswordAuthentication;

@Configuration
public class UserRealm  extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    ResourceService resourceService;
    /**
     * @description: 用户授权
     * @param principalCollection
     * @return: 
     * @author: Andy
     * @time: 2019/9/30 11:47
     */   
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(resourceService.selectUserPerms(ShiroUtil.getUserId()));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        User user=userService.findByAccount(token.getUsername());
        if(user==null){
            throw new UnknownAccountException();
        }
        if (ForbiddenEnum.DISABLE.getCode().toString().equals(user.getForbidden())){
            throw new DisabledAccountException();
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user,user.getPassword(),ByteSource.Util.bytes(user.getSalt()),getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        return simpleAuthenticationInfo;
    }

    /**
     * 密码验证服务
     * @param credentialsMatcher
     */
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5HashedCredentialsMatcher = new HashedCredentialsMatcher();
        md5HashedCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        md5HashedCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        md5HashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        super.setCredentialsMatcher(md5HashedCredentialsMatcher);
    }
}
