package com.icbc.rel.hefei.service.todo.salary.service;


import java.util.Map;

import com.icbc.rel.hefei.entity.todo.salary.SalaryTemplateAlternative;

public interface SalaryAlternativeService {
	/**
	 * 查询备选字段
	 * @param companyId 
	 * @return
	 */
	Map<String, Object> getAlternativeInfo(String companyId);
	/**
	 * 添加备选字段
	 * @param oaSalaryTemplateAlternative
	 */
	SalaryTemplateAlternative addAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative);
	/**
	 * 删除备选字段
	 * @param id
	 * @return
	 */
	void delAlternative(int id);
	/**
	 * 修改备选字段
	 * @param oaSalaryTemplateAlternative
	 * @return
	 */
	SalaryTemplateAlternative updateAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative);

}
