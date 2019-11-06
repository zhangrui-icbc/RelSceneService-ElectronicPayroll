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
 * 报销excel
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
	 * 跳转报销页面
	 * @return
	 */
	@RequestMapping("/reimbursement/jumpReimbursement")
	public String jumpReimbursement()
	{
	    return  "reimbursement/reimbursement";
	}
	/**
	 * 上传报销Excel
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
			ajaxResult = reService.uploadSalary(file,companyId);
			String mpId=SessionUtil.getMpId(request.getSession());
			anaylsisXmlUtil t=new anaylsisXmlUtil(); 
			logger.info("群发图文消息接口示例");
			String content;//上送的消息内容，需要是string
			String fanalXmlStr;
			String domainUrl = SystemConfigUtil.domainName;
			String title = "报销消息";//图文消息显示的标题
			String picurl = domainUrl + "RelSceneService/image/reimbursement/reimbursement/reimbursement.png";//图文消息的图片地址
			String url = domainUrl + "RelSceneService/com/salaryWebUser/jumpLogin?activityUid="+companyId+"&67f977b1ad597511737fff13a2909c1614c41391=0";//图文消息的正文链接
			JSONObject picMessage = MessageHelper.getPicArticles(title, picurl, url);
			content = URLEncoder.encode(picMessage.toString(),"utf-8");
			fanalXmlStr = t.makeXmlByHf005(mpId, "1", "","", "news", content);
			logger.info("上送得xml字符");
			logger.info(fanalXmlStr);
            int i=0;
			while(i<5) {
				Boolean isSend=MessageService.sendRtfByHf500(fanalXmlStr);
				if(isSend) {
					i=5;
					logger.info("------------推送消息成功!-------------");
					break;
				}else {
					logger.info("推送消息失败重试中----------------第"+(i+1)+"次");
					i++;
				}
			}
			
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
     * 报销模板下载
     * @param request
     * @param companyId
     */
    @RequestMapping("/reimbursement/export")
    public void export(HttpServletRequest request,HttpServletResponse response){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	reService.export(request,response,companyId);
    }
    /**
     * 查询报销单上传日志
     */
    @RequestMapping("/reimbursement/upLoadLog")
    @ResponseBody
    public AjaxResult upLoadLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("companyId" , companyId);
    	List<Reimbursement>  oaSalaryList= reService.getUpLoadLog(paramsMap);
    	return AjaxResult.success("成功", oaSalaryList);
    } 
    /**
     * 查询工资单上传详情
     */
    @RequestMapping("/reimbursement/upLoadDetail")
    @ResponseBody
    public AjaxResult upLoadDetail(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	String reId = request.getParameter("reId");
    	String date = request.getParameter("date");
    	//1.根据批次号查询出该批次的一个用户id
    	//2.根据用户id查询出表头
		List<String> titleList =  new ArrayList<String>();
    	titleList = reImportService.getTitleList(reId);
    	//3.根据表头拼接行转列sql
    	StringBuilder  sql = new StringBuilder("SELECT user_id AS 员工手机号,'"+date+"' AS 发放日期 ,") ;
    	for (int i = 0; i < titleList.size(); i++) {
    		sql.append("MAX(CASE template_col_name WHEN '"+titleList.get(i)+"' THEN import_amount ELSE 0 END) AS "+titleList.get(i)+",");
		}
    	sql.deleteCharAt(sql.length()-1);
    	sql.append(" FROM reimbursement_import  WHERE reimbursement_id = '"+reId+"' GROUP BY reimbursement_id,user_id");
    	List<LinkedHashMap<String,Object>> resultList = reImportService.getUpLoadDetail(sql.toString());
    	return AjaxResult.success("成功",resultList);
    }
    /**
     * 删除报销单上传日志
     */
    @RequestMapping("/reimbursement/delLog")
    @ResponseBody
    public AjaxResult delLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("活动失效!");
    	}
    	String salaryId = request.getParameter("reId");
    	reService.delLog(salaryId);
    	return AjaxResult.success("删除成功!");
    }
    
}
