  (function(){
      //方法内容
      var selector = $("#selector");
      var li_select = $("#selector .li_select");  //模拟select的ul选项
      var mrSelector = $("#selector .mr-selector");
      var li = $("#selector .li_select li"); //li选项

      selector.click(function(eve){
          eve.stopPropagation();   //阻止冒泡避免点击后消失    
      });

      mrSelector.click(function(){
          li_select.toggle();
      });
      $(".arrow").click(function(){
          li_select.toggle();
      });
      li.click(function(eve){
          var index = $(this).index();
          console.log(index);
          var text = $(this).text();  //获取当前点击li文本
          mrSelector.text(text);      //赋值默认选项文本
          console.log($("#selector .hd-selector option"));
          selector.find(".hd-selector option").eq(index)
              .attr("selected","selected").siblings().removeAttr("selected");
          $(this).parent().hide();

      });

      $("body").click(function(){
          li_select.hide();
      });
    //员工信息
      $("#mytab_05").click(function(){
      	$(".mobile-input").val("");
      	$.ajax({
      		 url : ctx+"/mp/salary/getAllStaff",
              type:'post',
              datatype:'json',
              data:{},
              error:function(){
              //	layerMsg("员工信息获取失败!")
              },
              success:function(res){
                 var data=res.data;
                 if(data==null&&res.code=='500'){
//              	   layerMsg(res.msg); 
                 }else{
              	   //将数据显示在页面上
              	   var btnHtml="<span class='change-btn' onclick='Edit(this)'>编辑</span> | <span class='delEmploy-btn' onclick='delStaff(this)'>删除</span> | <span class='initialPwd-btn' onclick='updatePwd(this)'>初始化密码</span>"
              	   //遍历数据
              	   var str="";
              	   for(var i in data){
              		   var dataList=data[i];
              		   var _num=Number(i)+1;
              		   str += "<tr><td class='hid'>"+data[i].id+"</td><td class='dept'>"+ifnul(data[i].dept)+"</td><td class='yg-name'>"+data[i].name+"</td><td class=''>"+data[i].mobile+"</td><td class='action-btns'>"+btnHtml+"</td>";
              	   }
              	   $('#ygMsg_tbody').html(str);
              	   
                 }
      		}
      	})
      }) 	
      }());
        
  function ifnul(i){
	  if(i.trim()==''||i==null){
		 return "--"; 
	  }else{
		  return i;
	  }
  }      
