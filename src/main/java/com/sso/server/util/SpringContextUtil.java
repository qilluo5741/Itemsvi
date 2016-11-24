package com.sso.server.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		SpringContextUtil.applicationContext=ac;
	}
	/***
	 * 获取ApplicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	/**
	 * 按名字获取Springbean实例
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
	/**
	 * 按照类型获取Spring bean
	 * @param beanType
	 * @return
	 */
	public static  <T>T getBean(Class<T> beanType){
		return applicationContext.getBean(beanType);
	}
}
