package com.icbc.rel.hefei.service.todo.salary.service;


import java.util.Map;

import com.icbc.rel.hefei.entity.todo.salary.SalaryTemplateAlternative;

public interface SalaryAlternativeService {
	/**
	 * ��ѯ��ѡ�ֶ�
	 * @param companyId 
	 * @return
	 */
	Map<String, Object> getAlternativeInfo(String companyId);
	/**
	 * ��ӱ�ѡ�ֶ�
	 * @param oaSalaryTemplateAlternative
	 */
	SalaryTemplateAlternative addAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative);
	/**
	 * ɾ����ѡ�ֶ�
	 * @param id
	 * @return
	 */
	void delAlternative(int id);
	/**
	 * �޸ı�ѡ�ֶ�
	 * @param oaSalaryTemplateAlternative
	 * @return
	 */
	SalaryTemplateAlternative updateAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative);

}
