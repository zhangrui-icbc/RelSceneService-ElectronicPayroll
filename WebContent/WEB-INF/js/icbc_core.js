/**
 * 工行基础工具类
 */
function ICBCUtil() {
}
/**
* 检测当前的操作系统是否为Windows
*/
ICBCUtil.isWin = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('Window')>-1) {
		return true;
	}
	return false;
};
/**
 * 检测当前操作系统是否为Mac
 */
ICBCUtil.isMac = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('Mac')>-1) {
		return true;
	}
	return false;
};
/**
 * 检测当前浏览器是否为iPhone(Safari)
 */
ICBCUtil.isIPhone = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('ICBCiPhoneBS')>-1||ua.indexOf('iPhone')>-1) {
		return true;
	}
	return false;
};
/**
 * 检测当前浏览器是否为IE(WindowsPhone)
 */
ICBCUtil.isWindowsPhone = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('ICBCWindowsPhoneBS')>-1||ua.indexOf('MSIE')>-1) {
		return true;
	}
	return false;
};
/**
 * 检测当前浏览器是否为Android(Chrome)
 */
ICBCUtil.isAndroid = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('ICBCAndroidBS')>-1||ua.indexOf('Android')>-1) {
		return true;
	}
	return false;
};
/**
 *返回客户端 
 */
ICBCUtil.returnBack=function(){
	if(ICBCUtil.isIPhone()){
		 this.iOSExcuteNativeMethod('Native://saveConfig=1&func=goBack');
		return false;
	}else if(ICBCUtil.isAndroid()){
		if(ICBCUtil.isSupportAndroidNewInterface()){
			//prompt('returnBack');//服务平台以此为准
			 prompt('callNativeMethod',"{obj:Native,func:returnBack}");
		}else{
			Native.returnBack();			
		}
		return false;
	}else if(ICBCUtil.isWindowsPhone()){
		var result = "{'type':'nativerequest','requestObject':'{\"type\":\"back\"}'}";
		window.external.notify(result);
		return false;
	}
};


/**
 * 是否支持Android新接口
 */
ICBCUtil.isSupportAndroidNewInterface=function(){
	try{
		if(ICBCUtil.isAndroid()){
			var ua = navigator.userAgent;
			if (ua.indexOf('PromptFlag') > -1) {
				return true;
			}
			return false;
		}
	}catch(e){}
	return false;
};

/**
 *  获取客户端版本号
 */
ICBCUtil.getFullVersion=function(){
	var ua = navigator.userAgent;
	ua = ua.split("fullversion");
	ua = ua[1];
	try {
			// 取版本号
			var version = ua.match(new RegExp('\\d\.\\d\.\\d'));
			if (version != undefined) {
				var versionInt = parseInt(version[0].replace(/\./g, ''));		
				return versionInt;
			}		
	} catch (e) {
	}
	return null;
};

/**
 * 提交订单
 */
ICBCUtil.submitOrder=function(params){
	var interfaceName=params.interfaceName;
	var interfaceVersion=params.interfaceVersion;
	var tranData=params.tranData;
	var merSignMsg=params.merSignMsg;
	var merCert=params.merCert;
	if(ICBCUtil.isAndroid()){
		//调用Native接口提交订单
		try{
			if(ICBCUtil.isSupportAndroidNewInterface()){
				var temp={};
				temp.interfaceName=interfaceName;
				temp.interfaceVersion=interfaceVersion;
				temp.tranData=tranData;
				temp.merSignMsg=merSignMsg;
				temp.merCert=merCert;
				temp.clientType="22";
				prompt('submitOrder',JSON.stringify(params));
			}else{
				PortalRequestService.submitOrder(interfaceName,interfaceVersion,tranData,merSignMsg,merCert,'22');
			}
		}catch(e){}
	}else if(ICBCUtil.isIPhone()){
		try{
			var param="Native://";
			param=ICBCUtil.appendParam(param,"startType","B2C");
			param=ICBCUtil.appendParam(param,"interfaceName",interfaceName);
			param=ICBCUtil.appendParam(param,"interfaceVersion",interfaceVersion);
			param=ICBCUtil.appendParam(param,"tranData",tranData);
			param=ICBCUtil.appendParam(param,"merSignMsg",merSignMsg);
			param=ICBCUtil.appendParam(param,"merCert",merCert);
			param=ICBCUtil.appendParam(param,"clientType","21");
			ICBCUtil.iOSExcuteNativeMethod(param);
			return false;
		}catch(e){}
	}
};


