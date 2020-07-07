	//验证表单是否为空，若为空则将焦点聚焦在input表单上，否则表单通过，登录成功
	function check(){
	  var password = $("#pwd").val();
	  if(!password || password == ""){
		  actAlert("请输入密码");
	    return false;
	  }
	//这里为用ajax获取用户信息并进行验证，如果账户密码不匹配则登录失败，如不需要验证用户信息，这段可不写
	 $.ajax({
	    url : "./login",// 获取自己系统后台用户信息接口
	    data :{"password":hex_md5(password)},
	    type : "POST",
	    dataType: "json",
	    success : function(data) {
	        if (data.code =="0") { //判断返回值，这里根据的业务内容可做调整
	        	window.location.href ="./jumpIndex1";
	          } else{
	        	  actAlert(data.msg);
	          }
	      },
	      error : function(XMLHttpRequest, textStatus, errorThrown){
	    	  alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
	    	  alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
	    	  alert("textStatus:"+textStatus);
	    	  actAlert("网络异常,请切换网络后重试!");
	      }
	  });
	}
	
	$(".forget-pwd").click(function(){
		actAlert("请联系管理员初始化登录密码");
		  })
	function actAlert(msg){
		 layer.open({
		    content: msg,
		    btn: 'OK',
		    shadeClose: false,
		 });
		};