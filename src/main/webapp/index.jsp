<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>首页</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<style>
.error {
	border: 1px solid red;
}

.error {
	color: red;
}

p.error {
	border:0px;
}

input.error {border 1px solid red;
	
}
.wrapper {
	width: 200px;
	margin: 50px auto;
}
</style>
</head>
<body ng-app="myApp" ng-controller="regontroller">
	<div class="wrapper">
		<h2>注册</h2>
		<form name="regForm" ng-submit="submitForm()">
			<div class="form-group"
				ng-class="{ 'has-success': regForm.username.$valid }">
				<label>用户名：</label> <input name="username" class="form-control"
					ng-minlength="4" ng-maxlength="10" required
					ng-model="userdata.username" />
				<!--
                    	验证用户名是否为空
                    -->
				<p class="error"
					ng-if="regForm.username.$error.required && regForm.username.$touched">
					用户名不能为空</p>
				<!--
                    	验证用户名长度是否合法
                    -->
				<p class="error"
					ng-if="(regForm.username.$error.minlength || regForm.username.$error.maxlength) && regForm.username.$touched">
					用户名的长度在4-10之间</p>
			</div>

			<div class="form-group"
				ng-class="{ 'has-success': regForm.password.$valid }">
				<label>密码：</label> <input type="password" name="password"
					class="form-control" ng-model="userdata.password" ng-maxlength="32"
					ng-minlength="6" required="" />
				<p class="error"
					ng-if="regForm.password.$error.required && regForm.password.$touched">密码不能为空</p>
				<p class="error"
					ng-if="(regForm.password.$error.minlength ||regForm.password.$error.maxlength) && regForm.password.$touched">密码长度为6-32位</p>
			</div>
			<div class="form-group"
				ng-class="{ 'has-success': regForm.password2.$valid}">
				<label>确认密码：</label> <input type="password" name="password2"
					class="form-control" compare="userdata.password" required
					ng-model="userdata.password2" />
				<p class="error"
					ng-if="regForm.password2.$error.compare && regForm.password.$touched">
					两次密码输入不一致</p>
			</div>

			<div class="form-group">
				<button style="width: 100%" class="btn btn-primary">注册</button>
			</div>
		</form>

	</div>
	<script src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>