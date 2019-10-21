package com.icbc.rel.hefei.dao.salary;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;


public interface SalaryCustomMapper {
	
	void insertSalaryCustomTemplate(@Param("paramsList") List<SalaryCustomTemplate> salaryTemplateList);

	void delCustomTemplate(@Param("id")int id);

	void updateCustomTemplate(SalaryCommonTemplate salaryCommonTemplate);

	List<SalaryCustomTemplate> getCustomTemplate(@Param("companyId")String companyId);

	void delCustomTemplateByComid(@Param("companyId")String companyId);
}
