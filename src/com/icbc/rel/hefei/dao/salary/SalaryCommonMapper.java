package com.icbc.rel.hefei.dao.salary;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;


public interface SalaryCommonMapper {
	void insertSalaryCommonTemplate(@Param("paramsList")List<SalaryCommonTemplate> salaryTemplateList);

	void delCommonTemplate(@Param("id")int id);

	void updateCommonTemplate(SalaryCommonTemplate salaryCommonTemplate);

	List<SalaryCommonTemplate> getCommonTemplate();

	List<SalaryCommonTemplate> judge(SalaryCommonTemplate salaryCommonTemplate);

	int getComCount();

	void updateColIndex(@Param("amount")int i);

}
