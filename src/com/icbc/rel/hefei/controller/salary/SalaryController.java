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
 * 工资excel
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
	 * 跳转工资条页面
	 * @return
	 */
	@RequestMapping("/salary/jumpSalary")
	public String jumpSalary()
	{
	    return  "salary/salary";
	}
	/**
	 * 上传工资Excel
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
    		return AjaxResult.error("活动失效!");
    	}
    	// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			return null;
		}
		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		List<FileItem> list = upload.parseRequest(request);
		String fileName = list.get(0).getName();
        if(!(fileName.contains("xls"))){
        	return AjaxResult.error("格式错误!仅支持xls格式文件.");
        }
		File file = new File(fileName);
		list.get(0).write(file);
    	AjaxResult ajaxResult;
		try {
			ajaxResult = salaryService.uploadSalary(/*request,*/file,companyId);
			return ajaxResult;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return AjaxResult.warn("模板格式不正确或者表格内有空值(不允许有空值,如无此项请填写0!)");
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("上传失败!");
		} 
    }
	/**
	 * 上传员工信息Excel
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
    		return AjaxResult.error("活动失效!");
    	}
    	// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			return null;
		}
		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		List<FileItem> list = upload.parseRequest(request);
		String fileName = list.get(0).getName();
        if(!(fileName.contains("xls"))){
        	return AjaxResult.error("格式错误!仅支持xls格式文件.");
        }
		File file = new File(fileName);
		list.get(0).write(file);
		List<SalaryStaff> staffList=  salaryService.uploadStaff(file,companyId);
    	AjaxResult  ajaxResult = salaryService.insertStaffInfo(staffList,companyId);
        return ajaxResult;
    }
    
    

    
    /**
     * 工资模板下载
     * @param request
     * @param companyId
     */
    @RequestMapping("/salary/export")
    public void export(HttpServletRequest request,HttpServletResponse response){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	salaryService.export(response,companyId);
    }
    
    /**
     * 员工信息模板下载
     * @param request
     * @param companyId
     */
    @RequestMapping("/salary/staffExport")
    public void staffExport(HttpServletResponse response){
    	salaryService.staffExport(response);
    }
    /**
     * 修改员工密码
     */
    @RequestMapping("/salary/updatePwd")
    @ResponseBody
    public AjaxResult updatePwd(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	String userName = request.getParameter("userName");
    	if(StringUtils.isEmpty(userName)) {
    		return AjaxResult.error("手机号不能为空!");
    	}
    	SalaryStaff salaryStaff = salaryService.getStaffInfo(userName,companyId);
    	if(salaryStaff==null) {
    		return AjaxResult.error("该账号不存在!");
    	}else {
    		salaryService.updatePwd(userName, companyId);
    		return AjaxResult.success("密码初始化成功!");
    	}
    	
    }  
    
    /**
     * 员工作废手机号码
     */
    @RequestMapping("/salary/delStaff")
    @ResponseBody
    public AjaxResult delStaff(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	String userName = request.getParameter("userName");
    	if(StringUtils.isEmpty(userName)) {
    		return AjaxResult.error("手机号不能为空!");
    	}
    	SalaryStaff salaryStaff = salaryService.getStaffInfo(userName,companyId);
    	if(salaryStaff==null) {
    		return AjaxResult.error("该账号不存在!");
    	}else {
    		salaryService.delStaff(userName, companyId);
    			return AjaxResult.success("员工账号"+userName+"移除成功!");
    	}
    }  
    
    /**
     * 员工更换手机号码
     */
    @RequestMapping("/salary/exchangeMobile")
    @ResponseBody
    public AjaxResult exchangeMobile(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	String userName = request.getParameter("userName");
    	String newUserName = request.getParameter("newUserName");
    	String newUserName1 = request.getParameter("newUserName1");
    	if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(newUserName)||StringUtils.isEmpty(newUserName1)) {
    		return AjaxResult.error("手机号不能为空!");
    	}else if(userName.equals(newUserName)) {
    		return AjaxResult.error("新旧手机号不能一致!");
    	} else if(!newUserName.equals(newUserName1)){
    		return AjaxResult.error("新手机号两次输入不一致!");
    	}
    	int flag = salaryService.getMobile(newUserName1);
    	if(flag>0) {
    		return AjaxResult.error("新手机号已存在!");
    	}
    	SalaryStaff salaryStaff = salaryService.getStaffInfo(userName,companyId);
    	if(salaryStaff==null) {
    		return AjaxResult.error("该账号不存在!");
    	}else {
    			salaryService.updateMobile(userName,newUserName, companyId);
    			return AjaxResult.success("更换成功!");
    	}
    	
    } 
    
    
    
    
    /**
     * 查询工资单上传日志
     */
    @RequestMapping("/salary/upLoadLog")
    @ResponseBody
    public AjaxResult upLoadLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("companyId" , companyId);
    	List<Salary>  oaSalaryList= salaryService.getUpLoadLog(paramsMap);
    	return AjaxResult.success("成功", oaSalaryList);
    }  
    /**
     * 查询工资单上传详情
     */
    @RequestMapping("/salary/upLoadDetail")
    @ResponseBody
    public AjaxResult upLoadDetail(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	String salaryId = request.getParameter("salaryId");
    	String date = request.getParameter("date");
    	//1.根据批次号查询出该批次的一个用户id
    	//2.根据用户id查询出表头
		List<String> titleList =  new ArrayList<String>();
    	titleList = salaryImportService.getTitleList(salaryId);
    	//3.根据表头拼接行转列sql
    	StringBuilder  sql = new StringBuilder("SELECT user_id AS 员工手机号,'"+date+"' AS 发放日期 ,") ;
    	for (int i = 0; i < titleList.size(); i++) {
    		sql.append("MAX(CASE template_col_name WHEN '"+titleList.get(i)+"' THEN import_amount ELSE 0 END) AS "+titleList.get(i)+",");
		}
    	sql.deleteCharAt(sql.length()-1);
    	sql.append(" FROM salary_import  WHERE salary_id = '"+salaryId+"' GROUP BY salary_id,user_id");
    	List<LinkedHashMap<String,Object>> resultList = salaryImportService.getUpLoadDetail(sql.toString());
    	return AjaxResult.success("成功",resultList);
    }  
    
    
    
    
    /**
     * 删除工资单上传日志
     */
    @RequestMapping("/salary/delLog")
    @ResponseBody
    public AjaxResult delLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	String salaryId = request.getParameter("salaryId");
        salaryService.delLog(salaryId);
    	return AjaxResult.success("删除成功!");
    }
    
    
    
    
    
}
