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
	 * ����excel
	 * @param file
	 * @return
	 */
	AjaxResult uploadSalary(/*HttpServletRequest request ,*/File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException;
	/**
	 * ���湤����Ϣ
	 * @param oaSalary
	 */
//	void insertSalaryInfo(Salary oaSalary);
	/**
	 * ����ģ��
	 * @param companyId
	 */
	void export(HttpServletResponse response,String companyId);
	/**
	 * Ա��ģ��
	 * @param response
	 * @param companyId
	 */
	void staffExport(HttpServletResponse response);
	/**
	 * �ϴ�Ա����Ϣ
	 * @param file
	 * @param companyId 
	 * @return
	 */
	 List<SalaryStaff> uploadStaff(File file, String companyId);
	 /**
	  * ����Ա����Ϣ
	  * @param staffList
	 * @param companyId 
	  */
	 AjaxResult insertStaffInfo(List<SalaryStaff> staffList, String companyId);
	/**
	 * �޸�Ա������
	 * @param userName
	 * @param companyId 
	 */
	void updatePwd(String userName, String companyId);
	
	int getMobile(String userName);
	
	SalaryStaff getStaffInfo(String userName, String companyId);
	/**
	 * ��ȡ�����ϴ���־
	 * @param paramsMap
	 * @return
	 */
	List<Salary> getUpLoadLog(Map<String, Object> paramsMap);
	void delLog(String salaryId);
	int delStaff(String userName, String companyId);
	int updateMobile(String userName, String newUserName, String companyId);
	
}
