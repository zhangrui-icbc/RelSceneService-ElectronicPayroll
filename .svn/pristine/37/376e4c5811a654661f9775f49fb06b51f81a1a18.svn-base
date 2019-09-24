function formatDatetime(value)
{
	
	  if(value.createTime==undefined)
		  {
		  return "--";
		  }
	 return new Date(value.createTime).format('yyyy-MM-dd hh:mm:ss');
}

function formatType(value){
	if(value.operateType==1){
		return "新增数据";
	}else if(value.operateType==2){
		return "更新数据";
	}else if(value.operateType==3){
		return "删除数据";
	}
}

function formatvalue(obj)
{
	  if(obj.value==undefined ||obj.value=="" ||obj.value=="null")
		  {
		  return "--";
		  }
	 return obj.value;
}

function formatoldvalue(obj)
{
	
	  if(obj.oldValue==undefined ||obj.oldValue=="" ||obj.oldValue=="null")
		  {
		  return "--";
		  }
	 return obj.oldValue;
}