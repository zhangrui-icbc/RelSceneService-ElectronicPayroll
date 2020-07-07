package com.icbc.rel.hefei.dao.salary.client;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;


/**
 * 工资明细
 * @author fc
 *
 */
public interface SalaryWebMapper{
	/**
	 * 根据公司id查出模板信息
	 * @param companyId
	 * @return
	 */
	List<SalaryCustomTemplate> getSalaryTemplate(@Param("companyId") Long companyId);
	/**
	 * 保存工资信息
	 * @param oaSalary
	 */
	void insertOaSalary(Salary oaSalary);
	
	/**
	 * 
	 * @param oaSalary
	 */
	void insertOaSalaryImport(Salary oaSalary);
	
	
	List<SalaryImportVO> getSalaryInfo(@Param("params")Map<String, Object> paramsMap);
	
	/**
	 * 汇总
	 * @param paramsMap
	 * @return
	 */
	List<SalaryVO> getSumSalaryInfo(@Param("params")Map<String, Object> paramsMap);
	/**
	 * 工资明细
	 * @param paramsMap
	 * @return
	 */
	SalaryImportVO getSalaryDetail(@Param("params")Map<String, Object> paramsMap);
	List<SalaryImportVO> getSumSalaryInfoList(Map<String, Object> paramsMap);
	List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap);
	

	
	
	
	
	
	
}