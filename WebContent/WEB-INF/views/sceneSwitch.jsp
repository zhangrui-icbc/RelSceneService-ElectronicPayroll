<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>home</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/sys/sceneSwitch.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all">
<link href="../css/common.css" rel="stylesheet" type="text/css"/>  
<link href="../css/parameter.css" rel="stylesheet" type="text/css"/> 
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
	                 <div class="layui-card-header">场景开关</div>
	                 <div class="layui-card-body" >
	       
	       <table id="demo"  lay-filter="test" style="width:100%"></table>
	        <script type="text/html" id="toolbar">
	        <div class="layui-btn-container" >
	            <button class="layui-btn layui-btn-sm" lay-event="edit">编辑</button>
	        </div>
            </script>
	       </div>
	        </div>
	        <br/>
	         
	
</div>
</div>
</div>

<div id="diag" style="display:none">
	<form class="layui-form" id="sceneform" lay-filter="scene-filter">
		<div class="layui-form-item" style="margin-left:180px;margin-top:50px;">
			<label class="layui-form-label">场景名称</label>
			<div class="layui-input-inline">
				<input type="text" name="sceneName" class="layui-input" disabled="true"></input>
			</div>
			<input type="text"  name="scene" class="layui-input" style="display:none"></input>
		</div>
		<div class="layui-form-item" style="margin-left:180px;margin-top:50px;">
			<label class="layui-form-label">场景开关</label>
			<div class="layui-input-inline">
				<select name="status" lay-filter="fiter-status" lay-verify="required">
					<option value="1">开</option>
					<option value="-1">关</option>
				</select>
			</div>
		</div>
  
		 <button class="layui-btn" type="button" id="btnsubmit" lay-filter="btnsubmit" lay-submit>保存</button>
		 <button class="layui-btn" type="button"  id="btncancel" onclick="cancelCoupon()">取消</button>
	 </form>
</div>

<div class="footer"></div>
<script src="../js/common.js"></script>
<script>
layui.use(['table','laydate','form'], function(){
  var table = layui.table;
  var form=layui.form;
  //第一个实例
  var logtable=table.render({
    elem: '#demo'
    ,toolbar:'#toolbar'
    ,method:"post"
    ,url: './getSceneStatus' //数据接口
    ,page: true //开启分页
    ,cols: [[ //表头
    	 {type:'radio',width:50}
      ,{field: 'sceneName',  title: '场景',    width:225}
      ,{field: 'status',     title: '开关',    width:215,templet:getSceneStatusDesc}
      ,{field: 'createTime', title: '创建时间', width:275,templet:formatCreatetime}
      ,{field: 'modifyTime', title: '修改时间', width:275,templet:formatModifytime}
    ]]
  });
  var key;
  table.on('toolbar(test)',function(obj){
		var checkStatus=table.checkStatus(obj.config.id);
        if(checkStatus.data.length==0){
        	layer.open({
        		type:0,
        		title:'提示',
        		content:"请先选中记录！"
        		
        	});
        	return;
		}
        scene=checkStatus.data[0].scene;
        console.log(scene);
        $.ajax({
			type : "GET",
			url : "./getScene?scene="+checkStatus.data[0].scene,
			cache:false,
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			async : false,
			success : function(data) {
				console.log(JSON.stringify(data));
				/* $("#item").html(data.message); */
				form.val('scene-filter',data.data);
				layer.open({
					title:'场景开关',
					type:1,
					content:$('#diag'),
					area:['700px','500px']
				});
			}
		});				
  });
  
function para(key,name,value){
	this.key=key;
	this.name=name;
	this.value=value;
}
//保存场景开关配置信息
	 form.on('submit(btnsubmit)',function(data){
			var info=data.field;
			$.ajax({
				type : "POST",
				url : "./updateStatus",
				data:JSON.stringify(info),
				dataType:"json",
				contentType:"application/json;charset=utf-8",
				async : false,
				success : function(data) {
					layer.open({
		        		type:0,
		        		title:'提示',
		        		content:"修改成功"
		        	});
				}
			});
			layer.closeAll();
			logtable.reload();
			return false;
			
	 });

});

//关闭礼券配置对话框
function cancelCoupon(){
	layer.closeAll();
}
</script>

</body>
</html>