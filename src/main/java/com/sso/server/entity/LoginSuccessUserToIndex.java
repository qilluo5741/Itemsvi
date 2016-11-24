package com.sso.server.entity;

/**
 * 用户登陆成功返回主页
 * @author niewei
 *
 */
public class LoginSuccessUserToIndex extends UserInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginName;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	
}
