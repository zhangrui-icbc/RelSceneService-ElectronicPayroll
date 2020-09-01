<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> 
<link rel="stylesheet" href="../css/order/ordercfg.css"  type="text/css"/> 
<link rel="stylesheet" href="../css/salary/salReim.css"  type="text/css"/> 
</head>

<body>
<div class="header"> 
<div class="header_a">
<div class="header_mp"></div>
<div class="user"><span><%=session.getAttribute("mpName").toString()%></span></div>
</div>
</div>
 <input type="hidden" id="contextPath" name="contextPath"  value="${pageContext.request.contextPath}"/>
 <input type="hidden" id="falg" name="flag"  value="${falg}"/>
 <input type="hidden" id="activityUid" name="activityUid"  value="${activityUid}"/>
 <input type="hidden" id="data" name="data"  value="data"/>
<div class="content2">
	<div class="vbm"  id="vbm">
     	<div class="klkl">
	         <div class="layui-card config">
	             <div class="layui-card-header">参数配置</div>
	             <div class="layui-card-body para-card" >
	               <form class="layui-form" id="test" lay-filter="main-filter">
	                 <div class="layui-form-item">
	                     <label class="layui-form-label">活动名称<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                         <input type="text" name="activityName" class="layui-input mouseover" ></input>
	                         <input type="text" name="iID" class="layui-input  hide" ></input>
	                     </div>
	                     <label class="layui-form-label">活动描述<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                         <input type="text" name="activityDesc" class="layui-input mouseover" ></input>
	                     </div>
	             <div class="btngroup" style="position:absolute;margin-left:84%;">
	                  <button class="layui-btn" type="button" id="btnsave" lay-filter="btnsave" lay-submit>保存</button>
	                 <button class="layui-btn" type="button"  id="btnreturn" onclick="returnLast()">返回</button>
	               </div>
	                 </div>
	               </form>
	             </div>
	        </div>
     		 <div class="nav">
		    	<span class="qiu">工资单管理</span>
		    	<span>报销单管理</span>
		    	<span>平台使用说明</span>
			</div>
			<iframe id="ifream1" class="detail" src="./salary"  frameBorder=0 width="100%"></iframe>
			<iframe id="ifream2" class="detail" src="./reimbursement" style="display: none;"></iframe>
			<iframe id="ifream3" class="detail" src="./explain" style="display: none;"></iframe>
		</div>
	</div>
</div>
<script src="../js/common.js"></script>
<script src="../js/salary/jquery-ui.js"></script>
<script src="../js/salary/loading.js"></script>
<script src="../js/salary/layer.js"></script>
<!-- <script src="../js/salary/salReim.js"></script> -->
<script>
//  选项卡切换
    $(function(){
    	if(location.href.indexOf("#reloaded")==-1){
    	       location.href=location.href+"#reloaded";
    	       location.reload();
    	   }  
    	window.scroll(0,0); 
    	changeFrameHeight(); 
    	$(".nav span").each(function(index) {
        	$(this).click(function(e) {
            	$(this).addClass("qiu").siblings().removeClass("qiu");
            	$(".detail").eq(index).show().siblings(".detail").hide();
        	})
		})
    });
//ifream自适应	
	function changeFrameHeight(){
        var ifm1= document.getElementById("ifream1");
        var ifm2= document.getElementById("ifream2");
        ifm1.height=document.documentElement.clientHeight+30;
        ifm2.height=document.documentElement.clientHeight+30;
    }
    window.onresize=function(){ changeFrameHeight();}
    
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
    	}else if(data.activityName.length>10){
    		content="活动名称长度超出字数限制！";
    	}else if(data.activityDesc.length>10){
    		content="活动描述长度超出字数限制！";
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
    
    $(document).on('click', '.closex', function () {
    	$(".content2").children('.listDetail_box').remove();
    	})

</script>
</body>
</html>