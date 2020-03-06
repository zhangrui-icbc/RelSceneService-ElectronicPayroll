<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资单管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
                <li class="stitle alter" id="mytab_05" onclick="nTabs('mytab_05');window.open='#mytab_05'">
                	<span>员工信息</span>
                	<img src="../image/salary/salary/icon4.png" />
                </li>
                <li class="stitle" id="mytab_02" onclick="nTabs('mytab_02');window.open='#mytab_02'">
                	<span>工资单明细增加</span>
                	<img src="../image/salary/salary/icon1.png" />
                </li>
                <li class="stitle zdy alter1" style="width: 160px;"id="mytab_03" onclick="nTabs('mytab_03');window.open='#mytab_03'">
                	<span>工资单模板调整</span>
                	<img src="../image/salary/salary/icon2.png" />
                </li>
               	<li class="stitle sc" id="mytab_04" onclick="nTabs('mytab_04');window.open='#mytab_04'">
               		<span>工资单上传</span>
               		<img src="../image/salary/salary/icon3.png" />
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
	            	<div class="input-box" style="padding: 17px 0;">
	            		<span class="input-name" style="align-self: center;margin-right: 1%;">类型名称：</span><input class="add-input column_b" style="margin-right: 18%;" type="text" placeholder="请输入类型名称" /><br />
	            		<span class="input-name" style="align-self: center;margin-right: 1%;">收支类型：</span>
	            		
	            		<div id="selector">
					        <select class="hd-selector add-input column_num_b select">
					            <option value="1">收入</option>
					            <option value="2">支出</option>
					            <option value="4">单位支出</option>
					            <option value="6">专项附加扣除</option>
					        </select>
					        <span class="mr-selector" style="margin-right: 10%;">收入</span>
					        <span class="arrow"></span>
					        <ul class="li_select">
					            <li>收入</li>
					            <li>支出</li>
					            <li>单位支出</li>
					            <li>专项附加扣除</li>
					        </ul>
					    </div>
	            		<a href="##" class="add-btn btn2" style="margin-left: 4%;">确定提交</a>
	            	</div>
            		<table class="table table-bordered table-striped" id="mytable2" >
						<thead>
							<tr>
								<td style="width: 33%;">类型名称</td>
								<td>收支类型</td>
								<td style="width: 33%;">操作</td>
							</tr>
						</thead>
						<tbody id="tab-com2">
						
						</tbody>
					</table>
            	</div>
            	
            	<!--模板调整--> 
                <div class="tab tab3" id="mytab_03_1">
                	<h3 style="font-size:15px;padding-left:8%;font-weight:600;">备选明细类型:</h3>
                	<div class="alternative">
                	</div>
                  <div id="notes" class="notes"  style="margin: 2% 0% 2% 10%;">
	                	<h2 class="descp"><span style="color:#c00000">温馨提示：</span>实际收入、收入合计、支出合计为必选项，已默认选择。点击上方备选明细类型选择所需工资单明细类型，点击下方单个已选明细类型，前后挪动可更改明细类型展示的先后顺序。</h2>
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
                
        		 <!-- 工资单上传 -->
				<div class="tab tab4" id="mytab_04_1">
								<div class="download-bg">
									<div class="down-div">
										<button type="button" class="gz-download" style="background: #2fafe6;">工资单模板下载</button>
										<button class="sub" lay-event="uploadSal" id="sal_sub"><i class="layui-icon"></i>上传工资信息</button>
									</div>
								</div>
								<table class="table table-bordered table-striped" >
									<thead>
										<tr><th style="width: 25%;">导入时间</th><th style="width: 25%;">发放时间</th><th style="width: 25%;">excel文件</th><th style="width: 25%;">操作</th></tr>
									</thead>
									<tbody class="sal_log">
									</tbody>
								</table>
							</form>
				</div>
				<div th:include="include :: footer"></div>	
	<div class="tab tab5"  id="mytab_05_1">
		<div class="download-bg">
			<div class="down-div">
           <label style="line-height:30px;">手机号：</label>
           <input  type="number"  class="mobile-input"/>
           <button id="ygMsg-detail"  type="button">查询</button>
		    	<button type="button" class="yg-download" style="background: #2fafe6;">员工信息模板下载</button>
				<button class="sub" lay-event="uploadSta" id="sta_sub"><i class="layui-icon"></i>上传员工信息</button>
			</div>
		</div>	
		<div class="ygMsg" id="ygMsg">
			<table id="ygMsg_tab" cellspacing="0">
				
				<thead><th style="width: 25%;">部门</th><th style="width: 25%;">姓名</th><th style="width: 25%;">手机号</th><th style="width: 25%;">操作</th></thead>
				<tbody id="ygMsg_tbody"></tbody>
			</table>
		</div>
	</div>
		</div>
    </div>
	 </div>
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/salary/layer.js"></script>
<script src="../js/common.js"></script>
<script src="../js/order/ordercfg.js"></script>
<script src="../js/salary/jquery-ui.js"></script>
<script src="../css/layui/layui.js"></script>
<script src="../js/salary/salary/salary.js"></script>
<script src="../js/salary/salary/zdy.js"></script>
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
	//员工信息上传
 	upload.render({
		elem:"#sta_sub",
		url:'../mp/salary/uploadStaff',
		accept:'file', 
		exts:'xls|xlsx',
		done:function(data){
			if(data.code==1){
				data.msg="上传成功";
			}
			$("#mytab_05").click();
			layer.open({
				title:'提示',
				type:0,
				content:data.msg
			   });
			if(data.data.errList.length>0){
				window.location.href="../mp/salary/exportErrPhone";
			}
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
		url:'../mp/salary/uploadSalary',
		accept:'file', 
		exts:'xls|xlsx',
		done:function(data){
			if(data.code==1){
				data.msg="上传成功";
			}
			$("#mytab_04").click();
			layer.open({
				title:'提示',
				type:0,
				content:data.msg
			   });
			if(data.data.errorSalaryList.length>0){
				window.location.href="../mp/salary/exportErrSalaryInfo";
			}
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
	$('#mytab_05').addClass('active'); 
	$(".alter").click();
	$('#mytab_05_1').show(); 
} 

</script>
</body>
</html>