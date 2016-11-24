package com.sso.server.mapper;

import java.util.Map;

import com.sso.server.entity.UserOtherInfo;

public interface IUserMapper {
	// 根据登陆用户执行登陆 返回用户信息
	UserOtherInfo getUserInfo(String useraccount);

	// 根据用户名或者是手机号验证账号是否存在 得到密码
	String getpwdbyuseraccount(String useraccount);

	// 设置lt
	int setLoginToken(Map<String, String> map);

	// 根据登陆用户执行登陆 返回用户信息
	UserOtherInfo getUserInfoByLogintoken(String lt);
	
	

}
