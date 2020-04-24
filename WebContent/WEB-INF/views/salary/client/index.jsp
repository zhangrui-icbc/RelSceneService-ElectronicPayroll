<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="yes" name="apple-mobile-web-app-capable" />
    	<meta content="yes" name="apple-touch-fullscreen" />
    	<meta name="data-spm" content="a215s" />
    	<meta content="telephone=no,email=no" name="format-detection" />
    	<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<title>工资单查询</title>
		<style>
			a{ -webkit-tap-highlight-color:rgba(255,0,0,0);}
		</style>
		<link rel="stylesheet" href="../../css/salary/client/common.css">
		<link rel="stylesheet" href="../../css/salary/client/index.css">
	</head>
	<body>
	<input id="accountId" type="hidden" th:value="${session.accountId}" />
	<input id="jumpUrl" type="hidden" th:value="${session.currentUrl}" />
		<div class="btn-box">
			<button class="btn"  id="query"><img src="../../image/salary/client/xinzi-btn.png" /></button>
			<button class="btn" id="summary"><img src="../../image/salary/client/huizong-btn.png" /></button>
			<button class="btn" id="reimbursement"><img src="../../image/salary/client/reimbursement-btn.png" /></button>
			<button class="btn" id="reset"><img src="../../image/salary/client/pwd-btn.png" onclick="show()"/></button>
		</div>
		<img class="btn-back" src="../../image/salary/client/btn-back.png"  />
		<div class="alert-pwd" style="display: none;">
			<p  class="return">关闭</p>
			<div class="pwd-box">
				<input id="npwd" type="text" placeholder="请输入新密码"/><br />
				<input id="npwd1" type="text" placeholder="请再次输入密码"/><br />
				<button class="submin-btn" onclick="check()">确认</button>
			</div>
			<img class="back" src="../../image/salary/client/btn-back.png" />
		</div>
	    <script src="../../js/jquery-3.2.1.min.js"></script>
		<script src="../../js/salary/client/layer.js"  ></script>
		<script src="../../js/salary/client/index.js"  ></script>
		<script src="../../js/salary/md5.js"></script>
        <script type="text/javascript" th:inline="javascript" > 
				function show(){
					$(".alert-pwd").show();
				}
				$('input,textarea').on('blur',function(){
				window.scroll(0,0);
				});
				$('select').on('change',function(){
				window.scroll(0,0);
				});
				$(".return").click(function(){
					   $(".alert-pwd").hide();
					  })
		</script>
		</body>
</html>








