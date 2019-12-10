<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="yes" name="apple-mobile-web-app-capable" />
    	<meta content="yes" name="apple-touch-fullscreen" />
    	<meta name="data-spm" content="a215s" />
    	<meta content="telephone=no,email=no" name="format-detection" />
    	<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<title>工资单查询</title>
		<link href="../../css/salary/client/common.css" rel="stylesheet">
		<link href="../../css/salary/client/query.css" rel="stylesheet">
	</head>
	<body>
	<input type="hidden" id="contextPath" name="contextPath"   value="${pageContext.request.contextPath}"/>
	<input id="jumpUrl" type="hidden" th:value="${session.currentUrl}" />
		<h2 class="title">工资单查询<span class="fl-r" ></span></h2>
		<img src="../../image/salary/client/home.png" class="home-btn" />
		<p class="list-title">日期<span class="fl-r">工资单（元）</span></p>
		<div class="list" id="list">
		</div>
	</body>
	<script type="text/javascript"  src="../../js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript"  src="../../js/salary/client/query.js"  ></script>
	<script type="text/javascript" th:inline="javascript" > 
		//	var ctx = [[@{/}]],page=[[${page}]],website=[[${website}]];
				var s=$(window).height();//是滚动条高度
//				var h=$(".company-msg").height();//是标签高度
				var t=$(".list").offset().top;
				console.log(s);
				console.log(t);
				$(".list").height(s-t-30);
				console.log(s-t-50);
	</script>
</html>
