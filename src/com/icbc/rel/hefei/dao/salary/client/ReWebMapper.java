package com.icbc.rel.hefei.dao.salary.client;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;


/**
 * 工资明细
 * @author fc
 *
 */
public interface ReWebMapper{
	/**
	 * 根据公司id查出模板信息
	 * @param companyId
	 * @return
	 */
	List<SalaryCustomTemplate> getReTemplate(@Param("companyId") Long companyId);
	
	
	
	List<SalaryVO> getReInfo(@Param("params")Map<String, Object> paramsMap);
	
	/**
	 * 工资明细
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getReDetail(@Param("params")Map<String, Object> paramsMap);
	List<SalaryImportVO> getSumSalaryInfoList(Map<String, Object> paramsMap);
	List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap);
	

	
	
	
	
	
	
}