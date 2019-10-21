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
     * 定义共用模板字段
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCommon/defineCommonTemplate")
    @ResponseBody
    public AjaxResult defineCommonTemplate(HttpServletRequest request,@RequestBody List<ReCommonTemplate> reTemplateList){
    	//TODO 检查是否存在
    	List<ReCommonTemplate> list= reCommonService.insertReCommonTemplate(reTemplateList);
    	if (list!=null) {
    		return  AjaxResult.success("添加成功!", list);
        }else {
        	reCommonService.updateColIndex(1);
    		return AjaxResult.error("该字段已存在");
        }
    }
    
    /**
     * 删除共用模板字段
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
    	return AjaxResult.success("删除成功");
    }
    
    /**
     * 修改共用模板字段
     * @param request
     * @param SalaryTemplate
     * @return
     */
    @RequestMapping("/reCommon/updateCommonTemplate")
    @ResponseBody
    public AjaxResult updateCommonTemplate(HttpServletRequest request,@RequestBody  ReCommonTemplate reCommonTemplate){
    	
    	ReCommonTemplate list = reCommonService.updateCommonTemplate(reCommonTemplate);
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
    @RequestMapping("/reCommon/getCommonTemplate")
    @ResponseBody
    public  AjaxResult getCommonTemplate(HttpServletRequest request){
    	List<ReCommonTemplate> reCommonTemplateList= reCommonService.getCommonTemplate();
    	return AjaxResult.success("查询成功!", reCommonTemplateList);
    }
    
    
}
