package com.sso.server.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sso.common.CookieUtil;
import com.sso.common.StringUtil;
import com.sso.server.entity.ClientSystem;
import com.sso.server.entity.Credential;
import com.sso.server.entity.UserInfo;
import com.sso.server.service.IPreLoginHandler;
import com.sso.server.util.Config;
import com.sso.server.util.TokenManager;

@Controller
public class LoginController {
	@Autowired
	private Config config;
	
	//单点退出
	@RequestMapping("logout")
	public String logout(String backUrl,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//得到vtoken cookie
		String vt=CookieUtil.getCookie("VT", request);
		/****移除lt****/
		// 清除自动登录cookie
		//得到当前用户是谁。
		UserInfo user = TokenManager.validata(vt);
		if (user != null) {
			//把用户数据库lt清除
			config.getAuthenticationHandler().clearLoginToken(user);
		}
		Cookie ltCookie = new Cookie("LT", null);
		ltCookie.setMaxAge(0);
		response.addCookie(ltCookie);
		//移除token
		TokenManager.romoveToken(vt);
		// 移除server端vt cookie
		Cookie cookie = new Cookie("VT", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		//通知客户端logout
		for (ClientSystem clientSystem : config.getClientSystems()) {
			clientSystem.noticeLogout(vt);
		}
		if(backUrl==null){
			return "logout";
		}else{
			response.sendRedirect(backUrl);
			return null;
		}
	}
	@RequestMapping(value="login",method=RequestMethod.GET)
	public  String login(HttpServletRequest request,String backUrl,HttpServletResponse response,ModelMap map,Boolean notLogin) throws Exception{
		//VT（validation token ）:验证token LT：login token登陆标识
		//得到VT
		String vt=CookieUtil.getCookie("VT", request);
		//得到lt
		String lt=CookieUtil.getCookie("LT", request);
		if(vt==null){//v不存在
			//验证自动登录
			return logintokenVadata(notLogin, response, backUrl, lt, map);
		}else{//VT存在
			UserInfo loginuser=TokenManager.validata(vt);
			if(loginuser!=null){//vt有效
				//验证成功后操作
				vt=authSuccess(response, false, loginuser);
				return validataSuccess(loginuser,backUrl, vt, response, map);
			}else{//vt无效  交给lt验证处理
				//得到lt
				return logintokenVadata(notLogin, response, backUrl, lt, map);
			}
		}
		
	}
	/**
	 * 自动登录操作
	 * @param lt
	 * @return
	 * @throws Exception 
	 */
	private String logintokenVadata(Boolean notLogin,HttpServletResponse response,String backUrl,String lt,ModelMap map) throws Exception{
		if(lt==null){//lt不存在
			//进入登陆页！
			return authFailed(notLogin,response,backUrl);
		}else{//lt存在
			//自动登陆 
			UserInfo user=config.getAuthenticationHandler().autoLogin(lt);
			if (user == null) {
				// return config.getLoginViewName();
				return authFailed(notLogin, response, backUrl);
			} else {
				String vt = authSuccess(response,true, user);
				return validataSuccess(user, backUrl, vt, response, map);
			}
		}
	}
	/**
	 * 授权认证失败时返回的内容设置
	 * @param notLogin
	 * @param response
	 * @param backUrl
	 * @return
	 * @throws IOException 
	 */
	private String authFailed(Boolean notLogin, HttpServletResponse response,
			String backUrl) throws IOException {
		if(notLogin!=null&&notLogin){
			response.sendRedirect(StringUtil.appendUrlParameter(backUrl, "__vt_param__", ""));
			return null;
		}else{
			return config.getLoginViewName();
		}
		
	}
	/***
	 * 登陆验证
	 * @param backUrl
	 * @param rememberMe
	 * @param request
	 * @param session
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String backUrl,Boolean rememberMe,HttpServletRequest request,HttpSession session,HttpServletResponse response,ModelMap map) throws Exception{
		//通过cookie判断是否已经登录，在执行重复刷新（避免登录重复提交）
		String c_vt=CookieUtil.getCookie("VT", request);
		//用cookkie去找到TokenManager是否存在
		if(c_vt!=null){
			UserInfo user=TokenManager.validata(c_vt);
			if(user!=null){
				String vt=authSuccess(response,rememberMe,user);
				return validataSuccess(user,backUrl, vt, response, map);
			}
		}
		
		
		final Map<String,String[]> params=request.getParameterMap();
		@SuppressWarnings("static-access")
		final Object sessionVal=session.getAttribute(config.SESSION_ATTR_NAME);
		
		Credential credential=new Credential() {
			
			@Override
			public Object getSettedSessionValue() {
				return sessionVal;
			}
			
			@Override
			public String[] getParameterValue(String name) {
				return params.get(name);
			}
			
			@Override
			public String getParameter(String name) {
				//从map中得请求参数的值
				String[] tmp=params.get(name);
				return tmp!=null && tmp.length>0?tmp[0]:null;
			}
		};
		UserInfo user=config.getAuthenticationHandler().authenticate(credential);
		if(user==null){//登陆失败！
			map.put("msg",credential.getError());
			return config.getLoginViewName();//返回相应的页面（视图名字）
		}else{//(授权成功)
			String vt=authSuccess(response,rememberMe,user);
			//System.out.println("vt:------》"+vt);
			return validataSuccess(user,backUrl, vt, response, map);
		}
	}
	//预处理请求支持
	@RequestMapping("preLogin")
	@ResponseBody
	public Object preLogin(HttpSession session) throws Exception{
		IPreLoginHandler handler=config.getPreLogHandler();
		if(handler==null){
			throw new Exception("没有配置preLoginHandler,无法执行预处理！");
		}
		return handler.handle(session);
	}
	// 授权成功后的操作
	private String authSuccess(HttpServletResponse response, Boolean rememberMe,UserInfo user) throws Exception {
		// 生成VT
		String vt=StringUtil.uniqueKey();
		// 生成LT
		if(rememberMe!=null&&rememberMe){
			String lt=config.getAuthenticationHandler().loginToken(user);
			setLtCookie(lt, response);
		}
		// 存入Map
		TokenManager.addToken(vt, user);
		// 写Cookie
		Cookie cookie=new Cookie("VT", vt);
		// 是否仅https模式，如果是，设置cookie secure为true
		if (config.isSecureModel()){
			cookie.setSecure(true);
		}
		response.addCookie(cookie);
		return vt;
	}

	// 写lt cookie
	private void setLtCookie(String lt, HttpServletResponse response) {
		Cookie ltCookie = new Cookie("LT", lt);
		ltCookie.setMaxAge(config.getAutoLoginExpDays() * 24 * 60 * 60);
		if (config.isSecureModel()) {
			ltCookie.setSecure(true);
		}
		response.addCookie(ltCookie);
	}
	//VT验证成功或登录成功后的操作
	private String validataSuccess(UserInfo user,String backUrl,String vt,HttpServletResponse response,ModelMap map) throws IOException{
		//如果回调不为空
		if(backUrl!=null&&!backUrl.equals("")){
			System.out.println(backUrl+"  "+vt);
			response.sendRedirect(StringUtil.appendUrlParameter(backUrl, "__vt_param__",vt));
			return null;
		}else{
			map.put("sysList",config.getClientSystems());
			map.put("VT", vt);
			map.put("user", user);
			return config.getIndexViewName();
		}
	}
}
