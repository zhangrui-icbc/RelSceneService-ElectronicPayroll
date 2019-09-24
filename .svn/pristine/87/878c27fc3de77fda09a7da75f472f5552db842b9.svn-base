<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> /> 
<link rel="stylesheet" href="../css/order/ordercfg.css"  type="text/css"/> 
		<style>
			html{
				background: #F5F5F5;
			}
			body{
				background: #F5F5F5;
			}
			.nav{
				border-bottom: 1px solid #00A65A;
			}
			.nav span{
				display: inline-block;
			    list-style: none;
			    text-align: center;
			    padding: 6px 33px;
			    color:#00A65A;
			}
			.nav .qiu {
				border: 1px solid #00A65A;
			    border-bottom: 0;
			    background: #00A65A;
			    color: white;
			    border-radius: 5px 5px 0 0;
			}
			iframe{
				width: 100%;
			    border: 0px;
			        padding-top: 40px;
			}
		.klkl{ margin-left: 200px; margin-top: 80px;margin-bottom:0px;}
		</style>
</head>

<body>
<div class="header"> 
<div class="header_a">
<div class="header_mp"></div>
<div class="user"><span><%=session.getAttribute("mpName").toString()%></span></div>
</div>
</div>
 <input type="hidden" id="contextPath" name="contextPath"  value="${pageContext.request.contextPath}"/>
<div class="content2">
	<div class="vbm">
     	<div class="klkl">
     		 <div class="nav">
		    	<span class="qiu">工资单管理</span>
		    	<span>报销管理</span>
			</div>
			<iframe id="ifream1" class="detail" src="./salary" scrolling="auto" frameBorder=0 width="100%"></iframe>
			<iframe id="ifream2" class="detail" src="./reimbursement" style="display: none;"></iframe>
		</div>
	</div>
</div>
<script src="../js/common.js"></script>
<script src="../js/todo/jquery-ui.js"></script>
<script src="../js/todo/layer.js"></script>
<script>
//  选项卡切换
    $(function(){
    	changeFrameHeight();
    	$(".nav span").each(function(index) {
        	$(this).click(function(e) {
            	$(this).addClass("qiu").siblings().removeClass("qiu");
            	$(".detail").eq(index).show().siblings(".detail").hide();
        	})
		})
    });
//ifream自适应	
	function changeFrameHeight(){
        var ifm1= document.getElementById("ifream1");
        var ifm2= document.getElementById("ifream2");
        ifm1.height=document.documentElement.clientHeight-176;
        ifm2.height=document.documentElement.clientHeight-176;
    }
    window.onresize=function(){ changeFrameHeight();}
	</script>
</body>
</html>