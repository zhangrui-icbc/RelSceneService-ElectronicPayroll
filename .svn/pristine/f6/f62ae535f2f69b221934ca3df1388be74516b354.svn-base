package com.icbc.rel.hefei.dao.todo.salary;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.todo.salary.SalaryTemplateAlternative;


public interface SalaryAlternativeMapper {
	List<SalaryTemplateAlternative> getAlternativeInfo();
	
	void addAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative);
	
	void delAlternative(@Param("id") int id);

	void updateAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative);

	List<SalaryTemplateAlternative> judge(SalaryTemplateAlternative oaSalaryTemplateAlternative);

	SalaryCustomTemplate getAlternativeInfoAlone(@Param("id")int id);
}
