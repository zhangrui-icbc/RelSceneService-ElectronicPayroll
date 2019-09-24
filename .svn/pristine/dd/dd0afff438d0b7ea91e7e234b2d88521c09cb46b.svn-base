<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>统计分析</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/order/orderAnalysis.js"></script>
<script src="../css/layui/layui.js"></script>
<link rel="stylesheet" href="../css/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="../css/common.css"  type="text/css"/> 
<link rel="stylesheet" href="../css/order/orderAnalysis.css" type="text/css"/> 
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
	      <div class="main">
	       <!-- 这里是需要填充的主体内容 -->
	       <div id="cards" class="layui-rows">
	          <!--  <div class="layui-col-xs3">
	               <div class="layui-card">
	                 <div class="layui-card-header">累计总访问量</div>
	                 <div class="layui-card-body" id="allaccessNum"></div>
	               </div>       
	           </div>
	         <div class="layui-col-xs3">
	         <div class="layui-card">
	             <div class="layui-card-header">累计访问用户数</div>
	             <div class="layui-card-body" id="accessPersonNum"></div>
	         </div>
	          </div> -->
	         <div class="layui-col-xs3">
	         <div class="layui-card">
	             <div class="layui-card-header">本年累计订单量</div>
	             <div class="layui-card-body" id="yearOrderNum"></div>
	         </div>
	          </div>
	         <div class="layui-col-xs3">
	         <div class="layui-card">
	             <div class="layui-card-header">本月订单量</div>
	             <div class="layui-card-body" id="monthOrderNum"></div>
	         </div>
	          </div>
	       </div>
	       
	       <div id="activity-panel">
	           <div class="layui-card activity-detail">
	             <div class="layui-card-header">活动概览</div>
	             <div class="layui-card-body" >
	                  <table class="layui-table">
	                      <colgroup>
	                          <col width="150"/>
	                          <col width="230"/>
	                      </colgroup>
	                      <tbody>
	                          <tr>
	                              <td>活动名称</td>
	                              <td id="activityName"></td>
	                          </tr>
	                          <tr>
	                              <td>场景名称</td>
	                              <td>线上点餐</td>
	                          </tr>
	                          <tr>
	                              <td>操作</td>
	                              <td><button class="layui-btn" data-type="reload" onclick="edit()">活动编辑</button></td>
	                          </tr>
	                      </tbody>
	                  </table>
	             </div>
	           </div>
	           
	           <div class="layui-card activity-rule">
	             <div class="layui-card-header">订餐规则</div>
	             <div class="layui-card-body scroll" id="rule">	                
	             </div>
	           </div>
	       </div>
	       
	       <div id="visit-panel">
	           <div class="layui-card">
	             <div class="layui-card-header">今日菜品信息</div>
	             <div class="layui-card-body" >
	             <form class="layui-form" id="menu" lay-filter="menu-filter">
	                 <div class="tool"> 
	                 <div class="layui-form-item">
	                  <label class="layui-form-label">日期</label>
	                  <div class="layui-input-inline"><input class="layui-input" name="dateStr" id="date1"/></div>
	               <button class="layui-btn" type="button" lay-filter="queryMenu" lay-submit>查询</button>
	               <button class="layui-btn" type="button" id="btnprint" onclick="printMenu('dishTable')">打印</button>
	               <button class="layui-btn" type="button" onclick="exportDish()">导出</button></div>
	               </div></form>
	               
	             <table id="dishTable" lay-filter="dish-filter" style="width:100%"></table>
	             </div>
	           </div>
	       </div>
	       
	       
	      <div class="data-panel">
	       <div class="layui-card">
	             <div class="layui-card-header">今日预订信息</div>
	             <div class="layui-card-body" >
	              <form class="layui-form" id="test" lay-filter="main-filter">
	                 <div class="tool"> 
	                 <div class="layui-form-item">
	                  <label class="layui-form-label">订单日期</label>
	                  <div class="layui-input-inline"><input class="layui-input" name="dateStr" id="date"/></div>
	                 <label class="layui-form-label">手机号</label>
	                  <div class="layui-input-inline"><input class="layui-input" name="mobileNo" id="mobileNo"/></div>
	                  
	               <button class="layui-btn" type="button" lay-filter="queryOrder" lay-submit>查询</button>
	               <button class="layui-btn" type="button" id="btnprint" onclick="printMenu('orderTable')">打印</button>
	               <button class="layui-btn" type="button" onclick="exportAll()">全部导出</button></div>
	               </div></form>
	               <table id="orderTable" lay-filter="order-filter" style="width:100%"></table>
	             </div>
	           </div>
	       
	 </div>
	 </div>
</div>
</div></div>
<div class="footer"></div>

