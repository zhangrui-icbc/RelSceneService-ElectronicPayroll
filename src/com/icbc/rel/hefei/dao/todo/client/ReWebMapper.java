package com.icbc.rel.hefei.dao.todo.client;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.todo.client.SalaryVO;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;


/**
 * ������ϸ
 * @author fc
 *
 */
public interface ReWebMapper{
	/**
	 * ���ݹ�˾id���ģ����Ϣ
	 * @param companyId
	 * @return
	 */
	List<SalaryCustomTemplate> getReTemplate(@Param("companyId") Long companyId);
	
	
	
	List<SalaryVO> getReInfo(@Param("params")Map<String, Object> paramsMap);
	
	/**
	 * ������ϸ
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getReDetail(@Param("params")Map<String, Object> paramsMap);
	List<SalaryImportVO> getSumSalaryInfoList(Map<String, Object> paramsMap);
	List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap);
	

	
	
	
	
	
	
}