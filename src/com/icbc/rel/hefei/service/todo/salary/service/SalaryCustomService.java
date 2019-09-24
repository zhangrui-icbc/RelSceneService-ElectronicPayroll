package com.icbc.rel.hefei.service.todo.salary.service;


import java.util.List;

import com.icbc.rel.hefei.entity.todo.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;

public interface SalaryCustomService {
	/**
	 * ���빫˾ģ����Ϣ
	 * @param salaryTemplateList
	 * @param companyId 
	 */
	void insertSalaryCustomTemplate(List<SalaryCustomTemplate> salaryTemplateList, String companyId);
	/**
	 * ɾ��ģ���ֶ�
	 * @param id
	 */
	void delCustomTemplate(int id);
	/**
	 * �޸�
	 * @param salaryCommonTemplate
	 */
	void updateCustomTemplate(SalaryCommonTemplate salaryCommonTemplate);
	/**
	 * ��ѯ
	 * @param companyId
	 * @return
	 */
	List<SalaryCustomTemplate> getCustomTemplate(String companyId);

}
