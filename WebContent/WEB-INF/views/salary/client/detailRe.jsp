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
		<title>报销单查询</title>
		<link rel="stylesheet" href="../../css/salary/client/common.css">
		<link rel="stylesheet" href="../../css/salary/client/detail.css">
	</head>
	<body>
	<input type="hidden" id="id" name="id"  value="${id}">
		<h2 class="title">报销单明细</h2>
		<img src="../../image/salary/client/home.png" class="home-btn" />
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
	<script src="../../js/jquery-3.2.1.min.js"></script>
	<script src="../../js/salary/client/detailRe.js"></script>
	<script src="../../js/salary/client/layer.js"></script>
</html>
