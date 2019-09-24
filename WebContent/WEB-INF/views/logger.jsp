<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统日志</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/sys/logger.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link href="../css/common.css" rel="stylesheet" type="text/css"/>  
<link href="../css/logger.css" rel="stylesheet" type="text/css"/> 
</head>

<body>

	 <div class="header"> 
<div class="header_a">
<div class="header_admin"></div>
<div class="user"><span><%=session.getAttribute("adminName").toString()%></span></div>
</div>
</div>
<div class="content3">
<div class="vbm">
     <div class="klkl">
	      <div class="tttt"></div>
	       <!-- 这里是需要填充的主体内容 -->
	       <div class="layui-card">
	                 <div class="layui-card-header">系统日志</div>
	                 <div class="layui-card-body" >
	      <form class="layui-form" id="test" lay-filter="log-filter">
	      <div class="tool">
	       <div class="layui-form-item">
	       <label class="layui-form-label">公众号名称</label>
	      <div class="layui-input-inline"> <input type="text"  class="layui-input" name="mpuser" id="publicname"></input></div>
	      <label class="layui-form-label">类型</label>
	      <div class="layui-input-inline">
	      <select name="type" id="type">
	      <option value="0">全部</option>
	         <option value="1">新增数据</option>
	         <option value="2">更新数据</option>
	         <option value="3">删除数据</option>
	      </select></div>
	      
	       <label class="layui-form-label">操作日期</label>
	       <div class="layui-input-inline" style="width:150px"><input type="text" class="layui-input" name="startDate" id="startDate"  ></input></div>
	      <div class="layui-form-mid">-</div>
	       <div class="layui-input-inline" style="width:150px"><input type="text" class="layui-input" name="endDate" id="endDate" ></input></div>
	      
	      <button class="layui-btn" type="button" lay-filter="btnQuery" lay-submit>查询</button>
	      </div>
	      </div>
	      </form>
	       <table id="demo"  lay-filter="test" style="width:100%"></table>
	       </div>
	        </div>
	        <br/>
	     <div class="layui-card" id="dataDetail">
	           <div class="layui-card-header">数据明细</div>
	           <div class="layui-card-body" >
	     
	       <table id="detail"  style="width:100%"></table>
	       </div>
	    </div>       
	
</div></div>
</div>
<div class="footer"></div>
<script src="../js/common.js"></script>
<script>
layui.use(['table','laydate','form'], function(){
  var table = layui.table;
  var laydate=layui.laydate;
  var form=layui.form;

	 var startTime=laydate.render({
	    	elem:'#startDate',
	    	btns: ['confirm'],
	    	done:function(value,date){
	    	    endTime.config.min={  
	    	    	year:date.year,
	        	    month:date.month-1,//关键
	                date:date.date,
	                hours:date.hours,
	                minutes:date.minutes,
	                seconds:date.seconds
	    	    };
	    	    
	    	}
	    });
	    var endTime=laydate.render({
	    	elem:'#endDate',
	    	btns: ['confirm'],
	    	done:function(value,date){
	         startTime.config.max={
	    	    		year:date.year,
		        	    month:date.month-1,//关键
		                date:date.date,
	                    hours:date.hours,
	                    minutes:date.minutes,
	                    seconds:date.seconds
	    	    }
	    	    
	    	}
	    });
  //第一个实例
  var logtable=table.render({
    elem: '#demo'
    ,url: './getPlatFormLog2' //数据接口
    ,method:"post"
    ,page: true //开启分页
    ,cols: [[ //表头

      {field: 'activityName', title: '场景名称'}
      ,{field: 'sceneName', title: '场景类型'}
      ,{field: 'operateType', title: '日志类型',templet:formatType}
      ,{field: 'tableName', title: '操作对象'}
      ,{field: 'createUser', title: '公众号名称'}
      ,{field: 'createTime', title: '操作时间',templet:formatDatetime}
    ]]
  });
  
  var logdetailtable=table.render({
    elem: '#detail'
    ,cols: [[ //表头

       {field: 'columnName', title: '字段名'}
      ,{field: 'columnText', title: '字段含义'}
      ,{field: 'value', title: '字段值',templet:formatvalue}
      ,{field: 'oldValue', title: '原值',templet:formatoldvalue}
    ]]
  });
  
  form.on('submit(btnQuery)',function(data){
	   var info=data.field;
	   logtable.reload({
		  page:{page:1},
		  url: './getPlatFormLog2',
		  method:'post',
		  where:info
		  });
	  
 });
  
//监听行单击事件（单击事件为：rowDouble）
  table.on('row(test)', function(obj){
	  $("#dataDetail")[0].style.display="block";
    var data = obj.data;
    $("#dataDetail .layui-card-header")[0].innerText="数据明细（操作对象： "+data.tableName+"    日志类型： "+formatType(data)+"）";
    loguid=data.logUid;
    $.ajax({
		type : "GET",
		url : "./getLogData?logUid="+loguid,
		async : false,
		cache:false,
		success : function(data) {
			 logdetailtable.reload({data:data.data.data} );
			 
			}
		});
   
    //标注选中样式
    obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
  });
  
});
</script>

</body>
</html>