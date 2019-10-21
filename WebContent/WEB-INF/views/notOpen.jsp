<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>地区未开放</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/order/ordercfg.js"></script>
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> /> 
<link rel="stylesheet" href="../css/order/ordercfg.css"  type="text/css"/> 
p {
	font-size:50px;
	line-height:80px;
	text-align:center;
	margin-top:500px;
}
</head>

<body>

<div class="header"> 
<div class="header_a">
<div class="header_mp"></div>
<div class="user"><span><%=session.getAttribute("mpName").toString()%></span></div>
</div>
</div>
<div class="content2">
<div class="vbm">
     <div class="klkl">
		<p style="text-align: center;font-size: 45px;top: 40%;position: absolute;left: 40%;">该场景并未对当前地区开放</p>
	</div>
</div>
</div>
<div class="footer"></div>
<div id="diag">
 <table id="detailTable" lay-filter="detailfilter" style="width:100%"></table>
</div>
	       
<script src="../js/common.js"></script>
<script>

</script>



</body>
</html>