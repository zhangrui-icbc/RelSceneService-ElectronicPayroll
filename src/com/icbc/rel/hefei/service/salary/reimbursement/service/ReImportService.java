package com.icbc.rel.hefei.service.salary.reimbursement.service;

import java.util.LinkedHashMap;
import java.util.List;


public interface ReImportService {

	List<String> getTitleList(String reId);

	List<LinkedHashMap<String, Object>> getUpLoadDetail(String sql);

	
}
