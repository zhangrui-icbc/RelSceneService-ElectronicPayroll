package com.icbc.rel.hefei.service.salary.reimbursement.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.icbc.rel.hefei.dao.salary.reimbursement.ReImportMapper;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReImportService;

/**
 * »’÷æœÍ«È
 * @author fc
 *
 */
@Transactional
@Service
public class ReImportServiceImpl implements ReImportService {
	@Autowired
	ReImportMapper reImportMapper;
	@Override
	public ArrayList<String> getTitleList(String reId) {
		
		return reImportMapper.getTitleList(reId);
	}
	@Override
	public List<LinkedHashMap<String, Object>> getUpLoadDetail(String sql) {
		// TODO Auto-generated method stub
		return reImportMapper.getUpLoadDetail(sql);
	}
	
}
