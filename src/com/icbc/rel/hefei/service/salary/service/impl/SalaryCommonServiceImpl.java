package com.icbc.rel.hefei.service.salary.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.salary.SalaryCommonMapper;
import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.service.salary.service.SalaryCommonService;

@Service
public class SalaryCommonServiceImpl implements SalaryCommonService {

	@Autowired
	SalaryCommonMapper salaryCommonMapper;
	
	/**
	 * π≤”√
	 */
	@Override
	public List<SalaryCommonTemplate> insertSalaryCommonTemplate(List<SalaryCommonTemplate> salaryTemplateList) {
		List<SalaryCommonTemplate> list =new  ArrayList<SalaryCommonTemplate>();
		for (int i = 0; i < salaryTemplateList.size(); i++) {
			 list =salaryCommonMapper.judge(salaryTemplateList.get(i));
		}
		if(null == list || list.size() ==0) {
			salaryCommonMapper.insertSalaryCommonTemplate(salaryTemplateList);
			return salaryTemplateList;
		}else {
			return null;
		}
	}

	@Override
	public void delCommonTemplate(int id) {
		// TODO Auto-generated method stub
		salaryCommonMapper.delCommonTemplate(id);
	}

	@Override
	public SalaryCommonTemplate updateCommonTemplate(SalaryCommonTemplate salaryCommonTemplate) {
		// TODO Auto-generated method stub
	//	List<SalaryCommonTemplate> list =new  ArrayList<SalaryCommonTemplate>();
	//	list =salaryCommonMapper.judge(salaryCommonTemplate);
		//if(null == list || list.size() ==0) {
			salaryCommonMapper.updateCommonTemplate(salaryCommonTemplate);
			return salaryCommonTemplate;
	//	}else {
		//	return null;
		//}
	}

	@Override
	public List<SalaryCommonTemplate> getCommonTemplate() {
		// TODO Auto-generated method stub
		return salaryCommonMapper.getCommonTemplate();
	}

	@Override
	public void updateColIndex(int i) {
		// TODO Auto-generated method stub
		salaryCommonMapper.updateColIndex(i);
	}

}
