package com.icbc.rel.hefei.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

    /*
	 * 该类可以从浏览器请求中提取出cookies并进行对cookis的相关操作
	 * 
	 */

	public class CookiesUtil  {
		private static String openid="Openid";
		
		public static String getOpenId(HttpServletRequest request)
		{
			javax.servlet.http.Cookie openidCook = CookiesUtil.getCookieByName(request, openid);
			String openid = null;
			if (openidCook != null) {
				openid = openidCook.getValue();
			}
			if (openid == null || openid.length() == 0) {
				openid = "123456789";
			}
			return openid;
		}
		
		public static void SaveOpenId(HttpServletResponse response,String openid)
		{
			if (openid != null) {
				CookiesUtil.setCookie(response, "Openid", openid, 3600);
			}
		}
		
	    
	    /**
	     * 根据名字获取cookie
	     * 
	     * @param request
	     * @param name
	     * cookie名字
	     * @return
	     */
	    public static Cookie getCookieByName(HttpServletRequest request, String name) {
	        Map<String, Cookie> cookieMap = ReadCookieMap(request);
	        if (cookieMap.containsKey(name)) {
	            Cookie cookie = (Cookie) cookieMap.get(name);
	            return cookie;
	        } else {
	            return null;
	        }
	    }

	    /**
	     * 将cookie封装到Map里面
	     * 
	     * @param request
	     * @return
	     */
	    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
	        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	        Cookie[] cookies = request.getCookies();
	        if (null != cookies) {
	            for (Cookie cookie : cookies) {
	                cookieMap.put(cookie.getName(), cookie);
	            }
	        }
	        return cookieMap;
	    }

	    /**
	     * 保存Cookies
	     * 
	     * @param response
	     *            servlet请求
	     * @param value
	     *            保存值
	     * @author jxf
	     */
	    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value,int time) {
	        // new一个Cookie对象,键值对为参数
	        Cookie cookie = new Cookie(name, value);
	        // tomcat下多应用共享
	        cookie.setPath("/");
	        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
	        try {
	            URLEncoder.encode(value, "utf-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        cookie.setMaxAge(time);
	        // 将Cookie添加到Response中,使之生效
	        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
	        return response;
	    }

}
