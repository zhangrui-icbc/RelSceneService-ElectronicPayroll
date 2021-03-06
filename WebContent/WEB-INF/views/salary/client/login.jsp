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
		<link rel="stylesheet" href="../../css/salary/client/common.css">
		<link rel="stylesheet" href="../../css/salary/client/login.css">
	</head>
	<body>
		<!--登陆框-->
		<!-- <img th:src="@{/oa/salary/images/back.png}" /> -->
        <div class="g-shade">
    		<input type="password" class="input-val" id="pwd" placeholder="请输入密码"/>
            <button class="login-btn" onclick="check()">确认</button>
            <div  class="forget-pwd">忘记密码？</div>
        </div>
        <div id="CheckMsg" class="msg"></div>
		<script src="../../js/jquery-3.2.1.min.js"></script>
		<script src="../../js/salary/client/layer.js"  ></script>
		<script src="../../js/salary/client/login.js?version=20200616"  ></script>
		<script src="../../js/salary/md5.js"></script>
		<script src="../../js/ICBCutil.js"></script>
		<script src="../../js/icbc_core.js"></script>
        <script type="text/javascript" th:inline="javascript" > 
        		//当前的客户id
				ICBCUtil.getEncryptIMUserID({
					'callBack' : function(result) {
						console.log("B调c获取加密userid:"+result);
					$.post("${pageContext.request.contextPath}/com/getImUserId?imuserid="+result,
							function(data){
						console.log(data.message);
						console.log(JSON.parse(data.message)); 
						});
					}
				});
        
				$('input,textarea').on('blur',function(){
				window.scroll(0,0);
				});
				$('select').on('change',function(){
				window.scroll(0,0);
				});
		</script> 
	</body>
</html>

