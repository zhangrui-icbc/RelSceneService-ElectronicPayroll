package com.icbc.rel.hefei.controller.salary.client;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;
import com.icbc.rel.hefei.service.salary.client.service.SalaryWebService;
import com.icbc.rel.hefei.util.SessionParamConstant;

/**
 * 
 * @author ft
 * ����excel
 *
 */
@RequestMapping("/com")
@Controller
public class SalaryWebController {
	@Autowired
	private SalaryWebService salaryWebService;
    
    
    /**
     * ��ѯ������Ϣ/����
     * @param request
     * @return
     */
    @RequestMapping("/salary/getSalaryInfo")
    @ResponseBody
    public AjaxResult getSalaryInfo(HttpServletRequest request){
    	SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
    	String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("userId" , user.getMobile());//�ֻ���,�ѵ������ݱ��Ա�����Ҳ����ֻ�����
    	paramsMap.put("companyId" , companyId);
    	paramsMap.put("startDate", startDate);
    	paramsMap.put("endDate", endDate);
    	List<SalaryVO>  oaSalaryList= salaryWebService.getSalaryInfo(paramsMap);
//    	List<SalaryImportVO> importList = salaryWebService.getSalaryInfoList(paramsMap);
    	return AjaxResult.success("�ɹ�", oaSalaryList);
    }
    
    /**
     * ��ȡ������ϸ
     */
    @RequestMapping("/salary/getSalaryDetail")
    @ResponseBody
    public AjaxResult getSalaryDetail(HttpServletRequest request){
    	String userId = request.getParameter("userId");
    	String issueTime = request.getParameter("issueTime");
    	String salaryId = request.getParameter("salaryId");
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("userId" , userId);
    	paramsMap.put("issueTime" , issueTime);
    	paramsMap.put("salaryId", salaryId);
    	List<SalaryImportVO>  oaSalaryList= salaryWebService.getSalaryDetail(paramsMap);
    	return AjaxResult.success("�ɹ�", oaSalaryList);
    }
}
