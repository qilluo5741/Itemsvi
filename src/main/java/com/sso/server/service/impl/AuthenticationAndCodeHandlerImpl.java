package com.sso.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sso.common.StringUtil;
import com.sso.server.dao.UserPersistenceObject;
import com.sso.server.entity.Credential;
import com.sso.server.entity.UserInfo;
import com.sso.server.entity.UserOtherInfo;
import com.sso.server.mapper.IUserMapper;
import com.sso.server.service.IAuthenticationHandler;
import com.sso.server.util.MD5Util;

/**
 * 示例性的鉴权处理器，当用户名和密码都相同时授权通过，返回的也是一个示例性Credential实例
 * 
 * @author niewei
 *
 */
@Service
public class AuthenticationAndCodeHandlerImpl implements IAuthenticationHandler {
	@Autowired
	private UserPersistenceObject userPersistenceObject;
	@Autowired
	private IUserMapper mapper;

	public UserInfo authenticate(Credential credential) {
		// 传入的验证码
		String code = credential.getParameter("code");
		// 服务器缓存sessionCode验证码
		Object scodeObj = credential.getSettedSessionValue();
		if (scodeObj == null) {
			credential.setError("验证码超时！");
			return null;
		}
		String sessionCode = scodeObj.toString();
		// 获取验证码
		if (!sessionCode.equalsIgnoreCase(code)) {
			credential.setError("验证码不正确！");
			return null;
		}
		// 验证账号是否存在
		String username = credential.getParameter("username");// 用户名
		String userPwd = mapper.getpwdbyuseraccount(username);// 根据用户名得到用户密码
		if (userPwd == null) {
			credential.setError("用户名不存在！");
			return null;
		}
		// 验证登录
		String pwd1 = credential.getParameter("password");

		String pwd2 = MD5Util.GetMD5Code(userPwd + sessionCode);// 密码

		// 比较密码是否一致
		if ((pwd1).equalsIgnoreCase(pwd2)) {
			// 登陆成功
			//修改登陆时间
			
			// 查询用户信息
			UserOtherInfo user = mapper.getUserInfo(username);
			return user;
		} else {
			// 密码错误
			credential.setError("用户名密码不正确！");
			return null;
		}
	}

	/**
	 * 自动登陆
	 */
	public UserInfo autoLogin(String lt) throws Exception {
		// 通过lt查询用户基本信息
		UserOtherInfo user = mapper.getUserInfoByLogintoken(lt);
		// 没有匹配项则表示自动登录标识无效
		return user;
	}

	// 生成自动登录标识
	public String loginToken(UserInfo userinfo) throws Exception {

		// 生成一个唯一标识用作lt
		String lt = StringUtil.uniqueKey();
		UserOtherInfo user = (UserOtherInfo) userinfo;
		// 将新lt更新到当前user对应字段
		userPersistenceObject.updateLoginToken(user.getUseraccount(), lt);
		return lt;
	}

	// 更新持久化的lt
	public void clearLoginToken(UserInfo userinfo) throws Exception {
		UserOtherInfo user = (UserOtherInfo) userinfo;
		userPersistenceObject.updateLoginToken(user.getUseraccount(), "");
	}

}
