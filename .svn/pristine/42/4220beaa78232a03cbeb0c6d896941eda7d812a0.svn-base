package com.icbc.rel.hefei.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhenggui on 17/3/15.
 */
public class CommonUtil {

	public static String getTodayDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getYesterdayDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(new Date(currentTime.getTime() - 1000l * 24l * 60l * 60l));
		return dateString;
	}

	public static float parseFloat(String str) {
		float result = 0;
		try {
			result = Float.parseFloat(str);

		} catch (Exception ex) {

		}
		return result;

	}
	
	public static int parseInteger(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(str);

		} catch (Exception ex) {
          return 0;
		}
		return result;

	}
	
	public static Date parseDate(String str) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date result = null;
		try {
			result = sdf.parse(str);
			return result;

		} catch (Exception ex) {
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				result = sdf.parse(str);

			} catch (Exception e) {
                  result=null;
			}
		}
		
		return result;

	}
	public static Date parseTime(String str) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = null;
		try {
			result = sdf.parse(str);

		} catch (Exception ex) {

		}
		return result;

	}
	
	public static String DateConvertStr(Date str,String format) {
		String dateString="";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			dateString = formatter.format(str);

		} catch (Exception ex) {

		}
		return dateString;

	}
}
