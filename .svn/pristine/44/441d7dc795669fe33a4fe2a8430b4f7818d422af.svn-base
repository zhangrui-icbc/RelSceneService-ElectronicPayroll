package com.icbc.rel.hefei.dao.todo.reimbursement;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.reimbursement.ReTemplateAlternative;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;


public interface ReAlternativeMapper {
	List<ReTemplateAlternative> getAlternativeInfo();
	
	void addAlternative(ReTemplateAlternative reTemplateAlternative);
	
	void delAlternative(@Param("id") int id);

	void updateAlternative(ReTemplateAlternative reTemplateAlternative);

	List<ReTemplateAlternative> judge(ReTemplateAlternative reTemplateAlternative);

	SalaryCustomTemplate getAlternativeInfoAlone(@Param("id")int id);
}
