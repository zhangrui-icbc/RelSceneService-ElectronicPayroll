package com.icbc.rel.hefei.service.salary.client.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.icbc.rel.hefei.dao.salary.client.ReWebMapper;
import com.icbc.rel.hefei.entity.salary.client.ReImportVO;
import com.icbc.rel.hefei.entity.salary.client.ReVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;
import com.icbc.rel.hefei.service.salary.client.service.ReWebService;

/**
 * 文件上传处理
 * @author fc
 *
 */
@Transactional
@Service
public class ReWebServiceImpl implements ReWebService {
	@Autowired
	ReWebMapper reWebMapper;
	    
	/**
	 * 获取报销信息 
	 */
	@Override
	public List<ReVO> getReInfo(Map<String, Object> paramsMap) {
//		String startDate = (String) paramsMap.get("startDate");
//		String endDate = (String) paramsMap.get("endDate");
		List<ReVO> list  = new ArrayList<ReVO>();
		list = reWebMapper.getReInfo(paramsMap);
		return list;
	}

	@Override
	public List<ReImportVO> getReDetail(Map<String, Object> paramsMap) {
		return reWebMapper.getReDetail(paramsMap);
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
