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
		<title>报销单查询</title>
		<link href="../../css/salary/client/common.css" rel="stylesheet">
		<link href="../../css/salary/client/queryRe.css" rel="stylesheet">		
	</head>
	<body>
	<input id="jumpUrl" type="hidden" th:value="${session.currentUrl}" />
		<h2 class="title">报销单查询<span class="fl-r" ></span></h2>
		<img src="../../image/salary/client/home.png" class="home-btn" />
		<p class="list-title">日期<span class="fl-r">报销单（元）</span></p>
		<div class="list" id="list">
		</div>
	</body>
	
	<script type="text/javascript"  src="../../js/jquery-3.2.1.min.js" ></script>
	<script type="text/javascript" src="../../js/salary/client/layer.js"  ></script>
	<script type="text/javascript" src="../../js/salary/client/queryRe.js"  ></script>
	<script type="text/javascript" th:inline="javascript" > 
				var s=$(window).height();//是滚动条高度
				var t=$(".list").offset().top;
				console.log(s);
				console.log(t);
				$(".list").height(s-t-30);
				console.log(s-t-50);
	</script>
</html>
