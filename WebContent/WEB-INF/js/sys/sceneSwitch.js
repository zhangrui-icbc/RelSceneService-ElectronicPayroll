function formatCreatetime(value)
{
	
	  if(value.createTime==undefined)
		  {
		  return "--";
		  }
	 return new Date(value.createTime).format('yyyy-MM-dd hh:mm:ss');
}

function formatModifytime(value)
{
	
	  if(value.modifyTime==undefined)
		  {
		  return "--";
		  }
	 return new Date(value.modifyTime).format('yyyy-MM-dd hh:mm:ss');
}

function getSceneStatusDesc(value){
	switch(value.status.toString()){
	case "1":return "开";break;
	case "-1":return "关";break;
	}
}
