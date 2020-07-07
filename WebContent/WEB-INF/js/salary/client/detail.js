var id = $("#id").val();
$(function() {
		detail();
	})
	
function  detail(){
	$.ajax({
	    url : "../salary/getSalaryDetail",// 获取自己系统后台用户信息接口
	    data :{"id":id},
	    type : "POST",
	    dataType: "json",
	    success : function(res) {
	        if (res.code == "0") { //判断返回值，这里根据的业务内容可做调整
	        	var importList= res.data;
	        	var specialJson =  JSON.parse(importList.specialInfo);
//	        	实际收入
	        	 $(".total_list").children().eq(0).html(importList.realIncome);
	        	   var txt1="<p><span>实际收入</span><span>"+importList.realIncome+"</span></p>";
		 	       $(".list-box").eq(0).prepend(txt1);
//		 	      收入合计 	
	        	 $(".total_list").children().eq(1).html(importList.totalRevenue);
	        	 var txt2="<p><span>收入合计</span><span>"+importList.totalRevenue+"</span></p>";
	 	         var item = specialJson.totalRevenue;
	 	        if(item!=undefined){
		 	         for (var prop in item) {
			 	     	    if (item.hasOwnProperty(prop)) {
			 	     		 txt2+="<p><span>"+prop+"</span><span>"+item[prop]+"</span></p>";
			 	     	    } 
			 	         }
	 	        }
		 	     $(".list-box").eq(1).prepend(txt2);
//		 	      支出合计    	
	        	 $(".total_list").children().eq(2).html(importList.totalExpenditure);
	        	 var txt3="<p><span>支出合计</span><span>"+importList.totalExpenditure+"</span></p>";
	 	         var item3 = specialJson.totalExpenditure;
	 	         if(item3!=undefined){
	 	        	 for (var prop in item3) {
	 	        		  if (item3.hasOwnProperty(prop)) {
	 	        			 txt3+="<p><span>"+prop+"</span><span>"+item3[prop]+"</span></p>";
	 	        			  } 
	 	        	 }
	 	         }
		 	     $(".list-box").eq(2).prepend(txt3);
//		 	     专项附加扣除   	
	        	if(importList.specialDeduction!=""&&importList.specialDeduction!=null&&importList.specialDeduction!='undefined'){
	        		var txt="<p><span>专项附加扣除</span><span>"+importList.specialDeduction+"</span></p>";
		 	        var item = specialJson.specialDeduction;
		 	        if(item!=undefined){
			 	       for (var prop in item) {
				 	    	   if (item.hasOwnProperty(prop)) {
				 	    		txt+="<p><span>"+prop+"</span><span>"+item[prop]+"</span></p>";
				 	    	   } 
				 	       }
		 	        }
		 	        $(".list-box").eq(3).prepend(txt);
	        	}else{
	        		//隐藏行
	        		$(".list-box").eq(3).hide();
	        	} 
//		 	      单位支出  result==null || result=="" || result=='undefined'
	        	if(importList.unitExpenditure!=""&&importList.unitExpenditure!=null&&importList.unitExpenditure!='undefined'){
	        		var txt="<p><span>单位支出</span><span>"+importList.unitExpenditure+"</span></p>";
		 	        var item = specialJson.unitExpenditure;
		 	        if(item!=undefined){
			 	       for (var prop in item) {
				 	    	   if (item.hasOwnProperty(prop)) {
				 	    		txt+="<p><span>"+prop+"</span><span>"+item[prop]+"</span></p>";
				 	    	   } 
				 	       }
		 	        }
		 	      $(".list-box").eq(4).prepend(txt);
	        	}else{
	        		//隐藏行
	        		$(".list-box").eq(4).hide();
	        	}
//		 	      备注
	 	       var remark = importList.salaryRemark.replace(/。/g,"。<br>");
	 	       txt="<p><span style='float: left;'>备注</span>&nbsp;<span>"+remark+"</span></p>";
	 	       $(".list-box").eq(5).prepend(txt);
	 	       
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

