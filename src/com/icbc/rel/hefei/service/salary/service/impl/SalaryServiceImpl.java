package com.icbc.rel.hefei.service.salary.service.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Case;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Rows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.icbc.rel.hefei.controller.salary.client.ReWebController;
import com.icbc.rel.hefei.dao.salary.SalaryCommonMapper;
import com.icbc.rel.hefei.dao.salary.SalaryCustomMapper;
import com.icbc.rel.hefei.dao.salary.SalaryMapper;
import com.icbc.rel.hefei.dao.salary.SalaryUserMapper;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.ErrorInfo;
import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryImport;
import com.icbc.rel.hefei.entity.salary.SalaryImportOld;
import com.icbc.rel.hefei.entity.salary.SalaryOld;
import com.icbc.rel.hefei.entity.salary.SalaryStaff;
import com.icbc.rel.hefei.service.rel.MessageHelper;
import com.icbc.rel.hefei.service.rel.MessageService;
import com.icbc.rel.hefei.service.salary.service.SalaryService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.util.DateUtils;
import com.icbc.rel.hefei.util.ExcelUtil;
import com.icbc.rel.hefei.util.MobileUtil;
import com.icbc.rel.hefei.util.SalaryExcelUtil;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SystemConfigUtil;
import com.icbc.rel.hefei.util.UUIDUtils;
import com.mysql.cj.result.Row;

/**
 * 文件上传处理
 * @author fc
 *
 */
@Transactional
@Service
public class SalaryServiceImpl implements SalaryService {
	private static Logger logger = Logger.getLogger(SalaryServiceImpl.class);
	@Autowired
	SalaryMapper salaryMapper;
	@Autowired
	SalaryCustomMapper salaryCustomMapper;
	@Autowired
	SalaryCommonMapper salaryCommonMapper;
	@Autowired
	SalaryUserMapper salaryUserMapper;
	
	/**
	 * 处理上传的Excel文件
	 */
//	@Override
//	public AjaxResult uploadSalary(File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException {
//		String fileName =  file.getName();
//        if(!(fileName.contains("xls"))){
//        	return AjaxResult.warn("格式错误!仅支持xls格式文件.");
//        }
//        //指定文件存放路径，可以是相对路径或者绝对路径
//        String filePath = "./src/main/resources/templates/";
//        try {
////			uploadFile(File2byte(file), filePath, fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//        File serverFile=new File(filePath+fileName);
//        //根据公司id取出该公司对面的模板信息
//        List<SalaryCustomTemplate> templateList = salaryMapper.getSalaryTemplate(companyId);
//        List<String> staffMobList = salaryUserMapper.getMobByCompanyId(companyId);	
//        AjaxResult ajaxResult= read2003Excel(serverFile,templateList,staffMobList);
//        int code = (int) ajaxResult.get("code");
//        if(code==500) {
//        	 return ajaxResult;
//        }else {
//        	Salary oaSalary = (Salary)ajaxResult.get("data");  
//        	oaSalary.setId(UUIDUtils.getGuid());
//        	if(oaSalary!=null) {
//        		salaryMapper.insertOaSalary(oaSalary);
//        		salaryMapper.insertOaSalaryImport(oaSalary);
//        	}
//        	 return ajaxResult;
//        }
//       
//	}
	

	@Override
	public AjaxResult uploadSalary1(String value, String companyId)throws FileNotFoundException, IOException, ParseException, NullPointerException {
		 List<SalaryCustomTemplate> templateList = salaryMapper.getSalaryTemplate(companyId);
		 File serverFile=new File(value);
  		 List<String> staffMobList = salaryUserMapper.getMobByCompanyId(companyId);	
		 AjaxResult ajaxResult= read2003Excel(serverFile,templateList,staffMobList);
	     int code = (int) ajaxResult.get("code");
	     if(code==500) {
	     	 return ajaxResult;
	     }else {
	 	 Map<String,Object> map = (Map<String, Object>) ajaxResult.get("data");
	     	Salary oaSalary = (Salary)map.get("oaSalary");  
	     	if(oaSalary.getImportList()!=null&&oaSalary.getImportList().size()>0) {
	     		Long startTime = System.currentTimeMillis();
	     		logger.info("开始插入工资信息-----------------------");
	     		salaryMapper.insertOaSalary(oaSalary);
	     		List<SalaryImport> importList = oaSalary.getImportList();
	     		List<SalaryImport> importList1 = oaSalary.getImportList();
	     		int size = importList.size();
	     		int index = 0;
	     		int limit =3000;
	     		while(true) {
	     			if(index+limit>=size) {
	     				importList1 = importList.subList(index, size);
	     				salaryMapper.insertOaSalaryImport(importList1);
	     				break;
	     			}else {
	     				salaryMapper.insertOaSalaryImport(importList.subList(index, index+limit));
	     				index = index+limit;
	     			}
	     			
	     		}
	     		Long endTime = System.currentTimeMillis();
	     		logger.info("插入工资信息完成-----------------------");
	     		long count = endTime-startTime;
	     		logger.info("插入工资信息耗时:"+count+"毫秒");
	     	}
	     	 return ajaxResult;
	     }
	}
	
	
	/**
	 * 上传员工信息
	 */
	@Override
	public  AjaxResult uploadStaff(File file, String companyId) {
		HSSFWorkbook hwb;
		List<SalaryStaff>  staffList = new ArrayList<SalaryStaff>();
		try {
			hwb = new HSSFWorkbook(new FileInputStream(file));
			List<String[]> data = SalaryExcelUtil.ReadExcel(hwb, 0);
	        if(data.get(0).length!=3) {
	        	return AjaxResult.error("上传文件内容格式不正确！");
	        }
		    for(int i = 1;i< data.size();i++){
		    	SalaryStaff salaryStaff =new SalaryStaff();
		    	String[] row = data.get(i);
		    	salaryStaff.setCompanyId(companyId);
		    	salaryStaff.setDept(row[0]);
		    	salaryStaff.setName(row[1]);
		    	salaryStaff.setMobile(row[2]);
		    	salaryStaff.setCreateTime(new Date());
		    	salaryStaff.setUpdateTime(new Date());
		    	staffList.add(salaryStaff);
		    }
		} catch (FileNotFoundException e) {
			logger.error("上传员工信息service", e);
		} catch (IOException e) {
			logger.error("上传员工信息service", e);
		}
        return AjaxResult.success("解析员工信息成功!", staffList);
	}
	
	
	
