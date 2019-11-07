package com.icbc.rel.hefei.controller.salary.client;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;
import com.icbc.rel.hefei.service.salary.client.service.ReWebService;
import com.icbc.rel.hefei.util.SessionParamConstant;

/**
 * 
 * @author ft
 * 工资excel
 *
 */
@RequestMapping("/com")
@Controller
public class ReWebController {
	private static Logger logger = Logger.getLogger(ReWebController.class);
	@Autowired
	private ReWebService reWebService;
    
    
    /**
     * 获取报销明细
     */
    @RequestMapping("/reimbursement/getReDetail")
    @ResponseBody
    public AjaxResult getReDetail(HttpServletRequest request){
    	List<SalaryImportVO>  oaSalaryList =new ArrayList<SalaryImportVO>();
    	try {
    		String userId = request.getParameter("userId");
    		String issueTime = request.getParameter("issueTime");
    		String salaryId = request.getParameter("salaryId");
    		Map<String, Object> paramsMap =new HashMap<String, Object>();
    		paramsMap.put("userId" , userId);
    		paramsMap.put("issueTime" , issueTime);
    		paramsMap.put("salaryId", salaryId);
    		oaSalaryList= reWebService.getReDetail(paramsMap);
		} catch (Exception e) {
			logger.error("获取报销明细报错:"+e.getMessage());
		}
    	return AjaxResult.success("成功", oaSalaryList);
    }
    
    /**
     * 查询报销信息/汇总
     * @param request
     * @return
     */
    @RequestMapping("/reimbursement/getReInfo")
    @ResponseBody
    public AjaxResult getSalaryInfo(HttpServletRequest request){
    	List<SalaryVO>  oaSalaryList =new ArrayList<SalaryVO>();
    	try {
        	SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
        	String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
        	String startDate = request.getParameter("startDate");
        	String endDate = request.getParameter("endDate");
        	Map<String, Object> paramsMap =new HashMap<String, Object>();
        	paramsMap.put("userId" , user.getMobile());//手机号,把导入数据表的员工编号也设成手机号码
        	paramsMap.put("companyId" , companyId);
        	paramsMap.put("startDate", startDate);
        	paramsMap.put("endDate", endDate);
        	oaSalaryList= reWebService.getReInfo(paramsMap);
		} catch (Exception e) {
			logger.error("查询报销信息汇总报错:"+e.getMessage());
		}
    	return AjaxResult.success("成功", oaSalaryList);
    }
    
    
    
    
}
