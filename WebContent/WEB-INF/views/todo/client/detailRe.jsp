<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta content="yes" name="apple-mobile-web-app-capable" />
    	<meta content="yes" name="apple-touch-fullscreen" />
    	<meta name="data-spm" content="a215s" />
    	<meta content="telephone=no,email=no" name="format-detection" />
    	<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<title>电子报销单查询</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/todo/client/common.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/todo/client/detail.css">
	</head>
	<body>
	<input type="hidden" id="contextPath" name="contextPath"  value="${pageContext.request.contextPath}"/>
	<input type="hidden" id="salaryId" name="salaryId"  value="${salaryId}">
	<input type="hidden" id="issueTime" name="issueTime"  value="${issueTime}">
	<input id="jumpUrl" type="hidden" value="${session.currentUrl}" />
	<input type="hidden" id="userId" name="userId"  value="${userId}">
		<h2 class="title">报销单明细</h2>
		<img src="${pageContext.request.contextPath}/image/todo/client/home.png" class="home-btn" />
		<p class="tit">单位：元</p>
		<div class="total">
			<table>
				<tr>
					<td style="color: orange;">报销合计</td>
				</tr>
				<tr class="total_list">
					<td id="real_income">0.00</td>
				</tr>
			</table>
		</div>
		
		<div class="list">
			<div class="list-box">
			</div>
		</div>
	</body>
	<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/todo/client/detailRe.js"></script>
	<script src="${pageContext.request.contextPath}/js/todo/client/layer.js"></script>
	<script type="text/javascript" th:inline="javascript" > 
/* 		var s=$(window).height();//是滚动条高度
//		var h=$(".company-msg").height();//是标签高度
		var t=$(".list").offset().top;
		console.log(s);
		console.log(t);
		$(".list").height(s-t-30);
		console.log(s-t-50); */
	</script>
</html>
