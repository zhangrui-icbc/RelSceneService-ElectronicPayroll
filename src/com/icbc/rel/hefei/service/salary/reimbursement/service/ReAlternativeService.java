package com.icbc.rel.hefei.service.salary.reimbursement.service;


import java.util.Map;

import com.icbc.rel.hefei.entity.salary.reimbursement.ReTemplateAlternative;


public interface ReAlternativeService {
	/**
	 * 查询备选字段
	 * @param companyId 
	 * @return
	 */
	Map<String, Object> getAlternativeInfo(String companyId);
	/**
	 * 添加备选字段
	 * @param reTemplateAlternative
	 */
	ReTemplateAlternative addAlternative(ReTemplateAlternative reTemplateAlternative);
	/**
	 * 删除备选字段
	 * @param id
	 * @return
	 */
	void delAlternative(int id);
	/**
	 * 修改备选字段
	 * @param reTemplateAlternative
	 * @return
	 */
	ReTemplateAlternative updateAlternative(ReTemplateAlternative reTemplateAlternative);

}