ICBCUtil.appendParam=function(param,name,value){
	return param+=name+"="+value+"&";
};

ICBCUtil.isIMOverSea = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('view_from_fovac')>-1||ua.indexOf('ICBCAppType=ICBC_GLOBAL')>-1||ua.indexOf('ICBCAppType=ICBC_CANADA')>-1) {
		return true;
	}
	return false;
};

ICBCUtil.isMIMS = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('F-MIMS')>-1) {
		return true;
	}
	return false;
};


ICBCUtil.iOSExcuteNativeMethod=function(param){
	var iFrame;
	iFrame=document.createElement("iframe");
	//新增判断融E联客户端UA
	if(ICBCUtil.isMIMS()){
		/* 2018.01.30 配合IOS的XCODE升级（增加icbc?） start*/
		var ind = param.indexOf("//"); 
		ind = ind + 1;
		var ind1 = param.indexOf("&");
		var substr0 = "";
		if(ind1 == -1){
			substr0 = param.substring(ind+1);
		}else{
			substr0 = param.substring(ind+1,ind1);
		}
		if(substr0.indexOf("?") == -1){//如果param中“Native://"后，“&”符号前不包含?需要在//后添加icbc?iphoneFlag=1
			var substr1 = param.substring(0,ind+1);
			var substr2 = param.substring(ind+1);
			param = substr1 + "icbc?" + substr2;
		}
		/* 2018.01.30 配合IOS的XCODE升级（增加icbc?） end*/
	}

	iFrame.setAttribute("src", param);
	iFrame.setAttribute("style", "display:none");
	iFrame.setAttribute("height", "0px");
	iFrame.setAttribute("width", "0px");
	iFrame.setAttribute("frameborder", "0");
	document.body.appendChild(iFrame);
	iFrame.parentNode.removeChild(iFrame);
	iFrame=null;
};
/**
 * 客户端存储数据
 */