var ctx = $("#contextPath").val().trim();	
$(".btn1").bind("click", function(){ 
		 	var field=$(".field").val();
		   	var column=$(".column").val();
		   	var column_num=$(".column_num").val();
		   	var note=$(".note").val();
		 	if(field==""||column==""||column_num==""){
		 		layerMsg("信息不能为空");
		   		return;
		   }
		 	else{
		 		defineCom();
		 	}
			$("input").val("");
	   }); 
	   
	   $(".btn2").bind("click", function(){ 
	 	var field_b=$(".field_b").val();
	   	var column_b=$(".column_b").val();
	   	var column_num_b=$(".column_num_b").val();
	   	var note_b=$(".note_b").val();
	 	if(field_b==""||column_b==""||column_num_b==""){
	 		layerMsg("信息不能为空")
	   		return;
	   }else{
	 	//	明细类型增加
			var type=$(".field_b").val();
			   var name=$(".column_b").val();
			   if(name.length>10){
				   layerMsg("类型名称长度超出字数限制!");
				   return ;
			   }
			   var category=$(".column_num_b").val();
			   var temp ={"type":type,"name":name,"category":category}
				$.ajax({
			    url : ctx+"/mp/alternative/addAlternative",// 获取自己系统后台用户信息接口
			    type : "POST",
			    data:JSON.stringify(temp),
			    contentType:"application/json",
			    dataType: "json",
			    success : function(res) {
			    	if(res.code=="0"){
			    		$("#mytable2").append("<tr>"+"<td>"+column_b+"</td><td>"+column_num_b+"</td>"+alterColEdicHtml+"</tr>");
			    		$("#mytab_02").click();
			    		layerMsg(res.msg);
			    	}else if(res.code=="500"){
			    		layerMsg(res.msg);
			    	}else{
			    		 layerMsg("访问失败!");
			    	}
			      },
			      error : function(data){
			    	  layerMsg("访问失败")
			      }
			  });
	 	}
		$("input").val("");	
	   }); 

  var params = null;  		//Parameters
  var colsEdi =null;
  var comNewColHtml = '<div class="btn-group pull-right">'+
					'<button id="bEdit" type="button" class="iconfont icon-xiugai btn btn-sm btn-default" onclick="rowEdit(this);">' +
					'<span class="glyphicon glyphicon-pencil" > </span>'+
					'</button>'+
					'<button id="bElim" type="button" class="iconfont icon-shanchu btn btn-sm btn-default" onclick="comRowElim(this);">' +
					'<span class="glyphicon glyphicon-trash" > </span>'+
					'</button>'+
					'<button id="bAcep" type="button" class="iconfont icon-duihao btn btn-sm btn-default" style="display:none;" onclick="comRowAcep(this);">' + 
					'<span class="glyphicon glyphicon-ok" > </span>'+
					'</button>'+
					'<button id="bCanc" type="button" class="iconfont icon-right btn btn-sm btn-default" style="display:none;" onclick="rowCancel(this);">' + 
					'<span class="glyphicon glyphicon-remove" > </span>'+
					'</button>'+
    				'</div>';
  var alterNewColHtml = '<div class="btn-group pull-right">'+
	'<span class="glyphicon glyphicon-pencil" > </span>'+
	'</button>'+
	'<button id="bElim" type="button" class="iconfont icon-shanchu btn btn-sm btn-default" onclick="alterRowElim(this);">' +
	'<span class="glyphicon glyphicon-trash" > </span>'+
	'</button>'+
	'<button id="bAcep" type="button" class="iconfont icon-duihao btn btn-sm btn-default" style="display:none;" onclick="alterRowAcep(this);">' + 
	'<span class="glyphicon glyphicon-ok" > </span>'+
	'</button>'+
	'<button id="bCanc" type="button" class="iconfont icon-right btn btn-sm btn-default" style="display:none;" onclick="rowCancel(this);">' + 
	'<span class="glyphicon glyphicon-remove" > </span>'+
	'</button>'+
	'</div>';
  
  var alterNewColHtml1 = '<div class="btn-group pull-right">'+
	'<span class="glyphicon glyphicon-pencil" > </span>'+
	'</button>'+
	'<button id="bElim" type="button" class="iconfont icon-shanchu1 btn btn-sm btn-default" >' +
	'<span class="glyphicon glyphicon-trash" > </span>'+
	'</button>'+
	'<button id="bAcep" type="button" class="iconfont icon-duihao btn btn-sm btn-default" style="display:none;" onclick="alterRowAcep(this);">' + 
	'<span class="glyphicon glyphicon-ok" > </span>'+
	'</button>'+
	'<button id="bCanc" type="button" class="iconfont icon-right btn btn-sm btn-default" style="display:none;" onclick="rowCancel(this);">' + 
	'<span class="glyphicon glyphicon-remove" > </span>'+
	'</button>'+
	'</div>';
  
  
  
  var comColEdicHtml = '<td name="buttons">'+comNewColHtml+'</td>'; 
  var alterColEdicHtml = '<td name="buttons">'+alterNewColHtml+'</td>';
 // $("#mytable tbody tr").append(comColEdicHtml);
   $("#mytable2 tbody tr").append(alterColEdicHtml);
  $.fn.SetEditable = function (options) {
    var defaults = {
        columnsEd: null,         //Index to editable columns. If null all td editables. Ex.: "1,2,3,4,5"
        $addButton: null,        //Jquery object of "Add" button
        onEdit: function() {},   //Called after edition
		onBeforeDelete: function() {}, //Called before deletion
        onDelete: function() {}, //Called after deletion
        onAdd: function() {}     //Called when added a new row
    };
    params = $.extend(defaults, options);
    this.find('tbody tr').append(comColEdicHtml);
	var $tabedi = this;   //Read reference to the current table, to resolve "this" here.
    if (params.$addButton != null) {
        params.$addButton.click(function() {
            rowAddNew($tabedi.attr("id"));
        });
    }
    if (params.columnsEd != null) {
        colsEdi = params.columnsEd.split(',');
    }
  };
function IterarCamposEdit($cols, tarea) {
    var n = 0;
    $cols.each(function() {
        n++;
        if ($(this).attr('name')=='buttons') return;  //excluye columna de botones
        if (!EsEditable(n-1)) return;   //noe s campo editable
        tarea($(this));
    });
    
    function EsEditable(idx) {
        if (colsEdi==null) {  //no se definió
            return true;  //todas son editable
        } else {  //hay filtro de campos
            for (var i = 0; i < colsEdi.length; i++) {
              if (idx == colsEdi[i]) return true;
            }
            return false;  //no se encontró
        }
    }
}
/**
 * 确认修改后的填充
 * @param but
 * @returns
 */