	 /**
	  * 保存员工信息
	  */
	 	@Override
	 	public AjaxResult insertStaffInfo(List<SalaryStaff> staffList, String companyId) {
	 		Map<String, Object> resultMap = new HashMap<String, Object>();
	 		List<SalaryStaff> errList = new ArrayList<SalaryStaff>();
	 		//判断导入的手机号是否重复
	 		for (int i = 0; i < staffList.size(); i++) {
	 			for (int j = staffList.size()-1; j >i; j--) {
	 				String  sPhone = staffList.get(i).getMobile();
	 				String  ePhone = staffList.get(j).getMobile();
	 	           if  (ePhone.equals(sPhone))  {  
	 	        	  return AjaxResult.error("Excel中手机号:"+ePhone+"重复,请检查后导入!");
	 	            } 
				}
			}
	 		List<SalaryStaff> mobileList = salaryMapper.getMobileList(companyId);
	 		for (int i = 0; i < mobileList.size(); i++) {
				for (int j = 0; j < staffList.size(); j++) {
					if(mobileList.get(i).getMobile().equals(staffList.get(j).getMobile())) {
						//去除已存在的员工手机号码
						staffList.remove(j);
						j--;
						continue;
					}
				}
			}
	 		for (int j = 0; j < staffList.size(); j++) {
	 			//手机号码格式校验,过滤非手机号
	 			if(!MobileUtil.checkGeneralPhone(staffList.get(j).getMobile())) {
	 				errList.add(staffList.get(j));
	 				staffList.remove(j);
	 				j--;
	 			}
			}
	 		if(staffList.size()>0) {
	 			salaryMapper.insertStaffInfo(staffList);
	 		}
	 		resultMap.put("errList" , errList);
	 		resultMap.put("staffList" , staffList);
	 		return AjaxResult.success("上传成功！本次新增"+staffList.size()+"个员工手机号码。",resultMap);
	 	}
	 	
	
	
