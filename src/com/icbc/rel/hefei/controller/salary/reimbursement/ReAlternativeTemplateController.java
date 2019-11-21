package com.icbc.rel.hefei.controller.salary.reimbursement;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReTemplateAlternative;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReAlternativeService;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;


@RequestMapping("/mp")
@Controller
public class ReAlternativeTemplateController {
	@Autowired
	private ReAlternativeService reAlternativeService;
	 /**
     * 查询出所有备选字段
     * @param request
     */
    @RequestMapping("/reAlternative/alternative")
    @ResponseBody
    public AjaxResult alternative(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	Map<String, Object> result  = reAlternativeService.getAlternativeInfo(companyId);
    	return AjaxResult.success("查询成功", result);
    }
    /**
     * 添加备选字段
     * @param request
     */
    @RequestMapping("/reAlternative/addAlternative")
    @ResponseBody
    public AjaxResult addAlternative(HttpServletRequest request,@RequestBody ReTemplateAlternative reTemplateAlternative){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	reTemplateAlternative= reAlternativeService.addAlternative(reTemplateAlternative);
        if(reTemplateAlternative!=null) {
        	return  AjaxResult.success("添加成功!", reTemplateAlternative);
        }else {
        	return AjaxResult.error("该字段已存在");
        }
    }
    
    /**
     * 修改已有备选字段
     * @param request
     */
    @RequestMapping("/reAlternative/updateAlternative")
    @ResponseBody
    public AjaxResult updateAlternative(HttpServletRequest request,@RequestBody ReTemplateAlternative reTemplateAlternative){
    	reTemplateAlternative= reAlternativeService.updateAlternative(reTemplateAlternative);
        if(reTemplateAlternative!=null) {
        	return  AjaxResult.success("修改成功!", reTemplateAlternative);
        }else {
        	return AjaxResult.error("该字段已存在");
        }
    }
    
    
    /**
     * 删除某个备选字段
     * @param request
     */
    @RequestMapping("/reAlternative/delAlternative")
    @ResponseBody
    public AjaxResult delAlternative(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	int id = Integer.valueOf(request.getParameter("id"));
    	reAlternativeService.delAlternative(id);
    	return AjaxResult.success("删除成功");
    }
}
