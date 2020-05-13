package com.icbc.rel.hefei.controller.salary;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.RequestBody;
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
import com.icbc.rel.hefei.entity.salary.ErrorInfo;
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
 * 工资excel
 *
 */
@RequestMapping("/mp")
@Controller
public class SalaryController {
	private static final Logger logger = Logger.getLogger(SalaryController.class);
	@Autowired
	private SalaryService salaryService;
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
    @SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value="/salary/uploadSalary")
    @ResponseBody
    public AjaxResult uploadSalary(HttpServletRequest request) throws Exception{
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	List<Long> mobileList = new ArrayList<Long>();
    	Map<String,Object> map = new HashMap<String,Object>();
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	FileUploadUtil util = new FileUploadUtil();
		TwoTupleTO to = util.UploadFile(request, SystemConfigUtil.tempPath);
		String str=to.getName();
		String format=str.substring(str.indexOf(".")+1);
		logger.info("format"+format);
		if(!(format.equals("xls"))) {				
			return AjaxResult.error("格式错误!仅支持xls格式文件.");
		}
		String excelName = new File(to.getValue()).getName().split("\\.")[0];
		List<String> nameList  = salaryService.getExcelNameList(companyId);
		if(nameList.size()>0||nameList!=null) {
			if(nameList.contains(excelName)) {
				return AjaxResult.error("名称为\""+excelName+"\"的excel已经上传。");
			}
		}
		AjaxResult ajaxResult;
		try {
			ajaxResult =salaryService.uploadSalary1(to.getValue(), companyId);
			map = (Map<String, Object>) ajaxResult.get("data");
			int code = (int) ajaxResult.get("code");
			if(code==0) {
	    		List<ErrorInfo> list = (List<ErrorInfo>) map.get("errorSalaryList");
	    		request.getSession().setAttribute("errorSalaryList", list);
				String mpId=SessionUtil.getMpId(request.getSession());
				anaylsisXmlUtil t=new anaylsisXmlUtil(); 
				logger.info("群发图文消息接口------------");
				String content;//上送的消息内容，需要是string
				String fanalXmlStr;
				String domainUrl = SystemConfigUtil.domainName;
				String title = "薪资消息";//图文消息显示的标题
				String picurl = domainUrl + "RelSceneService/image/salary/salary/salary.png";//图文消息的图片地址
				String url = domainUrl + "RelSceneService/com/salaryWebUser/jumpLogin?activityUid="+companyId+"&67f977b1ad597511737fff13a2909c1614c41391=0";//图文消息的正文链接
				//上传员工手机号码集合
				mobileList = (List<Long>) map.get("mobileList");
				JSONObject picMessage = MessageHelper.getPicArticlesForHF005(title, picurl, url);
				content = picMessage.toString();
				fanalXmlStr = t.makeXmlByHf005(mpId, "1","","", "news", content);
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
			}else if(code!=500){
	    		List<ErrorInfo> list = (List<ErrorInfo>) map.get("errorSalaryList");
	    		request.getSession().setAttribute("errorSalaryList", list);
			}else {//500
				String msString = (String)ajaxResult.get("msg");
				return AjaxResult.error(msString);
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
	 * 上传员工信息Excel
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/salary/uploadStaff")
    @ResponseBody
    public AjaxResult uploadStaff(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	
    	FileUploadUtil util = new FileUploadUtil();
		TwoTupleTO to = util.UploadFile(request, SystemConfigUtil.tempPath);
		String str=to.getName();
		String format=str.substring(str.indexOf(".")+1);
		logger.info("format"+format);
		if(!(format.equals("xls"))) {				
			return AjaxResult.error("格式错误!仅支持xls格式文件.");
		}
		File file=new File(to.getValue());
		AjaxResult ajaxResult=  salaryService.uploadStaff(file,companyId);
    	int code = (int) ajaxResult.get("code");
    	if (code==500) {
    		return ajaxResult;
    	}
		List<SalaryStaff> staffList = (List<SalaryStaff>)ajaxResult.get("data");
        if(staffList.size()>SessionParamConstant.rowsLimit) {
        	return AjaxResult.error("该功能仅支持单次最多上传三万条数据!");
        }
    	AjaxResult  ajaxRes = salaryService.insertStaffInfo(staffList,companyId);
    	int code1 = (int) ajaxRes.get("code");
    	if (code1!=500) {
    		Map<String,Object> map = (Map<String, Object>) ajaxRes.get("data");
    		List<SalaryStaff> list = (List<SalaryStaff>) map.get("errList");
    		request.getSession().setAttribute("errList", list);
    	}
        return ajaxRes;
    }
 /**
  * 导出错误的员工信息   
  * @param request
  * @param response
  */
    @SuppressWarnings("unchecked")
	@RequestMapping("/salary/exportErrPhone")
    public void exportErrPhone(HttpServletRequest request,HttpServletResponse response){
    	List<SalaryStaff> list =  (List<SalaryStaff>) request.getSession().getAttribute("errList");
    	salaryService.exportErrPhone(request,response,list);
    	request.getSession().removeAttribute("errList");
    }

    /**
     * 导出错误的工资单信息   
     * @param request
     * @param response
     */
       @RequestMapping("/salary/exportErrSalaryInfo")
       public void exportErrSalInfo(HttpServletRequest request,HttpServletResponse response){
       	List<ErrorInfo> list =  (List<ErrorInfo>) request.getSession().getAttribute("errorSalaryList");
       	salaryService.exportErrSalInfo(request,response,list);
       	request.getSession().removeAttribute("errorSalaryList");
       }
       
    /**
     * 工资模板下载
     * @param request
     * @param companyId
     */
    @RequestMapping("/salary/export")
    public void export(HttpServletRequest request,HttpServletResponse response){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	salaryService.export(request,response,companyId);
    }
    
    /**
     * 员工信息模板下载
     * @param request
     * @param companyId
     */
    @RequestMapping("/salary/staffExport")
    public void staffExport(HttpServletRequest request,HttpServletResponse response){
    	salaryService.staffExport(request,response);
    }
    /**
     * 员工密码初始化
     */
    @RequestMapping("/salary/updatePwd")
    @ResponseBody
    public AjaxResult updatePwd(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	String id = request.getParameter("id");
    	salaryService.updatePwd(Integer.valueOf(id));
    	return AjaxResult.success("密码初始化成功!");
    }  
    
    /**
     * 删除员工手机号码
     */
    @RequestMapping("/salary/delStaff")
    @ResponseBody
    public AjaxResult delStaff(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	String id = request.getParameter("id");
    	salaryService.delStaff(Integer.valueOf(id));
    	return AjaxResult.success("删除成功!");
    }  
    
    /**
     * 新增/更换手机号码
     */
    @RequestMapping("/salary/updateStaffInfo")
    @ResponseBody
    public AjaxResult exchangeMobile(HttpServletRequest request,SalaryStaff salaryStaff){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	List<SalaryStaff> staffList = salaryService.getStaffInfo(companyId,salaryStaff.getMobile());
    	if(staffList.size()>0 && staffList.get(0).getId()!=salaryStaff.getId()) {
    		return AjaxResult.error("手机号:"+salaryStaff.getMobile()+"已存在");
    	}
    	salaryService.updateAddStaffInfo(salaryStaff);
    	return AjaxResult.success("操作成功!");
    } 
    
    
    
    
    /**
     * 查询工资单上传日志
     */
    @RequestMapping("/salary/upLoadLog")
    @ResponseBody
    public AjaxResult upLoadLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	Map<String, Object> paramsMap =new HashMap<String, Object>();
    	paramsMap.put("companyId" , companyId);
    	List<Salary>  oaSalaryList= salaryService.getUpLoadLog(paramsMap);
    	return AjaxResult.success("成功", oaSalaryList);
    }  
    
    /**
     * 删除工资单上传日志
     */
    @RequestMapping("/salary/delLog")
    @ResponseBody
    public AjaxResult delLog(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	String salaryId = request.getParameter("salaryId");
        salaryService.delLog(salaryId);
    	return AjaxResult.success("删除成功!");
    }
    
    /**
     * 查询公司员工信息
     */
    @RequestMapping("/salary/getAllStaff")
    @ResponseBody
    public AjaxResult getAllStaff(HttpServletRequest request){
    	String companyId=(String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
    	if(com.alibaba.druid.util.StringUtils.isEmpty(companyId)) {
    		return AjaxResult.error("请先保存参数配置信息！");
    	}
    	String mobile =request.getParameter("mobile");
    	List<SalaryStaff> staffList = salaryService.getStaffInfo(companyId,mobile);
    	return AjaxResult.success("查询成功", staffList);
    }  
    /**
     * 文件下载方法
     * @param response
     * @param filePath
     * @param encode
     */
    @RequestMapping("/salary/explain")
    public void download(HttpServletResponse response,HttpServletRequest request) {
        try {
           String path= request.getSession().getServletContext().getRealPath("/WEB-INF/file/Instructions.pptx");
          // path是指欲下载的文件的路径。
          File file = new File(path);
          // 取得文件名。
          String filename = file.getName();
          // 取得文件的后缀名。
          String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
     
          // 以流的形式下载文件。
          InputStream fis = new BufferedInputStream(new FileInputStream(path));
          byte[] buffer = new byte[fis.available()];
          fis.read(buffer);
          fis.close();
          // 清空response
          response.reset();
          // 设置response的Header
          response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
          response.addHeader("Content-Length", "" + file.length());
          OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
          response.setContentType("application/octet-stream");
          toClient.write(buffer);
          toClient.flush();
          toClient.close();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    
    
}
