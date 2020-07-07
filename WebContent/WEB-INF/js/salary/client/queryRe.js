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
			        	var str ="";
			        	for(var i = 0; i <res.data.length; i++){
			        		var importList =res.data[i];
			        		var issueTime= importList.issueTime.substr(0,10);
			        		var userId=importList.userId;
			        		str+="<div class='list-box'>"+
			        		"<p class='left'><span class='year'>"+issueTime.substr(0,4)+"</span><br><span>"+issueTime.substr(5,5)+"</span><br><a class='detail-btn' onclick=jumpDetail('"+importList.id+"')>查看详情</a></p>"+
			        		"<div class='right'>"+
			        		"<p class='total'>报销合计<span>"+importList.totalReim+"</span></p>"+
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
	function jumpDetail(id){
		window.location.href = "../salaryWebUser/jumpReDetail?id="+id;
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