	  private static void uploadFile(byte[] file, String filePath, String fileName) {
	        File targetFile = new File(filePath);
	        if (!targetFile.exists()) {
	            targetFile.mkdirs();
	        }
	        FileOutputStream out;
			try {
				out = new FileOutputStream(filePath + fileName);
				out.write(file);
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
	    }
	  

	  
		 /**
	     * 读取 office 2003 excel
		 * @param staffMobList 
	     * @throws IOException
	     * @throws FileNotFoundException 
		 * @throws ParseException */
	    private static AjaxResult read2003Excel(File file,List<SalaryCustomTemplate> templateList, List<String> staffMobList) throws FileNotFoundException, IOException, ParseException, NullPointerException {
	    	HSSFWorkbook hwb;
	        Salary oaSalary =new Salary();
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        List<SalaryImport>  oaSalaryImportList = new ArrayList<SalaryImport>();
	        List<ErrorInfo>  errorSalaryList = new ArrayList<ErrorInfo>();
	        List<Long> mobileList = new ArrayList<Long>();
	        hwb = new HSSFWorkbook(new FileInputStream(file));
	        List<String[]> data = SalaryExcelUtil.ReadExcel(hwb, 0);
	        if((data.get(0).length-2)!=templateList.size()) {//标头列数判断
	        	return AjaxResult.error("请下载最新工资单模板后重新上传！");
	        }else {
		        for (int i = 0; i < (data.get(0).length-2); i++) {
					if(!data.get(0)[i+2].equals(templateList.get(i).getName())) {//标头文字判断
						return AjaxResult.error("请下载最新工资单模板后重新上传！");
					}
				}
	        }
	        if(data.size()>SessionParamConstant.rowsLimit) {
	        	return AjaxResult.error("该功能仅支持单次最多上传三万条数据!");
	        }
			oaSalary.setImportTime(new Date());//创建时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date issueTime;
			issueTime = sdf.parse(data.get(1)[1]);
			oaSalary.setIssueTime(issueTime);//工资发放时间
			String fileName = file.getName();
			String arr[] = fileName.split("\\.");
			String excelName = arr[0];
			oaSalary.setExcelName(excelName);
			//根据excel的行数循环
			String 	batchNo = UUIDUtils.getGuid();
			String companyId = templateList.get(0).getCompanyId();
			for(int i = 1;i< data.size();i++){
				String specialInfoJson =""; 
				Map<String, Object> specialInfoMap = new HashMap<String, Object>();
				Map<String, Object> TRevenue = new LinkedHashMap<String, Object>();//收入合计
				Map<String, Object> TExpenditure = new LinkedHashMap<String, Object>();//支出合计
				Map<String, Object> SDeduction = new LinkedHashMap<String, Object>();//专项扣除
				Map<String, Object> UExpenditure = new LinkedHashMap<String, Object>();//单位支出
				SalaryImport oaSalaryImport =new  SalaryImport();
				ErrorInfo eInfo  = new  ErrorInfo();
				String mbl = data.get(i)[0];
				if (StringUtils.isEmpty(mbl)) {
					return AjaxResult.error("第"+i+"行手机号为空，请填写后重新上传！");
				}
				long mobile = Long.valueOf(data.get(i)[0]);
				if(mobileList.contains(mobile)) {
					eInfo.setMobile(String.valueOf(mobile));
					eInfo.setReason("重复手机号码！");
					errorSalaryList.add(eInfo);
					continue;
				}else if(!MobileUtil.checkGeneralPhone(mbl)){
					eInfo.setMobile(String.valueOf(mobile));
					eInfo.setReason("手机号码格式不正确！");
					errorSalaryList.add(eInfo);
					continue;
				}else {
					mobileList.add(mobile);
				}
/*			boolean flag = checkStaffIsExist(staffMobList,mobile);
			if(flag) {
				eInfo.setMobile(String.valueOf(mobile));
				eInfo.setReason("不存在该手机号码的关联员工信息！");
				errorSalaryList.add(eInfo);
				continue;
			}*/
				

			//根据模板信息去取想要的信息
				for(int j=0;j<templateList.size();j++) {
					//列号
					int colIndex = templateList.get(j).getColIndex();
					String  name = data.get(0)[colIndex];
					String  value = data.get(i)[colIndex];
					if(!data.get(0)[colIndex].equals("备注")&&StringUtils.isEmpty(value)) {
						value="0";
					}
					// TODO 加判断次数限制?
					if(data.get(0)[colIndex].equals("实际收入")) {
						oaSalaryImport.setRealIncome(value);
					}else if (data.get(0)[colIndex].equals("收入合计")) {
						oaSalaryImport.setTotalRevenue(value);
					}else if (data.get(0)[colIndex].equals("支出合计")) {
						oaSalaryImport.setTotalExpenditure(value);
					}else if (data.get(0)[colIndex].equals("专项附加扣除")) {
						oaSalaryImport.setSpecialDeduction(value);
					}else if (data.get(0)[colIndex].equals("单位支出")) {
						oaSalaryImport.setUnitExpenditure(value);
					}else if (data.get(0)[colIndex].equals("备注")) {
						oaSalaryImport.setSalaryRemark(value);
					}
						
					//个性化信息分组封装为json {"实际收入":{"area":"北京","smsCheckType":"white"},"收入合计":{"initTotal":"0","whiteEffect":"1"}}
					int category = getCategory(templateList,data.get(0)[colIndex]);
					if(category==1) {
						TRevenue.put(name,value);
					}else if (category==2) {
						TExpenditure.put(name,value);
					}else if (category==4) {
						SDeduction.put(name,value);
					}else if (category==5) {
						UExpenditure.put(name,value);
					}
					
				}
				
				if (TExpenditure.size()>0) {
					specialInfoMap.put("totalExpenditure", TExpenditure);
				}
				if (SDeduction.size()>0) {
					specialInfoMap.put("specialDeduction", SDeduction);
				}
				if (UExpenditure.size()>0) {
					specialInfoMap.put("unitExpenditure", UExpenditure);
				}
				if (TRevenue.size()>0) {
					specialInfoMap.put("totalRevenue", TRevenue);
				}
				specialInfoJson =JSON.toJSON(specialInfoMap).toString();
				oaSalaryImport.setSpecialInfo(specialInfoJson);
				oaSalaryImport.setBatchNo(batchNo);
				oaSalaryImport.setCreateTime(new Date());
				oaSalaryImport.setIssueTime(issueTime);
				oaSalaryImport.setUserId(mbl);
				oaSalaryImport.setCompanyId(companyId);
				oaSalaryImportList.add(oaSalaryImport);
		}
			oaSalary.setCompanyId(companyId);
			oaSalary.setId(batchNo);
	        oaSalary.setImportList(oaSalaryImportList);
	 		resultMap.put("errorSalaryList" , errorSalaryList);
	 		resultMap.put("oaSalary" , oaSalary);
	 		resultMap.put("mobileList" , mobileList);
	 		int rightRowsCount = oaSalaryImportList.size();
	 		if(oaSalaryImportList!=null&&oaSalaryImportList.size()>0) {
	 			return AjaxResult.success("本次上传成功"+rightRowsCount+"条记录。",resultMap);
	 		}
	 		return AjaxResult.warn("本次上传成功"+rightRowsCount+"条记录。",resultMap);
	    }
	    /**
	     * 获取字段分组
	     * @param companyId
	     * @param name
	     * @return
	     */
	    private static Integer getCategory(List<SalaryCustomTemplate> templateList , String name)  {
	    	int category =0;
	    	for (int i = 0; i < templateList.size(); i++) {
				if(templateList.get(i).getName().equals(name)) {
					category = templateList.get(i).getCategory();
				}
			}
	    	return category;
	    }
	   
	    
	    

	    /**
	     * 读取Office 2007 excel
	     * */
//	    private static Salary read2007Excel(File file,List<SalaryCustomTemplate> templateList)  {
//	    	XSSFWorkbook xwb;
//	        XSSFSheet sheet=null;
//	    	Salary oaSalary =new Salary();
//	        List<SalaryImport>  oaSalaryImportList = new ArrayList<SalaryImport>();
//	        try {
//	        	xwb = new XSSFWorkbook(new FileInputStream(file));
//				sheet = xwb.getSheetAt(0);
//				XSSFRow row = null;
//			    XSSFCell cell = null;
//			    oaSalary.setImportTime(new Date());//创建时间
//			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			    Date issueTime;
//			    issueTime = sdf.parse(String.valueOf((int)sheet.getRow(1).getCell(4).getNumericCellValue()));
//			    oaSalary.setIssueTime(issueTime);//工资发放时间
//			    //根据excel的行数循环
//			    for(int i = 1;i<= sheet.getLastRowNum();i++){
//			    	row = sheet.getRow(i);
//			    	//根据模板信息去取想要的信息
//			    	for(int j=0;j<templateList.size();j++) {
//			    		SalaryImport oaSalaryImport =new  SalaryImport();
//			    		//列号
//			    		int ColIndex = templateList.get(j).getColIndex();
//			    		cell = row.getCell(ColIndex);
//			    		oaSalaryImport.setImportAmount(cell.getStringCellValue());//具体值
//			    		oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(ColIndex).getStringCellValue());//名称
//			    		oaSalaryImport.setSalaryItemId(j);//元素id
//			    		oaSalaryImport.setTemplateId(templateList.get(j).getCompanyId());//模板id
//			    		oaSalaryImport.setUserId((long) row.getCell(2).getNumericCellValue());//员工编号(固定且具体的某一列)
//			    		oaSalaryImportList.add(oaSalaryImport);
//			    	}
//			    }
//	        } catch (Exception e) {
//				e.printStackTrace();
//			}
//	        oaSalary.setImportList(oaSalaryImportList);
//	        return oaSalary;
//	    }
	    
	
	
	/**
	 * 模板导出
	 */
	@Override
	public void export(HttpServletRequest request,HttpServletResponse response,String companyId) {
		List<SalaryCustomTemplate> oaSalaryImportTemplates =salaryMapper.getSalaryTemplate(companyId);
		List<SalaryCommonTemplate>  salaryCommonTemplate=salaryCommonMapper.getCommonTemplate();
		sort(oaSalaryImportTemplates);
		sort1(salaryCommonTemplate);
		ServletOutputStream os = null;
		try {
			String name="工资信息导入模板"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
			String userAgent = request.getHeader("user-agent").toLowerCase();  
			  
			if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			        // win10 ie edge 浏览器 和其他系统的ie  
				name = URLEncoder.encode(name, "UTF-8");  
			} else {  
			        // fe  
				name = new String(name.getBytes("UTF-8"), "iso-8859-1");  
			}
			 response.setContentType("text/html;charset=UTF-8");
			 response.addHeader("content-type", "application/x-msdownload");
			 response.addHeader("Content-Disposition", "attachment;filename="+ name);
			os = response.getOutputStream();
			createWorkbook(salaryCommonTemplate,oaSalaryImportTemplates).write(os);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
	}
	
	/**
	 * 错误手机号码导出
	 */
	@Override
	public  void exportErrPhone(HttpServletRequest request,HttpServletResponse response,List<SalaryStaff> errList) {
		ServletOutputStream os = null;
		try {
			String name="错误手机号码"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
			String userAgent = request.getHeader("user-agent").toLowerCase();  
			  
			if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			        // win10 ie edge 浏览器 和其他系统的ie  
				name = URLEncoder.encode(name, "UTF-8");  
			} else {  
			        // fe  
				name = new String(name.getBytes("UTF-8"), "iso-8859-1");  
			}
			 response.setContentType("text/html;charset=UTF-8");
			 response.addHeader("content-type", "application/x-msdownload");
			 response.addHeader("Content-Disposition", "attachment;filename="+ name);
			os = response.getOutputStream();
			createErrWorkbook(errList).write(os);
		} catch (IOException e) {
			logger.error("错误手机号码导出service", e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				logger.error("错误手机号码导出service", e);
			}
		}
		
	}
	
	
	
