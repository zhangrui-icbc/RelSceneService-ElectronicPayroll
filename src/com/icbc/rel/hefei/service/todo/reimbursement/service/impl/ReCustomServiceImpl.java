package com.icbc.rel.hefei.service.todo.reimbursement.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.todo.reimbursement.ReAlternativeMapper;
import com.icbc.rel.hefei.dao.todo.reimbursement.ReCommonMapper;
import com.icbc.rel.hefei.dao.todo.reimbursement.ReCustomMapper;
import com.icbc.rel.hefei.entity.todo.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.entity.todo.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.service.todo.reimbursement.service.ReCustomService;

@Service
public class ReCustomServiceImpl implements ReCustomService {

	@Autowired
	ReCustomMapper reCustomMapper;
	@Autowired
	ReAlternativeMapper reAlternativeMapper;
	@Autowired
	ReCommonMapper reCommonMapper;
	
	/**
	 * ���빫˾����ģ��
	 */
	@Override
	public void insertReCustomTemplate(List<ReCustomTemplate> reTemplateList, String companyId) {
		reCustomMapper.delCustomTemplateByComid(companyId);
		int count =reCommonMapper.getComCount();
		for (int i = 0; i < reTemplateList.size(); i++) {
			reTemplateList.get(i).setColIndex(count+i);
			reTemplateList.get(i).setCompanyId(companyId);;
		}
		if(reTemplateList.size()>0) {
			reCustomMapper.insertReCustomTemplate(reTemplateList);
		}
	}
	@Override
	public void delCustomTemplate(int id) {
		reCustomMapper.delCustomTemplate(id);
	}
	@Override
	public void updateCustomTemplate(ReCommonTemplate reCommonTemplate) {
		reCustomMapper.updateCustomTemplate(reCommonTemplate);
	}
	@Override
	public List<ReCustomTemplate> getCustomTemplate(String companyId) {
		// TODO Auto-generated method stub
		return reCustomMapper.getCustomTemplate(companyId);
	}
	


}