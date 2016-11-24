package com.sso.server.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.sso.server.entity.ClientSystem;
import com.sso.server.entity.UserInfo;


/**
 * 存储VT_USER信息，并提供操作方法
 * 
 * @author niewei
 *
 */
public class TokenManager {
	//日志
	private static Logger log=org.apache.log4j.Logger.getLogger(TokenManager.class);
	//得到其他配置
	private static final Config config=(Config) SpringContextUtil.getBean("config");
	//令牌存储结构
	private static final Map<String,Token> DATA_MAP=new ConcurrentHashMap<String, TokenManager.Token>();
	//计时器
	private static final Timer timer =new Timer(true);
	static{
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				for(Entry<String,Token> entry:DATA_MAP.entrySet()){
					String vt=entry.getKey();
					Token token=entry.getValue();
					Date now =new Date();
					//当前时间大于过期时间
					if(now.compareTo(token.expried)>0){
						//因为令牌支持自动延期服务，并且应用客户端缓存级之后
						//令牌最后访问时间是存储在客户端的，所以服务端向所有客户端发起一次timeout通知
						//客户端根据tastAccessTime+tokenTimeOut计算是否过期
						//若没有过期，用客户端最大有效期更新当前过期时间
						List<ClientSystem> clientSystems=config.getClientSystems();
						Date maxClientExpired=token.expried;
						//迭代所有的客户端
						for (ClientSystem clientSystem : clientSystems) {
							//给客户端发起通知 客户端返回过期时间 如果过期返回null 
							Date clientExpired=clientSystem.noticeTimeout(vt, config.getTokenTimeout());
							if(clientExpired!=null&&clientExpired.compareTo(now)>0){
								//从客户端得到时间。如果得到比当前最大的时间才更新，否则还是原始时间
								//保证所有客户端存储的是最向
								maxClientExpired=maxClientExpired.compareTo(clientExpired)<0?clientExpired:maxClientExpired;
							}
						}
						//判断最大时间是否比当前时间大
						if(maxClientExpired.compareTo(now)>0){
							log.debug("更新过期时间到："+maxClientExpired);
							token.expried=maxClientExpired;
						}else{
							log.debug("清除过期token:"+vt);
							//已经过期
							DATA_MAP.remove(vt);
						}
						
						
					}
				}
			}
		}, 60*1000 ,60*1000);//第一次执行时间  间隔时间
	}
	//避免静态类被实例化
	private TokenManager() {
	}
	//复合结构体，含login user与过期时间expried两个成员
	private static class Token{
		private UserInfo user;//登陆用户对象
		private Date expried;//过期时间
	}
	
	/**
	 *  验证令牌有效性
	 * @param vt
	 * @return
	 */
	public static UserInfo validata(String vt){
		Token token=null;
		try {
			token = DATA_MAP.get(vt);
		} catch (Exception e) {
			System.out.println("获取user信息报错！");
			return null;
		}
		return token==null?null:token.user;
	}
	/***
	 * 用户授权成功后，将授权信息存入
	 * @param vt
	 * @param user
	 */
	public static void addToken(String vt,UserInfo user){
		//System.out.println("添加token"+vt);
		Token token=new Token();
		token.user=user;
		//非自动登陆处理
		token.expried=new Date(new Date().getTime()+config.getTokenTimeout()*60*1000);
		//存入map中
		DATA_MAP.put(vt, token);
	}
	/**
	 * 移除token
	 * @param token
	 */
	public  static void romoveToken(String token){
		if(token!=null){
			DATA_MAP.remove(token);
		}
	}

}
