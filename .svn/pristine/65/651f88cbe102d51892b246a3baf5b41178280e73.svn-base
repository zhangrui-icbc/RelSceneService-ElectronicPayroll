package com.icbc.rel.hefei.dao.todo.reimbursement;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.entity.todo.reimbursement.ReCustomTemplate;


public interface ReCustomMapper {
	
	void insertReCustomTemplate(@Param("paramsList") List<ReCustomTemplate> reTemplateList);

	void delCustomTemplate(@Param("id")int id);

	void updateCustomTemplate(ReCommonTemplate reCommonTemplate);

	List<ReCustomTemplate> getCustomTemplate(@Param("companyId")String companyId);

	void delCustomTemplateByComid(@Param("companyId")String companyId);
}
