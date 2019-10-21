package com.icbc.rel.hefei.controller.salary.reimbursement;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReCommonService;


@RequestMapping("/mp")
@Controller
public class ReCommonTemplateController {
	@Autowired
	private ReCommonService reCommonService;
	  /**
     * ���干��ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCommon/defineCommonTemplate")
    @ResponseBody
    public AjaxResult defineCommonTemplate(HttpServletRequest request,@RequestBody List<ReCommonTemplate> reTemplateList){
    	//TODO ����Ƿ����
    	List<ReCommonTemplate> list= reCommonService.insertReCommonTemplate(reTemplateList);
    	if (list!=null) {
    		return  AjaxResult.success("��ӳɹ�!", list);
        }else {
        	reCommonService.updateColIndex(1);
    		return AjaxResult.error("���ֶ��Ѵ���");
        }
    }
    
    /**
     * ɾ������ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCommon/delCommonTemplate")
    @ResponseBody
    public AjaxResult delCommonTemplate(HttpServletRequest request){
    	int id = Integer.valueOf(request.getParameter("id"));
    	reCommonService.delCommonTemplate(id);
    	reCommonService.updateColIndex(-1);
    	return AjaxResult.success("ɾ���ɹ�");
    }
    
    /**
     * �޸Ĺ���ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCommon/updateCommonTemplate")
    @ResponseBody
    public AjaxResult updateCommonTemplate(HttpServletRequest request,@RequestBody  ReCommonTemplate reCommonTemplate){
    	
    	ReCommonTemplate list = reCommonService.updateCommonTemplate(reCommonTemplate);
    	if (list!=null) {
    		return  AjaxResult.success("�޸ĳɹ�!", list);
        }else {
        	return AjaxResult.error("���ֶ��Ѵ���");
        }
    }
    
    /**
     * ��ѯ����ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCommon/getCommonTemplate")
    @ResponseBody
    public  AjaxResult getCommonTemplate(HttpServletRequest request){
    	List<ReCommonTemplate> reCommonTemplateList= reCommonService.getCommonTemplate();
    	return AjaxResult.success("��ѯ�ɹ�!", reCommonTemplateList);
    }
    
    
}
