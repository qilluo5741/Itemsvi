package com.sso.server.service.impl;

import com.sso.server.entity.UserInfo;
import com.sso.server.entity.UserOtherInfo;
import com.sso.server.util.UserSerializer;
/***
 * 序列化 TestLoginUser
 * @author niewei
 *
 */
public class TestUserSerializer extends UserSerializer{

	@Override
	protected void translate(UserInfo user, UserData userData) throws Exception {
		//实现类型已知，可强制性转换
		UserOtherInfo loginUser=(UserOtherInfo) user;
		userData.setId(loginUser.getUserid());;
		userData.setProperty("user", user);
	}

}
