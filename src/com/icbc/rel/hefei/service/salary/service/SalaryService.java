package com.icbc.rel.hefei.service.salary.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryStaff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface SalaryService {
	/**
	 * 解析excel
	 * @param file
	 * @return
	 */
	AjaxResult uploadSalary(/*HttpServletRequest request ,*/File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException;
	/**
	 * 保存工资信息
	 * @param oaSalary
	 */
//	void insertSalaryInfo(Salary oaSalary);
	/**
	 * 导出模板
	 * @param companyId
	 */
	void export(HttpServletResponse response,String companyId);
	/**
	 * 员工模板
	 * @param response
	 * @param companyId
	 */
	void staffExport(HttpServletResponse response);
	/**
	 * 上传员工信息
	 * @param file
	 * @param companyId 
	 * @return
	 */
	 List<SalaryStaff> uploadStaff(File file, String companyId);
	 /**
	  * 保存员工信息
	  * @param staffList
	 * @param companyId 
	  */
	 AjaxResult insertStaffInfo(List<SalaryStaff> staffList, String companyId);
	/**
	 * 修改员工密码
	 * @param userName
	 * @param companyId 
	 */
	void updatePwd(String userName, String companyId);
	
	int getMobile(String userName);
	
	SalaryStaff getStaffInfo(String userName, String companyId);
	/**
	 * 获取工资上传日志
	 * @param paramsMap
	 * @return
	 */
	List<Salary> getUpLoadLog(Map<String, Object> paramsMap);
	void delLog(String salaryId);
	int delStaff(String userName, String companyId);
	int updateMobile(String userName, String newUserName, String companyId);
	
}
