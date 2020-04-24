$(function() {
		getSalaryInfo();
	})
	/**
	 * 工资信息
	 * @returns
	 */
	function  getSalaryInfo(){
		 $.ajax({
			    url : "../salary/getSalaryInfo",// 获取自己系统后台用户信息接口
			    data :{},
			    type : "POST",
			    dataType: "json",
			    success : function(res) {
			        if (res.code == "0") { //判断返回值，这里根据的业务内容可做调整
			        //	$(".year").html(res.data[0].issueTime.substr(0,4));
			        	var str ="";
			        	for(var i = 0; i <res.data.length; i++){
			        		var arr =res.data[i];
			        		var issueTime= arr.issueTime.substring(0,10);
			        		var test = new RegExp(/-/g);
			        		var issueTime = issueTime.replace(test,"");
			        		var userId=arr.importList[1].userId;
			        		var aa;
			        		var bb;
			        		var cc;
			        		for(j in arr.importList){
			        			 //	   	实际收入
						 	      if(arr.importList[j].category==11){ 
						 	    	 aa = arr.importList[j].importAmount;
						 	          }
//						 	      收入合计
						 	      if(arr.importList[j].category==22){
						 	    	  bb=arr.importList[j].importAmount
						 	          }
//						 	      支出合计
						 	      if(arr.importList[j].category==33){
						 	    	 cc=arr.importList[j].importAmount
						 	          }
			        		}
			        		str+="<div class='list-box'>"+
			        		"<p class='left'><span class='year'>"+arr.issueTime.substr(0,4)+"</span><br><span>"+arr.issueTime.substr(5,6)+"</span><br><a class='detail-btn' onclick=jumpDetail('"+arr.id+"','"+issueTime+"','"+userId+"')>查看详情</a></p>"+
			        		"<div class='right'>"+
			        		"<p class='total'>实发合计<span>"+aa+"</span></p>"+
			        		"<div>"+
			        		"<p>收入合计<br><span>"+bb+"</span></p>"+
			        		"<p>支出合计<br><span>"+cc+"</span></p>"+
			        		"</div>"+
			        		"</div>"+
			        		"</div>"
			        	}
			        	console.log(str);
			        	$("#list").append(str);
			        	
			          } else {
			            return false;
			          }
			      },
			      error : function(res){
			      }
			  });
	}
	/**
	 * 跳转详情页
	 * @returns
	 */
	function jumpDetail(salaryId,issueTime,userId){
		window.location.href = "../salaryWebUser/jumpDetail?salaryId="+salaryId+"&&issueTime="+issueTime+"&&userId="+userId;
	}
	/**
	 * 汇总页面
	 * @returns
	 */
	function jumpSummery(){
		window.location.href = "../salaryWebUser/summary";
	}
	//验证表单是否为空，若为空则将焦点聚焦在input表单上，否则表单通过，登录成功
	function check(){
	  var username = $("#use").val();
	  var password = $("#pwd").val();
	  if(!username || username == ""){
		  actAlert("请输入用户名");
	    return false;
	  }
	  if(!password || password == ""){
		  actAlert("请输入密码");
	    return false;
	  }
	//这里为用ajax获取用户信息并进行验证，如果账户密码不匹配则登录失败，如不需要验证用户信息，这段可不写
	 $.ajax({
	    url : "../salaryWebUser/login",// 获取自己系统后台用户信息接口
	    data :{"password":password,"username":username},
	    type : "POST",
	    dataType: "json",
	    success : function(data) {
	      if (data){
	        if (data.code == "0") { //判断返回值，这里根据的业务内容可做调整
	        	window.location.href = "../salaryWebUser/query";
	          } else {
	            showMsg(data.message);//显示登录失败的原因
	            return false;
	          }
	        }
	      },
	      error : function(data){
	    	  actAlert(data.message);
	      }
	  });
	}
	//回首页
	$(".home-btn").click(function(){
		window.location.href = "../salaryWebUser/jumpIndex1";
	})
	function actAlert(msg){
		 layer.open({
		    content: msg,
		    btn: 'OK',
		    shadeClose: false,
		 });
		};
