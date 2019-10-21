var ctx = $("#contextPath").val().trim();	
$(function() {
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
		    url : ctx+"/com/salary/getSalaryInfo",// 获取自己系统后台用户信息接口
		    data :{"startDate":startDate,"endDate":endDate},
		    type : "POST",
		    dataType: "json",
		    success : function(json) {
		    	console.log(json);
				var data=json.data;
				for (var i in data){
					console.log(data[i].importList);
					var importList=data[i].importList;
					
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
			 	      
		//	 	     单位支出
			 	      if(category==44){
			 	       $(".list-box").eq(3).prepend(txt);
			 	          }
			 	      
			 	      if(category==1){
			 	       $(".list-box").eq(1).append(txt); 
			 	      }
			 	      if(category==2){
			 	    	  $(".list-box").eq(2).append(txt)
			 	      }  
			 	     if(category==4){
			 	    	  $(".list-box").eq(3).append(txt)
			 	      }
					}
				}
				var str = $(".list-box").eq(3).children().length;
	    		if(str==0){
	    			$(".list-box").eq(3).hide();
	    		}
		      },
		      error : function(res){
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
			date1.setMonth(date1.getMonth()-3);
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
			date1.setMonth(date1.getMonth()-6);
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
			//window.location.href = ctx+"/salaryWebUser/jumpIndex";
			window.location.href = ctx+"/com/salaryWebUser/jumpIndex1";
		})
		
		
		
		
		
function actAlert(msg){
 layer.open({
    content: msg,
    btn: 'OK',
    shadeClose: false,
 });
};
	
	
