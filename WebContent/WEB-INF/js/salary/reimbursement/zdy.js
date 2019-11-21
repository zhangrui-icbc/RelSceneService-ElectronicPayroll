var ctx = $("#contextPath").val().trim();	
$( ".DIY" ).sortable();
   $( ".DIY" ).disableSelection();
window.onload=function(){

}

/**
 * 自定义模板
 */
$(".sunmit-btn").click(function(){	
  //	var objArr = [{colIndex: 0, name: "报销合计", category: "11"}];
	var objArr =[];
    for(var i = 0; i<$(".DIY span").length; i++){
        var obj = {};
		var diy_category=$(".DIY span").eq(i).attr("data-category");
		var diy_name=$(".DIY span").eq(i).text();
		obj.colIndex=i;
        obj.name = diy_name;
        obj.category = diy_category;
//        if(diy_name!="报销合计"){
//        	objArr.push(obj);
//        }
        objArr.push(obj);
    }
	 $.ajax({
		 	type:"POST",
		 	anysc:false,
		 	url:ctx+"/mp/reCustom/defineCustomTemplate",
		 	data:JSON.stringify(objArr),
		 	contentType:"application/json",
			dataType: "json",
		 	error:function(){
		 		 layerMsg('自定义模板失败');
		 	},
		 	success:function(res){
		 		if(res.code=="0"){
		 			$("#mytab_03").click();
		 			layerMsg("自定义模板成功!");
	        	}else{
	        		layerMsg('请先保存参数配置信息！');
	        	}
		 	}
		 });
    console.log(objArr);
})
//点击报销单模板调整
$("#mytab_03").click(function(){	
	 var category=[];
		var name=[];
		var id=[];
		var type=[];
		var nativeSpan=[];
	$.ajax({
	    url : ctx+"/mp/reAlternative/alternative",// 获取自己系统后台用户信息接口
	    type : "POST",
	    anysc:false,
	    success : function(res) {
	    	if(res.code==0){
	    		var str = "";
	    		var list = res.data.up;
	    		var data1=res.data.down;
	    		console.log(data1);
	    		var str = "";
	    		var list = res.data.up;
	    		for(var i=0;i<list.length;i++){
	    			if(list[i].category==11||list[i].category==22 ||list[i].category==33){
	    				 str+="<span class='act1' data-id='"+list[i].id+"'  data-category='"+list[i].category+"' data-type='"+list[i].type+"'  data-name='"+list[i].name+"' >"+list[i].name+"</span>";			 
	    			}else{
	    				 str+="<span data-id='"+list[i].id+"'  data-category='"+list[i].category+"' data-type='"+list[i].type+"' data-name='"+list[i].name+"' >"+list[i].name+"</span>";			 
	    				
	    			 }
	    		}
	    		 $(".alternative").html(str);
	    		var xqo1 = eval( data1);
	    		var html1="";
	    		var ab=0;
	    		for(var i in xqo1){
	    			if(xqo1[i].name=="报销合计"){
	    				ab=1;
	    			}
	    		}
	    		if(ab!=1){
    				html1+="<span data-id='1' data-category='11' data-type='报销合计' data-name='报销合计' >报销合计</span>";
	    		}
	    		for(var i in xqo1){
	    			console.log(xqo1[i].id);
	    			if(xqo1[i].name=="报销合计"){
	    				html1+="<span data-id='"+xqo1[i].id+"' data-category='"+xqo1[i].category+"' data-type='"+xqo1[i].type+"' data-name='"+xqo1[i].name+"' >"+xqo1[i].name+"</span>"
	    			}else{
	    				html1+="<span data-id='"+xqo1[i].id+"' data-category='"+xqo1[i].category+"' data-type='"+xqo1[i].type+"' data-name='"+xqo1[i].name+"' >"+xqo1[i].name+"<i class='delete'></i></span>"
	    			}
				}
	    		 $(".DIY").html(html1);
	    			$(".DIY span .list-id").each(function(index, element) {
	    		       	var ID=$(this).val();
	    				id.push(ID);
	    		    });
	    		  	$(".DIY span .list-type").each(function(index, element) {
	    		       	var Type=$(this).val();
	    				type.push(Type);
	    		  	});
	    		  	$(".DIY span .list-category").each(function(index, element) {
	    		       	var Category=$(this).val();
	    				category.push(Category);
	    		  	});
	    			$(".DIY span").each(function(index, element) {
	    			       	var content = $(this).text() ;   
	    					name.push(content);	
	    			});
	    		 ////////////////////////////////////
	    		    for(var i=0;i<name.length;i++){
	    		   		$(".alternative span").each(function(index, element) {
	    					var aa=$(this).text();
	    					if(aa==name[i]){
	    						if($(this).children(".list-category").val()==11||$(this).children(".list-category").val()==22 ||$(this).children(".list-category").val()==33){
	    							$(this).removeClass("act1");
	    							$(this).addClass("act");
	    						}
	    						else{
	    							$(this).addClass("act");
	    						}
	    						
	    					}
	    					
	    				})

	    		    };
	    	}
	    	else{
	    	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
})

function uploadFile(){
	var fileM=document.querySelector("#filePath");
	var flag = $("#filePath").val();
	if(flag=='' || flag==null){
		layerMsg("请先上传文件");
	}else{
		  var fileObj = fileM.files[0];
		    //创建formdata对象，formData用来存储表单的数据，表单数据时以键值对形式存储的。
		    var formData = new FormData();
		    formData.append('file', fileObj);
		    $.ajax({
		        url: ctx+"/mp/reimbursement/uploadRe",
		        type: "post",
		        dataType: "json",
		        data: formData,
		        async: false,
		        cache: false,
		        contentType: false,
		        processData: false,
		        success: function (res) {
		        	if(res.code=="0"){
		        		var test = document.getElementById('filePath');
		        		test.value = ''; 
		        		layerMsg(res.msg);
		        		$("#mytab_04").click();
		        	}else if(res.code=="301"||res.code=="500"){
		        		layerMsg(res.msg);
		        	}else{
		        		 layerMsg("上传失败!");
		        	}
		        },error:function(){
			 		 layerMsg("上传失败!");
			 	}
		    });
	}
	
}


$(".sal_rev").click(function(){	
	file = $("#filePath");     
	file.after(file.clone());     
	file.remove();  
})
$(".stf_rev").click(function(){	
	var test = document.getElementById('filePath1');
	test.value = ''; 
})
function layerMsg(msg){
	layer.open({
		title:'提示',
		type:0,
		content:msg
	   });
}
 
