<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>

<head>
<meta name="viewport" content="user-scalable=no;">
<link rel="stylesheet" href="../css/order/myOrderdetail.css">
<script src="../js/order/angular.min.js"></script>
<script src="../js/jquery-3.2.1.min.js"></script>
<title>我的订单</title>


</head>
<body ng-app="myOrder" ng-controller='orderController'>
	<div id="header">
	    <div class="he1"><img class="bigimg" src="../image/order/touxiang.png" /></div>
	    <div class="he2" ng-cloak>{{status}}<br>
	    <span class="he3">感谢您的信任，期待您的再次光临</span></div>
    </div>
    <div id="content">
    <div class="gap"></div>
	<div class="content1" >
	<div class="fd">菜品信息</div><hr>
	<ul>
	   <li ng-repeat="x in infos" on-finish>
	      <div class="dishName" ng-cloak ng-cloak>{{x.dishName}}</div>
	      <div class="dishAmount" ng-cloak>×{{x.dishAmount}}</div>
	      <div class="dishPrice" ng-cloak>{{getprice(x.dishPrice)}}</div>
	   </li>
	</ul>
<!--     <div class="totalAmt">合计{{totalAmt}}元</div> -->
	</div>
<div class="gap"></div>
    <div class="content1">
        <div class="fd">领取信息</div><hr>
        <ul>
            <li><span class="text1">领取时间：</span><span class="val1" ng-cloak>{{time}}</span></li>
            <li><span class="text1">领取地址：</span><span class="val1" ng-cloak>{{location}}</span></li>
            <li><span class="text1">联系方式：</span><span class="val1" ng-cloak>{{telephNo}}</span></li>
        </ul>
    </div>
    <div class="gap"></div>
    <div class="content1">
        <div class="fd">订单信息</div><hr>
        <ul>
            <li><span class="text1">订单号：</span><span class="val1" ng-cloak>{{orderId}}</span></li>
            <li><span class="text1">支付方式：</span><span class="val1">领取时支付</span></li>
            <li><span class="text1">下单时间：</span><span class="val1" ng-cloak>{{orderTimeDesc}}</span></li>
            <li><span class="text1">客户昵称：</span><span class="val1" ng-cloak>{{userName}}</span></li>
            <li><span class="text1">联系方式：</span><span class="val1" ng-cloak>{{mobileNo}}</span></li>
        </ul>
    </div>
    </div>
    
   


	<div id="footer">
		<button  ng-click="cancel()">取消订单 </button>

	</div>
</body>

</html>

<script>
    var activityUid=${activityUid};
    var activityName=${activityName};
    $(".fd").innerText=activityName;
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
	var app = angular.module('myOrder', []);
	
	app.directive('onFinish', function($timeout) {
		return {
			restrict : 'A',
			link : function(scope, element, attr) {
				if (scope.$last === true) {
					$timeout(function() {
						scope.$emit('ngRepeatFinished');
					});
				}
			}

		}
	});
	
    var orderId=${orderId};
	app.controller('orderController', function($scope, $http) {
		$http({
			method : 'GET',
			url : './getOrderDetail?orderId='+orderId+'&activityUid='+activityUid
		}).then(function successCallback(response) {
			var data=response.data.data;
			$scope.infos = data.infos;
			$scope.orderId = data.orderId;
			$scope.userName = data.userName;
			$scope.userStruName = data.userStruName;
			$scope.mobileNo = data.mobileNo;
			$scope.time = data.time;
			$scope.flag = data.flag;
			$scope.status = data.status;
			$scope.location = data.location;
			$scope.telephNo = data.telephNo;
			$scope.orderTimeDesc = data.orderTimeDesc;
			$scope.totalAmt = data.totalAmt;
			$scope.getprice=function(price){
				if(price==0){
				    return "实价";}
				else{
					return "¥"+price;
				}
			};
			if($scope.flag==1){
				$("button")[0].style.display="block";
			}else{
				$("button")[0].style.display="none";
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
		});

		$scope.cancel = function() {
			$.post('./updateOrderStatus',{"orderId":$scope.orderId,"status":-1,"activityUid":activityUid}, function(data) {
                alert("取消成功");
                location.reload();
				
			});

		};
		
        $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
			
			var dishnames=$(".dishName");
			for(var i=0;i<dishnames.length;i++){
				var name=dishnames[i].innerText;
				console.log(name.length);
				if(name.length<=16){
					dishnames[i].setAttribute('style','line-height:100px;');
				}else if(name.length>16 && name.length<=32){
					dishnames[i].setAttribute('style','line-height:50px;');
				}else{
					dishnames[i].setAttribute('style','line-height:50px;  height: 150px;');
				}
			}
			
		});
		
	});
</script>