function FijModoNormal(but) {
    $(but).parent().find('#bAcep').hide();
    $(but).parent().find('#bCanc').hide();
    $(but).parent().find('#bEdit').show();
    $(but).parent().find('#bElim').show();
    var $row = $(but).parents('tr');  //accede a la fila
    $row.attr('id', '');  //quita marca
}
function FijModoEdit(but) {
    $(but).parent().find('#bAcep').show();
    $(but).parent().find('#bCanc').show();
    $(but).parent().find('#bEdit').hide();
    $(but).parent().find('#bElim').hide();
    var $row = $(but).parents('tr');  //accede a la fila
    $row.attr('id', 'editing');  //indica que está en edición
}
function ModoEdicion($row) {
    if ($row.attr('id')=='editing') {
        return true;
    } else {
        return false;
    }
}
/**
 * 共用字段
 * 确认修改按钮
 * @param but
 * @returns
 */
function comRowAcep(but) {
//Acepta los cambios de la edición
    var $row = $(but).parents('tr');  //accede a la fila
    var $cols = $row.find('td');  //lee campos
    if (!ModoEdicion($row)) return;  //Ya está en edición
    //Está en edición. Hay que finalizar la edición
    IterarCamposEdit($cols, function($td) {  //itera por la columnas
      var cont = $td.find('input').val(); //lee contenido del input
      $td.html(cont);  //fija contenido y elimina controles
    });
    FijModoNormal(but);
    var paramsArr=[];
    for ( var i = 0; i <$cols.length; i++){
    	paramsArr[i]=$cols[i].innerText;
    }
    updateCom(paramsArr);
    params.onEdit($row);
}
/**
 * 备选字段
 * 确认修改按钮
 * @param but
 * @returns
 */
function alterRowAcep(but) {
	//Acepta los cambios de la edición
	    var $row = $(but).parents('tr');  //accede a la fila
	    var $cols = $row.find('td');  //lee campos
	    if (!ModoEdicion($row)) return;  //Ya está en edición
	    //Está en edición. Hay que finalizar la edición
	    IterarCamposEdit($cols, function($td) {  //itera por la columnas
	      var cont = $td.find('input').val(); //lee contenido del input
	      $td.html(cont);  //fija contenido y elimina controles
	    });
	    FijModoNormal(but);
	    var paramsArr=[];
	    for ( var i = 0; i <$cols.length; i++){
	    	paramsArr[i]=$cols[i].innerText;
	    }
	    updateAlter(paramsArr);
	    params.onEdit($row);
	}
function rowCancel(but) {
//Rechaza los cambios de la edición
    var $row = $(but).parents('tr');  //accede a la fila
    var $cols = $row.find('td');  //lee campos
    if (!ModoEdicion($row)) return;  //Ya está en edición
    //Está en edición. Hay que finalizar la edición
    IterarCamposEdit($cols, function($td) {  //itera por la columnas
        var cont = $td.find('div').html(); //lee contenido del div
        $td.html(cont);  //fija contenido y elimina controles
    });
    FijModoNormal(but);
}
/**
 * 修改行内容
 * @param but
 * @returns
 */
function rowEdit(but) {  //Inicia la edición de una fila
    var $row = $(but).parents('tr');  //accede a la fila
    var $cols = $row.find('td');  //lee campos
    if (ModoEdicion($row)) return;  //Ya está en edición
    //Pone en modo de edición
    IterarCamposEdit($cols, function($td) {  //itera por la columnas
        var cont = $td.html(); //lee contenido
        var div = '<div style="display: none;">' + cont + '</div>';  //guarda contenido
        var input = '<input class="form-control input-sm"  value="' + cont + '">';
        $td.html(div + input);  //fija contenido
    });
//    //获取到了id
    var paramsArr=[];
    for ( var i = 0; i <$cols.length; i++){
    	paramsArr[i]=$cols[i].innerText;
    }
    
    FijModoEdit(but);
}
/**
 * 共用字段删除行
 * @param but
 * @returns
 */
function comRowElim(but) {  //Elimina la fila actual
    var $row = $(but).parents('tr');  //accede a la fila
    var $cols = $row.find('td'); 
    IterarCamposEdit($cols, function($td) {  //itera por la columnas
        var cont = $td.html();
    });
    params.onBeforeDelete($row);
    $row.remove();
    params.onDelete();
  //获取到了id
    var id =$cols[0].innerText;
    delCom(id);
}
/**
 * 备用字段删除行
 * @param but
 * @returns
 */
