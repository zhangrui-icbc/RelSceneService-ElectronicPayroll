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
     * 定义共用模板字段
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/defineCommonTemplate")
    @ResponseBody
    public AjaxResult defineCommonTemplate(HttpServletRequest request,@RequestBody List<SalaryCommonTemplate> salaryTemplateList){
    	//TODO 检查是否存在
    	List<SalaryCommonTemplate> list= salaryCommonService.insertSalaryCommonTemplate(salaryTemplateList);
    	if (list!=null) {
    		return  AjaxResult.success("添加成功!", list);
        }else {
        	salaryCommonService.updateColIndex(1);
    		return AjaxResult.error("该字段已存在");
        }
    }
    
    /**
     * 删除共用模板字段
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
    	return AjaxResult.success("删除成功");
    }
    
    /**
     * 修改共用模板字段
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/updateCommonTemplate")
    @ResponseBody
    public AjaxResult updateCommonTemplate(HttpServletRequest request,@RequestBody  SalaryCommonTemplate salaryCommonTemplate){
    	
    	SalaryCommonTemplate list = salaryCommonService.updateCommonTemplate(salaryCommonTemplate);
    	if (list!=null) {
    		return  AjaxResult.success("修改成功!", list);
        }else {
        	return AjaxResult.error("该字段已存在");
        }
    }
    
    /**
     * 查询共用模板字段
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/getCommonTemplate")
    @ResponseBody
    public  AjaxResult getCommonTemplate(HttpServletRequest request){
    	List<SalaryCommonTemplate> salaryCommonTemplateList= salaryCommonService.getCommonTemplate();
    	return AjaxResult.success("查询成功!", salaryCommonTemplateList);
    }
    
    
}
