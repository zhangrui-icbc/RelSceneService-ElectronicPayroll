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
import com.icbc.rel.hefei.entity.salary.client.ReImportVO;
import com.icbc.rel.hefei.entity.salary.client.ReVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;
import com.icbc.rel.hefei.service.salary.client.service.ReWebService;
import com.icbc.rel.hefei.util.SessionParamConstant;

/**
 * 
 * @author ft
 * ����excel
 *
 */
@RequestMapping("/com")
@Controller
public class ReWebController {
	private static Logger logger = Logger.getLogger(ReWebController.class);
	@Autowired
	private ReWebService reWebService;
    
    
    /**
     * ��ȡ������ϸ
     */
    @RequestMapping("/reimbursement/getReDetail")
    @ResponseBody
    public AjaxResult getReDetail(HttpServletRequest request){
    	List<ReImportVO>  oaReList =new ArrayList<ReImportVO>();
    	try {
    		String id = request.getParameter("id");
    		Map<String, Object> paramsMap =new HashMap<String, Object>();
    		paramsMap.put("id", id);
    		oaReList= reWebService.getReDetail(paramsMap);
		} catch (Exception e) {
			logger.error("��ȡ������ϸ����:"+e.getMessage(),e);
		}
    	return AjaxResult.success("�ɹ�", oaReList);
    }
    
    /**
     * ��ѯ������Ϣ/����
     * @param request
     * @return
     */
    @RequestMapping("/reimbursement/getReInfo")
    @ResponseBody
    public AjaxResult getSalaryInfo(HttpServletRequest request){
    	List<ReVO>  oaReList =new ArrayList<ReVO>();
    	try {
        	SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
        	String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
        	String startDate = request.getParameter("startDate");
        	String endDate = request.getParameter("endDate");
        	Map<String, Object> paramsMap =new HashMap<String, Object>();
        	paramsMap.put("userId" , user.getMobile());//�ֻ���,�ѵ������ݱ��Ա�����Ҳ����ֻ�����
        	paramsMap.put("companyId" , companyId);
        	paramsMap.put("startDate", startDate);
        	paramsMap.put("endDate", endDate);
        	oaReList= reWebService.getReInfo(paramsMap);
		} catch (Exception e) {
			logger.error("��ѯ������Ϣ���ܱ���:"+e.getMessage());
		}
    	return AjaxResult.success("�ɹ�", oaReList);
    }
    
    
    
    
}
