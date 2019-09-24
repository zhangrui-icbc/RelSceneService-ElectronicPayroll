var currentType = "000";
layui.use(['carousel','table'], function(){
  //图片轮播
  var carousel = layui.carousel;
  carousel.render({
    elem: '#lunbotu'
    ,width: '100%'
    ,height: '210px'
    ,interval: 3000
    ,indicator:'outside'
  });
  
  //表格 
  var table = layui.table;
  
  //第一个实例
  table.render({
	 id:'demoReload' 
    ,elem: '#demo'
    ,method:"post"
    ,url: '../com/getAllSencene' //获取场景数据
    ,height: '470'
    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
        layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
    	,curr: 1//设定初始在第 5 页
    	,groups: 5 //只显示 1 个连续页码
    	,limit:10
    	}  
    ,cols: [[ //表头
    	{field: 'xuhao', width:'60',title: '序号',templet:'#xuhao',rowspan:2} 
      ,{field: 'mpName', width:'130',title: '公众号名称',rowspan:2}
      ,{field: 'relSceneName', width:'100',title: '场景名称',align:'center'}
      ,{field: 'activityName', width:'140',title: '活动名称',align:'center'}
      ,{field: 'activityDesc', width:'260',title: '活动描述',align:'center'}
      ,{field: 'beginTime', width:'160',title: '开始时间',align:'center',templet:formatDatetime}
      ,{field: 'endTime', width:'160',title: '结束时间',align:'center',templet:formatDatetime1}
      /*,{fixed: 'right', width: '100', title: '操作',align:'center', toolbar: '#barDemo'}*/
      /* ,{field: 'CreateTime', width:'140',title: '创建时间',rowspan:2} */
    ]]
  }); 
  
  
  
  table.on('tool(test)',function(obj){
      var data=obj.data;     
     if(obj.event=='analysis'){
			if(data.relSceneUid=='lottery'){
				window.location.href='./../mp/lotteryAnalysis?activityUid='+data.activityUid;
			}if(data.relSceneUid=='order'){
				window.location.href='./../mp/orderAnalysis?activityUid='+data.activityUid;
			}if(data.relSceneUid=='meeting'){
				window.location.href='./../mp/appoint?activityUid='+data.activityUid;
			}if(data.relSceneUid=='asks'){
				window.location.href='./../mp/asks?activityUid='+data.activityUid;
			}	
		}
	});
  
  
  
  
  
  /***
  这里就是添加列表的点击事件响应
  这里是为li标签绑定响应事件
  所以li标签一下的标签，可以用jquery的find方法去查找，修改对应的样式、属性等等
  主要操作就是：
  1、清除上一次点击的效果，通过一个循环，挨个清除
  2、设置点击效果，点击了哪个就设置哪个的效果
  ***/
  $(".title_listb").on('click', function() {
	  var targetLi = $(this);
	  console.log(targetLi);
	  
	  $.each($(this).parent("ul"), function(){
	    $(this).find(".y2").css('color', '#ffffff').css('fontSize', '16px');
	  
	  });
	  //一定要写上面那个循环的后面
	  targetLi.find(".y2").css('color', '#FF0000').css('fontSize', '18px');  
	  currentType=$(this).find(".iid").text();
	  var RelScenUid=currentType;
	  var data={RelScenUid:currentType};
	  
	  //RelScenUid="002";
	  table.reload('demoReload',{
	 	  page:{curr:1},
	 	  url:"../com/getAllSencene?sceneUid="+RelScenUid,
	 	  where:data
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
	 return new Date(value.endTime).format('yyyy-MM-dd hh:mm:ss');
}


