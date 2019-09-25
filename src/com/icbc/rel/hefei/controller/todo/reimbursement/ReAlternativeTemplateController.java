package com.icbc.rel.hefei.controller.todo.reimbursement;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.todo.reimbursement.ReTemplateAlternative;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.service.todo.reimbursement.service.ReAlternativeService;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;


@RequestMapping("/reAlternative")
@Controller
public class ReAlternativeTemplateController {
	@Autowired
	private ReAlternativeService reAlternativeService;
	 /**
     * ��ѯ�����б�ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/alternative")
    @ResponseBody
    public AjaxResult alternative(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	Map<String, Object> result  = reAlternativeService.getAlternativeInfo(companyId);
    	return AjaxResult.success("��ѯ�ɹ�", result);
    }
    /**
     * ���ӱ�ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/addAlternative")
    @ResponseBody
    public AjaxResult addAlternative(HttpServletRequest request,@RequestBody ReTemplateAlternative reTemplateAlternative){
    	reTemplateAlternative= reAlternativeService.addAlternative(reTemplateAlternative);
        if(reTemplateAlternative!=null) {
        	return  AjaxResult.success("���ӳɹ�!", reTemplateAlternative);
        }else {
        	return AjaxResult.error("���ֶ��Ѵ���");
        }
    }
    
    /**
     * �޸����б�ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/updateAlternative")
    @ResponseBody
    public AjaxResult updateAlternative(HttpServletRequest request,@RequestBody ReTemplateAlternative reTemplateAlternative){
    	reTemplateAlternative= reAlternativeService.updateAlternative(reTemplateAlternative);
        if(reTemplateAlternative!=null) {
        	return  AjaxResult.success("�޸ĳɹ�!", reTemplateAlternative);
        }else {
        	return AjaxResult.error("���ֶ��Ѵ���");
        }
    }
    
    
    /**
     * ɾ��ĳ����ѡ�ֶ�
     * @param request
     */
    @RequestMapping("/delAlternative")
    @ResponseBody
    public AjaxResult delAlternative(HttpServletRequest request){
    	int id = Integer.valueOf(request.getParameter("id"));
    	reAlternativeService.delAlternative(id);
    	return AjaxResult.success("ɾ���ɹ�");
    }
}