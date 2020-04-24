$(function() {
		getReInfo();
	})
	/**
	 * 工资信息
	 * @returns
	 */
	function  getReInfo(){
		 $.ajax({
			    url : "../reimbursement/getReInfo",// 获取自己系统后台用户信息接口
			    data :{},
			    type : "POST",
			    dataType: "json",
			    success : function(res) {
			        if (res.code == "0") { //判断返回值，这里根据的业务内容可做调整
			        //	$(".year").html(res.data[0].issueTime.substr(0,4));
			        	var str ="";
			        	for(var i = 0; i <res.data.length; i++){
			        		var arr =res.data[i];
			        		var issueTime= arr.issueTime.substr(0,10);
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
			        		}
			        		str+="<div class='list-box'>"+
			        		"<p class='left'><span class='year'>"+arr.issueTime.substr(0,4)+"</span><br><span>"+arr.issueTime.substr(5,6)+"</span><br><a class='detail-btn' onclick=jumpDetail('"+arr.id+"','"+issueTime+"','"+userId+"')>查看详情</a></p>"+
			        		"<div class='right'>"+
			        		"<p class='total'>报销合计<span>"+aa+"</span></p>"+
			        		"<div>"+
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
		window.location.href = "../salaryWebUser/jumpReDetail?salaryId="+salaryId+"&&issueTime="+issueTime+"&&userId="+userId;
	}
	//回首页
	$(".home-btn").click(function(){
		//window.location.href = ctx+"/salaryWebUser/jumpIndex";
		window.location.href = "../salaryWebUser/jumpIndex1";
	})
	function actAlert(msg){
		 layer.open({
		    content: msg,
		    btn: 'OK',
		    shadeClose: false,
		 });
		};
