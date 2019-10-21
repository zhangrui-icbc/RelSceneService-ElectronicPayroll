package com.icbc.rel.hefei.controller.salary;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.service.salary.service.SalaryCustomService;
import com.icbc.rel.hefei.util.SessionParamConstant;


@RequestMapping("/mp")
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
    @RequestMapping("/custom/defineCustomTemplate")
    @ResponseBody
    public AjaxResult defineCustomTemplate(HttpServletRequest request,@RequestBody  List<SalaryCustomTemplate> salaryTemplateList){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(StringUtils.isEmpty(companyId)) {
    	  return AjaxResult.error("�Զ���ʧ��!");
    	}
    	salaryCustomService.insertSalaryCustomTemplate(salaryTemplateList,companyId);
    	return  AjaxResult.success("�Զ���ɹ�!",salaryTemplateList);
    }
    /**
     * ɾ�����Ի�ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/custom/delCustomTemplate")
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
    @RequestMapping("/custom/updateCustomTemplate")
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
    @RequestMapping("/custom/getCustomTemplate")
    @ResponseBody
    public  List<SalaryCustomTemplate> getCustomTemplate(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	List<SalaryCustomTemplate> SalaryCustomTemplateList= salaryCustomService.getCustomTemplate(companyId);
    	return SalaryCustomTemplateList;
    }
    
}
