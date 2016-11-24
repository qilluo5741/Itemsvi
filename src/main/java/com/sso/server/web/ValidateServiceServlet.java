package com.sso.server.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sso.server.entity.UserInfo;
import com.sso.server.util.Config;
import com.sso.server.util.SpringContextUtil;
import com.sso.server.util.TokenManager;
import com.sso.server.util.UserSerializer;

/**
 * 用户处理 以及序列化
 */
@WebServlet("/validate_service")
public class ValidateServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateServiceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设定返回类型，编码
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		//客户端传来的vt
		String vt=request.getParameter("vt");
		UserInfo user=null;
		//验证vt有效性
		if(vt!=null){
			user=TokenManager.validata(vt);
		}
		//传来的vt:
		//System.out.println("远端验证传来的vt:"+vt+"user:"+((TestLoginUser)user).toString());
		//返回结果集
		Config config=(Config) SpringContextUtil.getBean("config");
		UserSerializer serializer=config.getUserSerializer();
		try {
			//System.out.println("远端验证序列："+serializer.serial(user));
			response.getWriter().write(serializer.serial(user));
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
