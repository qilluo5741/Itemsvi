package com.sso.server.entity;
/**
 * 用户信息
 * @author niewei
 *
 */
public class UserOtherInfo extends UserInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;//用户标识
	private String username;//用户名字
	private String useraccount;//用户账号
	private String phone;//用户手机号
	private String regtime;//注册时间
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	@Override
	public String toString() {
		return "UserOtherInfo [userid=" + userid + ", username=" + username
				+ ", useraccount=" + useraccount + ", phone=" + phone
				+ ", regtime=" + regtime + "]";
	}
	
}
