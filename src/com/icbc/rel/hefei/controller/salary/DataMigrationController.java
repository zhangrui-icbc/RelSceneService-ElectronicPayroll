package com.icbc.rel.hefei.controller.salary;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.SalaryImport;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReImport;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReService;
import com.icbc.rel.hefei.service.salary.service.SalaryService;

/**
 * 
 * @author ft
 * 工资excel
 *
 */
@RequestMapping("/mp")
@Controller
public class DataMigrationController {
	private static final Logger logger = Logger.getLogger(DataMigrationController.class);
	
	private static  int flag = 1;
	@Autowired
	private SalaryService salaryService;
	@Autowired
	private ReService reService;
    /**
     * 工资信息迁移
     */
    @RequestMapping("/salary/dataMigration")
    @ResponseBody
    @Transactional
    public AjaxResult dataMigration(HttpServletRequest request){
    	if(flag==1) {
    		flag=2;
    		List<String>  companyList = salaryService.getActivityByRelScenUid();
    		for (int i = 0; i < companyList.size(); i++) {
    			String companyId= companyList.get(i);
    			logger.info("公司:"+companyId+",工资报销信息迁移方法=========>>>>>>>");
    			try {
    				Map<String, Object> paramsMap = new HashMap<String, Object>();
    				paramsMap.put("companyId", companyId);
    				boolean reFlag=false;
    				boolean salFlag = salaryService.salDM(companyId);
    				if(salFlag) {
    					reFlag = reService.reDM(companyId);
    				}
    				if(salFlag&&reFlag) {
    					logger.info("公司:"+companyId+",数据迁移成功!=========>>>>>>>");
    					//return AjaxResult.success("数据迁移成功!");
    				}else {
    					logger.info("公司:"+companyId+",数据迁移失败!=========>>>>>>>");
    				}
    			} catch (Exception e) {
    				logger.error("公司:"+companyId+",数据迁移异常",  e);
    			}
    		}
    		return AjaxResult.success("数据迁移数据完成!");
    	}else {
    		return AjaxResult.success("数据迁移数据已执行,请耐心等待!");
    	}
    } 
    
    /**
     * 数据迁移验证接口
     */
    @RequestMapping("/salary/checkSuccess")
    @ResponseBody
    @Transactional
    public AjaxResult checkSuccess(HttpServletRequest request){
    	logger.info("数据迁移验证接口=========>>>>>>>");
    	Map<String,Object> resMap = new HashMap<String,Object>();
    	//场景号中公司id
    	List<String>  companyList = salaryService.getActivityByRelScenUid();
    	logger.info("场景号中公司companyList:"+companyList.toString());
    	//工资日志表中公司id不为null
    	List<String> salList = salaryService.getNotNull();
    	logger.info("工资日志表中salList:"+salList.toString());
    	//报销日志表中公司id不为null
    	List<String> reList = reService.getNotNull();
    	logger.info("报销日志表中reList:"+reList.toString());
    	resMap.put("场景号中公司companyList", companyList.size());
    	resMap.put("工资日志表中salList", salList.size());
    	resMap.put("报销日志表中reList", reList.size());
    	return AjaxResult.success("访问成功!",resMap);
    } 
    
}
