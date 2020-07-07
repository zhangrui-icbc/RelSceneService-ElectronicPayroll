var id = $("#id").val();
$(function() {
		detail();
	})
	
function  detail(){
	$.ajax({
	    url : "../reimbursement/getReDetail",// 获取自己系统后台用户信息接口
	    data :{"id":id},
	    type : "POST",
	    dataType: "json",
	    success : function(res) {
	        if (res.code == "0") { //判断返回值，这里根据的业务内容可做调整
	        	var importList= res.data[0];
	        	var specialJson =  JSON.parse(importList.specialInfo);
//	        	报销合计
	        	$(".total_list").children().eq(0).html(importList.totalReim);
	        	var txt="<p><span>报销合计</span><span>"+importList.totalReim+"</span></p>";
	 	        var item = specialJson;
	 	        if(item!=undefined){
		 	         for (var prop in item) {
			 	     	    if (item.hasOwnProperty(prop)) {
			 	     		 txt+="<p><span>"+prop+"</span><span>"+item[prop]+"</span></p>";
			 	     	    } 
			 	         }
	 	        }
 	        	$(".list-box").eq(0).append(txt);    
		        } else {
		            return false;
	            }
	    		},
		      error : function(res){
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

