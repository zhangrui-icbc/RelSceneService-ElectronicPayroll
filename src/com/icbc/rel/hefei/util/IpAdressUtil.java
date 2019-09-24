package com.icbc.rel.hefei.util;

import javax.servlet.http.HttpServletRequest;

public class IpAdressUtil {
	
/*
 * ªÒ»°ipµÿ÷∑
 */
	public static String getip(HttpServletRequest request) {
		if(request.getHeader("x-forwarded-for")==null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
