var ctx = $("#contextPath").val().trim();	
var salaryId = $("#salaryId").val();
var issueTime = $("#issueTime").val();
var userId = $("#userId").val();
$(function() {
		detail();
	})
	
function  detail(){
	$.ajax({
	    url : ctx+"/com/salary/getSalaryDetail",// 获取自己系统后台用户信息接口
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
//			 	      收入合计
			 	      if(category==22){
			 	       $(".list-box").eq(1).prepend(txt);
			 	       $(".total_list").children().eq(1).html(list.importAmount);
			 	       
			 	          }
//			 	      支出合计
			 	      if(category==33){
			 	       $(".list-box").eq(2).prepend(txt);
			 	       $(".total_list").children().eq(2).html(list.importAmount);
			 	       
			 	          }
//			 	      单位支出
			 	      if(category==44){
			 	       $(".list-box").eq(3).prepend(txt);
			 	          }
//			 	     专项附加扣除
			 	      if(category==66){
			 	    	  $(".list-box").eq(4).prepend(txt);
			 	      }
//			 	      备注
			 	      if(category==55){
			 	       txt="<p><span>"+list.templateColName+"</span>&nbsp;<span>"+list.importAmount+"</span></p>";
			 	       $(".list-box").eq(5).prepend(txt);
			 	       
			 	          }
			 	      
			 	     var str = $(".list-box").eq(3).children().length;
			 	    
			 	      if(category==1){
			 	       $(".list-box").eq(1).append(txt); 
			 	      }
			 	      if(category==2){
			 	    	  $(".list-box").eq(2).append(txt)
			 	      }
			 	     if(category==4){
				    		if(str==0){
				    			$(".list-box").eq(3).hide();
				    		}
			 	    	  $(".list-box").eq(3).append(txt)
			 	      }
			 	    if(category==5){
			 	    	  $(".list-box").eq(5).append(txt)
			 	      }
			 	    var str2 = $(".list-box").eq(4).children().length;
			 	    if(category==6){
			    		if(str2==0){
			    			$(".list-box").eq(4).hide();
			    		}
			 	    	  $(".list-box").eq(4).append(txt)
			 	      }
					}
		        
		        	//循环外
		    		if(str==0){
		    			$(".list-box").eq(3).hide();
		    		}
		    		if(str2==0){
		    			$(".list-box").eq(4).hide();
		    		}
		    		
		        	var str1 = $(".list-box").eq(5).children().length;
		    		if(str1==0){
		    			$(".list-box").eq(5).hide();
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

