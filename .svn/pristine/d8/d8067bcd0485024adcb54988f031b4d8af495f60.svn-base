



function initfooter(activityUid,num){
	$("#footer").html('<div id="homepage" class="img_wrap" style="width: 33%; float: left">'
		+'<img id="homePageimg" style="width: 60px;" src="../image/order/homew.png"/><br/>'
	    +'<span style="font-size:  30px;  color: #333333;">首页</span>'
        +'</div>'
        +'<div id="shoppingCar" class="img_wrap" style="width: 33%; float: left">'
	    +'<img id="shoppingCarimg" style="width: 60px;" src="../image/order/carw.png"/>'
        +'<sup id="num" style="display: none"></sup><br/>'
        +'<span style="font-size:  30px;    color: #333333;">购物车</span>'
        +'</div>'
        +'<div id="myorder" class="img_wrap" style="width: 34%; float: left">'
        +'<img id="myorderimg" style="width: 60px;" src="../image/order/userw.png"/><br/>'
        +'<span style="font-size: 30px;    color: #333333;">我的订单</span>'
        +'</div>');
	
	var span=$("#footer span");
	span[num].style.color="red";
	if(num==0){
		$("#homePageimg")[0].src="../image/order/home.png";
	}else if(num==1){
		$("#shoppingCarimg")[0].src="../image/order/car.png";
	}else if(num==2){
		$("#myorderimg")[0].src="../image/order/user.png";
	}
	
	$("#homepage").click(function(){
		window.location.href="./order?activityUid="+activityUid;
	});

	$("#shoppingCar").click(function(){
		window.location.href="./shoppingCar?activityUid="+activityUid;
	});
	$("#myorder").click(function(){
		window.location.href="./myOrder?activityUid="+activityUid;
	});
};


