package com.icbc.rel.hefei.controller.todo.salary;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.service.todo.salary.service.SalaryCustomService;
import com.icbc.rel.hefei.util.SessionParamConstant;


@RequestMapping("/custom")
@Controller
public class CustomTemplateController {
	@Autowired
	private SalaryCustomService salaryCustomService;
	/**
     * ������Ի�ģ��
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/defineCustomTemplate")
    @ResponseBody
    public AjaxResult defineCustomTemplate(HttpServletRequest request,@RequestBody  List<SalaryCustomTemplate> salaryTemplateList){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	salaryCustomService.insertSalaryCustomTemplate(salaryTemplateList,companyId);
    	return  AjaxResult.success("�ɹ�!",salaryTemplateList);
    }
    /**
     * ɾ�����Ի�ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/delCustomTemplate")
    @ResponseBody
    public void delCustomTemplate(HttpServletRequest request){
    	int id = Integer.valueOf(request.getParameter("id"));
    	salaryCustomService.delCustomTemplate(id);
    }
    
    /**
     * �޸ĸ��Ի�ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/updateCustomTemplate")
    @ResponseBody
    public void updateCustomTemplate(HttpServletRequest request,@RequestBody  SalaryCommonTemplate salaryCommonTemplate){
    	
    	salaryCustomService.updateCustomTemplate(salaryCommonTemplate);
    	
    }
    
    /**
     * ��ѯ���Ի�ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/getCustomTemplate")
    @ResponseBody
    public  List<SalaryCustomTemplate> getCustomTemplate(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	List<SalaryCustomTemplate> SalaryCustomTemplateList= salaryCustomService.getCustomTemplate(companyId);
    	return SalaryCustomTemplateList;
    }
    
}