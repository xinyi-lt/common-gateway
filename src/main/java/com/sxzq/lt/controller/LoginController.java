package com.sxzq.lt.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sxzq.lt.bean.GlobalErrorCode;
import com.sxzq.lt.bean.RestResult;
import com.sxzq.lt.bean.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.Constants;
import org.springframework.web.server.WebSession;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc 权限控制器
 * @author wangzhimin
 * @date 2018/7/17 17:48
 **/

@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public RestResult<Void> logout() {

        LOGGER.info("进入退出登录方法>>>>");

        RestResult<Void> result = new RestResult<>();

        try {
            Object principal = SecurityUtils.getSubject().getPrincipal();
            LOGGER.info("shiro principal={}", JSON.toJSONString(principal));

            if (null != principal) {
                SecurityUtils.getSubject().logout();
            }
        } catch (Exception e) {
            LOGGER.error("退出登录异常，e={}", e.getMessage());
            result.setCode(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode());
            result.setMessage("退出登录异常");
        }

        LOGGER.info("退出登录方法结束>>>>");

        return result;
    }

    @PostMapping("/login")
    public RestResult<Object> login(@RequestBody UserInfo userInfo, WebSession webSession) {

        LOGGER.info("登录参数为：{}", JSON.toJSONString(userInfo));

        RestResult<Object> result = new RestResult<>();

        String loginAccount = userInfo.getLoginAccount();
        String loginPassword = userInfo.getLoginPassword();

        // 判断验证码
		String capText = webSession.getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY);
        if (StringUtils.isEmpty(userInfo.getCaptcha()) || !userInfo.getCaptcha().equalsIgnoreCase(capText)) {
            webSession.invalidate();
        	result.setCode(GlobalErrorCode.VERIFICATION_CODE_ERROR.getCode());
        	result.setMessage("验证码不正确！");
        	return result;
		}
        
        if (StringUtils.isEmpty(loginAccount) || StringUtils.isEmpty(loginPassword)) {
            result.setCode(GlobalErrorCode.MISSING_PARAMETER.getCode());
            result.setMessage("用户名或者密码为空！");
            return result;
        }

        // 在认证提交前准备 token（令牌）
//        UsernamePasswordToken token = new UsernamePasswordToken(loginAccount, AssetDigestUtils.md5DigestAsHex(loginPassword.getBytes()).toUpperCase());
        UsernamePasswordToken token = new UsernamePasswordToken(loginAccount, loginPassword);

        try {
            // 执行认证登陆
            SecurityUtils.getSubject().login(token);
            Session session = SecurityUtils.getSubject().getSession();
            JSONObject user = JSON.parseObject((String)session.getAttribute("userSession"));
            result.setData(user);

        }catch (UnknownAccountException ex) {
            LOGGER.error("用户不存在或者密码错误，e={}", ex.getMessage());
            result.setCode(402);
            result.setMessage("用户不存在或者密码错误！");
        } catch (IncorrectCredentialsException ex) {
            LOGGER.error("用户不存在或者密码错误，e={}", ex.getMessage());
            result.setCode(402);
            result.setMessage("用户不存在或者密码错误！");
        } catch (LockedAccountException ex) {
            LOGGER.error("您目前处于不在职状态，请联系管理员，e={}", ex.getMessage());
            result.setCode(401);
            result.setMessage("您目前处于不在职状态，请联系管理员！");
        } catch (DisabledAccountException ex) {
            LOGGER.error("您是无效用户，请联系管理员，e={}", ex.getMessage());
            result.setCode(401);
            result.setMessage("您是无效用户，请联系管理员！");
        } catch (AuthenticationException ex) {
            LOGGER.error("用户授权失败，e={}", ex.getMessage());
            result.setCode(500);
            result.setMessage("用户授权失败");
        } catch (Exception ex) {
            LOGGER.error("内部错误，请稍后重试，e={}", ex.getMessage(),ex);
            result.setCode(500);
            result.setMessage("内部错误，请稍后重试！");
        }

        return result;
    }
}
