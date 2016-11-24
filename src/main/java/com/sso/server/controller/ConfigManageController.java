package com.sso.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sso.server.util.Config;

/***
 * 更新参数配置
 * @author niewei
 *
 */
@Controller
public class ConfigManageController {
	@Autowired
	private Config config;
	@RequestMapping("/config")
	public void configPage(){
		
	}
	/***
	 * 更改配置
	 * @param code
	 * @return
	 */
	@RequestMapping("/config/refresh")
	@ResponseBody
	public boolean config(String code){
		if("admin".equals(code)){
			config.refreshConfig();
			return true;
		}
		return false;
	}
}
