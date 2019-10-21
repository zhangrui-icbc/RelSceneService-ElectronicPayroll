package com.icbc.rel.hefei.service.salary.client.service;


import java.util.List;
import java.util.Map;

import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;

public interface SalaryWebService {
	/**
	 * ��ѯ������Ϣ
	 * @param paramsMap
	 * @return
	 */
	List<SalaryVO> getSalaryInfo(Map<String, Object> paramsMap);
	/**
	 * ��ѯ������ϸ
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getSalaryDetail(Map<String, Object> paramsMap);
	/**
	 *  ��ѯ������Ϣ/����
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap);

}
