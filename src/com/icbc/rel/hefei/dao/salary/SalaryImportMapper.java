package com.icbc.rel.hefei.dao.salary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface SalaryImportMapper {

	ArrayList<String> getTitleList(@Param("salaryId") String salaryId);

	List<LinkedHashMap<String, Object>> getUpLoadDetail(@Param("value") String sql);

}