function alterRowElim(but) {  //Elimina la fila actual
    var $row = $(but).parents('tr');  //accede a la fila
    var $cols = $row.find('td'); 
    IterarCamposEdit($cols, function($td) {  //itera por la columnas
        var cont = $td.html();
    });
    //获取到了id
    var id =$cols[0].innerText;
    var type =$cols[2].innerText;
    if(type=="汇总项"){
    	layerMsg("汇总项不可删除!");
    	return false;
    }
    layer.open({
        content: "确定删除\""+$cols[1].innerText+"\"吗?"
        ,btn: ['确认', '取消']
        ,yes: function(index){
    		$.ajax({
    		    url : ctx+"/mp/alternative/delAlternative?id="+id,// 获取自己系统后台用户信息接口
    		    success : function(res) {
    		    	if(res.code=="0"){
    		    		params.onBeforeDelete($row);
    		        	$row.remove();
    		        	params.onDelete();
    		    		layerMsg(res.msg);
    		    	}else if(res.code=="500"){
    		    		layerMsg(res.msg);
    		    	}else{
    		    		 layerMsg("访问失败!");
    		    	}
    		      },
    		      error : function(data){
    		    	  layerMsg("访问失败");
    		      }
    		  });
        }
    })
    
}

function comRowAddNew(tabId) {  //Agrega fila a la tabla indicada.
var $tab_en_edic = $("#" + tabId);  //Table to edit
    var $filas = $tab_en_edic.find('tbody tr');
    if ($filas.length==0) {
        //No hay filas de datos. Hay que crearlas completas
        var $row = $tab_en_edic.find('thead tr');  //encabezado
        var $cols = $row.find('th');  //lee campos
        //construye html
        var htmlDat = '';
        $cols.each(function() {
            if ($(this).attr('name')=='buttons') {
                //Es columna de botones
                htmlDat = htmlDat + comColEdicHtml;  //agrega botones
            } else {
                htmlDat = htmlDat + '<td></td>';
            }
        });
        $tab_en_edic.find('tbody').append('<tr>'+htmlDat+'</tr>');
    } else {
        //Hay otras filas, podemos clonar la última fila, para copiar los botones
        var $ultFila = $tab_en_edic.find('tr:last');
        $ultFila.clone().appendTo($ultFila.parent());  
        $ultFila = $tab_en_edic.find('tr:last');
        var $cols = $ultFila.find('td');  //lee campos
        $cols.each(function() {
            if ($(this).attr('name')=='buttons') {
                //Es columna de botones
            } else {
                $(this).html('');  //limpia contenido
            }
        });
    }
	params.onAdd();
}

function alterRowAddNew(tabId) {  //Agrega fila a la tabla indicada.
	var $tab_en_edic = $("#" + tabId);  //Table to edit
	    var $filas = $tab_en_edic.find('tbody tr');
	    if ($filas.length==0) {
	        //No hay filas de datos. Hay que crearlas completas
	        var $row = $tab_en_edic.find('thead tr');  //encabezado
	        var $cols = $row.find('th');  //lee campos
	        //construye html
	        var htmlDat = '';
	        $cols.each(function() {
	            if ($(this).attr('name')=='buttons') {
	                //Es columna de botones
	                htmlDat = htmlDat +alterColEdicHtml;  //agrega botones
	            } else {
	                htmlDat = htmlDat + '<td></td>';
	            }
	        });
	        $tab_en_edic.find('tbody').append('<tr>'+htmlDat+'</tr>');
	    } else {
	        //Hay otras filas, podemos clonar la última fila, para copiar los botones
	        var $ultFila = $tab_en_edic.find('tr:last');
	        $ultFila.clone().appendTo($ultFila.parent());  
	        $ultFila = $tab_en_edic.find('tr:last');
	        var $cols = $ultFila.find('td');  //lee campos
	        $cols.each(function() {
	            if ($(this).attr('name')=='buttons') {
	                //Es columna de botones
	            } else {
	                $(this).html('');  //limpia contenido
	            }
	        });
	    }
		params.onAdd();
	}

function TableToCSV(tabId, separator) {  //Convierte tabla a CSV
    var datFil = '';
    var tmp = '';
	var $tab_en_edic = $("#" + tabId);  //Table source
    $tab_en_edic.find('tbody tr').each(function() {
        if (ModoEdicion($(this))) {
            $(this).find('#bAcep').click();  //acepta edición
        }
        var $cols = $(this).find('td');  //lee campos
        datFil = '';
        $cols.each(function() {
            if ($(this).attr('name')=='buttons') {
                //Es columna de botones
            } else {
                datFil = datFil + $(this).html() + separator;
            }
        });
        if (datFil!='') {
            datFil = datFil.substr(0, datFil.length-separator.length); 
        }
        tmp = tmp + datFil + '\n';
    });
    return tmp;
}

$('#mytable').SetEditable({
	$addButton: $('#add')
}); 



function addAct(){
	if($(this).hasClass("act")){
		$(this).removeClass("act");
	}
	else{
		$(this).addClass("act");
	}
	
}

$(".tab3-btn1").click(function(){
		$(".add .DIY").empty();	
		var adds = new Array(); //或者写成：var btns= [];

		jQuery('.act,.act1').each(function(key,value){
		     adds[key] = $(this).html();
		});
     
      for(var i=0;i<adds.length;i++){
      	    var addHtml="<span>"+adds[i]+"</span>"
      		$(".add .DIY").append(addHtml);
      }

})
/**
 * 查询上传日志
 * @returns
 */
