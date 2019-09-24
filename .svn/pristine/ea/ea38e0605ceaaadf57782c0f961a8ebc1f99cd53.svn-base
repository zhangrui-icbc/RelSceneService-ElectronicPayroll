package com.icbc.rel.hefei.util;

/**
 * 密码校验
 * @author fc
 *
 */
public class PasswordCheck {
	/**
	 * 位数与数字字母
	 * @param passwordStr
	 * @return
	 */
	public static String checkPassword(String passwordStr) {
	    if (passwordStr != null && !"".equals(passwordStr) && (passwordStr.length() < 6 || passwordStr.length() > 12)) {
	        return "密码为 6-12 位字母、数字或英文字符!";
	    }
	    // Z = 字母       S = 数字           T = 特殊字符
	    String regexZ = "[A-Za-z]+";
	    String regexS = "^\\d+$";
	    String regexZS = "[0-9A-Za-z]+";
	    if(!isPasswordContinuous(passwordStr)) {
	    	return "error";
	    }
	    if(passwordStr.matches(regexZ)||passwordStr.matches(regexS)||passwordStr.matches(regexZS)) {
	    	return "success";
	    }
	    return "error";
	}
	
	

	/**
	 * * 密码是否是正序或反序连续4位及以上
	  * @param pwd
	 * @return true为正确，false为错误。
	 * @param pwd
	 * @return
	 */
	  public static boolean isPasswordContinuous(String pwd) {
			int count = 0;//正序次数
			int reverseCount = 0;//反序次数
			String[] strArr = pwd.split("");
			for(int i = 1 ; i < strArr.length-1 ; i ++) {//从1开始是因为划分数组时，第一个为空
				if(isPositiveContinuous(strArr[i],strArr[i+1])) {
					count ++;
				}
				else {
					count = 0;
				}
				if(isReverseContinuous(strArr[i],strArr[i+1])) {
					reverseCount ++;
				}
				else {
					reverseCount = 0;
				}
				if(count > 2 || reverseCount > 2) break;
			}
			if(count > 2 || reverseCount > 2) return false;
			return true;
		}
	  
	  /**
		 * 是否是正序连续
		 * @param str1
		 * @param str2
		 * @return
		 */
		public static boolean isPositiveContinuous(String str1 , String str2) {
			if(str2.hashCode() - str1.hashCode() == 1) return true;
			return false;
		}
		/**
		 * 是否是反序连续
		 * @param str1
		 * @param str2
		 * @return
		 */
		public static boolean isReverseContinuous(String str1 , String str2) {
			if(str2.hashCode() - str1.hashCode() == -1) return true;
			return false;
		}
}