<script src="../js/common.js"></script>
<script type="text/javascript">
var activityUid=${activityUid};
$(document).ready(function(){
	$.ajax({
		type : "POST",
		url : "./getOrderReportInfos",// 请求分析数据
		data:{"activityUid":activityUid},
		datatype : "json",
		async : false,
		success : function(data) {
			infos=data.data;
		/* 	$("#allaccessNum")[0].innerText=data.data.allaccessNum;
			$("#accessPersonNum")[0].innerText=data.data.accessPersonNum; */
			$("#yearOrderNum")[0].innerText=data.data.yearOrderNum;
			$("#monthOrderNum")[0].innerText=data.data.monthOrderNum;
			$("#rule")[0].innerText=data.data.note; 
			$("#activityName")[0].innerText=data.data.activityName;
			}
		});
});
var today = new Date().format('yyyy-MM-dd');
//轮播
layui.use(['carousel','table','form','element','laydate'],function(){
	var carousel=layui.carousel;
	var laydate=layui.laydate;
	carousel.render({
		elem:'#analysis',
		width:'100%',
		indicator:'outside',
		autoplay:false
	});
	laydate.render({
		elem:'#date',
		type:'date',
		format:'yyyy-MM-dd', 
		value:today
	});
	laydate.render({
		elem:'#date1',
		type:'date',
		format:'yyyy-MM-dd', 
		value:today
	});
   var form=layui.form;
  
//表格
  var table = layui.table;
  
  //订单详情表
  var orderTable=table.render({
    elem: '#orderTable'
    ,id:'orderTable'
    ,height:600
    ,url: './getTodayOrders?activityUid='+activityUid+"&dateStr="+today+"&timestamp="+new Date().getMilliseconds()//数据接口
    ,cols: [[ //表头
      {field: 'orderId', title: '订单号'}
      ,{field: 'userName', title: '昵称'}
      ,{field: 'moblieNo', title: '手机号'}
      ,{field: 'orderDesc', title: '预订详情'}
      ,{field: 'createTime', title: '预定时间',templet:formatDatetime}
    ]]
  });
  //菜品预订剩余数量表
  var dishTable=table.render({
	    elem: '#dishTable'
	    ,id:'dishTable'
	    ,height:280
	    ,url: './getAvailableOrders?activityUid='+activityUid+"&dateStr="+today+"&timestamp="+new Date().getMilliseconds() //数据接口
	    ,cols: [[ //表头
	      {field: 'iid', title: '序号'}
	      ,{field: 'classifyName', title: '类别'}
	      ,{field: 'dishName', title: '菜品名称'}
	      ,{field: 'amount', title: '总份数'}
	      ,{field: 'usedAmount', title: '已订份数'}
	    ]]
	  });
  
  
 //查询指定日期所有订单
  form.on('submit(queryOrder)',function(data){
	   var info=data.field;
	  info.activityUid=activityUid;
	  //alert(JSON.stringify(info));
	  orderTable.reload({
		  url: './getTodayOrders',
		  method:'post',
		  where:info
		  });
	  
  });
  
  //查询指定日期所有订单
  form.on('submit(queryMenu)',function(data){
	   var info=data.field;
	  info.activityUid=activityUid;
	  //alert(JSON.stringify(info));
	  dishTable.reload({
		  url: './getAvailableOrders',
		  method:'post',
		  where:info
		  });
	  
  });
});

function printMenu (layid)
{
    var v = document.createElement("div");
    var f = ["<head>", "<style>", "body{font-size: 12px; color: #666;}", "table{width: 100%; border-collapse: collapse; border-spacing: 0;}", "th,td{line-height: 20px; padding: 9px 15px; border: 1px solid #ccc; text-align: left; font-size: 12px; color: #666;}", "a{color: #666; text-decoration:none;}", "*.layui-hide{display: none}", "</style>", "</head>"].join("");
    $(v).append($("[lay-id=\"" + layid+ "\"]").find(".layui-table-header").html());
    $(v).find("tr").after($("[lay-id=\"" + layid+ "\"] .layui-table-body.layui-table-main table").html()); $(v).find("th.layui-table-patch").remove();
    $(v).find(".layui-table-col-special").remove();
    var h = window.open("打印窗口", "_blank");
    h.document.write(f + $(v).prop("outerHTML"));
    h.document.close();
    h.print();
    h.close();
}

function edit(){
	window.location.href='./ordercfg?activityUid='+activityUid;}

//关闭对话框
function cancelDiag(){
	layer.closeAll();
	
}

function exportAll(){
	var str=$("#date")[0].value;
	window.location.href='./exportAllOrders?activityUid='+activityUid+"&dateStr="+str+'';
}

function exportDish(){
	var str=$("#date1")[0].value;
	window.location.href='./exportAllDishes?activityUid='+activityUid+"&dateStr="+str+'';
}

</script>

</body>
</html>