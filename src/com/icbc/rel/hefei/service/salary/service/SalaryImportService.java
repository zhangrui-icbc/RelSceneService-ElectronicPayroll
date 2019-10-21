package com.icbc.rel.hefei.service.salary.service;

import java.util.LinkedHashMap;
import java.util.List;


public interface SalaryImportService {

	List<String> getTitleList(String salaryId);

	List<LinkedHashMap<String, Object>> getUpLoadDetail(String sql);

	
}
