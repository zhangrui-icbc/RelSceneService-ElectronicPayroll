<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>点餐配置</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/order/ordercfg.js"></script>
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> 
<link rel="stylesheet" href="../css/order/ordercfg.css"  type="text/css"/> 

</head>
<body>
<div class="header"> 
<div class="header_a">
<div class="header_mp"></div>
<div class="user"><span><%=session.getAttribute("mpName").toString()%></span></div>
</div>
</div>
<div class="content2">
<div class="vbm">
     <div class="klkl">
	      <div class="tttt"></div>
	       <!-- 这里是需要填充的主体内容 -->
	       <div class="main">
	        <div id="circle-panel">
	        <div id="param-panel">
	         <div class="layui-card">
	             <div class="layui-card-header">参数配置</div>
	             <div class="layui-card-body para-card" >
	               <form class="layui-form" id="test" lay-filter="main-filter">
	                 <div class="layui-form-item">
	                     <label class="layui-form-label">活动名称<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                         <input type="text" name="activityName" class="layui-input mouseover" ></input>
	                         <input type="text" name="iID" class="layui-input  hide" ></input>
	                     </div>
	                 </div>
	                 <div class="layui-form-item">
	                     <label class="layui-form-label">活动描述<span class="red">*</span></label>
	                     <div class="layui-input-inline" >
	                         <textarea style="width:400px;min-height:60px;" maxlength="50" name="activityDesc" class="layui-textarea mouseover" ></textarea>
	                     </div>
	                 </div>
	                 <div class="btngroup">
	                  <button class="layui-btn" type="button" id="btnsave" lay-filter="btnsave" lay-submit>保存</button>
	                 <button class="layui-btn" type="button"  id="btnreturn" onclick="returnLast()">返回</button>
	               </div></form>
	             </div>
	        </div>
	        </div>
	        
	 </div></div>
	  </div>
	        
	 </div></div>
<div class="footer"></div>
<div id="diag">
 <table id="detailTable" lay-filter="detailfilter" style="width:100%"></table>
</div>
	       
<script src="../js/common.js"></script>
<script>
var flag=${flag};//0：新增  1：编辑  
var activityUid=${activityUid};
var salParaInfo=${data};
//选项卡
layui.use(['element','laydate','table','form','upload'],function(){
	var laydate=layui.laydate;
	var table = layui.table;
	var form=layui.form;
	var upload=layui.upload;
	 var nowTime=new Date(); 
	 
	 var orderType=0;
	 var date=new Date();
	 var minTime={
			 year:date.getFullYear(),
       	     month:date.getMonth(),//关键
             date:date.getDate(),
             hours:0,
             minutes:0,
             seconds:0}
	  var maxTime={
		   year:date.getFullYear(),
       	   month:date.getMonth(),
           date:date.getDate(),
           hours:23,
           minutes:59,
           seconds:59
	  }
	  
	 var startTime=laydate.render({
	    	elem:'#orderBeginTime',
	    	type:'time',
	    	format:'HH:mm:ss',
	    	btns: ['confirm'],
	    	//max:'nowTime',//默认最大值为当前日期
	    	done:function(value,date){
	    		console.log(value); //得到日期生成的值，如：2017-08-18
//	    	    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
	    	    if(orderType==0){
	    	    	endTime.config.max=maxTime;
	    	    	endTime.config.min={  
	    	    	year:date.year,
	        	    month:date.month-1,//关键
	                date:date.date,
	                hours:date.hours,
	                minutes:date.minutes,
	                seconds:date.seconds
	    	    };}else{
	    	    	endTime.config.min=minTime;
	    	    	endTime.config.max={  
	    	    	    	year:date.year,
	    	        	    month:date.month-1,//关键
	    	                date:date.date,
	    	                hours:date.hours,
	    	                minutes:date.minutes,
	    	                seconds:date.seconds
	    	    };}
	    	    
	    	}
	    });
	    var endTime=laydate.render({
	    	elem:'#orderEndTime',
	    	type:'time',
	    	btns: ['confirm'],
	    	format:'HH:mm:ss', 
	    	//max:'nowTime',
	    	done:function(value,date){
	    		console.log(value); //得到日期生成的值，如：2017-08-18
//	    	    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}    	   
	    	    if(orderType==0){
	    	    	startTime.config.min=minTime;
	    	    	startTime.config.max={
		    	    		year:date.year,
			        	    month:date.month-1,//关键
			                date:date.date,
		                    hours:date.hours,
		                    minutes:date.minutes,
		                    seconds:date.seconds
		    	    };}else{
		    	    	startTime.config.max=maxTime;
		    	    	startTime.config.min={
			    	    		year:date.year,
				        	    month:date.month-1,//关键
				                date:date.date,
			                    hours:date.hours,
			                    minutes:date.minutes,
			                    seconds:date.seconds
			    	    };
		    	    }
	    	    }
	    });
	  form.on('select(filterOrderTimeType)',function(data){
		  orderType=data.value;
		  $("#orderBeginTime").val('');
		  $("#orderEndTime").val('');
		  
		  startTime.config.min=minTime;
		  startTime.config.max=maxTime;
		  endTime.config.min=minTime;
		  endTime.config.max=maxTime;
		});
	laydate.render({
		elem:'#cancelTime',
		type:'time',
		format:'HH:mm:ss',
	});
	if(flag==1){
		 form.val('main-filter',salParaInfo); 
	}
	//保存所有配置信息
	 form.on('submit(btnsave)',function(data){
			var info=data.field;
			if(info.isSendMsg=="on"){
				info.isSendMsg=1;
			}else{
				info.isSendMsg=0;
			}
			info.activityUid=activityUid;
			var flag=check(info);
			if(!flag){
				return;
			}
			console.log(JSON.stringify(info));
			
				$.ajax({
					type : "POST",
					data :JSON.stringify(info),
					url : "./saveSalInfo",
					dataType:"json",
					contentType:"application/json;charset=utf-8",
					async : false,
					complete : function(data) {
						if (data.responseJSON.code == 1) {
							window.location.href='./success?activityUid='+data.responseJSON.message;
						} else {
							alert("fail");
						}
					}
				});
			
			
			
			
		});

}); 



//检查提交的元素
function check(data){
	var content="";
	if(data.activityName==""){
		content="请填写活动名称！";
	}else if(data.activityDesc==""){
		content="请填写活动描述！";
	}
	if(content.length==0){
		return true;
	}
	layer.open({
		type:0,
		title:'提示',
		content:content
	});
	return false;
	}
	
//返回上一步
function returnLast(){
	window.history.go(-1);
}	



</script>



</body>
</html>