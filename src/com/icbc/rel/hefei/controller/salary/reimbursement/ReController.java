package com.icbc.rel.hefei.controller.salary.reimbursement;



import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.controller.salary.SalaryController;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.reimbursement.Reimbursement;
import com.icbc.rel.hefei.service.rel.MessageHelper;
import com.icbc.rel.hefei.service.rel.MessageService;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReImportService;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReService;
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
public class ReController {
	private static final Logger logger = Logger.getLogger(ReController.class);
	@Autowired
	private ReService reService;
	@Autowired
	private ReImportService reImportService;
	/**
	 * ��ת����ҳ��
	 * @return
	 */
	@RequestMapping("/reimbursement/jumpReimbursement")
	public String jumpReimbursement()
	{
	    return  "reimbursement/reimbursement";
	}
	/**
	 * �ϴ�����Excel
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping(value="/reimbursement/uploadRe")
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
			ajaxResult = reService.uploadSalary(file,companyId);
			String mpId=SessionUtil.getMpId(request.getSession());
			anaylsisXmlUtil t=new anaylsisXmlUtil(); 
			logger.info("Ⱥ��ͼ����Ϣ�ӿ�ʾ��");
			String content;//���͵���Ϣ���ݣ���Ҫ��string
			String fanalXmlStr;
			String domainUrl = SystemConfigUtil.domainName;
			String title = "������Ϣ";//ͼ����Ϣ��ʾ�ı���
			String picurl = domainUrl + "RelSceneService/image/reimbursement/reimbursement/reimbursement.png";//ͼ����Ϣ��ͼƬ��ַ
			String url = domainUrl + "RelSceneService/com/salaryWebUser/jumpLogin?activityUid="+companyId+"&67f977b1ad597511737fff13a2909c1614c41391=0";//ͼ����Ϣ����������
			JSONObject picMessage = MessageHelper.getPicArticles(title, picurl, url);
			content = URLEncoder.encode(picMessage.toString(),"utf-8");
			fanalXmlStr = t.makeXmlByHf005(mpId, "1", "","", "news", content);
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
     * ����ģ������
     * @param request
     * @param companyId
     */
    @RequestMapping("/reimbursement/export")
    public void export(HttpServletRequest request,HttpServletResponse response){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	reService.export(request,response,companyId);
    }
    /**
     * ��ѯ�������ϴ���־
     */
    @RequestMapping("/reimbursement/upLoadLog")
    @ResponseBody
    public AjaxResult upLoadLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("companyId" , companyId);
    	List<Reimbursement>  oaSalaryList= reService.getUpLoadLog(paramsMap);
    	return AjaxResult.success("�ɹ�", oaSalaryList);
    } 
    /**
     * ��ѯ���ʵ��ϴ�����
     */
    @RequestMapping("/reimbursement/upLoadDetail")
    @ResponseBody
    public AjaxResult upLoadDetail(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	String reId = request.getParameter("reId");
    	String date = request.getParameter("date");
    	//1.�������κŲ�ѯ�������ε�һ���û�id
    	//2.�����û�id��ѯ����ͷ
		List<String> titleList =  new ArrayList<String>();
    	titleList = reImportService.getTitleList(reId);
    	//3.���ݱ�ͷƴ����ת��sql
    	StringBuilder  sql = new StringBuilder("SELECT user_id AS Ա���ֻ���,'"+date+"' AS �������� ,") ;
    	for (int i = 0; i < titleList.size(); i++) {
    		sql.append("MAX(CASE template_col_name WHEN '"+titleList.get(i)+"' THEN import_amount ELSE 0 END) AS "+titleList.get(i)+",");
		}
    	sql.deleteCharAt(sql.length()-1);
    	sql.append(" FROM reimbursement_import  WHERE reimbursement_id = '"+reId+"' GROUP BY reimbursement_id,user_id");
    	List<LinkedHashMap<String,Object>> resultList = reImportService.getUpLoadDetail(sql.toString());
    	return AjaxResult.success("�ɹ�",resultList);
    }
    /**
     * ɾ���������ϴ���־
     */
    @RequestMapping("/reimbursement/delLog")
    @ResponseBody
    public AjaxResult delLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("�ʧЧ!");
    	}
    	String salaryId = request.getParameter("reId");
    	reService.delLog(salaryId);
    	return AjaxResult.success("ɾ���ɹ�!");
    }
    
}
