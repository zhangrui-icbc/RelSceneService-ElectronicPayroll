$(function() {
	$(".three-btn").click();
	})
//查询
	$(".query-btn").click(function(){
		var startDate = $(".startDate").val();
		var endDate = $(".endDate").val();
		  if(startDate==null || startDate == ""){
			  actAlert("请输入起始日期");
		    return false;
		  }
		  if(endDate==null || endDate == ""){
			  actAlert("请输入结束日期");
		    return false;
		  }
		
		$(".list-box").eq(0).children().remove();
		$(".list-box").eq(1).children().remove();
		$(".list-box").eq(2).children().remove();
		$(".list-box").eq(3).children().remove();
		$(".list-box").eq(4).children().remove();
		 document.getElementById("real_income").innerHTML = "0.00";
		 document.getElementById("total_income").innerHTML = "0.00";
		 document.getElementById("total_expenditure").innerHTML = "0.00";
		$.ajax({
		    url : "../salary/getSalaryInfo",// 获取自己系统后台用户信息接口
		    data :{"startDate":startDate,"endDate":endDate},
		    type : "POST",
		    dataType: "json",
		    success : function(res) {
		    	console.log(res);
		        if (res.code == "0") { //判断返回值，这里根据的业务内容可做调整
		        	// TODO 判断data为空的情况
		        	if(res.data!=""&&res.data.length!=0){
			        	var importList= res.data[0];
			        	var specialJson =  JSON.parse(importList.specialInfo);
//			        	实际收入
			        	 $(".total_list").children().eq(0).html(importList.realIncome);
			        	   var txt1="<p><span>实际收入</span><span>"+importList.realIncome+"</span></p>";
				 	       $(".list-box").eq(0).prepend(txt1);
//				 	      收入合计 	
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
//				 	      支出合计    	
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
//				 	     专项附加扣除   	
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
//				 	      单位支出  result==null || result=="" || result=='undefined'
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
//				 	      备注
			 	       var remark = importList.salaryRemark.replace(/。/g,"。<br>");
			 	       txt="<p><span style='float: left;'>备注</span>&nbsp;<span>"+remark+"</span></p>";
			 	       $(".list-box").eq(5).prepend(txt);
		        	}
		        	
			        } else {
			            return false;
			          }
		      },
		      error : function(res){
		    	  actAlert("访问失败!");
		      }
		  });
		})
		
		
function common(startDate,endDate){
	$(".startDate").val(startDate);
	$(".endDate").val(endDate);  
	$(".query-btn").click();
	
}
		
		//当前日期
var date=new Date;
var year=date.getFullYear();
var month=date.getMonth()+1;
month =(month<10 ? '0'+month:month);
eDate = (year.toString()+'-'+month.toString());



//今年
var this_year=year.toString()+'-'+month.toString()
		$(".three-btn").click(function(){
			var date1 = new Date();
			date1.setMonth(date1.getMonth()-2);
			var year1=date1.getFullYear();
			var month1=date1.getMonth()+1;
			month1 =(month1<10 ? '0'+month1:month1);
			three_mon = (year1.toString()+'-'+month1.toString());
			console.log(this_year);
			console.log(three_mon);
			common(three_mon,this_year);
		})
		
		$(".six-btn").click(function(){
			var date1 = new Date();
			date1.setMonth(date1.getMonth()-5);
			var year1=date1.getFullYear();
			var month1=date1.getMonth()+1;
			month1 =(month1<10 ? '0'+month1:month1);
			six_mon = (year1.toString()+'-'+month1.toString());
			console.log(this_year);
			console.log(six_mon);
			common(six_mon,this_year);
		})
		$(".year-btn").click(function(){
			var date1 = new Date();
			var year1=year.toString()+'-01';
			console.log(this_year);
			console.log(year1);	
			common(year1,this_year);
		})
		$(".lastyear-btn").click(function(){
			var date1 = new Date();
			var year1=date1.getFullYear();		
			var lastYear1 = (year-1).toString()+'-01';
			var lastYear2 = (year-1).toString()+'-'+'12';
			console.log(lastYear1);
			console.log(lastYear2);
			common(lastYear1,lastYear2);
		})
		
		//回首页
		$(".home-btn").click(function(){
			window.location.href = "../salaryWebUser/jumpIndex1";
//			window.event.returnValue=false;  
		})
		
		
		
		
		
function actAlert(msg){
 layer.open({
    content: msg,
    btn: 'OK',
    shadeClose: false,
 });
};
	
	
