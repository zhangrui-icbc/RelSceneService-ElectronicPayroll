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
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> /> 
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
	         <div class="layui-card">
	             <div class="layui-card-header">菜单配置</div>
	             <div class="layui-card-body" >
	  	   <div class="layui-tab layui-tab-brief" lay-fiter="docDemoTabBrief">
	        <ul class="layui-tab-title">
	            <li class="layui-this">菜单导入</li>
	          <!--  <li>图片导入</li>  -->
	        </ul>
	        <div class="layui-tab-content">
	            <div class="layui-tab-item layui-show">
	            <script type="text/html" id="toolbar">
	         <div class="layui-btn-container" >
	            
	            <button class="layui-btn layui-btn-sm" lay-event="showDetail">查看菜单</button>
	            <button class="layui-btn layui-btn-sm" lay-event="delete">标记作废</button>
	         </div>
            </script>
            <blockquote class="layui-elem-quote">上传菜单请按照模板格式正确填写</blockquote>
              <label class="layui-form-label importlabel">示例文件<a href="../css/wm.xls" class="excel" ><img src="../image/excel.png"></img></a></label>
            <button class="layui-btn" lay-event="uploadbtn" id="uploadbtn"><i class="layui-icon"></i>上传菜单</button>
              <table id="menuTable" class="layui-table" lay-filter="menufilter" style="width:100%"></table>
               </div>
                <div class="layui-tab-item" style="display:none"> 
                   <script type="text/html" id="toolbar2">
	         <div class="layui-btn-container" >            
	            <button class="layui-btn layui-btn-sm" lay-event="delete">删除</button>
	         </div>
            </script>
            <blockquote class="layui-elem-quote">上传的图片名称需以菜品的名称来命名，建议大小 160*160像素</blockquote>
              
            <button class="layui-btn" lay-event="uploadpicbtn" id="uploadpicbtn"><i class="layui-icon"></i>上传图片</button>
              <table id="picTable" lay-filter="picfilter" style="width:100%"></table>
               </div>
               

		  </div>
		 </div></div>
	      </div> 
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
	                 <div class="layui-form-item">
	                     <label class="layui-form-label">可预订时间范围<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                      <select name="orderTimeType" lay-filter="filterOrderTimeType"><option value="-1">前一日</option>
	                         <option value="0">当日</option> </select>
	                         
	                     </div>
	                     <div class="layui-input-inline">
	                     <input type="text" class="layui-input readOnly" name="orderBeginTime" id="orderBeginTime" ></input></div>
	                    <div class="layui-form-mid">-当日</div>
	                     <div class="layui-input-inline">
	                         <input type="text" class="layui-input" name="orderEndTime" id="orderEndTime" ></input>
	                     </div>
	                 </div>
	               <!--   <div class="layui-form-item">
	                     <label class="layui-form-label">每人每种菜品预订数量上限<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                          <input type="number" class="layui-input mouseover" name="orderLimit" onkeyup="checklimit(this)" max=10></input>
	                     </div>
	                     
	                 </div> -->
	                 <div class="layui-form-item">
	                     <label class="layui-form-label">可取消订单时间<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                         <input type="text" class="layui-input mouseover" name="cancelTime" id="cancelTime" ></input>
	                     </div>
	                 </div> 
	                 <div class="layui-form-item">
	                     <label class="layui-form-label">领取外卖时间<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                        <input type="text" class="layui-input mouseover" name="takeTime" ></input>
	                     </div>
	                 </div> 
	                  
	                 <div class="layui-form-item layui-form-text">
	                     <label class="layui-form-label">食堂联系方式<span class="red">*</span></label>
	                     <div class="layui-input-inline">
	                         <input type="text" class="layui-input mouseover" name="telephone" id="telephone" ></input>
	                     </div>
	                 </div>
	                 <div class="layui-form-item layui-form-text">
	                     <label class="layui-form-label">领取外卖地点<span class="red">*</span></label>
	                     <div class="layui-input-inline" style="width:400px;">
	                         <input type="text" class="layui-input mouseover" name="takelocation" ></input>
	                     </div>
	                 </div>
	                 <div class="btngroup">
	                  <button class="layui-btn" type="button" id="btnsave" lay-filter="btnsave" lay-submit>保存</button>
	                 <button class="layui-btn" type="button"  id="btnreturn" onclick="returnLast()">返回</button>
	               </div></form>
	             </div>
	        </div>
	        </div>
	        
	 </div></div></div>
</div>
</div>
<div class="footer"></div>
<div id="diag">
 <table id="detailTable" lay-filter="detailfilter" style="width:100%"></table>
</div>
	       
