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
	case "0":return "开";break;
	case "1":return "开";break;
	case "-1":return "关";break;
	}
}


function getVisibleAreas(value)
{
	var res=new Array();
	var area = "";
	var tmp = value.visibleAreas;
	  if(value.visibleAreas==undefined||value.visibleAreas=="none"||value.visibleAreas=="")//无地域
		  {
		  return "--";
		  }
		for(var i=0;i<JSON.parse(tmp).length;i++){
			area  =JSON.parse(tmp)[i].name;
			res.push(area)
			}
		var strNew=res.join(',')
	 return strNew;
}
