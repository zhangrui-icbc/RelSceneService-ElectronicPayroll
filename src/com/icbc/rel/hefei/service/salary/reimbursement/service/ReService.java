package com.icbc.rel.hefei.service.salary.reimbursement.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.reimbursement.Reimbursement;

import javax.servlet.http.HttpServletResponse;
public interface ReService {
	/**
	 * 解析excel
	 * @param file
	 * @return
	 */
	AjaxResult uploadSalary(File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException;
	/**
	 * 保存工资信息
	 * @param reimbursement
	 */
	void insertReimbursement(Reimbursement reimbursement);
	/**
	 * 导出模板
	 * @param companyId
	 */
	void export(HttpServletResponse response,String companyId);
	
	/**
	 * 获取工资上传日志
	 * @param paramsMap
	 * @return
	 */
	List<Reimbursement> getUpLoadLog(Map<String, Object> paramsMap);
	void delLog(String salaryId);
	int delStaff(String userName, String companyId);
	int updateMobile(String userName, String newUserName, String companyId);

}
