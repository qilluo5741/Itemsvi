/**
 * 
 */
angular.module('myApp', [])
	.controller('regontroller', function($scope) {
		//初始化
		$scope.userdata = {};
		//提交
		$scope.submitForm = function() {
			//console.log($scope.userdata);
			if(!$scope.regForm.$invalid) {
			} else {
				
			}
		}
	})

.directive('compare', function() {

	var o = {};
	o.strict = 'AE';
	o.scope = {
		orgText: '=compare'
	}
	o.require = 'ngModel';
	o.link = function(sco, ele, att, con) {
		con.$validators.compare = function(v) {
			return v == sco.orgText;
		}
		sco.$watch('orgText', function() {
			con.$validate();
		})
	}
	return o;
})

var UNAME_COOKIE_NAME = "lastLoginUserName";
	$(function() {
		//如果name没有values值。从cookie存储过name值写入
		var eleName = $("input[name=username]");
		//得到cookie值
		//eleName.val(Cookie.get(UNAME_COOKIE_NAME));

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
		//表单提交验证是否经过验证

		$.fromValidCalback = function() {
			if ($("#fromValid").text() == "true") {
				return true;
			}
			return false;
		}
	});
	//加载验证码
	function deawCaptcha() {
		$.ajax("preLogin").done(function(data) {
			$("#captchaImg").attr("src", data.imageData);
		}).fail(function() {
			alert("验证码加载失败！");
		});
	}
	//验证当前窗口是否在最前 否则跳出去
	if (window != top){
		top.location.href = location.href;
	}