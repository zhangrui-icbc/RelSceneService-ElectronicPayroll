package com.icbc.rel.hefei.service.salary.client.service;


import java.util.List;
import java.util.Map;

import com.icbc.rel.hefei.entity.salary.client.ReImportVO;
import com.icbc.rel.hefei.entity.salary.client.ReVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;

public interface ReWebService {
	/**
	 * 查询工资信息
	 * @param paramsMap
	 * @return
	 */
	List<ReVO> getReInfo(Map<String, Object> paramsMap);
	/**
	 * 查询工资明细
	 * @param paramsMap
	 * @return
	 */
	List<ReImportVO> getReDetail(Map<String, Object> paramsMap);

}
