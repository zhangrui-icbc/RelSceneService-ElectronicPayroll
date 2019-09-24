<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>

<head>
<link rel="stylesheet" href="../css/order/shoppingCar.css">
<meta name="viewport" content="user-scalable=no;">
<script src="../js/order/angular.min.js"></script>
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/order/common.js"></script>
<title>已购菜品</title>


</head>
<body ng-app="myCar" ng-controller='carController'>
	<!-- <div id="header"></div> -->
	
	<div id="content" >
	<span class="title"></span>
	<div class="gap"></div>
	<ul>
	   <li ng-repeat="x in dishes" >
	      <div class="dishUid" hidden ng-cloak>{{x.dishUid}}</div>
	      <div class="dishName"><div class="name" ng-cloak> {{x.dishName}}</div></div>
	       <div class="dishPrice" ng-cloak><span style="margin-right:10px;">¥</span>{{getprice(x.dishPrice)}}</div>
	      <div class="operate">
	         <div class="btn-del"><button onclick="del(this)"></button></div> 
	         <div class="amount" ng-cloak>{{x.dishAmount}}</div> 
	         <div class="btn-plus" ><button onclick="add(this)"> </button></div>
	      </div>
	     
	      <div class="price" style="display:none" ng-cloak>{{x.dishPrice}}</div>
	   </li>

	</ul>
<div class="gap"></div>
		<div id="totalPrice" >
		<span style="width:35%;height:100%;color:black;display:none;">合计金额：</span>
		<span style="width:25%;height:100%;text-align: right;display:none;" ng-cloak>¥{{totalPrice}}</span>
		<button id= "btn" class="submit" ng-click="SubmitData() ">下 单</button>
		</div>
		
	</div>

   <div id="content1" style="display:none">
   <img src="../image/order/kong.png"/><br>
	    <span> 空空如也</span>
	</div>

	<div id="footer"></div>
</body>

</html>

<script>
var u = navigator.userAgent;
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
if(isiOS == true){
	window.alert = function(name){
		var iframe = document.createElement("IFRAME");
		iframe.style.display="none";
		iframe.setAttribute("src", 'data:text/plain,');
		document.documentElement.appendChild(iframe);
		window.frames[0].window.alert(name);
		iframe.parentNode.removeChild(iframe);
	}
}	
var activityUid=${activityUid};
initfooter(activityUid,1);
	//加号触发的事件
	function add(obj) {
		//获取选中的菜品信息
		var dishName = $(obj).parent().parent().parent().find(".dishName").text();
		var dishPrice = $(obj).parent().parent().parent().find(".price").text();
		var dishUid =  $(obj).parent().parent().parent().find(".dishUid").text();

		$.post("./addShoppingCar",
			{dishUid : dishUid,
			dishName : dishName,
			dishPrice : dishPrice,
			flag : 1}, 
			function(data) {
			if(data.code!=1){
				alert(data.message);
				return;
			}
			data=data.data;
			var ngSection = document.querySelector('[ng-controller=carController]');
	        var scope = angular.element(ngSection).scope();
	        document.getElementById("content1").style.display = "none";
	        document.getElementById("content").style.display = "block";
			scope.dishes = data.data;
			scope.totalPrice=data.totalPrice;
			scope.$apply();
			$("#num")[0].innerText=data.count;
		});
	};

	//减号触发的事件
	function del(obj) {

		//获取选中的菜品信息
		var dishName = $(obj).parent().parent().parent().find(".dishName").text();
		var dishPrice = $(obj).parent().parent().parent().find(".price").text();
		var dishUid =  $(obj).parent().parent().parent().find(".dishUid").text();

		$.post("./addShoppingCar", {dishUid : dishUid,
			dishName : dishName,
			dishPrice : dishPrice,
			flag : -1}, 
			function(data) {
			var ngSection = document
					.querySelector('[ng-controller=carController]');
			var scope = angular.element(ngSection).scope();
			data=data.data;
			scope.dishes = data.data;
			
			scope.totalPrice=data.totalPrice;
			scope.$apply();
			if (scope.dishes.length == 0) {
				document.getElementById("totalPrice").style.display = "none";
				document.getElementById("num").style.display = "none";
				document.getElementById("content1").style.display = "block";
				document.getElementById("content").style.display = "none";
			} else {
				$("#num")[0].innerText=data.count;
				document.getElementById("totalPrice").style.display = "block";
				document.getElementById("num").style.display = "";
				document.getElementById("content1").style.display = "none";
				document.getElementById("content").style.display = "block";
			}
		});

	};
	
	var classifies = null;
	var app = angular.module('myCar', []);

	app.controller('carController', function($scope, $http) {
		$http({
			method : 'GET',
			url : './getShoppingCar?activityUid='+activityUid
		}).then(function successCallback(response) {
			var data=response.data.data;
			$scope.dishes = data.data;
			$scope.totalPrice=data.totalPrice;
			$scope.getprice=function(price){
				if(price==0){
				    return "实价";}
				else{
					return price;
				}
			};
			if ( data.data.length == 0) {
				document.getElementById("totalPrice").style.display = "none";
				document.getElementById("num").style.display = "none";
				document.getElementById("content1").style.display = "block";
				document.getElementById("content").style.display = "none";
			} else {
				$("#num")[0].innerText=data.count;
				document.getElementById("totalPrice").style.display = "block";
				document.getElementById("num").style.display = "";
				document.getElementById("content1").style.display = "none";
				document.getElementById("content").style.display = "block";
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
		});

		/*angularjs的$http提交*/
		$scope.SubmitData = function() {
			$.post('./submitorder', {activityUid:activityUid},function(data) {
				if(data.code=="-1"){
					alert(data.message);
					return;
				}
				window.location.href="./orderSuccess?orderId="+data.message+"&activityUid="+activityUid;
			});

		};
		
		

	});

</script>