	/**
	 * 错误工资单信息导出
	 */
	@Override
	public  void exportErrSalInfo(HttpServletRequest request,HttpServletResponse response,List<ErrorInfo> errList) {
		ServletOutputStream os = null;
		try {
			String name="错误工资单上传信息"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
			String userAgent = request.getHeader("user-agent").toLowerCase();  
			  
			if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			        // win10 ie edge 浏览器 和其他系统的ie  
				name = URLEncoder.encode(name, "UTF-8");  
			} else {  
			        // fe  
				name = new String(name.getBytes("UTF-8"), "iso-8859-1");  
			}
			 response.setContentType("text/html;charset=UTF-8");
			 response.addHeader("content-type", "application/x-msdownload");
			 response.addHeader("Content-Disposition", "attachment;filename="+ name);
			os = response.getOutputStream();
			createSalWorkbook(errList).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
	}
	
	
	
	
	/**
	 * 错误手机号码
	 * @param salaryCommonTemplate
	 * @param oaSalaryImportTemplates
	 * @return
	 */
	private  HSSFWorkbook createErrWorkbook(List<SalaryStaff> errList){
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFFont font = workBook.createFont();//创建字体对象
			font.setFontHeightInPoints((short) 12);//设置字体大小      
			font.setFontName("仿宋_GB2312");
			HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
			cellStyle.setFont(font);
			HSSFSheet sheet = workBook.createSheet("Page");//使用workbook对象创建sheet对象
			HSSFRow rowsTitle = sheet.createRow((short) 0);
			HSSFCell cellTitle = rowsTitle.createCell(0);
			cellTitle.setCellStyle(cellStyle);
			cellTitle.setCellValue("部门");
			HSSFCell cellTitle1 = rowsTitle.createCell(1);
			cellTitle1.setCellStyle(cellStyle);
			cellTitle1.setCellValue("员工");
			HSSFCell cellTitle2 = rowsTitle.createCell(2);
			cellTitle2.setCellStyle(cellStyle);
			cellTitle2.setCellValue("手机号码");
			for (int i = 0; i < errList.size(); i++) {
				HSSFRow rows = sheet.createRow((short) i+1);
				HSSFCell cell = rows.createCell(0);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(errList.get(i).getDept());
				HSSFCell cell1 = rows.createCell(1);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue(errList.get(i).getName());
				HSSFCell cell2 = rows.createCell(2);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(errList.get(i).getMobile());
			}
		return workBook;
	}
	/**
	 * 错误工资单信息
	 * @param salaryCommonTemplate
	 * @param oaSalaryImportTemplates
	 * @return
	 */
	private  HSSFWorkbook createSalWorkbook(List<ErrorInfo> errList){
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFFont font = workBook.createFont();//创建字体对象
			font.setFontHeightInPoints((short) 12);//设置字体大小      
			font.setFontName("仿宋_GB2312");
			HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中    
			HSSFSheet sheet = workBook.createSheet("Page");//使用workbook对象创建sheet对象
			
			HSSFRow rowsTitle = sheet.createRow((short) 0);
			HSSFCell cellTitle = rowsTitle.createCell(0);
			cellTitle.setCellStyle(cellStyle);
			cellTitle.setCellValue("手机号码");
			HSSFCell cellTitle1 = rowsTitle.createCell(1);
			cellTitle1.setCellStyle(cellStyle);
			cellTitle1.setCellValue("错误原因");
			for (int i = 0; i < errList.size(); i++) {
				//设置列的宽度 
				sheet.setColumnWidth((short) 0, (short) 10000) ;
				sheet.setColumnWidth((short) 1, (short) 10000) ;
				HSSFRow rows = sheet.createRow((short) i+1);
				rows.setHeightInPoints(25);  
				HSSFCell cell = rows.createCell(0);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(errList.get(i).getMobile());
				HSSFCell cell1 = rows.createCell(1);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue(errList.get(i).getReason());
			}
		return workBook;
	}
	
	
	
