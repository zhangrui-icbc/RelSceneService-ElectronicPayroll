<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资单管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/order/ordercfg.js"></script>
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> 
<link rel="stylesheet" href="../css/order/ordercfg.css"  type="text/css"/> 
		<link rel="stylesheet" href="../css/todo/icon.css">
		<link rel="stylesheet" href="../css/todo/indexNew.css">
		<style type="text/css">
			.hid{
			display:none
			}
			.tab .act1{
			  background:brown;
			  color:white;
			  border:0
			}
			.main-tab{
				background: #F5F5F5;
			}
		</style>
</head>
<body>
 <input type="hidden" id="contextPath" name="contextPath"  value="${pageContext.request.contextPath}"/>
	 	<div class="main-tab">
        <div class="tab-box">
            <ul id="nav-ul">
                <li class="stitle alter" id="mytab_02" onclick="nTabs('mytab_02');location.href='#mytab_02'">
                	<span>工资单明细增加</span>
                	<img src="../image/todo/salary/icon1.png" />
                </li>
                <!-- <span>工资单明细类型增加</span>
                	<img th:src="@{/salary/img/icon1.png}"/>   ss -->
 <!--              	<li class="stitle com" id="mytab_01" onclick="nTabs('mytab_01');location.href='#mytab_01'">
                	<span>通用工资类型</span>
                	<img src="../image/todo/salary/icon6.png" />
                </li> -->
                <li class="stitle zdy alter1" style="width: 160px;"id="mytab_03" onclick="nTabs('mytab_03');location.href='#mytab_03'">
                	<span>工资单模板调整</span>
                	<img src="../image/todo/salary/icon2.png" />
                </li>
               	<li class="stitle sc" id="mytab_04" onclick="nTabs('mytab_04');location.href='#mytab_04'">
               		<span>工资单上传</span>
               		<img src="../image/todo/salary/icon3.png" />
               	</li>
                <li class="stitle" id="mytab_05" onclick="nTabs('mytab_05');location.href='#mytab_05'">
                	<span>员工信息</span>
                	<img src="../image/todo/salary/icon4.png" />
                </li>
                <li class="stitle" id="mytab_06"  onclick="nTabs('mytab_06');location.href='#mytab_06'">
               		<span>员工登录密码初始化</span>
                	<img src="../image/todo/salary/icon5.png"/>
                </li>              
            </ul>
            <div class="tab-content">
            	
            	<!--工资单明细类型增加-->
                <div class="tab active-txt" id="mytab_01_1"  shiro:hasRole="admin">
                	<div class="input-box">
                		<span class="input-name">项目名称：</span><input class="add-input column" type="text" placeholder="请输入项目名称" style="width:157px"/>
                		<span class="input-name">排列序号：</span><input class="add-input column_num" type="text" placeholder="请输入排列序号" style="width: 117px;"/>
                		<span class="input-name">备注：</span><input class="add-input note" type="text" placeholder="请输入备注" style="width: 117px;" /><br />
                		<a href="##" class="add-btn btn1">添加</a>
                	</div>
                	<table class="table" id="mytable" cellspacing="0">
					<thead>
					  <tr>
						<th>项目名称</th>
						<th>排列序号</th>
						<th>备注</th>
						<th>操作</th>
					  </tr>
					</thead>
					<tbody  id="tab-com">
					</tbody>
				</table>
                </div>
                
                <!--通用工资类型-->
            	<div class="tab tab2" id="mytab_02_1" >
	            	<div class="input-box">
	            		<span class="input-name">类型名称：</span><input class="add-input column_b" type="text" placeholder="请输入类型名称" /><br />
	            		<span class="input-name">收支类型：</span>
	            		<select class="add-input column_num_b select">
	            			<option value="1">收入</option>
	            			<option value="2">支出</option>
	            			<option value="4">单位支出</option>
	            			<option value="5">备注</option>
	            		</select>
	            		<a href="##" class="add-btn btn2">确定提交</a>
	            	</div>
            		<table class="table table-bordered table-striped" id="mytable2" style="margin-top:45px">
						<thead>
							<tr>
								<td>类型名称</td>
								<td>收支类型</td>
								<td style=" width: 123px;">操作</td>
							</tr>
						</thead>
						<tbody id="tab-com2">
						
						</tbody>
					</table>
            	</div>
            	
            	<!--模板调整--> 
                <div class="tab tab3" id="mytab_03_1">
                	<div class="alternative">
                	</div>
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
                	<div id="notes" class="notes"  style="margin-top:50px;">
	                	<h2>温馨提示：实际收入、收入合计、支出合计为必选项,否则会导致模板上传失败。<br>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	鼠标点击单个明细类型，可前后挪动更改明细类型前后位置顺序。</h2>
                	</div>
                </div>
                
        		 <!-- 工资单上传 -->
				<div class="tab tab4" id="mytab_04_1">
								<div class="download-bg">
									<div class="down-div">
										<button type="button" class="gz-download">工资单模板下载</button>
										<button class="sub" lay-event="uploadSal" id="sal_sub"><i class="layui-icon"></i>上传工资信息</button>
									</div>
								</div>
								<table class="table table-bordered table-striped"  style="margin-top:45px">
									<thead>
										<tr><th>导入时间</th><th>发放时间</th><th>excel文件</th><th>操作</th></tr>
									</thead>
									<tbody class="sal_log">
									</tbody>
								</table>
							</form>
				</div>
				<div th:include="include :: footer"></div>	
           		<!--员工信息-->
           		<div class="tab tab5" id="mytab_05_1">
						<div class="download-bg">
							<div class="down-div">
								<button type="button" class="yg-download">员工信息模板下载</button>
								<button class="sub" lay-event="uploadSta" id="sta_sub"><i class="layui-icon"></i>上传员工信息</button>
							</div>
						</div>	
					<div class="phone-num update-ph">
	       				<div class="replace-phone">
	       					<h3>更换员工手机号码</h3>
	       					<p>
	       						<label>旧手机号码：</label><input id="oldMbl" class="input-oldNum" placeholder="请输入旧手机号码"/><br />
	       						<label>新手机号码：</label><input id="newMbl" class="input-newNum1" placeholder="请输入新手机号码"/><br />
	       						<label>新手机号码：</label><input id="newMbl1" class="input-newNum2" placeholder="请再次输入新手机号码"/><br />
	       					</p>
	       					<input type="button" value="确认" id="exch_mbl" class="phone-btn sub-ph" onclick="exchMbl()"/>
	       				</div>
	       				<div class="del-phone">
	       					<h3>删除员工手机号码</h3>
	       					<p>
	       						<label>手机号码：</label><input id="del_mbl1" class="input-oldNum" placeholder="请输入手机号码"/>
	       					</p>
	       					<input type="button" value="删除" id="del_mbl" class="phone-btn sub-ph" onclick="delMb()"/>
	       				</div>
	       			</div>	
           		</div>
           		
           		
           		<!-- 密码重置 -->
           		<div class="tab tab6" id="mytab_06_1">
           			<div>
           				<input class="mobile" placeholder="请输入手机号" ></input>
           				<button class="pwd_up"  type="button">确定</button>
           			</div>
           			
           		</div>
		</div>
        
    </div>
	 </div>