ICBCUtil.nativeSaveConfig=function(param){
	try {
		var func = param.func;
		if(func=="changeBackUrl"){
			if (param.key == undefined||param.value == undefined) {
				return;
			}
			param.value=encodeURI(param.value);
			if (ICBCUtil.isIPhone()) {
				this.iOSExcuteNativeMethod('Native://changeBackUrl=1&key='+param.key+'&value='+param.value);
			} 
			else if (ICBCUtil.isAndroid()) {
				if(ICBCUtil.isSupportAndroidNewInterface()){
					prompt('callNativeMethod',"{obj:Native,func:changeBackUrl,args:['"+param.key+"','"+param.value+"']}");
				}else{
					Native.saveConfig(param.key,param.value);
				}	
			}
		}
		else if(func=="privateBankFingancing"){
			if (param.prodId == undefined 
					|| param.funcNo == undefined
					|| param.url == undefined
					||param.prodName == undefined) {
				return;
			}
			param.prodId=encodeURI(param.prodId);
			param.funcNo=encodeURI(param.funcNo);
			param.prodName=encodeURI(param.prodName);
			param.url=encodeURI(param.url);
			if (ICBCUtil.isIPhone()) {
				
				  this.iOSExcuteNativeMethod('Native://InjectToApp=1&inject=financeproduct&ID='+param.prodId+'&FuncNo='+param.funcNo+'&productShName='+param.prodName);
			 } 
			 else if (ICBCUtil.isAndroid()) {
				if(ICBCUtil.isSupportAndroidNewInterface()){
				    prompt('callNativeMethod',"{obj:Native,func:buyProduct,args:['"+param.url+"']}");
				    }else{
					Native.buyProduct(param.url);
				}	
			  }
		}
		else if(func=="licai"){
			 if (param.title == undefined
				 ||param.description == undefined
				 ||param.imageurl == undefined
				 ||param.url == undefined
				 ||param.func == undefined
				 ||param.PRODUCTTERM == undefined
				 ||param.INTENDYIELD == undefined
				 ||param.LOWESTBUYLEVEL == undefined
				 ||param.CURRNAME == undefined) {
					  return;
			 }	
			 param.title=encodeURI(param.title);
			 param.description=encodeURI(param.description);
			 param.imageurl=encodeURI(param.imageurl);
			 param.url=encodeURI(param.url);
			 param.func=encodeURI(param.func);			 
			 param.ProductTerm=encodeURI(param.PRODUCTTERM);
			 param.Intendyield=encodeURI(param.INTENDYIELD);
			 param.LowestBuyLevel=encodeURI(param.LOWESTBUYLEVEL);
			 param.CurrName=encodeURI(param.CURRNAME);
			 if (ICBCUtil.isIPhone()) {
				  this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&title='+param.title+'&description='+param.description+'&imageurl='+param.imageurl+'&ProductTerm='+param.ProductTerm+'&Intendyield='+param.Intendyield+'&LowestBuyLevel='+param.LowestBuyLevel+'&CurrName='+param.CurrName+'&url='+param.url);
			 } 
			 else if (ICBCUtil.isAndroid()) {
				if(ICBCUtil.isSupportAndroidNewInterface()){
				    prompt('callNativeMethod',"{obj:Native,func:saveConfigForFinance,args:['"+param.title+"','"+param.description+"','"+param.imageurl+"','"+param.url+"','"+param.func+"','"+param.ProductTerm+"','"+param.Intendyield+"','"+param.LowestBuyLevel+"','"+param.CurrName+"']}");
				    }else{
					Native.saveConfigForFinance(param.title,param.description,param.imageurl,param.url,param.func,param.ProductTerm,param.Intendyield,param.LowestBuyLevel,param.CurrName);
				}	
			  }

		}
		else if(func=="jijin"){
			 if (param.title == undefined||param.description == undefined||param.imageurl == undefined||param.url == undefined||param.func == undefined||param.fundType == undefined||param.zdrgVal == undefined) {
					  return;
			 }	
			 param.title=encodeURI(param.title);
			 param.description=encodeURI(param.description);
			 param.imageurl=encodeURI(param.imageurl);
			 param.url=encodeURI(param.url);
			 param.func=encodeURI(param.func);
			 param.fundType=encodeURI(param.fundType);
			 param.zdrgVal=encodeURI(param.zdrgVal);
			 var version = ICBCUtil.getFullVersion();
			 if (ICBCUtil.isIPhone()) {
					if (version >= 104) {
				        this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&title='+param.title+'&description='+param.description+'&imageurl='+param.imageurl+'&fundType='+param.fundType+'&zdrgVal='+param.zdrgVal+'&url='+param.url);
					}else{
						this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&title='+param.title+'&description='+param.description+'&imageurl='+param.imageurl+'&url='+param.url);
					}
			 } 
			 else if (ICBCUtil.isAndroid()) {
				 if(ICBCUtil.isSupportAndroidNewInterface()){
					 if (ICBCUtil.getIMChannel()=="C") {
					     if (version >= 105) {
					    	 prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.title+"','"+param.description+"','"+param.imageurl+"','"+param.url+"','"+param.func+"','"+param.fundType+"','"+param.zdrgVal+"']}");
					     }else{
					    	 prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.title+"','"+param.description+"','"+param.imageurl+"','"+param.url+"','"+param.func+"']}");
					     }
					 }else{
					     if (version >= 104) {
					    	 prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.title+"','"+param.description+"','"+param.imageurl+"','"+param.url+"','"+param.func+"','"+param.fundType+"','"+param.zdrgVal+"']}");
					     }else{
					    	 prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.title+"','"+param.description+"','"+param.imageurl+"','"+param.url+"','"+param.func+"']}");
					     }
					 }					     
				 }else{
					 Native.saveConfig(param.title,param.description,param.imageurl,param.url,param.func);
				 }	
			  }

		}
		else if(func=="addCustomer" || func=="addManager"){
			if (param.func == undefined||param.mobileNo == undefined||param.status == undefined) {
				  return;
		 }	
		 param.func=encodeURI(param.func);
		 param.mobileNo=encodeURI(param.mobileNo);
		 param.status=encodeURI(param.status);

		 if (ICBCUtil.isIPhone()) {
			 this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&mobileNo='+param.mobileNo+'&status='+param.status);
			 } 
		 else if (ICBCUtil.isAndroid()) {
			 if(ICBCUtil.isSupportAndroidNewInterface()){
		         prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.func+"','"+param.mobileNo+"','"+param.status+"']}");
			 }else{
				 Native.saveConfig(param.func,param.mobileNo,param.status);
			 }	
		  }	       
		}
		else if(func=="menu"){
			if (param.func == undefined||param.funcno == undefined) {
				  return;
		 }
		 param.func=encodeURI(param.func);
		 param.funcno=encodeURI(param.funcno);
		 param.data=encodeURI(param.data);
		 var version = ICBCUtil.getFullVersion();
		 if (ICBCUtil.isIPhone()) {
			 if (version >= 210) {
			 this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&funcno='+param.funcno+'&data='+param.data);
			 }else{
			 this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&funcno='+param.funcno);	 
			 }
			 } 
		 else if (ICBCUtil.isAndroid()) {
			 if(version >= 210){
				 if(ICBCUtil.isSupportAndroidNewInterface()){
					    prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.func+"','"+param.funcno+"','"+param.data+"']}");
					}else{
						Native.saveConfig(param.func,param.funcno,param.data);
					}	
			 }else{
				 if(ICBCUtil.isSupportAndroidNewInterface()){
					    prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.func+"','"+param.funcno+"']}");
					}else{
						Native.saveConfig(param.func,param.funcno);
					}	 
			 }
		    }	       
		}
		else if(func=="Ticket"){
			if (param.func == undefined
				||param.URL == undefined
				||param.FuncCode == undefined
				||param.funcno == undefined
				||param.HomePageKW == undefined
				||param.PhoneLoginKW == undefined
				||param.ErrorPageKW == undefined
				||param.CookieKW == undefined) {
				  return;
		 }
		 param.func=encodeURI(param.func);
		 param.URL=encodeURI(param.URL);
		 param.FuncCode=encodeURI(param.FuncCode);
		 param.funcno=encodeURI(param.funcno);
		 param.HomePageKW=encodeURI(param.HomePageKW);
		 param.PhoneLoginKW=encodeURI(param.PhoneLoginKW);
		 param.ErrorPageKW=encodeURI(param.ErrorPageKW);
		 param.CookieKW=encodeURI(param.CookieKW);
		 if (ICBCUtil.isIPhone()) {
			 this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&FuncCode='+param.FuncCode+'&funcno='+param.funcno+'&HomePageKW='+param.HomePageKW+'&PhoneLoginKW='+param.PhoneLoginKW+'&ErrorPageKW='+param.ErrorPageKW+'&CookieKW='+param.CookieKW+'&URL='+param.URL);
		 } 
		 else if (ICBCUtil.isAndroid()) {
			if(ICBCUtil.isSupportAndroidNewInterface()){
				prompt('callNativeMethod',"{obj:Native,func:saveConfigForEMall,args:['"+param.func+"','"+param.URL+"','"+param.FuncCode+"','"+param.funcno+"','"+param.HomePageKW+"','"+param.PhoneLoginKW+"','"+param.ErrorPageKW+"','"+param.CookieKW+"']}");
			}else{
				Native.saveConfigForEMall(param.func,param.URL,param.FuncCode,param.funcno,param.HomePageKW,param.PhoneLoginKW,param.ErrorPageKW,param.CookieKW);
			}	
		  }	       
		}
		else if(func=="funccomm"){
			if (param.func == undefined||param.title == undefined||param.FuncNo == undefined||param.FuncDesc == undefined||param.imageurl == undefined) {
				  return;
		 }
		 param.func=encodeURI(param.func);
		 param.title=encodeURI(param.title);
		 param.FuncNo=encodeURI(param.FuncNo);
		 param.FuncDesc=encodeURI(param.FuncDesc);
		 param.imageurl=encodeURI(param.imageurl);
		 if (ICBCUtil.isIPhone()) {
			 this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&title='+param.title+'&FuncDesc='+param.FuncDesc+'&imageurl='+param.imageurl+'&FuncNo='+param.FuncNo);
			 } 
		 else if (ICBCUtil.isAndroid()) {
			 if(ICBCUtil.isSupportAndroidNewInterface()){
			     prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.title+"','"+param.FuncDesc+"','"+param.imageurl+"','"+param.FuncNo+"','"+param.func+"']}");
			 }else{
				 Native.saveConfig(param.title,param.FuncDesc,param.imageurl,param.FuncNo,param.func);
			 }	

		  }	       
		}else if(func=="downloadeap"){
			//新增分支，在影像平台下载文件
			if (param.func == undefined||param.downloadUrl == undefined||param.fileExt == undefined||param.fileName == undefined||param.isOpen == undefined) {
				return;
			}
			param.func=encodeURI(param.func);
			param.downloadUrl=encodeURI(param.downloadUrl);
			param.fileExt=encodeURI(param.fileExt);
			param.fileName=encodeURI(param.fileName);
			param.isOpen=encodeURI(param.isOpen);
			if (ICBCUtil.isIPhone()) {
				this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&fileExt='+param.fileExt+'&fileName='+param.fileName+'&isOpen='+param.isOpen+'&downloadUrl='+param.downloadUrl);
			} 
			else if (ICBCUtil.isAndroid()) {
				//Native.saveConfig(param.downloadUrl,param.fileExt,param.fileName,param.isOpen,param.func);
			}	       
		}else if(func=="wakeUp"){
		//新增分支，给客户端传FUNCNO，唤起应用
		if (param.func == undefined||param.funcno == undefined) {
			return;
		}
		param.func=encodeURI(param.func);
		param.funcno=encodeURI(param.funcno);
		if (ICBCUtil.isIPhone()) {
			this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&funcno='+param.funcno);
		} 
		else if (ICBCUtil.isAndroid()) {
			if(ICBCUtil.isSupportAndroidNewInterface()){
			    prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.func+"','"+param.funcno+"']}");
			}else{
				Native.saveConfig(param.func,param.funcno);
			}	
		}	       
	}else if(func=="knowledge"){
		//知识库接口-客户经理
		if (param.func == undefined||param.answer == undefined) {
			return;
		}
		param.func=encodeURI(param.func);
		param.answer=encodeURI(param.answer);
		if (ICBCUtil.isIPhone()) {
			this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&answer='+param.answer);
		} 
		else if (ICBCUtil.isAndroid()) {
			    prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.func+"','"+param.answer+"']}");
		}	       
	}else if(func=="forwardIMApp"){
		if (param.func == undefined||param.funcno == undefined) {
			return;
		}
		param.func=encodeURI(param.func);
		param.funcno=encodeURI(param.funcno);
		if (ICBCUtil.isIPhone()) {
			this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&funcno='+param.funcno);
		} 
		else if (ICBCUtil.isAndroid()) {
			if(ICBCUtil.isSupportAndroidNewInterface()){
			    prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.func+"','"+param.funcno+"']}");
			}else{
				Native.saveConfig(param.func,param.funcno);
			}	
		}	    
	}else if(func=="openICBCMessageChat"){
		if (param.func == undefined||param.funcno == undefined) {
			return;
		}
		param.func=encodeURI(param.func);
		param.funcno=encodeURI(param.funcno);
		if (ICBCUtil.isIPhone()) {
			this.iOSExcuteNativeMethod('Native://saveConfig=1&func='+param.func+'&funcno='+param.funcno);
		} 
		else if (ICBCUtil.isAndroid()) {
			if(ICBCUtil.isSupportAndroidNewInterface()){
			    prompt('callNativeMethod',"{obj:Native,func:saveConfig,args:['"+param.func+"','"+param.funcno+"']}");
			}else{
				Native.saveConfig(param.func,param.funcno);
			}	
		}	    
	}
	} catch (e) {
	 console.log("Error: " + e);
	}
};
/**
 * 客户端取回数据
 */
