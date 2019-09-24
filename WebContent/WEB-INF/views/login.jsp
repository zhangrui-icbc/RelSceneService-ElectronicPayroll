<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Login</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/layui/css/layui.css" type="text/css"/>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/jquery.cookie-1.4.1.js"></script>
<script src="js/mp-utils.js"></script>
<script src="css/layui/layui.js"></script>
</head>
<body>
<script>

$(document).ready(function(){ 
		if ($.cookie("rmbUser") == "true") { 
		$("#ckbox").prop("checked", true); 
		$("#username").val($.cookie("account")); 
		} 
	});
	
document.onkeydown = function(event) {
	var e = event || window.event|| arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) { // enter 键
		login();
	}
};
		
function login() {
	
	var objPassword = document.getElementById("in_password");
	var objVerify = document.getElementById("in_verify");
	objVerify.commitKeyPart(objPassword);	
	objVerify.commitKeyPart(objVerify);
	var userName = $("#username").val();
	if (userName.length== 0) {
		$("#alert-band").html("");
		$("<div></div>").appendTo($("#alert-band")).addClass("alert alert-danger").attr("role", "alert").html("您还没有输入账号！");
		return;
	}
	
	if (objPassword.getLength() == 0) {
		$("#alert-band").html("");
		$("<div></div>").appendTo($("#alert-band")).addClass("alert alert-danger").attr("role", "alert").html("您还没有输入密码！");
		return;
	}
	if (objVerify.getLength() == 0) {
		$("#alert-band").html("");
		$("<div></div>").appendTo($("#alert-band")).addClass("alert alert-danger").attr("role", "alert").html("您还没有输入验证码！");
		return;
	}
	var password = objPassword.getValue();
	if (Sys.ie) {
		password = password.substring(0, password.indexOf("&"));
		password=password.substring(5);
	} else if (Sys.chrome) {
		password =  password;
	}
	
	var verify = objVerify.getValue();
	if (Sys.ie) {
		verify = verify.substring(0, verify.indexOf("&"));
	} else if (Sys.chrome) {
		verify ="Edit="+verify;
	}	
	
	
	$(this).attr("disabled",true);
	
			
	 $.ajax({
		type: "POST",
		url: "./servlet/ICBCVerifyImage",
		data: verify,
		dataType: "text",
		success: function(d, s) {
            if(d=="0"){ 
            	$.ajax({
    				type : "POST",
    				data : {
    					"account" : userName,
    					"psd" : password
    				},
    				url : "./ad/checkUser",
    				async : false,
    				complete : function(data) {
    					if (data.responseJSON.code == "1") { 
    						window.location.href ="ad/index";
    					 } else {
    						 window.location.href="./login?in=0";
    						
    					}
    				}
    			});
            if ($("#ckbox").prop("checked")) { 
        		$.cookie("rmbUser", "true", { expires: 14 }); //存储一个带14天期限的cookie 
        		$.cookie("account", userName, { expires: 14 }); 
        		}else{ 
        		$.cookie("rmbUser", "false", { expire: -1 }); 
        		$.cookie("account", "", { expire: -1 }); 
        	} 
            
            }else{
            	 window.location.href="./login?in=1";
            }
             }
	});  
	 $(".loginbt").removeAttr("disabled");

}
	</script>
	<div style="display:none;"><jsp:include page="/servlet/ICBCSM4UniqueId" /></div>
	<div class="home">
		<p class="title">场景服务共享云平台</p>
		<div class="login-form">
			<h1 class="col-xs-12">登&nbsp;录</h1>
			<hr />
			<div id="alert-band"></div>
			<%
			String[] errorMsg={"用户名或密码不存在,请重新输入!","验证码不正确,请重新输入!"};
			if(request.getParameter("in")!=null){
				int index=Integer.parseInt(request.getParameter("in"));
				%>
				<script>
				$("#alert-band").html("");
				$("<div></div>").appendTo($("#alert-band")).addClass("alert alert-danger").attr("role", "alert").html("<%=errorMsg[index] %>");
				</script>
		<%	}
			%>
			<table>
				<tr>
					<td>用户名</td>
					<td><input type="text" id="username" value="" /></td>
				</tr>
				<tr>
					<td>密&nbsp;&nbsp;&nbsp;码</td>
					<td><!-- <input type="password" id="pass" value="" />  --><script>
