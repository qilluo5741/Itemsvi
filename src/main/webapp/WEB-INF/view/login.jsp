<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<html class="no-js lt-ie9 lt-ie8 lt-ie7">
<html class="no-js lt-ie9 lt-ie8">
<html class="no-js lt-ie9">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>用户授权</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
<meta name="author" content="FREEHTML5.CO" />
<!-- CSS -->
<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="assets/css/supersized.css">
<link rel="stylesheet" href="assets/css/style.css">
<!-- Javascript -->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/cookie_util.js"></script>
<script type="text/javascript" src="js/md5.js"></script>
<script src="assets/js/jquery-1.8.2.min.js"></script>
<script src="assets/js/supersized.3.2.7.min.js"></script>
<script src="assets/js/supersized-init.js"></script>
<script src="assets/js/scripts.js"></script>
<style>
.error {
	border: 1px solid red;
}
.error {
	color: red;
}
p.error {
	border: 0px;
}
input.error {border 1px solid red;
}
.wrapper {
	width: 240px;
	margin: 30px auto;
	margin-top: 80px;	
}
</style>
</head>
<body ng-app="myApp" ng-controller="regontroller">
<div class="page-container">
	<div class="wrapper">
		<h1 align="center">BATP停车登录</h1><br/>
		<form name="regForm" action="login" method="post"
			onsubmit="return $.fromValidCalback()" ng-submit="submitForm()">
			<!--表单验证情况  -->
			<datalist style="display: none;" id="fromValid">{{regForm.$valid }}</datalist>
			<div class="form-group"
				ng-class="{ 'has-success': regForm.username.$valid }">
				<label>账号/手机号：</label> <input name="username" class="form-control"
					ng-minlength="4" ng-maxlength="11" required
					ng-model="userdata.username" value="" /><!-- cookiename ${name} -->
				<!--验证用户名是否为空-->
				<p class="error"
					ng-if="regForm.username.$error.required && regForm.username.$touched">
					用户名不能为空</p>
				<!-- 验证用户名长度是否合法 -->
				<p class="error"
					ng-if="(regForm.username.$error.minlength || regForm.username.$error.maxlength) && regForm.username.$touched">
					用户名的长度在4-11之间</p>
			</div>

			<div class="form-group"
				ng-class="{ 'has-success': regForm.password.$valid }">
				<label>密码：</label> <input type="password" name="password"
					class="form-control" ng-model="userdata.password" ng-maxlength="32"
					ng-minlength="5" required />
				<p class="error"
					ng-if="regForm.password.$error.required && regForm.password.$touched">密码不能为空</p>
				<p class="error"
					ng-if="(regForm.password.$error.minlength ||regForm.password.$error.maxlength) && regForm.password.$touched">密码长度为6-32位</p>
			</div>

			<!-- 验证码 -->
			<div class="form-group"
				ng-class="{ 'has-success': regForm.code.$valid }">
				<label>验证码</label>
				<div class="input-group">
					<input type="text" class="form-control" name="code" id="code"
						ng-model="userdata.code" ng-maxlength="4" ng-minlength="4"
						required> <span class="input-group-addon"
						style="width: 95px; height: 30px;">
						<img src="" id="captchaImg" height="18px;">
					</span>
				</div>
				<p class="error"
					ng-if="(regForm.code.$error.minlength ||regForm.code.$error.maxlength) && regForm.code.$touched">
					请输入4位验证码</p>
			</div>


			<!-- 回调地址 -->
			<input type="hidden" name="backUrl" value="${param.backUrl}" />
			
			<div class="form-group" align="right">
				7天免登录:&nbsp;&nbsp;<input type="checkbox" name="rememberMe" value="true">
			</div>
			
			<!--登录消息不为空-->
			<c:if test="${not empty msg}">
				<p style="color: red;">${msg}</p>
			</c:if>
			<div class="form-group">
				<button style="width:100%" class="btn btn-primary">登&nbsp;&nbsp;&nbsp;录</button>
			</div>
			<div class="form-group" align="right">
				忘记密码
			</div>
		</form>
	</div>
	 <div align="center" style="margin-top:100px;">Copyright &copy; 2015-2016 上海享泊信息科技有限公司</div>
	<!-- 如果用户不为空 -->
	<c:if test="${not empty user}">
		<c:redirect url="login"></c:redirect>
	</c:if>
	<script src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
</div>
</body>
</html>