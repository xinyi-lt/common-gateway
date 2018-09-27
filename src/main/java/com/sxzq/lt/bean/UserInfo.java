package com.sxzq.lt.bean;

import java.io.Serializable;

/**
 * @desc 用户登录VO
 * @date 2018/8/15 17:37
 **/
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 523135948159614708L;

    private String loginAccount;

    private String loginPassword;
    
    private String captcha;

    private String token;

    public UserInfo() {
    }

    public UserInfo(String loginAccount, String loginPassword) {
        this.loginAccount = loginAccount;
        this.loginPassword = loginPassword;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
	public String toString() {
		return "{\"loginAccount\":\"" + loginAccount + "\", \"loginPassword\":\"" + loginPassword + "\", \"captcha\":\""
				+ captcha + "\"}";
	}

}
