package com.sso.server.service;

import com.sso.server.entity.Credential;
import com.sso.server.entity.UserInfo;

public interface IAuthenticationHandler {
	//验证登陆
	public UserInfo authenticate(Credential credential) throws Exception;
	//自动登陆
	public UserInfo autoLogin(String lt) throws Exception;
	//生成自动登录标识
	public String loginToken(UserInfo loginUser) throws Exception;
	//持久化！
	public void clearLoginToken(UserInfo loginUser)throws Exception;
}
