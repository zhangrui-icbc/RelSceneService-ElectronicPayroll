package com.icbc.rel.hefei.controller.todo.client;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.todo.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.todo.client.SalaryUser;
import com.icbc.rel.hefei.entity.todo.client.SalaryVO;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.service.todo.client.service.ReWebService;
import com.icbc.rel.hefei.util.SessionParamConstant;

/**
 * 
 * @author ft
 * 工资excel
 *
 */
@RequestMapping("/reimbursement")
@Controller
public class ReWebController {
	@Autowired
	private ReWebService reWebService;
    
    
    /**
     * 获取报销明细
     */
    @RequestMapping("/getReDetail")
    @ResponseBody
    public AjaxResult getReDetail(HttpServletRequest request){
    	String userId = request.getParameter("userId");
    	String issueTime = request.getParameter("issueTime");
    	String salaryId = request.getParameter("salaryId");
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("userId" , userId);
    	paramsMap.put("issueTime" , issueTime);
    	paramsMap.put("salaryId", salaryId);
    	List<SalaryImportVO>  oaSalaryList= reWebService.getReDetail(paramsMap);
    	return AjaxResult.success("成功", oaSalaryList);
    }
    
    /**
     * 查询报销信息/汇总
     * @param request
     * @return
     */
    @RequestMapping("/getReInfo")
    @ResponseBody
    public AjaxResult getSalaryInfo(HttpServletRequest request){
    	SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
    	String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("userId" , user.getMobile());//手机号,把导入数据表的员工编号也设成手机号码
    	paramsMap.put("companyId" , companyId);
    	paramsMap.put("startDate", startDate);
    	paramsMap.put("endDate", endDate);
    	List<SalaryVO>  oaSalaryList= reWebService.getReInfo(paramsMap);
    	return AjaxResult.success("成功", oaSalaryList);
    }
    
    
    
    
}
