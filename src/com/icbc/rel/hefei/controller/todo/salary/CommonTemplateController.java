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
import com.icbc.rel.hefei.service.todo.salary.service.SalaryCommonService;


@RequestMapping("/common")
@Controller
public class CommonTemplateController {
	@Autowired
	private SalaryCommonService salaryCommonService;
	  /**
     * ���干��ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/defineCommonTemplate")
    @ResponseBody
    public AjaxResult defineCommonTemplate(HttpServletRequest request,@RequestBody List<SalaryCommonTemplate> salaryTemplateList){
    	//TODO ����Ƿ����
    	List<SalaryCommonTemplate> list= salaryCommonService.insertSalaryCommonTemplate(salaryTemplateList);
    	if (list!=null) {
    		return  AjaxResult.success("��ӳɹ�!", list);
        }else {
        	salaryCommonService.updateColIndex(1);
    		return AjaxResult.error("���ֶ��Ѵ���");
        }
    }
    
    /**
     * ɾ������ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/delCommonTemplate")
    @ResponseBody
    public AjaxResult delCommonTemplate(HttpServletRequest request){
    	int id = Integer.valueOf(request.getParameter("id"));
    	salaryCommonService.delCommonTemplate(id);
    	salaryCommonService.updateColIndex(-1);
    	return AjaxResult.success("ɾ���ɹ�");
    }
    
    /**
     * �޸Ĺ���ģ���ֶ�
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/updateCommonTemplate")
    @ResponseBody
    public AjaxResult updateCommonTemplate(HttpServletRequest request,@RequestBody  SalaryCommonTemplate salaryCommonTemplate){
    	
    	SalaryCommonTemplate list = salaryCommonService.updateCommonTemplate(salaryCommonTemplate);
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
    @RequestMapping("/getCommonTemplate")
    @ResponseBody
    public  AjaxResult getCommonTemplate(HttpServletRequest request){
    	List<SalaryCommonTemplate> salaryCommonTemplateList= salaryCommonService.getCommonTemplate();
    	return AjaxResult.success("��ѯ�ɹ�!", salaryCommonTemplateList);
    }
    
    
}
