<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${sysName}</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/cookie_util.js"></script>
<script type="text/javascript">
	var UNAME_COOKIE_NAME="lastLoginUserName";
	$(function(){
		//如果name没有values值。讲cookie存储过name值写入
		var eleName=$("input[name=name]");
		eleName.val(Cookie.get(UNAME_COOKIE_NAME));
		
		//登陆按钮被点击时记住当前name
		$("form").submit(function(){
			Cookie.set(UNAME_COOKIE_NAME,$.trim(eleName.val()),null,7*24*60);
		});
		//加载验证码
		deawCaptcha();
		$("#captchaImg").click(function(){
			deawCaptcha();
		});
	});
	function deawCaptcha(){
		$.ajax("preLogin").done(function(data){
			$("#captchaImg").attr("src",data.imageData);
		}).fail(function(){
			alert("验证码加载失败！");
		});
	}
	
</script>
</head>
<body>
	
	<!-- 如果用户为空 -->
	<c:if test="${empty user}">
		<h1>欢迎来到登录系统</h1>
		<!--登陆消息为空-->
		<c:if test="${not empty msg}">
			<p style="color: red;">
				${msg }
			</p>
		</c:if>
			<form action="login" method="post">
				账号：<input type="text" name="name" value="${name}">
				<br/>
				密码：<input type="password" name="password">
				<br>
				验证码：<input type="text" name="code" id="code"> 
				<img   src="" id="captchaImg">
				<input id="btnLogin" type="submit" value="登陆">
			</form>
		
	</c:if>
	
	<c:if test="${ not empty user}">
		 <h1>欢迎来到个人中心</h1>
		 <button onclick="location.href='logout'">退出</button>
		<hr>
		<c:forEach items="${sysList}" var="sys">
			<a href="${ sys.baseUrl}/${sys.homeUri}" target="_black" > ${sys.name} </a>
			<hr>
		</c:forEach>
	</c:if>
	
	<c:forEach items="${sysList}" var="sys">
		<script type="text/javascript" src="${sys.baseUrl}/cookie_set?vt=${vt}"></script>
	</c:forEach>
</body>
</html>