ICBCUtil.nativeGetConfigCallBack=undefined,
ICBCUtil.nativeGetConfig=function(param){
	try {
	 if (param.key == undefined||param.callBack == undefined) {
	  return;
	 }
	 ICBCUtil.nativeGetConfigCallBack=param.callBack;
	 if (ICBCUtil.isIPhone()) {
	  ICBCUtil.iOSExcuteNativeMethod('Native://getConfig=1&key='+param.key+'&callBack=ICBCUtil.nativeGetConfigCallBack');
	 } else if (ICBCUtil.isAndroid()) {
	  var value=Native.getConfig(param.key); 
	  ICBCUtil.nativeGetConfigCallBack(value);
	  }
	} catch (e) {
	 console.log("Error: " + e);
	}
};
/**
 * 判断Im客户端类型 2014.8.29 by MimSer-zhangshuang
 */
ICBCUtil.getIMChannel = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('view_from_m')>-1) {
		return "M";
	}
	if (ua.indexOf('view_from_c')>-1) {
		return "C";
	}
	return false;
};
/**
 * 服务器-客户端通用公共接口 2014 11月正常版启用 by kfzx-zhangshuang
 */
ICBCUtil.DataConfigServiceServerCallBack=undefined,
ICBCUtil.DataConfigServiceServer = function(param){
	try {
		 if (param.key == undefined||param.DataString == undefined||param.callBack == undefined||param.ReturnFlag == undefined) {
		     return;
		 }
		 param.key=encodeURI(param.key);
		 param.DataString=encodeURI(param.DataString);
		 if("1"==param.ReturnFlag){
			 ICBCUtil.DataConfigServiceServerCallBack=param.callBack;
			 if (ICBCUtil.isIPhone()) {
			     ICBCUtil.iOSExcuteNativeMethod('Native://DataConfigServiceServer=1&key='+param.key+'&DataString='+param.DataString+'&callBack=ICBCUtil.DataConfigServiceServerCallBack');
			 }else if (ICBCUtil.isAndroid()) {
//			     var value=prompt('callNativeMethod',"{obj:Native,func:DataConfigServiceServer,args:['"+param.key+"','"+param.DataString+"]}");
//			     ICBCUtil.DataConfigServiceServerCallBack(value);
				 prompt('callNativeMethod',"{obj:Native,func:DataConfigServiceServer,args:['"+param.key+"','"+param.DataString+"','ICBCUtil.DataConfigServiceServerCallBack']}");
			 }
		 }else{
			 if (ICBCUtil.isIPhone()) {
			     ICBCUtil.iOSExcuteNativeMethod('Native://DataConfigServiceServer=1&key='+param.key+'&DataString='+param.DataString);
			 } else if (ICBCUtil.isAndroid()) {
			     prompt('callNativeMethod',"{obj:Native,func:DataConfigServiceServer,args:['"+param.key+"','"+param.DataString+"']}"); 
			 } 
		 }
		} catch (e) {
		 console.log("Error: " + e);
		}
};
/**
 * 获取IMUserId
 */
