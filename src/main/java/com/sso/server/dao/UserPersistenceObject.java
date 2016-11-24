package com.sso.server.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sso.server.mapper.IUserMapper;
@Repository
public class UserPersistenceObject {
	@Autowired
	private IUserMapper mapper;
	/**
	 * 更新当前登录用户的lt标识
	 * @param loginName
	 * @param lt
	 * @throws Exception 
	 */
	public void updateLoginToken(String useraccount, String lt) throws Exception {
		//将信息写入存储文件test，格式为lt=loginName，如：02564fc6a02a35c689cbdf898458d2da=admin
		//将lt写入数据库中
		Map<String,String> map=new HashMap<String, String>();
		map.put("useraccount",useraccount);
		map.put("logintoken", lt);
		mapper.setLoginToken(map);
	}
	/**
	 * 按登录账号查询用户信息
	 * @param parameter
	 * @return
	 */
	/*public LoginSuccessUserToIndex getUser(String uname) {
		if ("admin".equals(uname)) {
			LoginSuccessUserToIndex loginUser = new LoginSuccessUserToIndex();
			loginUser.setLoginName("admin");
			return loginUser;
		}
		return null;
	}*/
}
