package com.icbc.rel.hefei.service.salary.reimbursement.service;


import java.util.Map;

import com.icbc.rel.hefei.entity.salary.reimbursement.ReTemplateAlternative;


public interface ReAlternativeService {
	/**
	 * ��ѯ��ѡ�ֶ�
	 * @param companyId 
	 * @return
	 */
	Map<String, Object> getAlternativeInfo(String companyId);
	/**
	 * ��ӱ�ѡ�ֶ�
	 * @param reTemplateAlternative
	 */
	ReTemplateAlternative addAlternative(ReTemplateAlternative reTemplateAlternative);
	/**
	 * ɾ����ѡ�ֶ�
	 * @param id
	 * @return
	 */
	void delAlternative(int id);
	/**
	 * �޸ı�ѡ�ֶ�
	 * @param reTemplateAlternative
	 * @return
	 */
	ReTemplateAlternative updateAlternative(ReTemplateAlternative reTemplateAlternative);

}
