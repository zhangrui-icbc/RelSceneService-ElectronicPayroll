package com.icbc.rel.hefei.dao.todo.client;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.todo.client.SalaryVO;
import com.icbc.rel.hefei.entity.todo.salary.Salary;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;


/**
 * ������ϸ
 * @author fc
 *
 */
public interface SalaryWebMapper{
	/**
	 * ���ݹ�˾id���ģ����Ϣ
	 * @param companyId
	 * @return
	 */
	List<SalaryCustomTemplate> getSalaryTemplate(@Param("companyId") Long companyId);
	/**
	 * ���湤����Ϣ
	 * @param oaSalary
	 */
	void insertOaSalary(Salary oaSalary);
	
	/**
	 * 
	 * @param oaSalary
	 */
	void insertOaSalaryImport(Salary oaSalary);
	
	
	List<SalaryVO> getSalaryInfo(@Param("params")Map<String, Object> paramsMap);
	
	/**
	 * ����
	 * @param paramsMap
	 * @return
	 */
	List<SalaryVO> getSumSalaryInfo(@Param("params")Map<String, Object> paramsMap);
	/**
	 * ������ϸ
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getSalaryDetail(@Param("params")Map<String, Object> paramsMap);
	List<SalaryImportVO> getSumSalaryInfoList(Map<String, Object> paramsMap);
	List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap);
	

	
	
	
	
	
	
}