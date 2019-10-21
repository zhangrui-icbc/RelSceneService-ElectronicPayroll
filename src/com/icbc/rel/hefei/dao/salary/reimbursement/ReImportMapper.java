package com.icbc.rel.hefei.dao.salary.reimbursement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface ReImportMapper {

	ArrayList<String> getTitleList(@Param("reId") String reId);

	List<LinkedHashMap<String, Object>> getUpLoadDetail(@Param("value") String sql);

}
