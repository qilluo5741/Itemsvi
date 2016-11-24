package com.sso.server.entity;
/**
 * 登陆用户
 * @author niewei
 *
 */
public class LoginUserInfo{
	private String useraccount;//用户登陆账号
	private String userpassword;//用户登陆密码
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public LoginUserInfo(String useraccount, String userpassword) {
		super();
		this.useraccount = useraccount;
		this.userpassword = userpassword;
	}
}
