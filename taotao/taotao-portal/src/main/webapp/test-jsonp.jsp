<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>jsonp</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
		<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="Description" content="网页信息的描述" />
		<meta name="Author" content="itcast.cn" />
		<meta name="Copyright" content="All Rights Reserved." />
		<link rel="shortcut icon" type="image/x-icon" href="itcast.ico"/>
		<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
		<script type="text/javascript">
		
		
			/** 异步请求 */
			$.ajax({
				url : "http://admin.taotao.com/jsonp.jsp", // 请求URL
				type : "get", // 请求方式
				dataType : "jsonp", // 服务器响应回来的数据类型   函数名(json);
				async : true, // 异步请求
				success : function(data){ // 请求成功
					alert(data.msg);
				},
				error : function(){ // 请求失败
					alert("数据加载失败！");
				}
			});
			
			var str = "var test = function(data){success(data)};";
			/** 运行js格式的字符串 */
			eval(str);
			
			test("aaa");
			
			/**var itcast = function(data){
				alert(data.msg);
			};*/
		
		</script>
		
		<!-- <script type="text/javascript" src="http://admin.taotao.com/jsonp.jsp?callback=itcast"></script>
		 -->
	</head>
	<body>
		
	</body>
</html>