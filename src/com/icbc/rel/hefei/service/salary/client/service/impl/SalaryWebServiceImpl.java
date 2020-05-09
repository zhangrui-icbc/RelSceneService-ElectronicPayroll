package com.icbc.rel.hefei.service.salary.client.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ibm.math.BigDecimal;
import com.icbc.rel.hefei.controller.salary.client.SalaryWebUserController;
import com.icbc.rel.hefei.dao.salary.client.SalaryWebMapper;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;
import com.icbc.rel.hefei.service.salary.client.service.SalaryWebService;

/**
 * 文件上传处理
 * @author fc
 *
 */
@Transactional
@Service
public class SalaryWebServiceImpl implements SalaryWebService {
	private static Logger logger = Logger.getLogger(SalaryWebServiceImpl.class);
	@Autowired
	SalaryWebMapper salaryWebMapper;
	    
	/**
	 * 获取工资信息 
	 */
	@Override
	public List<SalaryVO> getSalaryInfo(Map<String, Object> paramsMap) {
		logger.info("获取工资信息 ");
		String startDate = (String) paramsMap.get("startDate");
		String endDate = (String) paramsMap.get("endDate");
		List<SalaryVO> list  = new ArrayList<SalaryVO>();
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)) {//汇总
			List<SalaryVO> listSV = salaryWebMapper.getSumSalaryInfo(paramsMap);
			List<SalaryImportVO> listSIV= new ArrayList<SalaryImportVO>();
			for (int i = 0; i < listSV.size(); i++) {
				listSIV.addAll(listSV.get(i).getImportList());
			}
			for (int i = 0; i < listSIV.size(); i++) {
				for (int j = listSIV.size()-1;j>i; j--) {
					if(listSIV.get(i).getTemplateColName().equals(listSIV.get(j).getTemplateColName())) {
						listSIV.get(i).setImportAmount(new BigDecimal(listSIV.get(i).getImportAmount()).add(new BigDecimal(listSIV.get(j).getImportAmount())).toString());
						listSIV.remove(j);
					}
				}
			}
			listSV.get(0).setImportList(listSIV);
			List<SalaryVO> listSV1 =new ArrayList<SalaryVO>();
			listSV1.add(listSV.get(0));
			return listSV1;
		}else {
			 list =salaryWebMapper.getSalaryInfo(paramsMap);
			 sort(list);
			return list;
		}
	}

	@Override
	public List<SalaryImportVO> getSalaryDetail(Map<String, Object> paramsMap) {
		return salaryWebMapper.getSalaryDetail(paramsMap);
	}
	
	/**
	 *  查询工资信息/汇总
	 */
	@Override
	public List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap) {
		logger.info("查询工资信息/汇总 ");
		String startDate = (String) paramsMap.get("startDate");
		String endDate = (String) paramsMap.get("endDate");
		List<SalaryImportVO> list  = new ArrayList<SalaryImportVO>();
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)) {
			return salaryWebMapper.getSumSalaryInfoList(paramsMap);
		}else {
			 list =salaryWebMapper.getSalaryInfoList(paramsMap);
			return list;
		}
	}
	
	/**
	 * 排序
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<SalaryVO> SalaryVO){
		 Collections.sort(SalaryVO, new Comparator<SalaryVO>() {  
			  
	            @Override  
	            public int compare(SalaryVO o1, SalaryVO o2) { 
	            	//升序
	            	return o2.getIssueTime().compareTo(o1.getIssueTime());
	            	}
	        }); 
	}
	
}
