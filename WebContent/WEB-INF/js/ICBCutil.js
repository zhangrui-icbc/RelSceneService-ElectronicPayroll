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
		try{
		this.iOSExcuteNativeMethod('Native://getConfig=1&key=getEncryptIMUserID&callBack=ICBCUtil.getEncryptIMUserIDCallBack');
		}catch(e){
			alert(e);
		}
	}else if (ICBCUtil.isAndroid()) {
		try{
			var jsonDataString=prompt('callNativeMethod',"{obj:Native,func:getEncryptIMUserID}");
			var jsonData=jsonDataString;
			ICBCUtil.getEncryptIMUserIDCallBack(jsonData);
		}catch(e){
			alert(e);
		}
	}
};

/**
 * 工行基础工具类
 */
function ICBCUtil() {
}

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
 * 检测当前浏览器是否为Android(Chrome)
 */
ICBCUtil.isAndroid = function() {
	var ua = navigator.userAgent;
	if (ua.indexOf('ICBCAndroidBS')>-1||ua.indexOf('Android')>-1) {
		return true;
	}
	return false;
};