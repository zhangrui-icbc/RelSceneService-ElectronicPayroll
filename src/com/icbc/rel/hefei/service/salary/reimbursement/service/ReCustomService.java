package com.icbc.rel.hefei.service.salary.reimbursement.service;


import java.util.List;

import com.icbc.rel.hefei.entity.salary.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCustomTemplate;

public interface ReCustomService {
	/**
	 * ���빫˾ģ����Ϣ
	 * @param reTemplateList
	 * @param companyId 
	 */
	void insertReCustomTemplate(List<ReCustomTemplate> reTemplateList, String companyId);
	/**
	 * ɾ��ģ���ֶ�
	 * @param id
	 */
	void delCustomTemplate(int id);
	/**
	 * �޸�
	 * @param reCommonTemplate
	 */
	void updateCustomTemplate(ReCommonTemplate reCommonTemplate);
	/**
	 * ��ѯ
	 * @param companyId
	 * @return
	 */
	List<ReCustomTemplate> getCustomTemplate(String companyId);

}