$("#mytab_04").click(function(){
	$.ajax({
	    url : ctx+"/mp/salary/upLoadLog",// 获取自己系统后台用户信息接口
	    type : "POST",
	    success : function(res) {
	    	if(res.code==0){
	    		var str="";
	    		var list = res.data;
	    		for(var i=0;i<list.length;i++){
	    			var delHtml = '<button id="bElim1" style="margin-right: 16%;" type="button" class="iconfont icon-shanchu btn btn-sm btn-default">' +
	    			'<span class="glyphicon glyphicon-trash" ></span></button>';
	    			str+="<tr><td class='hid'>" + list[i].id + "</td><td>"+list[i].importTime+"</td><td>"+list[i].issueTime+"</td><td>"+list[i].excelName+"</td><td class='delLog' name='buttons' style='padding-left: 38px;'>"+delHtml+"</td></tr>";
	    		}
	    		$(".sal_log").html(str);
	    	}else{
//	    		layerMsg(res.msg);
	    	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败");
	      }
	  });
})
//删除上传日志
$(document).on('click', '#bElim1', function () {
    //执行代码
	var content = $(this).parents("tr");
    var arr = content.find('td'); 
  //获取到了id
    var id =arr[0].innerText;
    layer.open({
        content: "确定删除\""+arr[3].innerText+"\"吗?"
        ,btn: ['确认', '取消']
        ,yes: function(index){
        	$.ajax({
        	    url : ctx+"/mp/salary/delLog",// 获取自己系统后台用户信息接口
        	    type : "POST",
        	    data:{"salaryId":id},
        	    success : function(res) {
        	    	if(res.code=="0"){
        	    		$("#mytab_04").click();
        	    		layerMsg(res.msg);
        	    	}else if(res.code=="500"){
        	    		layerMsg(res.msg);
        	    	}else{
        	    		 layerMsg("访问失败!");
        	    	}
        	      },
        	      error : function(data){
        	    	  layerMsg("访问失败")
        	      }
        	  });
        }
    })
})

//上传日志详情
$(document).on('click', '.listDetail', function () {
    //执行代码
	var content = $(this).parents("tr");
    var arr = content.find('td'); 
  //获取到了id
    var id =arr[0].innerText;
    var date = arr[2].innerText;
$.ajax({
    url : ctx+"/mp/salary/upLoadDetail",// 获取自己系统后台用户信息接口
    type : "POST",
    data:{"salaryId":id,"date":date},
    success : function(res) {
    	if(res.code=="0"){
            var data=res.data;
            //将数据显示在页面上
             var str = "<div class='listDetail_box'><div class='detail_tab'><img  class='closex' style='position:absolute;right:5%' src='"+ctx+"/css/images/salary/x.png'><div class='detail_tab_div'><table id='listDetail_tab'><thead><tr>";
             for(var key in data[0]){
          str+="<th>"+key+"</th>"
         }
         str +="</tr></thead><tbody>";
             //遍历数据


        for(var i in data){
         console.log(data[i])
         var dataList=data[i];
         str += "<tr>";
         for( j in dataList){
          console.log(dataList[j])
          str +="<td>"+dataList[j]+"</td>";
         }
         str += "</tr>"
        }
        //遍历完成之后
        str +="</tbody></table></div></div></div>";
        //将表格添加到body中
        $(".content2" , parent.document).append(str);
       
        //$('.vbm').append(str);
    	}else if(res.code=="500"){
    		layerMsg(res.msg);
    	}else{
    		 layerMsg("访问失败!");
    	}
    	
      },
      error : function(data){
    	  layerMsg("访问失败")
      }
  });

})







/**
 * 清空自定义模板
 * @returns
 */
$(".clear").click(function(){
	$(".add .DIY").empty();	
	$(".alternative span").removeClass("act");
	
	var html1="";
	html1+="<span data-id='1' data-category='11' data-type='实际收入' data-name='实际收入' >实际收入</span>";
	html1+="<span data-id='2' data-category='22' data-type='收入合计' data-name='收入合计' >收入合计</span>";
	html1+="<span data-id='3' data-category='33' data-type='支出合计' data-name='支出合计' >支出合计</span>";
	$(".DIY").html(html1);
})





/**
 * 查询出共用模板字段
 * @returns
 */
$(".com").click(function(){
		$.ajax({
	    url : ctx+"/mp/common/getCommonTemplate",// 获取自己系统后台用户信息接口
	    type : "POST",
	    success : function(res) {
	    	if(res.code==0){
	    		var str = "";
	    		var list = res.data;
	    		for(var i=0;i<list.length;i++){
	    			str += "<tr>" + 
	    			"<td class='hid'>" + list[i].id + "</td>" +
	    			"<td>" + list[i].name + "</td>" +
	    			"<td>" + list[i].colIndex + "</td>" +
	    			"<td>" + list[i].remark + "</td>" +
	    			'<td name="buttons">'+comNewColHtml+"</td>"+
	    			"</tr>";
	    		}
	    		 $("#tab-com").html(str);
	    	}else{
	    	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
})

/**
 * 查询出个性化模板
 */
function  getCustom(){
		$.ajax({
	    url : ctx+"/mp/custom/getCustomTemplate",// 获取自己系统后台用户信息接口
	    type : "POST",
	    success : function(res) {
	    	if(res.code=="0"){
        		layerMsg(res.msg);
        	}else if(res.code=="500"){
        		layerMsg(res.msg);
        	}else{
        		 layerMsg("查询失败!");
        	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
}
/**
 * 删除共用字段
 * @returns
 */
function delCom(id){
	if(confirm("确定删除吗")){
		$.ajax({
		    url : ctx+"/mp/common/delCommonTemplate?id="+id,// 获取自己系统后台用户信息接口
		    success : function(res) {
		    	if(res.code==0){
		    		if(res.code=="0"){
		        		layerMsg(res.msg);
		        	}else if(res.code=="500"){
		        		layerMsg(res.msg);
		        	}else{
		        		 layerMsg("删除失败!");
		        	}
		    	}else{
		    	}
		      },
		      error : function(data){
		    	  layerMsg("访问失败");
		      }
		  });
	}else{
		layerMsg("已取消");
	}

}



/**
 * 查询出备选模板字段
 * @returns
 */
$("#mytab_02").click(function(){
		$.ajax({
	    url : ctx+"/mp/alternative/alternative",// 获取自己系统后台用户信息接口
	    type : "POST",
	    success : function(res) {
	    	if(res.code==0){
	    		var str = "";
	    		str +="<tr>" +  // id
    			"<td class='hid'>1</td>" +
    			"<td>实际收入</td>" +
    			"<td>" + salType(11) + "</td>" +
    			'<td name="buttons">'+alterNewColHtml1+"</td>"+
    			"</tr>";
	    		str +="<tr>" +  // id
	    		"<td class='hid'>1</td>" +
    			"<td>收入合计</td>" +
    			"<td>" + salType(22) + "</td>" +
    			'<td name="buttons">'+alterNewColHtml1+"</td>"+
    			"</tr>";
	    		str +="<tr>" +  // id
	    		"<td class='hid'>1</td>" +
    			"<td>支出合计</td>" +
    			"<td>" + salType(33) + "</td>" +
    			'<td name="buttons">'+alterNewColHtml1+"</td>"+
    			"</tr>";
	    		var list = res.data.up;
	    		var data1=res.data.down;
	    		 $("#tab-com2").children().remove();
	    		for(var i=0;i<list.length;i++){
	    			if(salType(list[i].category)=="汇总项"){
		    			str += "<tr>" +  // id
		    			"<td class='hid'>" + list[i].id + "</td>" +
		    			"<td>" + list[i].name + "</td>" +
		    			"<td>" + salType(list[i].category) + "</td>" +
		    			'<td name="buttons">'+alterNewColHtml1+"</td>"+
		    			"</tr>";
	    			}else{
		    			str += "<tr>" +  // id
		    			"<td class='hid'>" + list[i].id + "</td>" +
		    			"<td>" + list[i].name + "</td>" +
		    			"<td>" + salType(list[i].category) + "</td>" +
		    			'<td name="buttons">'+alterNewColHtml+"</td>"+
		    			"</tr>";
	    			}

	    		}
	    		 $("#tab-com2").html(str);
	    		 
	    	}else if(res.code=="500"){
//	    		layerMsg(res.msg);
	    	}else{
	    		 layerMsg("访问失败!");
	    	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
})

/**
 * 修改备选模板字段
 * @returns
 */
function updateAlter(arr){
	   var id=arr[0];
	   var column=arr[1];
	   var column_num=arr[2];
	   var temp ={"id":id,"name":column,"category":column_num}
		$.ajax({
	    url : ctx+"/mp/alternative/updateAlternative",// 获取自己系统后台用户信息接口
	    type : "POST",
	    data:JSON.stringify(temp),
	    contentType:"application/json",
	    dataType: "json",
	    success : function(res) {
	    	if(res.code=="0"){
	    		layerMsg(res.msg);
	    	}else if(res.code=="500"){
	    		layerMsg(res.msg);
	    	}else{
	    		 layerMsg("访问失败!");
	    	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
}



/**
 * 定义共用模板字段
 * @returns
 */
function  defineCom(){
   	   var column=$(".column").val();
   	   var column_num=$(".column_num").val();
   	   var note=$(".note").val();
   	   var list = new Array();
   	   var temp ={"name":column,"colIndex":column_num,"remark":note}
   	   list[0]=temp;
		$.ajax({
	    url : ctx+"/mp/common/defineCommonTemplate",// 获取自己系统后台用户信息接口
	    data :JSON.stringify(list),
	    type : "POST",
	    contentType:"application/json",
	    dataType: "json",
	    success : function(res) {
	    	if(res.code=="0"){
	    		layerMsg(res.msg);
	    		$("#mytable").append("<tr>"+"<td>"+column+"</td><td>"+column_num+"</td><td>"+note+"</td>"+comColEdicHtml+"</tr>");
	    		$("#mytab_01").click();
	    	}else if(res.code=="500"){
	    		layerMsg(res.msg);
	    	}else{
	    		 layerMsg("访问失败!");
	    	}
	      },
	      error : function(res){
	    	  layerMsg("访问失败")
	      }
	  });
}
/**
 * 修改共用模板字段
 * @returns
 */
function  updateCom(arr){
	   var id=arr[0];
   	   var column=arr[2];
   	   var column_num=arr[3];
   	   var note=arr[4];
   	   var temp ={"id":id,"name":column,"colIndex":column_num,"remark":note}
		$.ajax({
	    url : ctx+"/mp/common/updateCommonTemplate",// 获取自己系统后台用户信息接口
	    data :JSON.stringify(temp),
	    type : "POST",
	    contentType:"application/json",
	    dataType: "json",
	    success : function(data) {
	    	if(res.code=="0"){
	    		layerMsg(res.msg);
	    	}else if(res.code=="500"){
	    		layerMsg(res.msg);
	    	}else{
	    		 layerMsg("访问失败!");
	    	}
	      },
	      error : function(data){
	    	  //actlayerMsg(data.msg);
	    	  layerMsg("访问失败");
	      }
	  });
}
/**
 * 员工信息下载
 * @returns
 */
$(".yg-download").click(function(){
	window.location.href=ctx+"/mp/salary/staffExport";
})
/**
 * 工资条下载
 * @returns
 */
$(".gz-download").click(function(){
	window.location.href=ctx+"/mp/salary/export";
})
/**
 * 初始化员工密码
 * @returns
 */
$(".pwd_up").click(function(){
	   var mobile=$(".mobile").val();
		$.ajax({
	    url : ctx+"/mp/salary/updatePwd?userName="+mobile,// 获取自己系统后台用户信息接口
	    type : "get",
	    async :true,
	    contentType:"application/json",
	    dataType: "json",
	    success : function(res) {
	    	if(res.code=="0"){
	    		layerMsg(res.msg);
	    	}else if(res.code=="500"){
	    		layerMsg(res.msg);
	    	}else{
	    		 layerMsg("访问失败!");
	    	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
})
	$(".alternative").on("click","span",function(){
		var id = $(this).attr("data-id");
		var category = $(this).attr("data-category");
		var type = $(this).attr("data-type");
		var name = $(this).attr("data-name");
		if($(this).hasClass("act")){
		   return false;
		}
		if(addLabel(id,category,type,name)){                  //添加，并要把标签库里相应的标签设置为已选择
			   $(this).addClass("act");
		}
	
	});


	//添加标签到最上面                                    
	function addLabel(id,category,type,name){	    
	    var labelHTML = getappendHTML(id,category,type,name);
	    $(".DIY").append(labelHTML);	    
	    return true;
	}
	
	function getappendHTML(id,category,type,name){
		return "<span data-id='"+id+"' data-category='"+category+"' data-type='"+type+"' data-name='"+name+"'>"+name+"<i class='delete'></i></sapn>";
	} 
	//删除已选标签
	$(".DIY").on("click","span .delete",function(){
	    var id = $(this).parent().attr("data-id");
	    var text = $(this).parent().attr("data-name");
	    console.log(text);
		//删除最上面
		$(this).parent().remove();
		$(".alternative").find("span[data-name='"+text+"']").removeClass("act");
	});

function salType(type){
	switch(type) {
    case 1:
     return  "收入"
       break;
    case 2:
     return	"支出"
       break;
    case 4:
        return	"单位支出"
          break;
    case 5:
        return	"备注"
    case 6:
        return	"专项附加扣除"
          break;
    default:
    return  "汇总项";
}
}



//根据id删除员工信息
function delStaff(but){
    var $row = $(but).parents('tr');  
    var $cols = $row.find('td'); 
    var id =$cols[0].innerText;
    var temp = {"id":id};
    layer.open({
        content: "确定删除\""+$cols[2].innerText+"\"吗?"
        ,btn: ['确认', '取消']
        ,yes: function(index){
        	$.ajax({
        	    url : ctx+"/mp/salary/delStaff",// 获取自己系统后台用户信息接口
        	    type : "post",
        	    async :true,
        	    data:temp,
        	    success : function(res) {
        	    	layerMsg(res.msg);
        	    	$(but).parents("tr").remove();
        	      },
        	      error : function(data){
        	    	  layerMsg("访问失败")
        	      }
        	  });
        }
    })
}
//编辑
function Edit(a) {
    	var tr = a.parentNode.parentNode,
        toEdit = a.innerHTML == '编辑'; 
    //	a.innerHTML = toEdit ? '保存' : '编辑';
    	a.innerHTML = '保存';
    	if (toEdit) {//编辑
    		tr.cells[2].innerHTML = '<input type="text" value="' + tr.cells[2].innerHTML.replace(/"/g, '&quot;') + '"/>'
    		tr.cells[3].innerHTML = '<input type="text" value="' + tr.cells[3].innerHTML.replace(/"/g, '&quot;') + '"/>'
    	}
    	else {//保存
    	    var $row = $(a).parents('tr');  
    	    var $cols = $row.find('td'); 
    	    var id =$cols[0].innerText;
    	    var name= tr.cells[2].firstChild.value;
    	    var mobile=tr.cells[3].firstChild.value;
    		if (!/^[1][356789][0-9]{9}$/.test(mobile)) {
    			layerMsg("请输入正确的手机号码.");
    			return;
    		}
        	$.ajax({
        	    url : ctx+"/mp/salary/updateStaffInfo",// 获取自己系统后台用户信息接口
        	    type : "get",
        	    async :true,
        	    contentType:"application/json",
        	    dataType: "json",
        	    data:{"id":id,"name":name,"mobile":mobile},
        	    success : function(res) {
        	    	layerMsg(res.msg);
        	    	if(res.code==0){
        	    		tr.cells[2].innerHTML = tr.cells[2].firstChild.value.replace(/</g, '&lt;').replace(/>/g, '&gt;')
        	    		tr.cells[3].innerHTML = tr.cells[3].firstChild.value.replace(/</g, '&lt;').replace(/>/g, '&gt;')
        	    		a.innerHTML = '编辑';
        	    	}
        	    },
        	      error : function(data){
        	    	  layerMsg("访问失败")
        	      }
        	  });
    	}
		}


function nTabs(id) { 
	$('.stitle').removeClass('active'); 
	$('#'+id).addClass('active'); 
	$('.tab').hide(); 
	$('#'+id+'_1').show(); 
}
$("#filePath").click(function(){
	$(".sc").addClass("active");
})
function layerMsg(msg){
	layer.open({
		title:'提示',
		type:0,
		content:msg
	   });
}
//根据手机号查询员工信息
$("#ygMsg-detail").click(function(){
	var mobile=$(".mobile-input").val();
	$.ajax({
	    url : ctx+"/mp/salary/getAllStaff",// 获取自己系统后台用户信息接口
	    type : "get",
	    async :true,
	    contentType:"application/json",
	    dataType: "json",
	    data:{"mobile":mobile},
	    success : function(res) {
            var data=res.data;
 	    	//将数据显示在页面上
 	    	var btnHtml="<span class='change-btn' onclick='Edit(this)'>编辑</span>|<span class='delEmploy-btn' onclick='delStaff(this)'>删除</span>|<span class='initialPwd-btn'  onclick='updatePwd(this)'>初始化密码</span>"
 	     	//遍历数据
 	     	var str="";
 			for(var i in data){
 				var dataList=data[i];
 				var _num=Number(i)+1;
 				str += "<tr><td class='hid'>"+data[i].id+"</td><td class='yg-name'>"+data[i].name+"</td><td class=''>"+data[i].mobile+"</td><td class='action-btns'>"+btnHtml+"</td>";
 			}
 			$('#ygMsg_tbody').html(str);
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
})
//初始化员工密码
function updatePwd(but){
    var $row = $(but).parents('tr');  
    var $cols = $row.find('td'); 
    var id =$cols[0].innerText;
	$.ajax({
	    url : ctx+"/mp/salary/updatePwd",// 获取自己系统后台用户信息接口
	    type : "get",
	    async :true,
	    contentType:"application/json",
	    dataType: "json",
	    data:{"id":id},
	    success : function(res) {
	    	if(res.code=="0"){
	    		layerMsg(res.msg);
	    	}else if(res.code=="500"){
	    		layerMsg(res.msg);
	    	}else{
	    		 layerMsg("访问失败!");
	    	}
	      },
	      error : function(data){
	    	  layerMsg("访问失败")
	      }
	  });
}

