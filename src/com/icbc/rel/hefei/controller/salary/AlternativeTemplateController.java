package com.icbc.rel.hefei.controller.salary;


import java.util.Map;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.SalaryTemplateAlternative;
import com.icbc.rel.hefei.service.salary.service.SalaryAlternativeService;
import com.icbc.rel.hefei.util.SessionParamConstant;


@RequestMapping("/mp")
@Controller
public class AlternativeTemplateController {
	@Autowired
	private SalaryAlternativeService salaryAlternativeService;
	 /**
     * ��ѯ�����б�ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/alternative/alternative")
    @ResponseBody
    public AjaxResult alternative(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	Map<String, Object> result  = salaryAlternativeService.getAlternativeInfo(companyId);
    	return AjaxResult.success("��ѯ�ɹ�", result);
    }
    /**
     * ���ӱ�ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/alternative/addAlternative")
    @ResponseBody
    public AjaxResult addAlternative(HttpServletRequest request,@RequestBody SalaryTemplateAlternative oaSalaryTemplateAlternative){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
        oaSalaryTemplateAlternative= salaryAlternativeService.addAlternative(oaSalaryTemplateAlternative);
        if(oaSalaryTemplateAlternative!=null) {
        	return  AjaxResult.success("���ӳɹ�!", oaSalaryTemplateAlternative);
        }else {
        	return AjaxResult.error("���ֶ��Ѵ���");
        }
    }
    
    /**
     * �޸����б�ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/alternative/updateAlternative")
    @ResponseBody
    public AjaxResult updateAlternative(HttpServletRequest request,@RequestBody SalaryTemplateAlternative oaSalaryTemplateAlternative){
        oaSalaryTemplateAlternative= salaryAlternativeService.updateAlternative(oaSalaryTemplateAlternative);
        if(oaSalaryTemplateAlternative!=null) {
        	return  AjaxResult.success("�޸ĳɹ�!", oaSalaryTemplateAlternative);
        }else {
        	return AjaxResult.error("���ֶ��Ѵ���");
        }
    }
    
    
    /**
     * ɾ��ĳ����ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/alternative/delAlternative")
    @ResponseBody
    public AjaxResult delAlternative(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	int id = Integer.valueOf(request.getParameter("id"));
    	salaryAlternativeService.delAlternative(id);
    	return AjaxResult.success("ɾ���ɹ�");
    }
}