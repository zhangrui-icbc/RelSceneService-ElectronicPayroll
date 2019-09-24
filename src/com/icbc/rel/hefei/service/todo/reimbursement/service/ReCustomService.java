package com.icbc.rel.hefei.service.todo.reimbursement.service;


import java.util.List;

import com.icbc.rel.hefei.entity.todo.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.entity.todo.reimbursement.ReCustomTemplate;

public interface ReCustomService {
	/**
	 * 插入公司模板信息
	 * @param reTemplateList
	 * @param companyId 
	 */
	void insertReCustomTemplate(List<ReCustomTemplate> reTemplateList, String companyId);
	/**
	 * 删除模板字段
	 * @param id
	 */
	void delCustomTemplate(int id);
	/**
	 * 修改
	 * @param reCommonTemplate
	 */
	void updateCustomTemplate(ReCommonTemplate reCommonTemplate);
	/**
	 * 查询
	 * @param companyId
	 * @return
	 */
	List<ReCustomTemplate> getCustomTemplate(String companyId);

}
