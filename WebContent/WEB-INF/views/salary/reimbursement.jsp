<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报销单管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> 
<link rel="stylesheet" href="../css/order/ordercfg.css"  type="text/css"/> 
		<link rel="stylesheet" href="../css/salary/icon.css">
		<link rel="stylesheet" href="../css/salary/indexNew.css">
		<style type="text/css">
			.hid{
			display:none
			}
			.tab .act1{
			  background:brown;
			  color:white;
			  border:0
			}
			#mytab_04 table tr:nth-child(1){
				background:#009688;
			}
			.tab-content {
			    width: 70%;
			    margin: 0 auto;
			    min-width: 910px;
			}
			#mytab_01_1 .add-input {
	                width:130px		
			}
			.main-tab{
				background: #FFFFFF;
			}
		</style>
</head>
<body>
 <input type="hidden" id="contextPath" name="contextPath"  value="${pageContext.request.contextPath}"/>
     	<div class="main-tab">
        	<div class="tab-box">
            	<ul id="nav-ul">
                	<li class="stitle alter" id="mytab_02" onclick="nTabs('mytab_02');window.open='#mytab_02'">
                    	<sapn> 报销明细类型增加</span>
                    	<img src="../image/salary/salary/icon1.png" />
                	</li>
                <!-- <span>报销明细类型增加</span>
                	<img th:src="@{/salary/img/icon1.png}"/>   ss -->
   <!--              <li class="stitle com" id="mytab_01" onclick="nTabs('mytab_01');window.open='#mytab_01'" shiro:hasRole="admin">
                	 <span>通用报销类型</span>
                     <img th:src="@{/salary/img/icon6.png}" />
                     <img src="../image/salary/salary/icon6.png" />
                </li> -->
                	<li class="stitle zdy alter1" style="width: 160px;"id="mytab_03" onclick="nTabs('mytab_03');window.open='#mytab_03'">
                		<span> 报销模板调整</span>
                		<img src="../image/salary/salary/icon2.png" />
                	</li>
               		<li class="stitle sc" id="mytab_04" onclick="nTabs('mytab_04');window.open='#mytab_04'">
               			<span>报销上传</span>
               			<img src="../image/salary/salary/icon3.png" />
               		</li>
           	 	</ul>
            <div class="tab-content">
                <div class="tab active-txt" id="mytab_01_1"  shiro:hasRole="admin">
                	<div class="input-box">
                		<span class="input-name">项目名称：</span><input class="add-input column" type="text" placeholder="请输入项目名称" />
                		<span class="input-name">排列序号：</span><input class="add-input column_num" type="text" placeholder="请输入排列序号" />
                		<span class="input-name">备注：</span><input class="add-input note" type="text" placeholder="请输入备注" />
                		<a href="##" class="add-btn btn1">添加</a>
                	</div>
                	<table class="table" id="mytable"  cellspacing="0" style="margin-top:45px">
					<thead>
					  <tr>
						<th>项目名称</th>
						<th>排列序号</th>
						<th>备注</th>
						<th style="width:186px">操作</th>
					  </tr>
					</thead>
					<tbody  id="tab-com">
					</tbody>
				</table>
                </div>
                <!--  报销明细类型增加 -->
            	<div class="tab tab2" id="mytab_02_1" >
	            	<div class="input-box">
	            		<span class="input-name" style="line-height:32px">类型名称：</span><input class="add-input column_b" type="text" placeholder="请输入类型名称" />
	            		<a href="##" class="add-btn btn2">确定提交</a>
	            	</div>
            		<table class="table" id="mytable2"  cellspacing="0" style="margin-top:45px">
						<thead>
							<tr>
								<td>类型名称</td>
								<td>收支类型</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody id="tab-com2">
						
						</tbody>
					</table>
            	</div>
            	
                <div class="tab tab3" id="mytab_03_1">
                <h3 style="font-size:15px;padding-left:8%;font-weight:600;">备选明细类型:</h3>
                	<div class="alternative">
                	</div>
                	<div class="notes"  style="margin: 2% 0% 2% 10%;">
    	            	<h2 class="descp"><span style="color:#c00000">温馨提示：</span>报销合计为必选，否则将导致上传数据异常。点击上方备选明细类型选择所需报销单明细类型，点击下方单个已选明细类型，前后挪动可更改明细类型展示的先后顺序。</h2>
    	            	
                	</div>
                <h3 style="font-size:15px;padding-left:8%;font-weight:600;">已选明细类型:</h3>
                	<div class="add">
                		<div class="fixed_field">
                		</div>
                		<div class="DIY">
                		</div>
                	</div>
                	<div class="tab3_btnBox">
                		<div class="sunmit-btn">提交</div>
                		<div class="clear">清除</div>
                	</div>
                </div>
		         <!-- 报销单上传 -->
				<div class="tab tab4" id="mytab_04_1">
						<div class="download-bg">
							<div class="down-div select-list">
								<button type="button" class="gz-download bx-download">报销模板下载</button>
								<button class="sub" lay-event="uploadSal" id="sal_sub"><i class="layui-icon"></i>上传报销信息</button>
							</div>					
						</div>
						<table class="sal_log table" id="sal_log"  cellspacing="0" style="margin-top:45px">
						</table>
				<div th:include="include :: footer"></div>	
			</div>
   		 </div>
	</div>
</div>
<script src="../js/common.js"></script>
<script src="../js/salary/jquery-ui.js"></script>
<script src="../js/salary/layer.js"></script>
<script src="../js/salary/reimbursement/reimbursement.js"></script>
<script src="../js/salary/reimbursement/zdy.js"></script>
<script>
var flag=1;
var paraInfo=111;
var activityUid=11;
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
	//报销信息上传
 	upload.render({
		elem:"#sal_sub",
		url:'../mp/reimbursement/uploadRe',
		accept:'file', 
		exts:'xls|xlsx',
		done:function(data){
			if(data.code==1){
				data.msg="上传成功";
			}
			layer.open({
				title:'提示',
				type:0,
				content:data.msg
			   });
			$("#mytab_04").click();
		},
		error:function(){
			layer.open({
				title:'提示',
				type:0,
				content:"上传失败!"
			   });
		}
	});
}); 



	
//返回上一步
function returnLast(){
	window.history.go(-1);
}	



var hash = location.hash;
//获取url参数 
if(hash){ 
	$('.tab').hide(); 
	$(hash).addClass('active'); 
	$(hash+'_1').show(); 
}
else { 
	$('.tab').hide(); 
	$('#mytab_02').addClass('active'); 
	$(".alter").click();
	$('#mytab_02_1').show(); 
} 

</script>
</body>
</html>