ICBCUtil.getIMUserIDCallBack=undefined,
ICBCUtil.getIMUserID=function(param){
	if (param.callBack==undefined||typeof param.callBack != 'function') {
		return;
	}
	ICBCUtil.getIMUserIDCallBack=param.callBack;
	if (ICBCUtil.isIPhone()) {
		this.iOSExcuteNativeMethod('Native://getConfig=1&key=getIMUserID&callBack=ICBCUtil.getIMUserIDCallBack');
	}else if (ICBCUtil.isAndroid()) {
		try{
			var jsonDataString=prompt('callNativeMethod',"{obj:Native,func:getIMUserID}");
			var jsonData=eval('('+jsonDataString+')');
			ICBCUtil.getIMUserIDCallBack(jsonData);
		}catch(e){
			console.log(e);
		}
	}
};
//根据给定天数获取对当前时间偏移
// flag=0: 返回yyyyMMdd
// flag=1: 返回yyyy-MM-dd
// flag=2: 返回yyyy年MM月dd日
ICBCUtil.getDateValue=function(maxDays, flag) {
	var now = new Date();
	var mills = now.getTime() + maxDays * (24*3600*1000);
	var end = new Date(mills);
	var year = end.getFullYear();
	var month = end.getMonth() + 1;
	var day = end.getDate();
	
	if (month < 10) {
		month = '0' + month;
	}
	
	if (day < 10) {
		day = '0' + day;
	}
	
	var date = year + month + day;
	switch(flag) {
	case 0:
		date = year + month + day;
		break;
	case 1:
		date = year + "-" + month + "-" + day;
		break;
	case 2:
		date = year + "年" + month + "月" + day + "日";
		break;
	default:
		break;
	}
	return date;
};
//日期选择方法
ICBCUtil.dateClick=function(id){
	var date = $("input[name='" + id + "']").val();
	var now = (new Date()).getTime();
	if(date == '' || date == null){
		date = ICBCUtilTools.getDateValue({"year":0},0,now);
	}
	showDatePicker(id, date.replace(/-/g, ""));
};
//B调C方式显示系统日历
var ua = window.navigator.userAgent.toLowerCase();
function showDatePicker(id, date) {
	var DataString = '{"id": "' + id + '", "date": "' + date + '", "nowDates": ""}';
	if(ua.match(/f-wapb/i) == 'f-wapb'){
		DataString = '{"id": "' + id + '", "date": "' + date + '"}';
	}
	var callBack = function(result) {
		onSelectDate(result);
	};
	
	ICBCUtil.DataConfigServiceServer({
		'key' : 'showDatePicker',
		'DataString' : DataString,
		'callBack' : callBack,
		'ReturnFlag' : '1'
	});
}
function onSelectDate(result) {
	var res = eval('(' + ICBCUtilTools.convertUTF8(result) + ')');
	var tmp = res.date;
	var date = tmp.slice(0, 4) + "-" + tmp.slice(4, 6) + "-"
			+ tmp.slice(6, 8);
	$("input[name='" + res.id + "']").val(date);
}


