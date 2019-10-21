var ctx = $("#contextPath").val().trim();	
var salaryId = $("#salaryId").val();
var issueTime = $("#issueTime").val();
var userId = $("#userId").val();
$(function() {
		detail();
	})
	
function  detail(){
	$.ajax({
	    url : ctx+"/com/reimbursement/getReDetail",// 获取自己系统后台用户信息接口
	    data :{"salaryId":salaryId,"issueTime":issueTime,"userId":userId},
	    type : "POST",
	    dataType: "json",
	    success : function(res) {
	        if (res.code == "0") { //判断返回值，这里根据的业务内容可做调整
	        	var importList= res.data;
		        	for(j in importList){
						var list=importList[j];
						var category=list.category;
						var templateColName=list.templateColName;
						var importAmount=list.importAmount;
			 	   		var txt="<p><span>"+list.templateColName+"</span><span>"+list.importAmount+"</span></p>";		
			 //	   	实际收入
			 	      if(category==11){ 
			 	       $(".list-box").eq(0).prepend(txt);
			 	       $(".total_list").children().eq(0).html(list.importAmount);
			 	          }
			 	      if(category==0){
			 	       $(".list-box").eq(0).append(txt); 
			 	      }
			 	      
					}
		        
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
	window.location.href = ctx+"/com/salaryWebUser/jumpIndex1";
})
function actAlert(msg){
	 layer.open({
	    content: msg,
	    btn: 'OK',
	    shadeClose: false,
	 });
	};

