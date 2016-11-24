package com.sso.server.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 登陆页前置处理器
 * @author niewe
 */
public interface IPreLoginHandler {
	public static final String SESSION_ATTR_NAME="login_session_attr_name";
	/**
	 * 前置处理
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public abstract Map<?,?> handle(HttpSession session) throws Exception;
}
