<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>

<head>
<meta name="viewport" content="user-scalable=no;">
<link rel="stylesheet" href="../css/order/myOrder.css">
<script src="../js/order/angular.min.js"></script>
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/order/common.js"></script>
<title>历史订单</title>


</head>
<body ng-app="myOrder" ng-controller='orderController'>
<!-- <div id="header"></div> -->
	<div id="content" >
	<span class="title"></span>
	<div class="gap"></div>
	<ul>
	   <li ng-repeat="x in data" >
	      <div class="left">
	      <div class="orderId" hidden ng-cloak>{{x.orderId}}</div>
	      <div class="orderStatus" hidden ng-cloak>{{x.orderStatus}}</div>
	      <div class="p1">
	      <span class="orderTime" ng-cloak>订单时间： {{x.createTimeDesc}}</span>
	      </div>
	       <div class="p2">
	      <div class="orderDesc"><div class="orderdetail"  ng-cloak>选择菜品：{{x.orderDesc}}</div></div> 
	      </div>
	      </div>
	      <div class="right" onclick="GetDetail(this)"><span class="status" ng-cloak>订单{{x.orderStatusDesc}}&nbsp ></span></div>
	      
	   </li>

	</ul>

	</div>

    <div id="content1" style="display:none">
	    <img src="../image/order/kong.png"/><br>
	    <span> 空空如也</span>
	</div>

	<div id="footer"></div>
</body>

</html>

<script type="text/javascript">
	
var activityUid=${activityUid};
initfooter(activityUid,2);
function GetDetail(obj) {
	var orderId=$(obj).parent().find(".orderId").text();
	var orderStatus=$(obj).parent().find(".orderStatus").text();
	window.location.href="./myOrderDetail?activityUid="+activityUid +"&orderId="+orderId;

};

	var app = angular.module('myOrder', []);

	app.controller('orderController', function($scope, $http) {
		$http({
			method : 'GET',
			url : './getOrderInfo?activityUid='+activityUid
		}).then(function successCallback(response) {
			$scope.data = response.data.data;
			if($scope.data==null || $scope.data.length==0){
				document.getElementById("content1").style.display = "block";
				document.getElementById("content").style.display = "none";
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
		});

		
	});
</script>