/**
 * 获取加密的IMUserId
 */
ICBCUtil.getEncryptIMUserIDCallBack=undefined,
ICBCUtil.getEncryptIMUserID=function(param){
	if (param.callBack==undefined||typeof param.callBack != 'function') {
		return;
	}
	ICBCUtil.getEncryptIMUserIDCallBack=param.callBack;
	if (ICBCUtil.isIPhone()) {
		this.iOSExcuteNativeMethod('Native://getConfig=1&key=getEncryptIMUserID&callBack=ICBCUtil.getEncryptIMUserIDCallBack');
		
	}else if (ICBCUtil.isAndroid()) {
		try{
			var jsonDataString=prompt('callNativeMethod',"{obj:Native,func:getEncryptIMUserID}");
			var jsonData=jsonDataString;
			ICBCUtil.getEncryptIMUserIDCallBack(jsonData);
		}catch(e){
			console.log(e);
		}
	}
};

/**
 * 获取会话气泡中的客户经理id和客户id
 */
ICBCUtil.getIM2UserIDCallBack=undefined,
ICBCUtil.getIM2UserID=function(param){
	if (param.callBack==undefined||typeof param.callBack != 'function') {
		return;
	}
	ICBCUtil.getIM2UserIDCallBack=param.callBack;
	if (ICBCUtil.isIPhone()) {
		this.iOSExcuteNativeMethod('Native://getConfig=1&key=getIM2UserID&callBack=ICBCUtil.getIM2UserIDCallBack');
	}else if (ICBCUtil.isAndroid()) {
		try{
			var jsonDataString=prompt('callNativeMethod',"{obj:Native,func:getIM2UserID}");
			var jsonData=eval('('+jsonDataString+')');
			ICBCUtil.getIM2UserIDCallBack(jsonData);
		}catch(e){
			console.log(e);
		}
	}
};
/**
 * 保存第三方应用到客户端或跳转到三方商户页面
 */
