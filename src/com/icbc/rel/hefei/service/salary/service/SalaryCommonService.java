package com.icbc.rel.hefei.service.salary.service;


import java.util.List;

import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;
public interface SalaryCommonService {
	/**
	 * 定义共用模板字段
	 * @param salaryTemplateList
	 */
	List<SalaryCommonTemplate> insertSalaryCommonTemplate(List<SalaryCommonTemplate> salaryTemplateList);
   /**
    * 删除共用模板字段
    * @param id
    */
   	void delCommonTemplate(int id);
   	/**
   	 * 修改共用模板字段
   	 * @param salaryCommonTemplate
   	 */
   	SalaryCommonTemplate updateCommonTemplate(SalaryCommonTemplate salaryCommonTemplate);
   	/**
   	 * 获取共用模板字段
   	 * @return
   	 */
	List<SalaryCommonTemplate> getCommonTemplate();
	/**
	 * 增减完固定字段后,调整自定义字段列号
	 * @param i
	 */
	void updateColIndex(int i);

}