function isIE(){
	if (window.ActiveXObject || "ActiveXObject" in window) {
		 return true;
	}else{
		return false;
	}
}

if (Sys.chrome) {
	document.write("<object id='in_password' type='application/x-icbc-plugin-chrome-npxxin-input' width=80% height=30>");
} else {
	if (navigator.cpuClass != null && "x64" == navigator.cpuClass) {
		document.write("<object id='in_password' classid='CLSID:2C0ABAB2-9C90-407c-AB4D-7130C547AA7B' width=80% height=30 codebase='image/AxSafeControls_64.cab#version=1,0,0,28'>");
	} else {
		document.write("<object id='in_password' classid='CLSID:73E4740C-08EB-4133-896B-8D0A7C9EE3CD' width=80% height=30 codebase='image/AxSafeControls.cab#version=1,0,0,28'>");
	} 
}


</script>
		<param name='name' value='Edit' >
					<param name='minLength' value='0'>
					<param name='maxLength' value='20'>
					<param name="IsPassword" value="1">
					<param name="CryptAlg" value="0">
					<param name="UniqueID" value="<%=request.getSession().getAttribute("UNIQUE_ID") %>">
				</object>
<script>
if (Sys.chrome) {
	var mimetype = navigator.mimeTypes["application/x-icbc-plugin-chrome-npxxin-input"];
	if (mimetype == null || mimetype.enabledPlugin == null) {
		document.write("<div>请点击<a href='image/ICBCChromeExtension.msi'>这里</a>下载安全控件</div>");
	}
}
</script></td>

				</tr>
				<tr>
					<td>验证码</td>
					<td><!-- <input type="text" style="width: 100px" id="verifycode"
						value="" /> --> <script>


if (isIE()) {
	if (navigator.cpuClass != null && "x64" == navigator.cpuClass) {
		document.write("<object id='in_verify' classid='CLSID:2C0ABAB2-9C90-407c-AB4D-7130C547AA7B' width=30% height=30 codebase='image/AxSafeControls_64.cab#version=1,0,0,28'>");
	} else {
		document.write("<object id='in_verify' classid='CLSID:73E4740C-08EB-4133-896B-8D0A7C9EE3CD' width=30% height=30 codebase='image/AxSafeControls.cab#version=1,0,0,28'>");
	}
} else if (Sys.chrome) {
	document.write("<object id='in_verify' type='application/x-icbc-plugin-chrome-npxxin-input' width=30% height=30>");
}
</script>
				 <param name="borderVisible" value="true">
					    <param name="nameEdit" value="Edit">
					    <param name="minLength" value="0">
					    <param name="maxLength" value="4">
					    <param name="rule" value="00001">    
					    <param name="ruleName" value="CodeRuleName3">
					    <param name="changeRuleName" value="CodeChangeRuleName">
					    <param name="IsPassword" value="0">
					    <param name="CryptAlg" value="0">  <%-- 加密选项：0.3DES算法, 1.SM4算法，2.RSA2048算法 --%>
					    <param name="UniqueID" value="<%=request.getSession().getAttribute("UNIQUE_ID") %>">
					</object>		
				<iframe src="./com/verifyCode" frameborder="0" width="180"
					height="36" scrolling="no" id="verify-code-frame"></iframe></td>
				</tr>
				<tr>

					<td></td>
					<td style="position: relative"><input type="checkbox"
						id="ckbox" value="" /> <span id="remember">记住用户名</span></td>
				</tr>
				<tr>
					<td></td>
					<td><button class="loginbt" onclick="login()">登&nbsp;录</button></td>
				</tr>
			</table>



			<div class="signin"></div>
		</div>
	</div>
</body>

</html>