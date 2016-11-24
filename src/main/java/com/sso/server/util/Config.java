package com.sso.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import com.sso.server.entity.ClientSystem;
import com.sso.server.service.IAuthenticationHandler;
import com.sso.server.service.IPreLoginHandler;
/**
 * 应用配置信息
 * 
 * @author niewei
 *
 */
public class Config implements ResourceLoaderAware{
	private org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(this.getClass());
	public static final String SESSION_ATTR_NAME="login_session_attr_name";
	private IAuthenticationHandler authenticationHandler; // 鉴权处理器
	private String loginViewName = "/login"; // 登录页面视图名称  默认login
	private String indexViewName="/index";
	private Integer tokenTimeout=30;//token 过期时间
	private List<ClientSystem> clientSystems = new ArrayList<ClientSystem>();//客户系统
	private ResourceLoader resourceLoader;//读取文件
	private IPreLoginHandler preLogHandler;//登陆前的预处理器
	private boolean secureModel=false;//是否必须为https
	private int autoLoginExpDays=365;//自动登陆章状态有效期限，默认一年
    private UserSerializer userSerializer;// 用户信息转换序列化实现
	//刷新配置信息
	public void refreshConfig(){
		//加载config.properties
		Properties propertie=new Properties();
		try {
			Resource resource=resourceLoader.getResource("classpath:config.properties");
			propertie.load(resource.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
			log.warn("在classpath中没有找到 classpath:config.properties配置文件");
		}
		//vt有效期参数
		String configTokenTimeOut=(String)propertie.get("tokenTimeout");
		if(configTokenTimeOut!=null){
			try {
				tokenTimeout=Integer.parseInt(configTokenTimeOut);
				log.debug("config.properties 设置 tokenTimeout={"+tokenTimeout+"}");
			} catch (NumberFormatException e) {
				log.warn("tokentimeout参数配置不正确");
			}
		}
		//是否必须用https
		Boolean sm=Boolean.valueOf(propertie.get("secureModel").toString());
		if(sm!=null){
			try {
				secureModel=sm;
			} catch (Exception e) {
				log.debug("config.properties 设置 secureModel={"+sm+"}");
				log.warn("secureModel参数配置不正确");
			}
		}
		//自动登陆有效时间
		int autoLoginExpDays=Integer.valueOf(propertie.get("autoLoginExpDays").toString());
		try {
			this.autoLoginExpDays=autoLoginExpDays;
		} catch (Exception e1) {
			log.debug("config.properties 设置 autoLoginExpDays={"+autoLoginExpDays+"}");
			log.warn("autoLoginExpDays参数配置不正确");
		}
		
		
		//初始化客户端系统
		try {
			loadClentSystems();
		} catch (Exception e) {
			log.warn("加载客户端sys client 失败！");
		}
	}
	//加载客户端系统列表
	@SuppressWarnings("unchecked")
	private void loadClentSystems() throws DocumentException, IOException{
		Resource resource=resourceLoader.getResource("classpath:client_systems.xml");
		//dom4j
		SAXReader reader=new SAXReader();
		Document doc=reader.read(resource.getInputStream());
		//得到根  
		Element rootElement=doc.getRootElement();
		//得到集合
		List<Element> systemsElements=rootElement.elements();
		//在添加之前，清空里面的数据（否则数据累加！）
		clientSystems.clear();
		//遍历集合
		for (Element element : systemsElements) {
			ClientSystem clientSystem=new ClientSystem();
			clientSystem.setId(element.attributeValue("id"));
			clientSystem.setBaseUrl(element.elementText("baseUrl"));
			clientSystem.setName(element.attributeValue("name"));
			clientSystem.setInnerAddress(element.elementText("innerAddress"));
			clientSystem.setHomeUri(element.elementText("homeUri"));
			clientSystems.add(clientSystem);
		}
	}
	//通知客户端模块清除这个缓存
	public void destroy() {
		for(ClientSystem clientSystem :clientSystems){
			clientSystem.noticeShutdown();
		}
	}
	/**
	 * 获取当前鉴权处理器
	 * 
	 * @return
	 */
	public IAuthenticationHandler getAuthenticationHandler() {
		return authenticationHandler;
	}

	public void setAuthenticationHandler(
			IAuthenticationHandler authenticationHandler) {
		this.authenticationHandler = authenticationHandler;
	}

	/**
	 * 获取登录页面视图名称
	 * 
	 * @return
	 */
	public String getLoginViewName() {
		return loginViewName;
	}

	public void setLoginViewName(String loginViewName) {
		this.loginViewName = loginViewName;
	}

	public Integer getTokenTimeout() {
		return tokenTimeout;
	}

	public void setTokenTimeout(Integer tokenTimeout) {
		this.tokenTimeout = tokenTimeout;
	}

	public List<ClientSystem> getClientSystems() {
		return clientSystems;
	}

	public void setClientSystems(List<ClientSystem> clientSystems) {
		this.clientSystems = clientSystems;
	}
	//复制res
	public void setResourceLoader(ResourceLoader res) {
		resourceLoader=res;
	}
	public IPreLoginHandler getPreLogHandler() {
		return preLogHandler;
	}
	public void setPreLogHandler(IPreLoginHandler preLogHandler) {
		this.preLogHandler = preLogHandler;
	}
	public boolean isSecureModel() {
		return secureModel;
	}
	public int getAutoLoginExpDays() {
		return autoLoginExpDays;
	}
	public UserSerializer getUserSerializer() {
		return userSerializer;
	}
	public void setUserSerializer(UserSerializer userSerializer) {
		this.userSerializer = userSerializer;
	}
	public void setIndexViewName(String indexViewName) {
		this.indexViewName = indexViewName;
	}
	public String getIndexViewName() {
		return this.indexViewName ;
	}
	
}