	private HSSFWorkbook createWorkbook(List<SalaryCommonTemplate> salaryCommonTemplate,List<SalaryCustomTemplate> oaSalaryImportTemplates){
		HSSFWorkbook workBook = new HSSFWorkbook();
		
		List<String> fieldName=new ArrayList<String>();  //excel数据标题
		for(int i=0;i<oaSalaryImportTemplates.size();i++) {
			fieldName.add(oaSalaryImportTemplates.get(i).getName());
		}
		//确定sheet表格数
		HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
			HSSFSheet sheet = workBook.createSheet("Page " );//使用workbook对象创建sheet对象
			HSSFRow headRow = sheet.createRow((short) 0); //创建行，0表示第一行（本例是excel的标题）
			HSSFRow headRow1 = sheet.createRow((short) 1); //创建行，1表示第二行（本例是excel内容的示例）
			sheet.setColumnWidth((short) 0, (short) 3000) ;
			sheet.setColumnWidth((short) 1, (short) 3000) ;
			sheet.setColumnWidth((short) 2, (short) 3000) ;
			sheet.setColumnWidth((short) 3, (short) 3000) ;
			sheet.setColumnWidth((short) 4, (short) 3000) ;
			HSSFFont font = workBook.createFont();//创建字体对象
			font.setFontHeightInPoints((short) 12);//设置字体大小      
			font.setFontName("仿宋_GB2312");
			cellStyle.setFont(font);
			for (int i = 0; i < salaryCommonTemplate.size(); i++) {
				HSSFCell cell = headRow.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(salaryCommonTemplate.get(i).getName());
			}
			HSSFCell cell0 = headRow1.createCell(0);
			cell0.setCellValue(Long.valueOf("18888888001"));
			HSSFCell cell1 = headRow1.createCell(1);
			cell1.setCellValue(20010101);
			//设置标题
			int comSize =  salaryCommonTemplate.size();
			for (int j =comSize; j < fieldName.size()+comSize; j++) {//循环excel的标题
				HSSFCell cell = headRow.createCell( (short) j);//使用行对象创建列对象，0表示第1列
				//设置列的宽度/
				sheet.setColumnWidth((short) j, (short) 3000) ;
				if(fieldName.get(j-comSize) != null){
					//将创建好的样式放置到对应的单元格中
					cell.setCellStyle(cellStyle);
					cell.setCellValue((String) fieldName.get(j-comSize));//为标题中的单元格设置值
				}else{
					cell.setCellValue("-");
				}
				
			}
			
		return workBook;
	}
	/**
	 * 员工信息模板
	 */
	@Override
	public void staffExport(HttpServletRequest request,HttpServletResponse response) {
		HSSFWorkbook workBook = new HSSFWorkbook();
		List<String> fieldName=new ArrayList<String>();  //excel数据标题
		fieldName.add("部门");
		fieldName.add("姓名");
		fieldName.add("手机号码");
		//确定sheet表格数
		HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
		HSSFDataFormat format = workBook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
			HSSFSheet sheet = workBook.createSheet("Page " );//使用workbook对象创建sheet对象
			HSSFRow headRow = sheet.createRow((short) 0); //创建行，0表示第一行（本例是excel的标题）
			sheet.setColumnWidth( 0, (short) 3000) ;
			sheet.setColumnWidth( 1, (short) 3000) ;
			sheet.setColumnWidth( 2, (short) 3000) ;
			sheet.setDefaultColumnStyle(0,cellStyle);
			sheet.setDefaultColumnStyle(1,cellStyle);
			sheet.setDefaultColumnStyle(2,cellStyle);
			HSSFFont font = workBook.createFont();//创建字体对象
			font.setFontHeightInPoints((short) 12);//设置字体大小      
			font.setFontName("仿宋_GB2312");
			cellStyle.setFont(font);
			//设置标题
			for (int j = 0; j < fieldName.size(); j++) {//循环excel的标题
				HSSFCell cell = headRow.createCell( (short) j);//使用行对象创建列对象，0表示第1列
				//设置列的宽度/
				sheet.setColumnWidth((short) j, (short) 3000) ;
				if(fieldName.get(j) != null){
					//将创建好的样式放置到对应的单元格中
					cell.setCellStyle(cellStyle);
					cell.setCellValue((String) fieldName.get(j));//为标题中的单元格设置值
				}else{
					cell.setCellValue("-");
				}
				
			}
			ServletOutputStream os;
			try {
				String name="员工信息导入模板"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
				String userAgent = request.getHeader("user-agent").toLowerCase();  
				  
				if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
				        // win10 ie edge 浏览器 和其他系统的ie  
					name = URLEncoder.encode(name, "UTF-8");  
				} else {  
				        // fe  
					name = new String(name.getBytes("UTF-8"), "iso-8859-1");  
				}
				
				 response.setContentType("text/html;charset=UTF-8");
				 response.addHeader("content-type", "application/x-msdownload");
				 response.addHeader("Content-Disposition", "attachment;filename="+ name);
				os = response.getOutputStream();
				workBook.write(os);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		
	}
	
	
	
	
	/**
	 * 排序
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<SalaryCustomTemplate> oaSalaryImportTemplates){
		 Collections.sort(oaSalaryImportTemplates, new Comparator<SalaryCustomTemplate>() {  
			  
	            @Override  
	            public int compare(SalaryCustomTemplate o1, SalaryCustomTemplate o2) {  
	                //升序排列  
	                if (o1.getColIndex() > o2.getColIndex()) {  
	                    return 1;  
	                }  
	                if (o1.getColIndex() == o2.getColIndex()) {  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        }); 
	}
	/**
	 * 排序2
	 * @param salaryCommonTemplate
	 */
	private static void sort1(List<SalaryCommonTemplate> salaryCommonTemplate){
		 Collections.sort(salaryCommonTemplate, new Comparator<SalaryCommonTemplate>() {  
			  
	            @Override  
	            public int compare(SalaryCommonTemplate o1, SalaryCommonTemplate o2) {  
	                //升序排列  
	                if (o1.getColIndex() > o2.getColIndex()) {  
	                    return 1;  
	                }  
	                if (o1.getColIndex() == o2.getColIndex()) {  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        }); 
	}


	@Override
	public void updatePwd(int id) {
		// TODO Auto-generated method stub
		salaryMapper.updatePwd(id);
	}

	@Override
	public int getMobile(String userName) {
		// TODO Auto-generated method stub
		return salaryMapper.getMobile(userName);
	}

	
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date d = cell.getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = formater.format(d);
				cell.setCellValue(cellValue);
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
			
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			cellValue = String.valueOf(cell.getNumericCellValue());

			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue =NumberToTextConverter.toText(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "-";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;

		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}
	
	
	
	
	
	
	
	
	

	@Override
	public List<SalaryStaff> getStaffInfo( String companyId,String mobile) {
		return salaryMapper.getStaffInfo(companyId,mobile);
	}

	@Override
	public List<Salary> getUpLoadLog(Map<String, Object> paramsMap) {
		return salaryMapper.getUpLoadLog(paramsMap);
	}
	@Transactional
	@Override
	public void delLog(String salaryId) {
		 salaryMapper.delLog(salaryId);
		 salaryMapper.delLog1(salaryId);
	}

	@Override
	public int delStaff(int id) {
		// TODO Auto-generated method stub
		return salaryMapper.delStaff(id);
	}

	@Override
	public int updateMobile(String userName, String newUserName, String companyId) {
		// TODO Auto-generated method stub
		 return salaryMapper.updateMobile(userName,newUserName,companyId);
	}
	/**
     * 将文件转换成byte数组
     * @param filePath
     * @return
     */
	  public static byte[] File2byte(File tradeFile){
	        byte[] buffer = null;
	        try
	        {
	            FileInputStream fis = new FileInputStream(tradeFile);
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            byte[] b = new byte[1024];
	            int n;
	            while ((n = fis.read(b)) != -1)
	            {
	                bos.write(b, 0, n);
	            }
	            fis.close();
	            bos.close();
	            buffer = bos.toByteArray();
	        }catch (FileNotFoundException e){
	        	logger.error(e.getMessage(), e);
	        }catch (IOException e){
	        	logger.error(e.getMessage(), e);
	        }
	        return buffer;
	    }

	@Override
	public void updateAddStaffInfo(SalaryStaff salaryStaff) {
		// TODO Auto-generated method stub
		salaryMapper.updateAddStaffInfo(salaryStaff);
	}

  	private static boolean checkStaffIsExist(List<String> staffMobList,long mobile) {
  		String mob = String.valueOf(mobile); 
  		for (int i = 0; i < staffMobList.size(); i++) {
  			if(mob.equals(staffMobList.get(i))) {
  				return false;
  			}
		}
  		return true;
  	}


	@Override
	public List<String> getExcelNameList(String companyId) {
		return salaryMapper.getExcelNameList(companyId);
	}


	@Override
	public List<SalaryOld> getOldData() {
		
		int count = salaryMapper.getCount();
        //类型转换
        Integer pnum=Integer.valueOf(1);
        Integer psize=Integer.valueOf(1);
        //调用PageHelper获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pnum,psize);
		return salaryMapper.getOldData();
	}


	@Override
	public List<SalaryOld> getOldUpLoadLog(String companyId) {
//		int count = salaryMapper.getCount();
//        //类型转换
//        Integer pnum=Integer.valueOf(1);
//        Integer psize=Integer.valueOf(1);
//        //调用PageHelper获取第1页，10条内容，默认查询总数count
//        PageHelper.startPage(pnum,psize);
		return salaryMapper.getOldUpLoadLog(companyId);
	}


	@Override
	public void addSalCompanyId(String companyId) {
		salaryMapper.addSalCompanyId(companyId);
	}

	@Transactional
	@Override
	public boolean salDM(String companyId) {
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
	    	paramsMap.put("companyId", companyId);
	    	List<Salary> salList = salaryMapper.getUpLoadLog(paramsMap);
	    	if(salList.size()==0||salList.isEmpty()) {
	    		List<String> salaryIds  = salaryMapper.getSalaryIds(companyId);
	    		if(salaryIds.size()!=0) {
	    			for (int ii = 0; ii < salaryIds.size(); ii++) {
	    	        	List<SalaryOld> salaryList = salaryMapper.getOldUpLoadLog(salaryIds.get(ii));
	    	        	if(salaryList.size()!=0&&!salaryList.isEmpty()) {
	    	        		logger.info("工资信息迁移中=========>>>>>>>");
	    	            	List<SalaryImport> importList = new ArrayList<SalaryImport>();
	    	        		String specialInfoJson =""; 
	    	            	Map<String, List<SalaryImportOld>> resultMap= new HashMap<String,List<SalaryImportOld>>(); // 最终要的结果
	    	            	for (int i = 0; i < salaryList.size(); i++) {
	    	            		List<SalaryImportOld> oldImportList = salaryList.get(i).getImportList();
	    	        			for (int j = 0; j < oldImportList.size(); j++) {
	    	        				if(resultMap.containsKey(oldImportList.get(j).getSalaryId()+oldImportList.get(j).getUserId()+"#"+dateToString(salaryList.get(i).getIssueTime()))){
	    	        					resultMap.get(oldImportList.get(j).getSalaryId()+oldImportList.get(j).getUserId()+"#"+dateToString(salaryList.get(i).getIssueTime())).add(oldImportList.get(j));
	    	        				}else{
	    	        					List<SalaryImportOld> list = new ArrayList<SalaryImportOld>();
	    	        					list.add(oldImportList.get(j));
	    	        					resultMap.put(oldImportList.get(j).getSalaryId()+oldImportList.get(j).getUserId()+"#"+dateToString(salaryList.get(i).getIssueTime()),list);
	    	        				}
	    	        			}
	    	        		}
	    	                for (Map.Entry<String, List<SalaryImportOld>> entry : resultMap.entrySet()) {
	    	                	Date issueTime = java.sql.Date.valueOf(entry.getKey().split("#")[1]);
	    	            		Map<String, Object> specialInfoMap = new HashMap<String, Object>();
	    	            		Map<String, Object> TRevenue = new HashMap<String, Object>();//收入合计
	    	            		Map<String, Object> TExpenditure = new HashMap<String, Object>();//支出合计
	    	            		Map<String, Object> SDeduction = new HashMap<String, Object>();//专项扣除
	    	            		Map<String, Object> UExpenditure = new HashMap<String, Object>();//单位支出
	    	                	SalaryImport salaryImport = new SalaryImport();
	    	                    System.out.println(entry.getKey() + " ==> " + entry.getValue());
	    	                    List<SalaryImportOld> salaryImportOlds = entry.getValue();
	    	                    for (int k = 0; k < salaryImportOlds.size(); k++) {
	    	                    	String templateColName = salaryImportOlds.get(k).getTemplateColName();
	    	                    	String importAmount = salaryImportOlds.get(k).getImportAmount();
	    	        				if(templateColName.equals("实际收入")) {
	    	        					salaryImport.setRealIncome(importAmount);
	    	        				}else if (templateColName.equals("收入合计")) {
	    	        					salaryImport.setTotalRevenue(importAmount);
	    	        				}else if (templateColName.equals("支出合计")) {
	    	        					salaryImport.setTotalExpenditure(importAmount);
	    	        				}else if (templateColName.equals("专项附加扣除")) {
	    	        					salaryImport.setSpecialDeduction(importAmount);
	    	        				}else if (templateColName.equals("单位支出")) {
	    	        					salaryImport.setUnitExpenditure(importAmount);
	    	        				}else if (templateColName.equals("备注")) {
	    	        					salaryImport.setSalaryRemark(importAmount);
	    	        				}
	    	        				//个性化信息分组封装为json {"实际收入":{"area":"北京","smsCheckType":"white"},"收入合计":{"initTotal":"0","whiteEffect":"1"}}
	    	        				int category = salaryImportOlds.get(k).getCategory();
	    	        				if(category==1) {
	    	        					TRevenue.put(templateColName,importAmount);
	    	        				}else if (category==2) {
	    	        					TExpenditure.put(templateColName,importAmount);
	    	        				}else if (category==4) {
	    	        					SDeduction.put(templateColName,importAmount);
	    	        				}else if (category==5) {
	    	        					UExpenditure.put(templateColName,importAmount);
	    	        				}
	    	        			}
	    	        			if (TExpenditure.size()>0) {
	    	        				specialInfoMap.put("totalExpenditure", TExpenditure);
	    	        			}
	    	        			if (SDeduction.size()>0) {
	    	        				specialInfoMap.put("specialDeduction", SDeduction);
	    	        			}
	    	        			if (UExpenditure.size()>0) {
	    	        				specialInfoMap.put("unitExpenditure", UExpenditure);
	    	        			}
	    	        			if (TRevenue.size()>0) {
	    	        				specialInfoMap.put("totalRevenue", TRevenue);
	    	        			}
	    	        			specialInfoJson =JSON.toJSON(specialInfoMap).toString();
	    	        			salaryImport.setSpecialInfo(specialInfoJson);
	    	        			salaryImport.setBatchNo(salaryImportOlds.get(0).getSalaryId());
	    	        			salaryImport.setCreateTime(new Date());
	    	        			salaryImport.setIssueTime(issueTime);
	    	        			salaryImport.setUserId(String.valueOf(salaryImportOlds.get(0).getUserId()));
	    	        			salaryImport.setCompanyId(salaryImportOlds.get(0).getTemplateId());
	    	        			importList.add(salaryImport);
	    	                }
	    	            				
	    	         		int size = importList.size();
	    	         		int index = 0;
	    	         		int limit =3000;
	    	         		List<SalaryImport> importList1 = new ArrayList<SalaryImport>();
	    	         		while(true) {
	    	         			if(index+limit>=size) {
	    	         				importList1 = importList.subList(index, size);
	    	         				salaryMapper.insertOaSalaryImport(importList1);
	    	         				break;
	    	         			}else {
	    	         				salaryMapper.insertOaSalaryImport(importList.subList(index, index+limit));
	    	         				index = index+limit;
	    	         			}
	    	         			
	    	         		}
	    	        	}
	    			}
	    			salaryMapper.addSalCompanyId(companyId);
	    			logger.info("工资信息迁移完成=========>>>>>>>");
	    			return true;
	    		}else {
		    		logger.info("没有工资信息需要迁移=========>>>>>>>");
		    		return true;
		    	}
	    	}
	    	logger.info("工资信息迁移,公司id已存在迁移完成=========>>>>>>>");
			return true;
		} catch (Exception e) {
			logger.error("公司:"+companyId+",工资信息迁移出错",e);
			return false;
		}
    	
	}

    private static String dateToString(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        String tiem = sformat.format(date);
        return tiem;
    }


	@Override
	public List<String> getActivityByRelScenUid() {
		return salaryMapper.getActivityByRelScenUid();
	}


	@Override
	public List<String> getNotNull() {
		
		return salaryMapper.getNotNull();
	}  
}
