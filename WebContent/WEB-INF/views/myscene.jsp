<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的场景</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> 

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
	      <div class="main">	       
		   	<div class="layui-card">
	             <div class="layui-card-header">我的场景</div>
	             <div class="layui-card-body" >
	               <form class="layui-form" id="test" lay-filter="main-filter">
	                 <div class="tool"> 
	                 <div class="layui-form-item">
	                 <label class="layui-form-label">活动名称</label>
	                  <div class="layui-input-inline"><input class="layui-input" name="activityName" id="activityName"/></div>
	                 
	               <button class="layui-btn" type="button" lay-filter="btnQuery" lay-submit>查询</button></div>	            
	            </div></form>
	               <table id="demo" lay-filter="myfilter" class="layui-table"></table> 
  		    	
             	 <script type="text/html" id="barDemo">
                       <a class="layui-btn layui-btn-xs" lay-event="copy">拷贝链接</a>
                        <a class="layui-btn layui-btn-xs" lay-event="edit">查看配置</a>
						<a class="layui-btn layui-btn-xs" lay-event="analysis">统计分析</a>
                        <a class="layui-btn layui-btn-xs" lay-event="delete">删除</a>
                   </script>
	            </div>	
	            </div>
  		    		                   
	  </div>
	 </div>
</div>
</div>
<div class="footer"></div>
<script src="../js/common.js"></script>
<script type="text/html" id="xuhao">
{{d.LAY_TABLE_INDEX+1}}
</script>
<script>
//表格
layui.use('table', function(){
  var table = layui.table;  
  var form=layui.form;
  //第一个实例
  mytable=table.render({
	 id:'demoReload'
    ,elem: '#demo'
    ,url: './getMySencene' //获取场景数据
    ,method:"post"
    ,height: 800
    ,cols: [[ //表头
    	{field: 'xuhao', width:'60',title: '序号',templet:'#xuhao',rowspan:2} 
      /*   ,{field: 'publicNumberId', width:'130',title: '公众号名称',rowspan:2} */
        ,{field: 'relSceneName', width:'120',title: '场景名称',align:'center'}
        ,{field: 'activityName', width:'120',title: '活动名称',align:'center'}
       /*  ,{field: 'description', width:'260',title: '活动描述',align:'center'} */
        ,{field: 'beginTime', width:'160',title: '开始时间',align:'center',templet:formatDatetime}
        ,{field: 'endTime', width:'160',title: '结束时间',align:'center',templet:formatDatetime1}
        ,{field: 'activityUrl', width:'160',title: '活动链接',align:'center'}
        ,{fixed: 'right', width: '280', title: '操作',align:'center', toolbar: '#barDemo'}
        /* ,{field: 'CreateTime', width:'140',title: '创建时间',rowspan:2} */
      ]]
  }); 
  
//删除图片
	table.on('tool(myfilter)',function(obj){
        var data=obj.data;
        if(obj.event=='copy'){
        	var text=data.activityUrl;
			var clip=window.clipboardData;
			if(!clip){
				clip=e.originalEvent.clipboardData;
			}
			clip.setData("Text",text);
			
			
		}
        else if(obj.event=='edit'){
			if(data.relSceneUid=='lottery'){
				window.location.href='./lotterycfg?activityUid='+data.activityUid;
			}if(data.relSceneUid=='order'){
				window.location.href='./ordercfg?activityUid='+data.activityUid;
			}if(data.relSceneUid=='meeting'){
				window.location.href='./meetingCfg?activityUid='+data.activityUid;
			}if(data.relSceneUid=='asks'){
				window.location.href='./asksCfg?activityUid='+data.activityUid;
			}if(data.relSceneUid=='salary'){
				window.location.href='./salConfig?activityUid='+data.activityUid;
			}
			
		}else if(obj.event=='analysis'){
			if(data.relSceneUid=='lottery'){
				window.location.href='./lotteryAnalysis?activityUid='+data.activityUid;
			}if(data.relSceneUid=='order'){
				window.location.href='./orderAnalysis?activityUid='+data.activityUid;
			}if(data.relSceneUid=='meeting'){
				window.location.href='./appoint?activityUid='+data.activityUid;
			}if(data.relSceneUid=='asks'){
				window.location.href='./asks?activityUid='+data.activityUid;
			}	
		}else if(obj.event=="delete"){
			layer.confirm('确认删除活动:'+data.activityName,{icon:3,title:'提示'},function(index){
				$.ajax({
					type : "GET",
					url : "./delScene?activityUid="+data.activityUid,
					dataType:"json",
					contentType:"application/json;charset=utf-8",
					async : false,
					complete : function(data) {
						if (data.responseJSON.code == 0) {
							mytable.reload();						
						} else {
							alert("fail");
						}
					}
				});
				layer.close(index);
			});
		}
	});
    
	//查询指定日期所有订单
	  form.on('submit(btnQuery)',function(data){
		   var info=data.field;
		   mytable.reload({
			  url: './getMySencene',
			  method:'post',
			  where:info
			  });
		  
	  });

		
  
});

function formatDatetime(value)
{
	
	  if(value.beginTime==undefined)
		  {
		  return "--";
		  }
	 return new Date(value.beginTime).format('yyyy-MM-dd hh:mm:ss');
}

function formatDatetime1(value)
{
	
	  if(value.endTime==undefined)
		  {
		  return "--";
		  }
	 return new Date(value.endTime).format('yyyy-MM-dd  hh:mm:ss');
}



</script>


</body>
</html>