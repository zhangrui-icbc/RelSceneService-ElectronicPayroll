package com.icbc.rel.hefei.util;


public class EnumUtil {
	
	public enum sceneType{
		lottery("转盘抽奖"),order("线上点餐"),asks("考勤管理"),meeting("会议助手"),salary("工资单报销管理");
		private String sceneName;
		
		sceneType(String sceneName){
			this.sceneName=sceneName;
		}
		/*
		 * 获取场景名称
		 */
		public String getSceneName() {
			 return sceneName;
		}
	}
 
	/*
	 * 根据场景名称获取场景编码
	 */
	public static String getSceneType(String sceneName) {
		for(sceneType item:sceneType.values()) {
			if(sceneName.equals(item.getSceneName())) {
				return item.name();
			}
		}
		return "";
	}
	/*
	 * 根据场景值获取场景名称
	 */
	public static String getSceneName(String value) {
		for(sceneType item:sceneType.values()) {
			if(value.equals(item.name())) {
				return item.getSceneName();
			}
		}
		return "";
	}

}
