package com.icbc.rel.hefei.dao.todo.reimbursement;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.reimbursement.ReCommonTemplate;


public interface ReCommonMapper {
	void insertReCommonTemplate(@Param("paramsList")List<ReCommonTemplate> reTemplateList);

	void delCommonTemplate(@Param("id")int id);

	void updateCommonTemplate(ReCommonTemplate reCommonTemplate);

	List<ReCommonTemplate> getCommonTemplate();

	List<ReCommonTemplate> judge(ReCommonTemplate reCommonTemplate);

	int getComCount();

	void updateColIndex(@Param("amount")int i);

}