<script src="../js/common.js"></script>
<script>
var flag=${flag};//0：新增  1：编辑  
var paraInfo=${data};
var activityUid=${activityUid};

 
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
		 form.val('main-filter',paraInfo); 
		 if(paraInfo.orderTimeType==-1){
			  startTime.config.min=getTime(paraInfo.orderEndTime);
			  endTime.config.max=getTime(paraInfo.orderBeginTime);
		 }else{
			 startTime.config.max=getTime(paraInfo.orderEndTime);
			  endTime.config.min=getTime(paraInfo.orderBeginTime);
		 }
	 
	}
	

	//菜单上传
	upload.render({
		elem:"#uploadbtn",
		url:'./importmenu?activityUid='+activityUid,
 		accept:'file', 
 		exts:'xls|xlsx',
		done:function(data){
			if(data.code==1){
				data.message="上传成功";
			}
			layer.open({
				title:'提示',
				type:0,
				content:data.message
			   });
			menuTable.reload({});
		}
	});
	
	//图片上传
	upload.render({
		elem:"#uploadpicbtn",
		url:'./importPic?activityUid='+activityUid,
 		accept:'file', 
 		acceptMime:'image/*',
 		exts:'jpg|png|gif|bmp|jpeg',
 		multiple:'true',
		done:function(data){
			if(data.code==1){
				data.message="上传成功";
			}
			layer.open({
				title:'提示',
				type:0,
				content:data.message
			   });
			picTable.reload({
				  page:{page:1}
				  });
		}
	});
	
	//礼券表格展示
	 var menuTable=table.render({
	    elem: '#menuTable'
	    ,toolbar:'#toolbar' 
	    ,id:'menuTable'
	    ,method:"post"
	    ,url: './getFileMenu'  //数据接口
	    ,where:{"activityUid":activityUid }
	    ,page: true //开启分页
	    ,cols: [[ //表头
          {type:'radio',width:50}
	      ,{field: 'fileUid', title: '文件编号'}
	      ,{field: 'fileName', title: '文件名称'}
	      ,{field: 'importTime', title: '导入时间',templet:formatImportTime}
	      ,{field: 'status', title: '状态',templet:formatStatus}
	      
	      ]]  
	  });
	//构建图片信息表格
	 var picTable=table.render({
		    elem: '#picTable'
		    ,toolbar:'#toolbar2' 
		    ,method:'post'
		    ,id:'picTable'
		    ,url: './getPicInfo?activityUid='+activityUid //数据接口
		    ,page: true //开启分页
		    ,cols: [[ //表头
		    	{type:'checkbox'}
		      //,{field: 'iid', title: '编号'}
		      ,{field: 'dishName', title: '名称'}
		      /* ,{field: 'picUrl', title: '路径'} */
		      ,{field: 'picUrl', title: '图片',templet:showPic}
		      ,{field: 'importTime', title: '导入时间',templet:formatImportTime}
		      ]]  
		  });
	 
	
	 var detailTable=table.render({
		    elem: '#detailTable'
		    ,id:'detailTable'
		    ,height:600
		    ,cols:[[{field: 'fileUid', title: '文件编号'}]]
		  });
	//菜单处理（查看详情、作废）
	table.on('toolbar(menufilter)',function(obj){
		var checkStatus=table.checkStatus(obj.config.id);
		if(checkStatus.data.length==0){
			 layer.open({
					title:'提示',
					type:0,
					content:'请先选中记录'
				   });
			return;
		}
		switch(obj.event){
		//查看详情
		case 'showDetail':
			//todo
			$.ajax({
				type : "GET",
				url : './GetImportData?uid='+checkStatus.data[0].fileUid,
				async : false,
				success : function(data) {
					var columnnames=data.data.columns;
					var cols=[];
					var columns=[];
					var columns1=[];
					columns.push({field: 'classifyName', title: '类别',align:'center', rowspan:2,width: 100});
					for(var i=0;i<columnnames.length;i++){
						columns.push({title:columnnames[i],colspan:4,width:280,align:'center'});
					}
					for(var i=0;i<columnnames.length;i++){
						columns1.push({field:columnnames[i],title:'名称',width:160});
						columns1.push({field:columnnames[i]+'_price',title:'价格',width:60});
						columns1.push({field:columnnames[i]+'_amount',title:'份数',width:60});
						columns1.push({field:columnnames[i]+'_limit',title:'预订上限',width:80});
					}
					cols.push(columns);
					cols.push(columns1);
                   var detail= data.data.data.data;
					detailTable.reload({
						cols: cols
						,data:detail
						,limit:detail.length
					});
					
					layer.open({
						title:'菜单详情',
						type:1,
						resize:false,
						content:$("#diag"),
						area:['1100px','700px']
					   });
				}
			});
			
		   
		break;
		//作废
		case 'delete':
			if(checkStatus.data[0].status==-1){
				layer.open({
					title:'提示',
					type:0,
					content:'该记录已作废！'
				   });
			return;
			}
			$.ajax({
				type : "GET",
				url : './updateFileMenuStatus?fileUid='+checkStatus.data[0].fileUid,
				async : false,
				success : function(data) {
					layer.open({
						title:'提示',
						type:0,
						content:data.message
					   });
					menuTable.reload({});
				}
			});
			break;
		}
	});
	//删除图片
	table.on('toolbar(picfilter)',function(obj){
		var checkStatus=table.checkStatus(obj.config.id);
		var array=[];
		if(checkStatus.data.length==0){
			 layer.open({
					title:'提示',
					type:0,
					content:'请先选中要删除的记录'
				   });
			return;
		}
		for(var i=0;i<checkStatus.data.length;i++){
			array.push(checkStatus.data[i].iid);
		}
		if(obj.event=='delete'){
			$.ajax({
				type : "POST",
				url : "./deletePic",
				traditional:true,
				data:{"iid":array},
				async : false,
				success : function(data) {
					picTable.reload({
						  page:{page:1}
						  });
				}
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


function getTime(time){
	//time="13:00:00"
	console.info(time.substring(0,2));
	console.info(time.substring(3,5));
	console.info(time.substring(6,8));
	var dd=new Date();
	var obj={
			 year:dd.getFullYear(),
       	     month:dd.getMonth(),//关键
             date:dd.getDate(),
        hours:parseInt(time.substring(0,2)),
        minutes:parseInt(time.substring(3,5)),
        seconds:parseInt(time.substring(6,8))
};
	return obj;
}

</script>



</body>
</html>