package com.icbc.rel.hefei.service.salary.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.ErrorInfo;
import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryStaff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface SalaryService {
	/**
	 * ����excel
	 * @param file
	 * @return
	 */
//	AjaxResult uploadSalary(/*HttpServletRequest request ,*/File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException;
	/**
	 * ���湤����Ϣ
	 * @param oaSalary
	 */
//	void insertSalaryInfo(Salary oaSalary);
	/**
	 * ����ģ��
	 * @param companyId
	 */
	void export(HttpServletRequest request,HttpServletResponse response,String companyId);
	/**
	 * Ա��ģ��
	 * @param request 
	 * @param response
	 * @param companyId
	 */
	void staffExport(HttpServletRequest request, HttpServletResponse response);
	/**
	 * �ϴ�Ա����Ϣ
	 * @param file
	 * @param companyId 
	 * @return
	 */
	AjaxResult uploadStaff(File file, String companyId);
	 /**
	  * ����Ա����Ϣ
	 * @param response 
	 * @param request 
	  * @param staffList
	 * @param companyId 
	  */
	 AjaxResult insertStaffInfo( List<SalaryStaff> staffList, String companyId);
	/**
	 * �޸�Ա������
	 * @param id
	 */
	void updatePwd(int id);
	
	int getMobile(String userName);
	
	List<SalaryStaff> getStaffInfo(String companyId, String mobile);
	/**
	 * ��ȡ�����ϴ���־
	 * @param paramsMap
	 * @return
	 */
	List<Salary> getUpLoadLog(Map<String, Object> paramsMap);
	void delLog(String salaryId);
	int delStaff(int id);
	int updateMobile(String userName, String newUserName, String companyId);
	void updateAddStaffInfo(SalaryStaff salaryStaff);
	AjaxResult uploadSalary1(String value, String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException;
	void exportErrPhone(HttpServletRequest request, HttpServletResponse response, List<SalaryStaff> list);
	void exportErrSalInfo(HttpServletRequest request, HttpServletResponse response, List<ErrorInfo> list);
	List<String> getExcelNameList(String companyId);
	
}
