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
 * ����excel
 *
 */
@RequestMapping("/mp")
@Controller
public class SalaryUserController {
	@Autowired
	private SalaryUserService salaryUserService;
	
	/**
	 * ��¼
	 * @param username
	 * @param password
	 * @return
	 */
    @RequestMapping(value="/salaryUser/login",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request,String username, String password){
    	if(StringUtils.isEmpty(password)||StringUtils.isEmpty(password)) {
    		return AjaxResult.error("�û���������Ϊ��,�������������");
    	}else {
    		AjaxResult ajaxResult=salaryUserService.login(username,password);
    		request.getSession().setAttribute("username", username);
    		return ajaxResult;
    	}
    }
    
    /**
     * �����޸�
     */
    @RequestMapping(value="/salaryUser/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request,String newPassword1,String newPassword2){
    	if(StringUtils.isEmpty(newPassword1)&&StringUtils.isEmpty(newPassword2)) {
    		return AjaxResult.error("������Ϊ��,�������������");
    	}else {
    		if(!newPassword1.equals(newPassword2)) {
    			return AjaxResult.error("�������������벻һ��!,�������������");
    		}else {
    			String username = (String) request.getSession().getAttribute("username");
    			return salaryUserService.resetPassword(username,newPassword1);
    		}
    	}
    }
    
    
    
}
