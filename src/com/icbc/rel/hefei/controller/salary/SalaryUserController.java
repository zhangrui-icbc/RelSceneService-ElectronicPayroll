package com.icbc.rel.hefei.controller.salary;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.service.salary.service.SalaryUserService;
/**
 * 
 * @author ft
 * 工资excel
 *
 */
@RequestMapping("/mp")
@Controller
public class SalaryUserController {
	@Autowired
	private SalaryUserService salaryUserService;
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
    @RequestMapping(value="/salaryUser/login",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request,String username, String password){
    	if(StringUtils.isEmpty(password)||StringUtils.isEmpty(password)) {
    		return AjaxResult.error("用户名或密码为空,请检查后重新输入");
    	}else {
    		AjaxResult ajaxResult=salaryUserService.login(username,password);
    		request.getSession().setAttribute("username", username);
    		return ajaxResult;
    	}
    }
    
    /**
     * 密码修改
     */
    @RequestMapping(value="/salaryUser/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request,String newPassword1,String newPassword2){
    	if(StringUtils.isEmpty(newPassword1)&&StringUtils.isEmpty(newPassword2)) {
    		return AjaxResult.error("新密码为空,请检查后重新输入");
    	}else {
    		if(!newPassword1.equals(newPassword2)) {
    			return AjaxResult.error("新密码两次输入不一致!,请检查后重新输入");
    		}else {
    			String username = (String) request.getSession().getAttribute("username");
    			return salaryUserService.resetPassword(username,newPassword1);
    		}
    	}
    }
    
    
    
}
