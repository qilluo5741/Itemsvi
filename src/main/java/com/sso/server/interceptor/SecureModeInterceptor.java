package com.sso.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sso.server.util.Config;
import com.sso.server.util.SpringContextUtil;

public class SecureModeInterceptor implements HandlerInterceptor {
	private static final Config config=(Config) SpringContextUtil.getBean("config");
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}
	//处理前
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj) throws Exception {
		boolean ret=!config.isSecureModel() || req.isSecure();
		if(!ret){
			res.getWriter().write("must https!");
		}
		return ret;
	}
}
