<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		$("#ref").click(function(){
			/*$.ajax({
				url:"config/refresh",
				data:{"code":$("#code").val()},
				success:function(res){
					alert(res);
				}
			});*/
			
			$.ajax({
				url:"config/refresh",
				data:{code:$("#code").val()}
			}).done(function(success){
				alert(success ?"成功！":"失败！code错了！")
			}).fail(function(){
				alert("系统错了！");
			});
		});
	});
</script>
</head>
<body>
	<h1>更新配置</h1>
	 code:<input type="text" id="code">
	 <br/>
	<button id="ref">更新</button>
</body>
</html>