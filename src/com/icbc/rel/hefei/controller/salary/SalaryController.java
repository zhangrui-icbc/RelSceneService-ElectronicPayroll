package com.icbc.rel.hefei.controller.salary;



import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibm.btt.base.LinkedList;
import com.ibm.nws.ffdc.FFDC;
import com.icbc.rel.hefei.TO.TwoTupleTO;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryStaff;
import com.icbc.rel.hefei.service.salary.service.SalaryImportService;
import com.icbc.rel.hefei.service.salary.service.SalaryService;
import com.icbc.rel.hefei.util.FileUploadUtil;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SystemConfigUtil;

/**
 * 
 * @author ft
 * ����excel
 *
 */
@RequestMapping("/mp")
@Controller
public class SalaryController {
	@Autowired
	private SalaryService salaryService;
	
	@Autowired
	private SalaryImportService salaryImportService;
	/**
	 * ��ת������ҳ��
	 * @return
	 */
	@RequestMapping("/salary/jumpSalary")
	public String jumpSalary()
	{
	    return  "salary/salary";
	}
	/**
	 * �ϴ�����Excel
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping(value="/salary/uploadSalary")
    @ResponseBody
    public AjaxResult uploadSalary(HttpServletRequest request) throws Exception{
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	// 1������һ��DiskFileItemFactory����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2������һ���ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(factory);
		// ����ϴ��ļ�������������
		upload.setHeaderEncoding("UTF-8");
		// 3���ж��ύ�����������Ƿ����ϴ���������
		if (!ServletFileUpload.isMultipartContent(request)) {
			// ���մ�ͳ��ʽ��ȡ����
			return null;
		}
		// 4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
		List<FileItem> list = upload.parseRequest(request);
		String fileName = list.get(0).getName();
        if(!(fileName.contains("xls"))){
        	return AjaxResult.error("��ʽ����!��֧��xls��ʽ�ļ�.");
        }
		File file = new File(fileName);
		list.get(0).write(file);
    	AjaxResult ajaxResult;
		try {
			ajaxResult = salaryService.uploadSalary(/*request,*/file,companyId);
			return ajaxResult;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return AjaxResult.warn("ģ���ʽ����ȷ���߱�����п�ֵ(�������п�ֵ,���޴�������д0!)");
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("�ϴ�ʧ��!");
		} 
    }
	/**
	 * �ϴ�Ա����ϢExcel
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping(value="/salary/uploadStaff")
    @ResponseBody
    public AjaxResult uploadStaff(HttpServletRequest request) throws Exception{
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	// 1������һ��DiskFileItemFactory����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2������һ���ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(factory);
		// ����ϴ��ļ�������������
		upload.setHeaderEncoding("UTF-8");
		// 3���ж��ύ�����������Ƿ����ϴ���������
		if (!ServletFileUpload.isMultipartContent(request)) {
			// ���մ�ͳ��ʽ��ȡ����
			return null;
		}
		// 4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
		List<FileItem> list = upload.parseRequest(request);
		String fileName = list.get(0).getName();
        if(!(fileName.contains("xls"))){
        	return AjaxResult.error("��ʽ����!��֧��xls��ʽ�ļ�.");
        }
		File file = new File(fileName);
		list.get(0).write(file);
		List<SalaryStaff> staffList=  salaryService.uploadStaff(file,companyId);
    	AjaxResult  ajaxResult = salaryService.insertStaffInfo(staffList,companyId);
        return ajaxResult;
    }
    
    

    
    /**
     * ����ģ������
     * @param request
     * @param companyId
     */
    @RequestMapping("/salary/export")
    public void export(HttpServletRequest request,HttpServletResponse response){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	salaryService.export(response,companyId);
    }
    
    /**
     * Ա����Ϣģ������
     * @param request
     * @param companyId
     */
    @RequestMapping("/salary/staffExport")
    public void staffExport(HttpServletResponse response){
    	salaryService.staffExport(response);
    }
    /**
     * �޸�Ա������
     */
    @RequestMapping("/salary/updatePwd")
    @ResponseBody
    public AjaxResult updatePwd(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	String userName = request.getParameter("userName");
    	if(StringUtils.isEmpty(userName)) {
    		return AjaxResult.error("�ֻ��Ų���Ϊ��!");
    	}
    	SalaryStaff salaryStaff = salaryService.getStaffInfo(userName,companyId);
    	if(salaryStaff==null) {
    		return AjaxResult.error("���˺Ų�����!");
    	}else {
    		salaryService.updatePwd(userName, companyId);
    		return AjaxResult.success("�����ʼ���ɹ�!");
    	}
    	
    }  
    
    /**
     * Ա�������ֻ�����
     */
    @RequestMapping("/salary/delStaff")
    @ResponseBody
    public AjaxResult delStaff(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	String userName = request.getParameter("userName");
    	if(StringUtils.isEmpty(userName)) {
    		return AjaxResult.error("�ֻ��Ų���Ϊ��!");
    	}
    	SalaryStaff salaryStaff = salaryService.getStaffInfo(userName,companyId);
    	if(salaryStaff==null) {
    		return AjaxResult.error("���˺Ų�����!");
    	}else {
    		salaryService.delStaff(userName, companyId);
    			return AjaxResult.success("Ա���˺�"+userName+"�Ƴ��ɹ�!");
    	}
    }  
    
    /**
     * Ա�������ֻ�����
     */
    @RequestMapping("/salary/exchangeMobile")
    @ResponseBody
    public AjaxResult exchangeMobile(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	String userName = request.getParameter("userName");
    	String newUserName = request.getParameter("newUserName");
    	String newUserName1 = request.getParameter("newUserName1");
    	if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(newUserName)||StringUtils.isEmpty(newUserName1)) {
    		return AjaxResult.error("�ֻ��Ų���Ϊ��!");
    	}else if(userName.equals(newUserName)) {
    		return AjaxResult.error("�¾��ֻ��Ų���һ��!");
    	} else if(!newUserName.equals(newUserName1)){
    		return AjaxResult.error("���ֻ����������벻һ��!");
    	}
    	int flag = salaryService.getMobile(newUserName1);
    	if(flag>0) {
    		return AjaxResult.error("���ֻ����Ѵ���!");
    	}
    	SalaryStaff salaryStaff = salaryService.getStaffInfo(userName,companyId);
    	if(salaryStaff==null) {
    		return AjaxResult.error("���˺Ų�����!");
    	}else {
    			salaryService.updateMobile(userName,newUserName, companyId);
    			return AjaxResult.success("�����ɹ�!");
    	}
    	
    } 
    
    
    
    
    /**
     * ��ѯ���ʵ��ϴ���־
     */
    @RequestMapping("/salary/upLoadLog")
    @ResponseBody
    public AjaxResult upLoadLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("companyId" , companyId);
    	List<Salary>  oaSalaryList= salaryService.getUpLoadLog(paramsMap);
    	return AjaxResult.success("�ɹ�", oaSalaryList);
    }  
    /**
     * ��ѯ���ʵ��ϴ�����
     */
    @RequestMapping("/salary/upLoadDetail")
    @ResponseBody
    public AjaxResult upLoadDetail(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	String salaryId = request.getParameter("salaryId");
    	String date = request.getParameter("date");
    	//1.�������κŲ�ѯ�������ε�һ���û�id
    	//2.�����û�id��ѯ����ͷ
		List<String> titleList =  new ArrayList<String>();
    	titleList = salaryImportService.getTitleList(salaryId);
    	//3.���ݱ�ͷƴ����ת��sql
    	StringBuilder  sql = new StringBuilder("SELECT user_id AS Ա���ֻ���,'"+date+"' AS �������� ,") ;
    	for (int i = 0; i < titleList.size(); i++) {
    		sql.append("MAX(CASE template_col_name WHEN '"+titleList.get(i)+"' THEN import_amount ELSE 0 END) AS "+titleList.get(i)+",");
		}
    	sql.deleteCharAt(sql.length()-1);
    	sql.append(" FROM salary_import  WHERE salary_id = '"+salaryId+"' GROUP BY salary_id,user_id");
    	List<LinkedHashMap<String,Object>> resultList = salaryImportService.getUpLoadDetail(sql.toString());
    	return AjaxResult.success("�ɹ�",resultList);
    }  
    
    
    
    
    /**
     * ɾ�����ʵ��ϴ���־
     */
    @RequestMapping("/salary/delLog")
    @ResponseBody
    public AjaxResult delLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	String salaryId = request.getParameter("salaryId");
        salaryService.delLog(salaryId);
    	return AjaxResult.success("ɾ���ɹ�!");
    }
    
    
    
    
    
}
