var ctx = $("#contextPath").val().trim();	
var accountId = $("#accountId").val();	
$("#query").click(function(){
		window.location.href = ctx+"/com/salaryWebUser/query";
	})
	
	$("#summary").click(function(){
		window.location.href = ctx+"/com/salaryWebUser/jumpSummary";
	})
	
		$("#reimbursement").click(function(){
		window.location.href = ctx+"/com/salaryWebUser/queryRe";
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
	  var reg=/^[A-Za-z0-9]{6,12}$/;
      if(!reg.test(password1)){
    	  actAlert("密码必须为6-12位的数字和字母的组合"); 
          return false;
      }
	  
	  var i= trans(password2).count;
	  if(trans(password2).count==1){
		  actAlert("密码为不含符号，不连续的6–12位数字或字母组合");
		    return false;
	  }
	  
	//这里为用ajax获取用户信息并进行验证，如果账户密码不匹配则登录失败，如不需要验证用户信息，这段可不写
		 $.ajax({
		    url : ctx+"/com/salaryWebUser/resetPassword",// 获取自己系统后台用户信息接口
		    data :{"newPassword1":hex_md5(password1),"newPassword2":hex_md5(password2)},
		    type : "POST",
		    dataType: "json",
		    success : function(data) {
		      if (data.code=="0"){
		    	  layer.open({
		    		     content: '修改成功,请重新登录!'
		    		     ,btn: ['确认']
		    		     ,yes: function(index){
		    		    	 window.location.href = ctx+"/com/salaryWebUser/jumpLogin1";
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
/**
 * 密码连续校验
 * @param str
 * @returns
 */
function trans (str) {
	  let before = ''
	  let len = 0
	  let order = null
	  let matched = []
	  for (let i = 0, length = str.length; i < length; i++) {
	    let cur = str[i]
	    if (len === 0) {
	      before = cur
	      len = 1
	      order = null
	      continue
	    }

	    let diff = cur.charCodeAt(0) - before.charCodeAt(0)
	    if (Math.abs(diff) === 1) {
	      order = order || diff
	      if (order === diff) {
	        len += 1
	        before = cur
	        continue
	      }
	    }
	    if (len >= 4) {
	      matched.push(str.slice(i - len, i))
	    }
	    before = cur
	    len = 1
	    order = null
	  }
	  if (len >= 4) {
	    matched.push(str.slice(str.length - len))
	  }

	  return {
	    count: matched.length,
	    matched
	  }
	}
	
	function actAlert(msg){
		 layer.open({
		    content: msg,
		    btn: 'OK',
		    shadeClose: false,
		 });
		};