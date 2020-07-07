package com.icbc.rel.hefei.service.salary.client.service;


import java.util.List;
import java.util.Map;

import com.icbc.rel.hefei.entity.salary.client.ReImportVO;
import com.icbc.rel.hefei.entity.salary.client.ReVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;

public interface ReWebService {
	/**
	 * ��ѯ������Ϣ
	 * @param paramsMap
	 * @return
	 */
	List<ReVO> getReInfo(Map<String, Object> paramsMap);
	/**
	 * ��ѯ������ϸ
	 * @param paramsMap
	 * @return
	 */
	List<ReImportVO> getReDetail(Map<String, Object> paramsMap);

}