ICBCUtil.saveDataToClient=function(param){
	if(ICBCUtil.isAndroid()){
		try{
			if(ICBCUtil.isSupportAndroidNewInterface()){
				prompt("saveThirdApp","{'id':'"+param.id+"','appType':'"+param.appType+"','appName':'"+param.appName+"','appIdentifier':'"+param.appIdentifier+"','appUrl':'"+param.appUrl+"','appIconUrl':'"+param.appIconUrl+"'}");
			}else{
				PortalRequestService.saveThirdApp("{'id':'"+param.id+"','appType':'"+param.appType+"','appName':'"+param.appName+"','appIdentifier':'"+param.appIdentifier+"','appUrl':'"+param.appUrl+"','appIconUrl':'"+param.appIconUrl+"'}");
			}
		}catch(e){
		}
	}else if(ICBCUtil.isIPhone()){
		try{
			var params="Native://";
			params=ICBCUtil.appendParam(params,"startType","saveThirdApp");
			params=ICBCUtil.appendParam(params,"json","{'id':'"+param.id+"','appType':'"+param.appType+"','appName':'"+param.appName+"','appIdentifier':'"+param.appIdentifier+"','appUrl':'"+param.appUrl+"','appIconUrl':'"+param.appIconUrl+"'}");
			params=encodeURI(params);
			params=params.substring(0, params.length-1);
			ICBCUtil.iOSExcuteNativeMethod(params);
			return false;
		}catch(e){}
	}	
};

/**
 * 获取会话气泡中的客户经理id
 */
ICBCUtil.getIMManagerID = function() {
	var id="";
	ICBCUtil.getIM2UserID({
		'callBack' : function(result) {
		  var idArr = result.split(",");
		  if(idArr.length==2)
			  {
			  	id=idArr[0];
			  } 
		}
	});	
	return id;
};
/**
 * 获取会话气泡中的客户id
 */
