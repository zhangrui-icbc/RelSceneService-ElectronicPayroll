package com.icbc.rel.hefei.service.todo.client.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.icbc.rel.hefei.dao.todo.client.ReWebMapper;
import com.icbc.rel.hefei.entity.todo.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.todo.client.SalaryVO;
import com.icbc.rel.hefei.service.todo.client.service.ReWebService;

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
	 * 获取工资信息 
	 */
	@Override
	public List<SalaryVO> getReInfo(Map<String, Object> paramsMap) {
		String startDate = (String) paramsMap.get("startDate");
		String endDate = (String) paramsMap.get("endDate");
		List<SalaryVO> list  = new ArrayList<SalaryVO>();
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)) {
			return reWebMapper.getReInfo(paramsMap);
		}else {
			 list =reWebMapper.getReInfo(paramsMap);
			 sort(list);
			return list;
		}
	}

	@Override
	public List<SalaryImportVO> getReDetail(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
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
