package com.icbc.rel.hefei.controller.salary;



import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
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
import org.apache.log4j.Logger;
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

import com.alibaba.fastjson.JSONObject;
import com.ibm.btt.base.LinkedList;
import com.ibm.nws.ffdc.FFDC;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.TO.TwoTupleTO;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryStaff;
import com.icbc.rel.hefei.service.rel.MessageHelper;
import com.icbc.rel.hefei.service.rel.MessageService;
import com.icbc.rel.hefei.service.salary.service.SalaryImportService;
import com.icbc.rel.hefei.service.salary.service.SalaryService;
import com.icbc.rel.hefei.test.testController;
import com.icbc.rel.hefei.util.FileUploadUtil;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;
import com.icbc.rel.hefei.util.anaylsisXmlUtil;

/**
 * 
 * @author ft
 * ����excel
 *
 */
@RequestMapping("/mp")
@Controller
public class SalaryController {
	private static final Logger logger = Logger.getLogger(SalaryController.class);
	@Autowired
	private SalaryService salaryService;
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
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	FileUploadUtil util = new FileUploadUtil();
		TwoTupleTO to = util.UploadFile(request, SystemConfigUtil.tempPath);
		String str=to.getName();
		String format=str.substring(str.indexOf(".")+1);
		logger.info("format"+format);
		if(!(format.equals("xls"))) {				
			return AjaxResult.error("��ʽ����!��֧��xls��ʽ�ļ�.");
		}
		AjaxResult ajaxResult;
/*    	// 1������һ��DiskFileItemFactory����
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
//		list.get(0).write(file);
    	AjaxResult ajaxResult;*/
		try {
		//	ajaxResult = salaryService.uploadSalary(file,companyId);
			ajaxResult =salaryService.uploadSalary1(to.getValue(), companyId);
			String mpId=SessionUtil.getMpId(request.getSession());
			anaylsisXmlUtil t=new anaylsisXmlUtil(); 
			logger.info("Ⱥ��ͼ����Ϣ�ӿ�------------");
			String content;//���͵���Ϣ���ݣ���Ҫ��string
			String fanalXmlStr;
			String domainUrl = SystemConfigUtil.domainName;
			String title = "н����Ϣ";//ͼ����Ϣ��ʾ�ı���
			String picurl = domainUrl + "RelSceneService/image/salary/salary/salary.png";//ͼ����Ϣ��ͼƬ��ַ
			String url = domainUrl + "RelSceneService/com/salaryWebUser/jumpLogin?activityUid="+companyId+"&67f977b1ad597511737fff13a2909c1614c41391=0";//ͼ����Ϣ����������
			JSONObject picMessage = MessageHelper.getPicArticlesForHF005(title, picurl, url);
			content = picMessage.toString();
			fanalXmlStr = t.makeXmlByHf005(mpId, "1","","", "news", content);
			logger.info("���͵�xml�ַ�");
			logger.info(fanalXmlStr);
            int i=0;
			while(i<5) {
				Boolean isSend=MessageService.sendRtfByHf500(fanalXmlStr);
				if(isSend) {
					i=5;
					logger.info("------------������Ϣ�ɹ�!-------------");
					break;
				}else {
					logger.info("������Ϣʧ��������----------------��"+(i+1)+"��");
					i++;
				}
			}
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
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	
    	FileUploadUtil util = new FileUploadUtil();
		TwoTupleTO to = util.UploadFile(request, SystemConfigUtil.tempPath);
		String str=to.getName();
		String format=str.substring(str.indexOf(".")+1);
		logger.info("format"+format);
		if(!(format.equals("xls"))) {				
			return AjaxResult.error("��ʽ����!��֧��xls��ʽ�ļ�.");
		}
		File file=new File(to.getValue());
/*    	// 1������һ��DiskFileItemFactory����
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
//		list.get(0).write(file);*/
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
    	salaryService.export(request,response,companyId);
    }
    
    /**
     * Ա����Ϣģ������
     * @param request
     * @param companyId
     */
    @RequestMapping("/salary/staffExport")
    public void staffExport(HttpServletRequest request,HttpServletResponse response){
    	salaryService.staffExport(request,response);
    }
    /**
     * Ա�������ʼ��
     */
    @RequestMapping("/salary/updatePwd")
    @ResponseBody
    public AjaxResult updatePwd(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	String id = request.getParameter("id");
    	salaryService.updatePwd(Integer.valueOf(id));
    	return AjaxResult.success("�����ʼ���ɹ�!");
    }  
    
    /**
     * ɾ��Ա���ֻ�����
     */
    @RequestMapping("/salary/delStaff")
    @ResponseBody
    public AjaxResult delStaff(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	String id = request.getParameter("id");
    	salaryService.delStaff(Integer.valueOf(id));
    	return AjaxResult.success("ɾ���ɹ�!");
    }  
    
    /**
     * ����/�����ֻ�����
     */
    @RequestMapping("/salary/updateStaffInfo")
    @ResponseBody
    public AjaxResult exchangeMobile(HttpServletRequest request,SalaryStaff salaryStaff){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	List<SalaryStaff> staffList = salaryService.getStaffInfo(companyId,salaryStaff.getMobile());
    	if(staffList.size()>0 && staffList.get(0).getId()!=salaryStaff.getId()) {
    		return AjaxResult.error("�ֻ���:"+salaryStaff.getMobile()+"�Ѵ���");
    	}
    	salaryService.updateAddStaffInfo(salaryStaff);
    	return AjaxResult.success("�����ɹ�!");
    } 
    
    
    
    
    /**
     * ��ѯ���ʵ��ϴ���־
     */
    @RequestMapping("/salary/upLoadLog")
    @ResponseBody
    public AjaxResult upLoadLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("companyId" , companyId);
    	List<Salary>  oaSalaryList= salaryService.getUpLoadLog(paramsMap);
    	return AjaxResult.success("�ɹ�", oaSalaryList);
    }  
    
    /**
     * ɾ�����ʵ��ϴ���־
     */
    @RequestMapping("/salary/delLog")
    @ResponseBody
    public AjaxResult delLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	String salaryId = request.getParameter("salaryId");
        salaryService.delLog(salaryId);
    	return AjaxResult.success("ɾ���ɹ�!");
    }
    
    /**
     * ��ѯ��˾Ա����Ϣ
     */
    @RequestMapping("/salary/getAllStaff")
    @ResponseBody
    public AjaxResult getAllStaff(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("���ȱ������������Ϣ��");
    	}
    	String mobile =request.getParameter("mobile");
    	List<SalaryStaff> staffList = salaryService.getStaffInfo(companyId,mobile);
    	return AjaxResult.success("��ѯ�ɹ�", staffList);
    }  
    
}
