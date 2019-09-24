var ctx = $("#contextPath").val().trim();	
var accountId = $("#accountId").val();	
$("#query").click(function(){
		window.location.href = ctx+"/salaryWebUser/query";
	})
	
	$("#summary").click(function(){
		window.location.href = ctx+"/salaryWebUser/jumpSummary";
	})
	
		$("#reimbursement").click(function(){
		window.location.href = ctx+"/salaryWebUser/queryRe";
	})
	
	$("#reset").click(function(){
		$(".alert-pwd").show();
	})
	
	function check(){
	  var password1 = $("#npwd").val();
	  var password2 = $("#npwd1").val();
	  if(password1==null || password1 == ""){
		actAlert("请输入新密码");
	    return false;
	  }
	  if(password2==null || password2 == ""){
		 actAlert("请输入新密码");
	    return false;
	  }
	  
	//这里为用ajax获取用户信息并进行验证，如果账户密码不匹配则登录失败，如不需要验证用户信息，这段可不写
		 $.ajax({
		    url : ctx+"/salaryWebUser/resetPassword",// 获取自己系统后台用户信息接口
		    data :{"newPassword1":password1,"newPassword2":password2},
		    type : "POST",
		    dataType: "json",
		    success : function(data) {
		      if (data.code=="0"){
		    	  layer.open({
		    		     content: '修改成功,请重新登录!'
		    		     ,btn: ['确认']
		    		     ,yes: function(index){
		    		    	 window.location.href = ctx+"/salaryWebUser/jumpLogin1";
		    		     }
		    		 })
		        }else{
		        	 actAlert("密码为不含符号，不连续的6–12位数字或字母组合");
		        }
		      },
		      error : function(data){
		    	  actAlert("网络错误");
		      }
		  });
	  
	}
	
	
	function actAlert(msg){
		 layer.open({
		    content: msg,
		    btn: 'OK',
		    shadeClose: false,
		 });
		};