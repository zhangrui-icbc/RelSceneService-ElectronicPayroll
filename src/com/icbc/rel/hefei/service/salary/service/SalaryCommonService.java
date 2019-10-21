package com.icbc.rel.hefei.service.salary.service;


import java.util.List;

import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;
public interface SalaryCommonService {
	/**
	 * ���干��ģ���ֶ�
	 * @param salaryTemplateList
	 */
	List<SalaryCommonTemplate> insertSalaryCommonTemplate(List<SalaryCommonTemplate> salaryTemplateList);
   /**
    * ɾ������ģ���ֶ�
    * @param id
    */
   	void delCommonTemplate(int id);
   	/**
   	 * �޸Ĺ���ģ���ֶ�
   	 * @param salaryCommonTemplate
   	 */
   	SalaryCommonTemplate updateCommonTemplate(SalaryCommonTemplate salaryCommonTemplate);
   	/**
   	 * ��ȡ����ģ���ֶ�
   	 * @return
   	 */
	List<SalaryCommonTemplate> getCommonTemplate();
	/**
	 * ������̶��ֶκ�,�����Զ����ֶ��к�
	 * @param i
	 */
	void updateColIndex(int i);

}
