package com.sso.server.entity;

import java.util.Date;

import org.apache.log4j.Logger;

import com.sso.server.util.HttpUtil;

public class ClientSystem {
	private static Logger logger=org.apache.log4j.Logger.getLogger(ClientSystem.class);
	private String id; // 唯一标识
	private String name; // 系统名称
	private String baseUrl; // 应用基路径，代表应用访问起始点 
	private String homeUri; // 应用主页面URI，baseUrl + homeUri = 主页URL
	private String innerAddress; // 系统间内部通信地址
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getHomeUri() {
		return homeUri;
	}
	public void setHomeUri(String homeUri) {
		this.homeUri = homeUri;
	}
	public String getInnerAddress() {
		return innerAddress;
	}
	public void setInnerAddress(String innerAddress) {
		this.innerAddress = innerAddress;
	}

	/**
	 * 与客户端系统通信，通知客户端token过期
	 * 
	 * @param tokenTimeout
	 * @return 延期的有效期
	 */
	public Date noticeTimeout(String vt, int tokenTimeout) {
		//与客户端通信处理有效期
		String url=innerAddress+"/notice/timeout?vt="+vt+"&tokenTimeout="+tokenTimeout;
		try {
			String res=HttpUtil.request_get(url);
			System.out.println("通知客户端：时间到了。"+res);
			//判断是否为空
			if(res==null||res.equals("")){
				return null;
			}else{
				//得到客户端返回的时间
				return new Date(Long.valueOf(res.trim()));
			}
		} catch (NumberFormatException e) {
			logger.error("超时通知失败！");
			return null;
		}
	}

	/**
	 * 通知客户端用户退出
	 */
	public boolean noticeLogout(String vt) {
		String url=innerAddress+"/notice/logout?vt="+vt;
		try {
			String res=HttpUtil.request_get(url);
			return Boolean.parseBoolean(res);
		} catch (Exception e) {
			logger.error("通知客户端用户退出失败！");
			return false;
		}
	}

	/**
	 * 通知客户端服务端关闭，客户端收到信息后执行清除缓存操作
	 */
	public boolean noticeShutdown() {
		//通知shutdown
		String url=innerAddress+"/notice/shutdown";
		String res=HttpUtil.request_get(url);
		try {
			return Boolean.parseBoolean(res);
		} catch (Exception e) {
			logger.error("通知客户端服务端关闭失败！");
			return false;
		}
	}
}
