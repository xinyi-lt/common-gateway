package com.sxzq.lt.config;

import com.alibaba.fastjson.JSON;
import com.sxzq.lt.bean.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @desc shiro授权类
 * @date 2018/7/17 11:18
 **/
public class FeeRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(FeeRealm.class);

    private static final Map<String, UserInfo> accountMap = new HashMap<>();

    static {
        accountMap.put("admin", new UserInfo("admin", "6458b267142ce75e46229f803c540914"));
        accountMap.put("feiyong", new UserInfo("feiyong", "6458b267142ce75e46229f803c540914"));
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        logger.info("登录操作进行登录认证......");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        UserInfo userInfo = accountMap.get(token.getUsername());

        logger.debug("根据登录用户名【{}】查询到对应用户信息为:{}", token.getUsername(), userInfo);


        if (Objects.isNull(userInfo)) {
            // 没找到帐号
            throw new UnknownAccountException("没有在本系统中找到对应的用户信息");
        }


        //放入shiro 调用CredentialsMatcher检验密码
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo.getLoginAccount(), userInfo.getLoginPassword(), getName());

        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        userInfo.setLoginPassword(null);
        userInfo.setToken(UUID.randomUUID().toString());
        session.setAttribute("userSession", JSON.toJSONString(userInfo));
        return authenticationInfo;
    }
}
