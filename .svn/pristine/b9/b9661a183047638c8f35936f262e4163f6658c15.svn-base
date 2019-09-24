<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!Doctype html>
<html>
<head>
<meta name="viewport" content="user-scalable=no;">
<link rel="stylesheet" href="../css/order/order.css">
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/order/bootstrap.min.js"></script>
<script src="../js/order/angular.min.js"></script>
<script src="../js/order/fly.js"></script>
<script src="../js/order/requestAnimationFrame.js"></script>
<script src="../js/order/common.js"></script>
<script src="../js/ICBCutil.js"></script>
<script src="../js/icbc_core.js"></script>

<title>订餐</title>
</head>
<body ng-app="myApp" ng-controller='initController'>
	<div id="content" >
		<div class="right-content">
				 <ul>
					<li ng-cloak class="{{item.className}}" ng-repeat="item in infos2" on-finish>
					<div class="title" ng-cloak>》&nbsp{{item.classifyName}}&nbsp《</div>
					
					<div class="item" ng-repeat="ite in item.details ">
							<div class="item-img">
								 <img class="imageSize" ng-src="{{getpic(ite.picUrl,item.classifyName)}}"/>
								<!-- <img class="imageSize" ng-src="{{getpic(item.classifyName)}}"/> -->
							</div>
							<div class="item-dish">
								<div style="float: left; height:  60%; width: 100%;">
									<div class="dishName" ng-cloak>{{ite.dishName}}</div>
									<div class="totalAmount">总量{{ite.amount}}份</div>
									<div class="dishUid" hidden ng-cloak>{{ite.dishUid}}</div>
								</div>
								<div style="float: left; height: 40%; width: 100%;">
									 <div style="float: left; width: 80%; height: 100%">
										<div style="margin-top:5px" ng-cloak>
								<span class="price" style="display:none">{{ite.dishPrice}}</span> 
								<span class="desc1">¥</span><span class="dishPrice" ng-cloak>{{getprice(ite.dishPrice)}}</span><span class="desc2">/份</span>
										<span class="amount">剩余{{ite.availableAmount}}份</span>
										<span class="amount1" style="display:none">{{ite.amount}}</span>
										</div>
										
									</div> 
									<div style="float: left; width: 20%; height: 100%">
										<div class="mui-btn-numbox-plus"> <button onclick="addShoppingCar(this,event)"></button>
									</div>
								</div>

							</div>

						</div>
						
					</li>

				</ul>
		
	
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
var activityUid=${activityUid};
initfooter(activityUid,0);

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
//当前的客户id
ICBCUtil.getEncryptIMUserID({
	'callBack' : function(result) {
		console.log("B调c获取加密userid:"+result);
	$.post("./getImUserId?imuserid="+result,
			function(data){
		console.log(data.message);
		console.log(JSON.parse(data.message));
		});
	}
});
	var classifies = null;
	var app = angular.module('myApp', []);
	
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

	app.controller('initController', function($scope, $http) {
		$http({
			method : 'GET',
			url : './getTodayDinner?activityUid='+activityUid
		}).then(function successCallback(response) {
			var data=response.data.data;
			$scope.infos =data.data;
			$scope.infos2 = data.data;
			$scope.canOrder=data.canOrder;
			$scope.menuDate=data.menuDate;
			 $scope.getpic = function(url,title) { 
				/* $scope.getpic = function(title) { */
				 if (url != null) {
					return url;
				} else { 
					 return "../image/order/dish.jpg";
				} 				
			};
			$scope.getprice=function(price){
				if(price==0){
				    return "";}
				else{
					return price;
				}
			};
			if(data.count==0){
				document.getElementById("num").style.display="none";
				}else{
					document.getElementById("num").style.display="inline";
					$("#num")[0].innerText=data.count;}
			if($scope.infos=null || $scope.infos.length==0){
				document.getElementById("content1").style.display = "block";
		        document.getElementById("content").style.display = "none";
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
		});
		
		$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
           if($scope.canOrder=="1"){
        	   $("button").css("display","block");
           } else {
        	   $("button").css("display","none");
        	   }
						
			var amounts=$(".amount1");
			for(var i=0;i<amounts.length;i++){
				if(parseInt(amounts[i].innerText)<=0){
					$(amounts[i]).parent().parent().parent().find("button")[0].style.display="none";
				}
				
			}
			
			
		});
		
		

	});
	



	//加号触发的事件
	function addShoppingCar(obj,event)
	{
		//抛物线飞到购物车
		
        //获取选中的菜品信息
       var dishUid=$(obj).parent().parent().parent().parent().find(".dishUid").text();
       var dishName=$(obj).parent().parent().parent().parent().find(".dishName").text();
       var dishPrice=$(obj).parent().parent().parent().find(".price").text();
      
       //保存菜品信息到购物车
       addDishToShoppingCar(dishUid,dishName,dishPrice,event);
       
       
       
	}
	
	function refreshdinner()
	{
		$.post("./getTodayDinner?activityUid="+activityUid,
				function(data){
				var ngSection=document.querySelector('[ng-controller=initController]');
				var scope = angular.element(ngSection).scope();
				scope.infos = data.data.data;
				scope.infos2 = data.data.data;
				if(scope.infos=null || scope.infos.length==0){
					document.getElementById("content1").style.display = "block";
			        document.getElementById("content").style.display = "none";
				}
				if(data.data.count==0){
					document.getElementById("num").style.display="none";
					}else{
						document.getElementById("num").style.display="inline";
						$("#num")[0].innerText=data.data.count;}
				
				scope.$apply();
			});
	}
	
	
	
	function addDishToShoppingCar(dishUid,dishName,dishPrice,event)
	{
		$.post("./addShoppingCar",
				{dishUid:dishUid,
			dishName:dishName,
			dishPrice:dishPrice,
			flag:1},function(data){
				if(data.code!=1){
					alert(data.message);
				}else{
				fly(event);
				document.getElementById("num").style.display="inline";
				$("#num")[0].innerText=data.data.count;
				}
				refreshdinner();
			});
	}
	
	var endwidth=$(document).width()/2;
	var endheight=$(document).height()-100;
	var flyer = $('<img class="u-flyer" src="../image/order/add.png">'); 
	function fly(event)
	{
		
        
        flyer.fly({ 
            start: { 
                left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed 
                top: event.pageY, //开始位置（必填） 
                width: 48, //结束时宽度 
                height: 48 ,//结束时高度 
           },
            end: { 
                left: endwidth, //结束位置（必填） 
                 top: endheight, //结束位置（必填） 
               width: 48, //结束时宽度 
               height: 48 ,//结束时高度 
           },
            onEnd: function(){ //结束回调 
                this.destory(); //移除dom 
            },
        });
	}
	

	
	
	
</script>



