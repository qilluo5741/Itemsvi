package com.sso.server.service.impl;

import com.sso.server.entity.Credential;
import com.sso.server.entity.UserInfo;
import com.sso.server.service.IAuthenticationHandler;
/**
 *示例性的鉴权处理器，当用户名和密码都相同时授权通过，返回的也是一个示例性Credential实例
 * @author niewei
 *
 */
public class AuthenticationHandlerImpl implements IAuthenticationHandler {

	public UserInfo authenticate(Credential credential) {
		return null;
	}

	public UserInfo autoLogin(String lt) throws Exception {
		return null;
	}

	public String loginToken(UserInfo loginUser) throws Exception {
		return null;
	}

	public void clearLoginToken(UserInfo loginUser) throws Exception {
	}
}