<script src="../js/common.js"></script>
<script src="../js/todo/jquery-ui.js"></script>
<script src="../js/todo/layer.js"></script>
<script src="../js/todo/salary/salary.js"></script>
<script src="../js/todo/salary/zdy.js"></script>
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

	//员工信息上传
 	upload.render({
		elem:"#sta_sub",
		url:'../salary/uploadStaff',
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
		//	menuTable.reload({});
		},
		error:function(){
			layer.open({
				title:'提示',
				type:0,
				content:"上传失败!"
			   });
		}
	}); 
	//工资信息上传
 	upload.render({
		elem:"#sal_sub",
		url:'../salary/uploadSalary',
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
	//		menuTable.reload({});
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
					url : "./saveParaInfo",
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

$(".mouseover").mouseover(function(){
	var obj=$(this);
	var note="";
	if(obj[0].name=="activityName"){
		note="最多可输入10个汉字";
	}else if(obj[0].name=="activityDesc"){
		note="最多可输入50个汉字";
	}
	else if(obj[0].name=="cancelTime"){
		note="用户可在该时间点前取消订单";
	}
	else if(obj[0].name=="takeTime"){
		note="领取餐品时间，如17:30-18:30";
	}
	else if(obj[0].name=="telephone"){
		note="填写手机号或者固定电话";
	}else if(obj[0].name=="takelocation"){
		note="最多可输入20个汉字";
	}
	layer.tips(note,this,{tips:[2,'#009688']});
});


//检查提交的元素
function check(data){
	var content="";
	if(data.activityName==""){
		content="请填写活动名称！";
	}else if(data.activityDesc==""){
		content="请填写活动描述！";
	}
	else if(checklength(data.activityName)>20){
		content="活动名称最多可输入10个汉字";
	}else if(checklength(data.activityDesc)>100){
		content="活动描述最多可输入50个汉字";
	}
	else if(checklength(data.takelocation)>40){
		content="领取外卖地点最多可输入20个汉字";
	}
	else if(data.orderBeginTime=="" || data.orderEndTime==""){
	   content="请填写预订结束时间!";
	}else if(data.cancelTime==""){
	   content="请填写可取消订单时间!";
	}
	else if(!checkTelephone(data.telephone)){
		content="请正确填写联系方式！";
	}
	else if(!checkTime(data.takeTime)){
		content="请正确填写领取外卖时间！";
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