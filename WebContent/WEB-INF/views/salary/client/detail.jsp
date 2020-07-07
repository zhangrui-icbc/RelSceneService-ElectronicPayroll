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
		<title>工资单查询</title>
		<link rel="stylesheet" href="../../css/salary/client/common.css">
		<link rel="stylesheet" href="../../css/salary/client/detail.css">
	</head>
	<body>
	<input type="hidden" id="salaryId" name="salaryId"  value="${salaryId}">
	<input type="hidden" id="issueTime" name="issueTime"  value="${issueTime}">
	<input type="hidden" id="userId" name="userId"  value="${userId}">
	<input type="hidden" id="id" name="id"  value="${id}">
		<h2 class="title">工资单明细</h2>
		<img src="../../image/salary/client/home.png" class="home-btn" />
		<p class="tit">单位：元</p>
		<div class="total">
			<table>
				<tr>
					<td style="color: orange;">实发合计</td>
					<td style="color: orange;">收入合计</td>
					<td style="color: orange;">支出合计</td>
				</tr>
				<tr class="total_list">
					<td id="real_income">0.00</td>
					<td id="total_income">0.00</td>
					<td id="total_expenditure">0.00</td>
				</tr>
			</table>
		</div>
		
		<div class="list">
			<div class="list-box">
			</div>
			<div class="list-box">
			</div>
			<div class="list-box">
			</div>
			<div class="list-box">
			</div>
			<div class="list-box">
			</div>
			<div class="list-box desc">
			</div>
		</div>
	</body>
	<script src="../../js/jquery-3.2.1.min.js"></script>
	<script src="../../js/salary/client/detail.js"  ></script>
</html>
