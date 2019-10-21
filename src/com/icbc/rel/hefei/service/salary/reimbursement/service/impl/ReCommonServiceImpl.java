package com.icbc.rel.hefei.service.salary.reimbursement.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.salary.reimbursement.ReCommonMapper;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReCommonService;


@Service
public class ReCommonServiceImpl implements ReCommonService {

	@Autowired
	ReCommonMapper reCommonMapper;
	
	/**
	 * π≤”√
	 */
	@Override
	public List<ReCommonTemplate> insertReCommonTemplate(List<ReCommonTemplate> reTemplateList) {
		List<ReCommonTemplate> list =new  ArrayList<ReCommonTemplate>();
		for (int i = 0; i < reTemplateList.size(); i++) {
			 list =reCommonMapper.judge(reTemplateList.get(i));
		}
		if(null == list || list.size() ==0) {
			reCommonMapper.insertReCommonTemplate(reTemplateList);
			return reTemplateList;
		}else {
			return null;
		}
	}

	@Override
	public void delCommonTemplate(int id) {
		// TODO Auto-generated method stub
		reCommonMapper.delCommonTemplate(id);
	}

	@Override
	public ReCommonTemplate updateCommonTemplate(ReCommonTemplate reCommonTemplate) {
			reCommonMapper.updateCommonTemplate(reCommonTemplate);
			return reCommonTemplate;
	}

	@Override
	public List<ReCommonTemplate> getCommonTemplate() {
		// TODO Auto-generated method stub
		return reCommonMapper.getCommonTemplate();
	}

	@Override
	public void updateColIndex(int i) {
		// TODO Auto-generated method stub
		reCommonMapper.updateColIndex(i);
	}

}
