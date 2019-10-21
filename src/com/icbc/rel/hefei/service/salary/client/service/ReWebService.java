package com.icbc.rel.hefei.service.salary.client.service;


import java.util.List;
import java.util.Map;

import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;

public interface ReWebService {
	/**
	 * ��ѯ������Ϣ
	 * @param paramsMap
	 * @return
	 */
	List<SalaryVO> getReInfo(Map<String, Object> paramsMap);
	/**
	 * ��ѯ������ϸ
	 * @param paramsMap
	 * @return
	 */
	List<SalaryImportVO> getReDetail(Map<String, Object> paramsMap);

}
