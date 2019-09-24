<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>提交成功</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all"/>
<link href="../css/common.css" rel="stylesheet" type="text/css"/>  
<link href="../css/success.css" rel="stylesheet" type="text/css"/>  
</head>

<body>
</div>
</div>
	      <div class="tttt"></div>
	       <!-- 这里是需要填充的主体内容 -->
	       <!-- <div style="height:300px;width:500px;">
	       <img style="height:300px;" src="com/image/z2.png"></img>
	       <p style="position: absolute;
    left: 310px;
    top: 190px;
    z-index: 0;
    -webkit-traansform: rotate(9deg);
    transform: rotate(300deg);">华为</p>
	       </div>
	       
	        -->
	       <div class="success">
	         <img src="../image/com/right.png"></img>
	         <h3 class="t1">提交成功</h3>
	         <p class="t2"></a></p>
	         <p class="t3">将此链接配置到融e联服务与管理平台的公众号主页，即可实现融e联客户端访问</p>
	          <button class="layui-btn layui-btn-radius" type="button"  onclick="returnHome()">返回</button>
	       </div>
<div class="footer"></div>
<script src="../js/common.js"></script>
<script>

var activityUrl=${activityUrl};

/* $("#url").attr('href',activityUrl); */
$(".t2").text(activityUrl);

function returnHome(){
	window.location.href='./myscene';
}


</script>
</body>
</html>