ICBCUtil.getIMCustomID = function() {
	var id="";
	ICBCUtil.getIM2UserID({
		'callBack' : function(result) {
		  var idArr = result.split(",");
		  if(idArr.length==2)
			  {
			  	id=idArr[1];
			  } 
		}
	});	
	return id;
};

/*获取URL链接中参数 zhaojb*/
function getURLQueryString(name) 
{ 
    // 如果链接没有参数，或者链接中不存在我们要获取的参数，直接返回空 
    if(location.href.indexOf("?")==-1 || location.href.indexOf(name+'=')==-1) 
    { 
        return ''; 
    } 
    
    // 获取链接中参数部分 
    var queryString = location.href.substring(location.href.indexOf("?")+1); 

    // 分离参数对 ?key=value&key2=value2 
    var parameters = queryString.split("&"); 
  
    var pos, paraName, paraValue; 
    for(var i=0; i<parameters.length; i++) 
    { 
        // 获取等号位置 
        pos = parameters[i].indexOf('='); 
        if(pos == -1) { continue; } 
  
        // 获取name 和 value 
        paraName = parameters[i].substring(0, pos); 

        // 在chrome中需要解码一次，在safari中不需要
        paraValue = decodeURI(parameters[i].substring(pos + 1)); 
  
        // 如果查询的name等于当前name，就返回当前值，同时，将链接中的+号还原成空格 
        if(paraName == name) 
        { 
            return unescape(paraValue.replace(/\+/g, " ")); 
        } 
    } 
    return ''; 
}; 

/**
 * 获取服务号会话气泡中的mpid
 */
ICBCUtil.getChattingIMUserIDCallBack=undefined,
ICBCUtil.getChattingIMUserID=function(param){
	if (param.callBack==undefined||typeof param.callBack != 'function') {
		return;
	}
	ICBCUtil.getChattingIMUserIDCallBack=param.callBack;
	if (ICBCUtil.isIPhone()) {
		this.iOSExcuteNativeMethod('Native://getConfig=1&key=getChattingIMUserID&callBack=ICBCUtil.getChattingIMUserIDCallBack');
	}else if (ICBCUtil.isAndroid()) {
		try{
			var jsonDataString=prompt('callNativeMethod',"{obj:Native,func:getChattingIMUserID}");
			var jsonData=eval('('+jsonDataString+')');
			ICBCUtil.getChattingIMUserIDCallBack(jsonData);
		}catch(e){
			console.log(e);
		}
	}
};

// 启动融e购APP

function StartMallClient() {
	if (ICBCUtil.isIPhone()) {
		ICBCUtil
				.saveDataToClient({
					"id" : "30368f81-a1dd-4372-b940-2ef3dd43d907",
					"appType" : "app",
					"appName" : "融e购",
					"appIdentifier" : "com.icbc.iphone.emall://",
					"appUrl" : "https://itunes.apple.com/cn/app/ronge-gou/id859938926?mt=8",
					"appIconUrl" : "http://www.icbc.com.cn/SiteCollectionDocuments/ICBC/Resources/ICBC/icbcappstore/rongyilian.jpg"
				});
	} else {

		var data = '{"":""}';
		ICBCUtil.DataConfigServiceServer({
			'key' : 'StartEMallClient',
			'DataString' : data,
			'callBack' : '',
			'ReturnFlag' : ''
		});

	}
}

/**
 * 客户端服务号历史消息查询功能，获取客户端语言类型
 */
ICBCUtil.getLanguageType=undefined,
ICBCUtil.getLanguageType=function(param){
	if (param.callBack==undefined||typeof param.callBack != 'function') {
		return;
	}
	ICBCUtil.getLanguageTypeCallBack=param.callBack;
	if (ICBCUtil.isIPhone()) {
		this.iOSExcuteNativeMethod('Native://getConfig=1&key=getLanguageType&callBack=ICBCUtil.getLanguageTypeCallBack');
	}else if (ICBCUtil.isAndroid()) {
		try{
			var jsonDataString=prompt('callNativeMethod',"{obj:Native,func:getLanguageType}");
			var jsonData=eval('('+jsonDataString+')');
			ICBCUtil.getLanguageTypeCallBack(jsonData);
		}catch(e){
			console.log(e);
		}
	}
};