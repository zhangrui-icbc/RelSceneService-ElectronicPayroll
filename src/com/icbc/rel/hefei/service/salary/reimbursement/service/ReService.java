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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface ReService {
	/**
	 * ����excel
	 * @param file
	 * @return
	 */
	AjaxResult uploadSalary(File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException;
	/**
	 * ���湤����Ϣ
	 * @param reimbursement
	 */
	void insertReimbursement(Reimbursement reimbursement);
	/**
	 * ����ģ��
	 * @param request 
	 * @param companyId
	 */
	void export(HttpServletRequest request, HttpServletResponse response,String companyId);
	
	/**
	 * ��ȡ�����ϴ���־
	 * @param paramsMap
	 * @return
	 */
	List<Reimbursement> getUpLoadLog(Map<String, Object> paramsMap);
	void delLog(String salaryId);
	int delStaff(String userName, String companyId);
	int updateMobile(String userName, String newUserName, String companyId);
	AjaxResult uploadSalary1(String value, String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException;

}
