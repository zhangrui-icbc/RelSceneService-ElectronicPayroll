package com.icbc.rel.hefei.service.salary.client.service;


import java.util.List;
import java.util.Map;

import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;

public interface SalaryWebService {
	/**
	 * 查询工资信息
	 * @param paramsMap
	 * @return
	 */
	List<SalaryVO> getSalaryInfo(Map<String, Object> paramsMap);
	/**
	 * 查询工资明细
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getSalaryDetail(Map<String, Object> paramsMap);
	/**
	 *  查询工资信息/汇总
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap);

}
