package com.icbc.rel.hefei.controller.salary.reimbursement;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReCustomService;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;


@RequestMapping("/mp")
@Controller
public class ReCustomTemplateController {
	@Autowired
	private ReCustomService reCustomService;
	/**
     * ������Ի�ģ��
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCustom/defineCustomTemplate")
    @ResponseBody
    public AjaxResult defineCustomTemplate(HttpServletRequest request,@RequestBody  List<ReCustomTemplate> reTemplateList){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(StringUtils.isEmpty(companyId)) {
        	return AjaxResult.error("�Զ���ʧ��!");
        	}
    	reCustomService.insertReCustomTemplate(reTemplateList,companyId);
    	return  AjaxResult.success("�ɹ�!",reTemplateList);
    }
    /**
     * ɾ�����Ի�ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCustom/delCustomTemplate")
    @ResponseBody
    public void delCustomTemplate(HttpServletRequest request){
    	int id = Integer.valueOf(request.getParameter("id"));
    	reCustomService.delCustomTemplate(id);
    }
    
    /**
     * �޸ĸ��Ի�ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCustom/updateCustomTemplate")
    @ResponseBody
    public void updateCustomTemplate(HttpServletRequest request,@RequestBody  ReCommonTemplate reCommonTemplate){
    	
    	reCustomService.updateCustomTemplate(reCommonTemplate);
    	
    }
    
    /**
     * ��ѯ���Ի�ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCustom/getCustomTemplate")
    @ResponseBody
    public  List<ReCustomTemplate> getCustomTemplate(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	List<ReCustomTemplate> SalaryCustomTemplateList= reCustomService.getCustomTemplate(companyId);
    	return SalaryCustomTemplateList;
    }
    
}
