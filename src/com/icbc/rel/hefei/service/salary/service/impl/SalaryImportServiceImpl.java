package com.icbc.rel.hefei.service.salary.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icbc.rel.hefei.dao.salary.SalaryImportMapper;
import com.icbc.rel.hefei.service.salary.service.SalaryImportService;

/**
 * »’÷æœÍ«È
 * @author fc
 *
 */
@Transactional
@Service
public class SalaryImportServiceImpl implements SalaryImportService {
	@Autowired
	SalaryImportMapper salaryImportMapper;
	@Override
	public ArrayList<String> getTitleList(String salaryId) {
		
		return salaryImportMapper.getTitleList(salaryId);
	}
	@Override
	public List<LinkedHashMap<String, Object>> getUpLoadDetail(String sql) {
		// TODO Auto-generated method stub
		return salaryImportMapper.getUpLoadDetail(sql);
	}
	
}
