package com.icbc.rel.hefei.controller.salary;


import java.util.Map;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(AlternativeTemplateController.class);
	@Autowired
	private SalaryAlternativeService salaryAlternativeService;
	 /**
     * 查询出所有备选字段
     * @param request
     */
    @RequestMapping("/alternative/alternative")
    @ResponseBody
    public AjaxResult alternative(HttpServletRequest request){
    	logger.info("查询出所有备选字段=========>>>>>>>");
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	Map<String, Object> result  = salaryAlternativeService.getAlternativeInfo(companyId);
    	return AjaxResult.success("查询成功", result);
    }
    /**
     * 添加备选字段
     * @param request
     */
    @RequestMapping("/alternative/addAlternative")
    @ResponseBody
    public AjaxResult addAlternative(HttpServletRequest request,@RequestBody SalaryTemplateAlternative oaSalaryTemplateAlternative){
    	logger.info("添加备选字段=========>>>>>>>");
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	oaSalaryTemplateAlternative.setCompanyId(companyId);
        oaSalaryTemplateAlternative= salaryAlternativeService.addAlternative(oaSalaryTemplateAlternative);
        if(oaSalaryTemplateAlternative!=null) {
        	return  AjaxResult.success("添加成功!", oaSalaryTemplateAlternative);
        }else {
        	return AjaxResult.error("该字段已存在");
        }
    }
    
    /**
     * 修改已有备选字段
     * @param request
     */
    @RequestMapping("/alternative/updateAlternative")
    @ResponseBody
    public AjaxResult updateAlternative(HttpServletRequest request,@RequestBody SalaryTemplateAlternative oaSalaryTemplateAlternative){
    	logger.info("修改已有备选字段=========>>>>>>>");
        oaSalaryTemplateAlternative= salaryAlternativeService.updateAlternative(oaSalaryTemplateAlternative);
        if(oaSalaryTemplateAlternative!=null) {
        	return  AjaxResult.success("修改成功!", oaSalaryTemplateAlternative);
        }else {
        	return AjaxResult.error("该字段已存在");
        }
    }
    
    
    /**
     * 删除某个备选字段
     * @param request
     */
    @RequestMapping("/alternative/delAlternative")
    @ResponseBody
    public AjaxResult delAlternative(HttpServletRequest request){
    	logger.info("删除某个备选字段=========>>>>>>>");
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	int id = Integer.valueOf(request.getParameter("id"));
    	salaryAlternativeService.delAlternative(id);
    	return AjaxResult.success("删除成功");
    }
}
