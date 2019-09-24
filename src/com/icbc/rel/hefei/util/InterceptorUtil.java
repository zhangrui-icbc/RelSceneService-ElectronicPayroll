package com.icbc.rel.hefei.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 * 拦截器 用来做IP限流
 */
public class InterceptorUtil extends HandlerInterceptorAdapter{


    /**
     * 1.进入Controller方法之前执行
     * @param request
     * @param response
     * @param handler
     * @return 返回值为true时可继续执行Controller，preHandle()和afterCompletion()，为false时停止执行任何方法。
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       
        String url=request.getRequestURL().toString();
        //String ip=request.getRemoteAddr();
        String ip=IpAdressUtil.getip(request);
        System.out.println(ip+"正在访问"+url);
        //ip限流
        boolean flag=IplimitUtil.requestFrequencyCheck(ip,url);
        if(!flag) {
        	System.out.println(ip+"存在可疑恶意攻击行为");
        	return false;
        }
        
        //验证用户是否登录，没有登录则跳转到登录界面
        if(url.contains("/ad/") &&  !url.endsWith("myscene") && !url.endsWith("checkUser")) {
        	String loginId=SessionUtil.getAdminId(request.getSession());
        	if(loginId==null || loginId.equals("")) {
        		response.sendRedirect("../login");
        		return false;
        	}else {
        		return true;
        	}
        }
        if(url.contains("/mp/") &&  !url.endsWith("myscene")) {
        	String mpid=SessionUtil.getMpId(request.getSession());
        	if(mpid==null || mpid.equals("")) {
        		return false;
        	}else {
        		return true;
        	}
        }
        return true;
    }

    /**
     * 2.Controller方法执行完毕但是未进行视图渲染时执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 3.视图渲染完成（整个请求结束）执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
