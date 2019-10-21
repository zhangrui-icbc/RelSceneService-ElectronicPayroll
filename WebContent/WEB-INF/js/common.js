
//平台管理员
$(".header_admin").html('<div class="header1"><div class="logo">场景服务共享云平台</div></div><ul class="menu"><li><a href="./index">首页</a></li><li><a href="./logger">系统管理</a></li><div class="clear"></div></ul></div>'
		);
//公众号管理员
$(".header_mp").html('<div class="header1"><div class="logo">场景服务共享云平台</div></div><ul class="menu"><li><a href="./index">首页</a></li><li><a href="./myscene">我的场景</a></li><div class="clear"></div></ul></div>'
);

if($(".content").length>0){
	var sceneStatus;
	$.ajax({
		type : "POST",
		url : "./getSceneStatus",
		contentType:"application/json;charset=utf-8",
		async : false,
		success : function(data) {
			sceneStatus = data.data;
		}
	});
	var htmlstyle = '<div class="title">'
		+'<ul class="title_list">'
		+'<li class="title_listb"><div style="cursor:pointer;"><span class="iid" style="display:none">000</span><img class="y1" src="../image/com/a0.png" width="30" height="30"onclick="" /><span class="y2" style="color:#ffffff">全部</span></div><div class="clear"></div></li>';
	
	if (sceneStatus[0].status == 1) {
		htmlstyle = htmlstyle +'<li class="title_listb"><div style="cursor:pointer;"><span class="iid" style="display:none">order</span><img class="y1" src="../image/com/a2.png" width="30" height="30"onclick="" /><span class="y2" style="color:#ffffff">线上点餐</span></div><div class="clear"></div></li>';
	}
	
	htmlstyle = htmlstyle
		+'</ul>'
	    +'</div>';
	console.log(htmlstyle);
	$(".content").prepend(htmlstyle);
		
}else if($(".content2").length>0){
	var sceneStatus;
	$.ajax({
		type : "POST",
		url : "./getAllSceneStatus",
		contentType:"application/json;charset=utf-8",
		async : false,
		success : function(data) {
			sceneStatus = data.data;
		}
	});
	var htmlstyle = '<ul class="title_list">	'		
	+'<div class="subNav1"  style="color:#F5FFFA;" onclick="myscene()">我的场景</div>	'
	+'<div class="subNav currentDd" style="color:#F5FFFA;line-height:30px;">新增场景</div>'
	+'	<ul class="navContent">';
	
	if (sceneStatus[0].status == 1) {
		htmlstyle = htmlstyle +'		<li><a href="./ordercfg" style="color:#F5FFFA;">线上点餐</a></li>	';
	}
	if (sceneStatus[1].status == 1||sceneStatus[1].status == 0) {
		htmlstyle = htmlstyle +'		<li><a href="./salConfig" style="color:#F5FFFA;">工资报销管理</a></li>	';
	}
	
	htmlstyle = htmlstyle 
		+ '	</ul>	'	
		+'</ul>';
	console.log(htmlstyle);
	$(".content2").prepend(htmlstyle);
}
if($(".content3").length>0){
	$(".content3").prepend('<div class="title">'
	+'<ul class="title_list">'
	+'<li class="title_listb"><div style="cursor:pointer;"><span class="iid" style="display:none">007</span><img class="y1" src="../image/com/a7.png" width="30" height="30" /><span class="y2" style="color:#ffffff" onclick="mgLogger()">日志管理</span></div><div class="clear"></div></li>'
	+'<li class="title_listb"><div style="cursor:pointer;"><span class="iid" style="display:none">008</span><img class="y1" src="../image/com/a8.png" width="30" height="30" /><span class="y2" style="color:#ffffff" onclick="mgPara()">参数管理</span></div><div class="clear"></div></li>'
	+'<li class="title_listb"><div style="cursor:pointer;"><span class="iid" style="display:none">008</span><img class="y1" src="../image/com/a8.png" width="30" height="30" /><span class="y2" style="color:#ffffff" onclick="sceneSwitch()">场景开关</span></div><div class="clear"></div></li>'
	+'</ul>'
    +'</div>');
}


$(".footer").html('<ul class="mm">'
     /*  +'<li>网站声明</li> <li>服务网点</li> <li>网站地图</li>  <li>联系我行</li>  <li>服务热线95588</li>'*/
       +'<div class="clear"></div></ul>');

$(function() {	
	$(".subNav").click(
		function(){
			$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");//
			// 修改数字控制速度， slideUp(500)控制卷起速度
			$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
			});  
	
});

function myscene(){
	
	window.location.href="./myscene";
	
}

function mgLogger(){
	window.location.href="./logger";
}

function mgPara(){
	window.location.href="./parameter";
}

function sceneSwitch(){
	window.location.href="./sceneSwitch";
}

Date.prototype.format = function (format) {
    var args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),  
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var i in args) {
        var n = args[i];
        if (new RegExp("(" + i + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
    }
    return format;
};


function checklength(val) {
       var len = 0;
       for (var i = 0; i < val.length; i++) {
            var a = val.charAt(i);
            if (a.match(/[^\x00-\xff]/ig) != null) 
           {
               len += 2;
           }
           else
           {
               len += 1;
           }
       }
       return len;
       
   }

var mobile=/^1[3|5|6|7|8|9]\d{9}$/,phone=/^0\d{2,3}-?\d{7,8}$/;
function checkTelephone(value){
	return mobile.test(value)|| phone.test(value);
}

var time=/^(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]-(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]$/;
function checkTime(value){
	return time.test(value);
}