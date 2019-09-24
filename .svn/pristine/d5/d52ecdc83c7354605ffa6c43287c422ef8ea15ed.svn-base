
function showPic(value){
	return "<img class='img' style='width:50px;height:50px;' src='"+value.picUrl+"' alt=''' />"
}
function formatImportTime(value)
{
	
	  if(value.importTime==undefined)
		  {
		  return "--";
		  }
	 return new Date(value.importTime).format('yyyy-MM-dd hh:mm:ss');
}

function formatCreateTime(value)
{
	
	  if(value.createTime==undefined)
		  {
		  return "--";
		  }
	 return new Date(value.createTime).format('yyyy-MM-dd hh:mm:ss');
}


function getexpireTimeDesc(value){
	if(value.expireTime==""){
		return "--";
	}
	return value.expireTime;
}

function formatStatus(value){
	if(value.status==1){
		return "已生效";
	}else if(value.status==-1){
		return "已作废";
	}
}



function checklimit(e){
	if(e.value!=""){
		if(parseInt(e.value)>10){
			e.value=10;
			e.focus();
		}
	}
}

