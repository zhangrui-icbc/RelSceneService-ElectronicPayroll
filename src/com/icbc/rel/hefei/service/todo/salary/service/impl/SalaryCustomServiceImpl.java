package com.icbc.rel.hefei.service.todo.salary.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.todo.salary.SalaryAlternativeMapper;
import com.icbc.rel.hefei.dao.todo.salary.SalaryCommonMapper;
import com.icbc.rel.hefei.dao.todo.salary.SalaryCustomMapper;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.service.todo.salary.service.SalaryCustomService;

@Service
public class SalaryCustomServiceImpl implements SalaryCustomService {

	@Autowired
	SalaryCustomMapper salaryCustomMapper;
	@Autowired
	SalaryAlternativeMapper salaryAlternativeMapper;
	@Autowired
	SalaryCommonMapper salaryCommonMapper;
	
	/**
	 * 插入公司工资模板
	 */
	@Override
	public void insertSalaryCustomTemplate(List<SalaryCustomTemplate> salaryTemplateList, String companyId) {
		salaryCustomMapper.delCustomTemplateByComid(companyId);
		int count =salaryCommonMapper.getComCount();
		for (int i = 0; i < salaryTemplateList.size(); i++) {
			salaryTemplateList.get(i).setColIndex(count+i);
			salaryTemplateList.get(i).setCompanyId(companyId);;
		}
		if(salaryTemplateList.size()>0) {
			salaryCustomMapper.insertSalaryCustomTemplate(salaryTemplateList);
		}
	}
	@Override
	public void delCustomTemplate(int id) {
		salaryCustomMapper.delCustomTemplate(id);
	}
	@Override
	public void updateCustomTemplate(SalaryCommonTemplate salaryCommonTemplate) {
		salaryCustomMapper.updateCustomTemplate(salaryCommonTemplate);
	}
	@Override
	public List<SalaryCustomTemplate> getCustomTemplate(String companyId) {
		// TODO Auto-generated method stub
		return salaryCustomMapper.getCustomTemplate(companyId);
	}
	


}
