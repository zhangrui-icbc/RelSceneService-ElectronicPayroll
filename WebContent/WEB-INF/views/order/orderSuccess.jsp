<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no;">
<link rel="stylesheet" type="text/css" href="../css/order/success.css">
<script src="../js/order/angular.min.js"></script>
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/order/common.js"></script>
<title>下单成功</title>
</head>

<body>
	<!-- <div id="header" class="header"></div> -->
	<div id="content" ng-app="mysuccess" ng-controller='myController'>
		<div class="content">
			<p id="p1">
				<img src="../image/order/comp.png" width="256" height="256" />
			</p>
			<p id="p2">您的订单已完成</p>
			<p id="p22">请于{{time}}<br>前往{{location}}领取您的外卖</p>
		</div>

		<div class="detail">
			<p id="p3"
				ng-cloak>订单编号 <span id="orderId">{{orderId}}</span>
				<br>订单时间 <span id="orderTime">{{orderTimeDesc}}</span>
				<!-- <br>订单金额 <span id="orderAmt">¥{{info.orderAmt}}元</span> -->
                <br>订单详情 <a id="href" ng-href="myOrderDetail?orderId={{orderId}}&activityUid={{activityUid}}">点击查看</a><br>
			</p>
			
		</div>
	</div>


	<div id="footer"></div>
</body>
</html>
<script>
var activityUid=${activityUid};
initfooter(activityUid,2);
var app = angular.module('mysuccess', []);
var orderId=${orderId};
app.controller('myController', function($scope, $http) {
	$http({
		method : 'GET',
		url : './getOrderDetail?orderId='+orderId+'&activityUid='+activityUid
	}).then(function successCallback(response) {
		var data=response.data.data;
		$scope.orderId = orderId;
		$scope.orderTimeDesc = data.orderTimeDesc;
		$scope.time = data.time;
		$scope.location =data.location;
		$scope.activityUid=activityUid;
	});

});
</script>