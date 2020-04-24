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
		<link href="../../css/salary/client/common.css" rel="stylesheet">
		<link href="../../css/salary/client//summary.css" rel="stylesheet">
	</head>
	<body>
	<input id="jumpUrl" type="hidden" th:value="${session.currentUrl}" />
		<h2 class="title">汇总工资单明细</h2>
		<img src="../../image/salary/client/home.png" class="home-btn" />
		<div class="btn-div">
			<a class="three-btn">近3个月</a>
			<a class="six-btn">近6个月</a>
			<a class="year-btn">今年全年</a>
			<a class="lastyear-btn">上一年度</a>
		</div>
		<div class="btn-div date">
			<span>开始日期</span>
			<input type="month" class=" startDate " />
		</div>
		<div class="btn-div date">
			<span>结束日期</span>
			<input type="month" class="endDate" />
		</div>
		<div class="btn-div">
			<a class="query-btn">查询</a>
		</div>
		
		<p class="tit">单位：元</p>
		<div class="total">
			<table>
				<tr>
					<td style="color: orange;">实际收入</td>
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
		</div>
	</body>
	<script type="text/javascript" src="../../js/jquery-3.2.1.min.js" ></script>
	<script type="text/javascript" src="../../js/salary/client/summary.js"  ></script>
	<script src="../../js/salary/client/layer.js"></script>
	<script type="text/javascript" th:inline="javascript" > 
		var ddd = new Date();
                var day =ddd.getDate();

                if(ddd.getMonth()<10){
                  	var month = "0"+(ddd.getMonth()+1); 
                }

	              if(ddd.getDate()<10){
	               	day = "0"+ddd.getDate(); 
	              }

                var datew = ddd.getFullYear()+"-"+month+"-"+day;
                var datew = datew.toString();
		 $(".input1").val(datew);
	</script>
</html>
