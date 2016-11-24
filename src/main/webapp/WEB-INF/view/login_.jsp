<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户授权</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/cookie_util.js"></script>
<script type="text/javascript" src="js/md5.js"></script>
<script type="text/javascript">
	var UNAME_COOKIE_NAME = "lastLoginUserName";
	$(function() {
		//如果name没有values值。从cookie存储过name值写入
		var eleName = $("input[name=name]");
		//得到cookie值
		eleName.val(Cookie.get(UNAME_COOKIE_NAME));

		//登陆按钮被点击时记住当前name
		$("form").submit(
				function() {
					Cookie.set(UNAME_COOKIE_NAME, $.trim(eleName.val()), null,
							7 * 24 * 60);
					//将密码字段使用 MD5(MD5（密码）+验证码) 编码后发给服务端
					var elePassword = $("input[name=password]");
					var password = elePassword.val();
					elePassword.val($.md5($.md5(password) + $("#code").val()));
					//elePassword.val($.md5(password));
				});
		deawCaptcha();
		//加载验证码 
		$("#captchaImg").click(function() {
			deawCaptcha();
		});
	});
	function deawCaptcha() {
		$.ajax("preLogin").done(function(data) {
			$("#captchaImg").attr("src", data.imageData);
		}).fail(function() {
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
			<p style="color: red;">${msg }</p>
		</c:if>
		<form action="login" method="post">
			账号：<input type="text" name="name" value="${name}"> <br /> 密码：<input
				type="password" name="password"> <br> 验证码：<input
				type="text" name="code" id="code"> <br> 下次自动登陆:<input
				type="checkbox" name="rememberMe" value="true"> <img src=""
				id="captchaImg"> <input type="hidden" name="backUrl"
				value="${param.backUrl}" /> <input id="btnLogin" type="submit"
				value="登陆">
		</form>
	</c:if>

	<c:if test="${ not empty user}">
		<h1>欢迎来到个人中心</h1>
		<button onclick="location.href='logout'">退出</button>
		<hr>
		<c:forEach items="${sysList}" var="sys">
			<a href="${ sys.baseUrl}/${sys.homeUri}" target="_black">
				${sys.name} </a>
			<hr>
		</c:forEach>
		<c:forEach items="${sysList}" var="sys">
			<script type="text/javascript"
				src="${sys.baseUrl}/cookie_set?vt=${VT}"></script>
		</c:forEach>
	</c:if>


</body>
</html>