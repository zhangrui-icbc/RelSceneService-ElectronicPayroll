<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../js/common.js"  type="text/css"/> 
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> 
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link href="../css/index.css" rel="stylesheet" type="text/css"/>  

</head>
<body>

<% 
 if(session.getAttribute("loginType")!=null && session.getAttribute("loginType").toString().equals("1")){
	
	%>
	 <div class="header"> 
<div class="header_a">
<div class="header_admin"></div>
<div class="user"><span><%=session.getAttribute("adminName").toString()%></span></div>
</div>
</div>
	<% 
 }else{
	 %>
	 <div class="header"> 
<div class="header_a">
<div class="header_mp"></div>
<div class="user"><span><%=session.getAttribute("mpName").toString()%></span></div>
</div>
</div>
	<% 
	 
 }
%>
<div class="content">
<div class="vbm">
     <div class="klkl">
	      <div class="main">	       
	         
			<div class="layui-carousel" id="lunbotu">
			  <div carousel-item="">
			    <div><img src="../image/dzp.png"></div>
			    <div><img src="../image/dc.png"></div>			     
			    <div><img src="../image/meeting.png"></div>
			    <div><img src="../image/ask.png"></div>
			  </div>
			</div>
	       	<!-- <fieldset class="layui-elem-field layui-field-title " style="margin: 10px 0 10px;">
  			 <legend style="margin-left: 20px;padding: 0 10px;font-size: 10px;font-weight: 300">展示场景数据 - 以表格为例</legend>
		    </fieldset> -->
		   		<div class="layui-card">
	             <div class="layui-card-header">全量场景</div>
	             <div class="layui-card-body" >
  		     <table id="demo" lay-filter="test" class="layui-table"></table> 
  		      <script type="text/html" id="barDemo">                     
                 
				   <a class="layui-btn layui-btn-xs" lay-event="analysis">统计分析</a>
                     
                   </script>	
  		         </div></div>          
	  </div>
	 </div>
</div>
</div><!-- </div> -->
<div class="footer"></div>
<script src="../js/index.js"></script>
<script src="../js/common.js"></script>
<script type="text/html" id="xuhao">
{{d.LAY_TABLE_INDEX+1}}
</script>
</body>
</html>