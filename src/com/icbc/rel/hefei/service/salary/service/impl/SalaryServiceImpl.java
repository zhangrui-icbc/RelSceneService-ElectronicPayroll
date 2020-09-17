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
 * �ļ��ϴ�����
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
	 * �����ϴ���Excel�ļ�
	 */
//	@Override
//	public AjaxResult uploadSalary(File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException {
//		String fileName =  file.getName();
//        if(!(fileName.contains("xls"))){
//        	return AjaxResult.warn("��ʽ����!��֧��xls��ʽ�ļ�.");
//        }
//        //ָ���ļ����·�������������·�����߾���·��
//        String filePath = "./src/main/resources/templates/";
//        try {
////			uploadFile(File2byte(file), filePath, fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//        File serverFile=new File(filePath+fileName);
//        //���ݹ�˾idȡ���ù�˾�����ģ����Ϣ
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
	     		logger.info("��ʼ���빤����Ϣ-----------------------");
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
	     		logger.info("���빤����Ϣ���-----------------------");
	     		long count = endTime-startTime;
	     		logger.info("���빤����Ϣ��ʱ:"+count+"����");
	     	}
	     	 return ajaxResult;
	     }
	}
	
	
	/**
	 * �ϴ�Ա����Ϣ
	 */
	@Override
	public  AjaxResult uploadStaff(File file, String companyId) {
		HSSFWorkbook hwb;
		List<SalaryStaff>  staffList = new ArrayList<SalaryStaff>();
		try {
			hwb = new HSSFWorkbook(new FileInputStream(file));
			List<String[]> data = SalaryExcelUtil.ReadExcel(hwb, 0);
	        if(data.get(0).length!=3) {
	        	return AjaxResult.error("�ϴ��ļ����ݸ�ʽ����ȷ��");
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
			logger.error("�ϴ�Ա����Ϣservice", e);
		} catch (IOException e) {
			logger.error("�ϴ�Ա����Ϣservice", e);
		}
        return AjaxResult.success("����Ա����Ϣ�ɹ�!", staffList);
	}
	
	
	
	 /**
	  * ����Ա����Ϣ
	  */
	 	@Override
	 	public AjaxResult insertStaffInfo(List<SalaryStaff> staffList, String companyId) {
	 		Map<String, Object> resultMap = new HashMap<String, Object>();
	 		List<SalaryStaff> errList = new ArrayList<SalaryStaff>();
	 		//�жϵ�����ֻ����Ƿ��ظ�
	 		for (int i = 0; i < staffList.size(); i++) {
	 			for (int j = staffList.size()-1; j >i; j--) {
	 				String  sPhone = staffList.get(i).getMobile();
	 				String  ePhone = staffList.get(j).getMobile();
	 	           if  (ePhone.equals(sPhone))  {  
	 	        	  return AjaxResult.error("Excel���ֻ���:"+ePhone+"�ظ�,�������!");
	 	            } 
				}
			}
	 		List<SalaryStaff> mobileList = salaryMapper.getMobileList(companyId);
	 		for (int i = 0; i < mobileList.size(); i++) {
				for (int j = 0; j < staffList.size(); j++) {
					if(mobileList.get(i).getMobile().equals(staffList.get(j).getMobile())) {
						//ȥ���Ѵ��ڵ�Ա���ֻ�����
						staffList.remove(j);
						j--;
						continue;
					}
				}
			}
	 		for (int j = 0; j < staffList.size(); j++) {
	 			//�ֻ������ʽУ��,���˷��ֻ���
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
	 		return AjaxResult.success("�ϴ��ɹ�����������"+staffList.size()+"��Ա���ֻ����롣",resultMap);
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
	     * ��ȡ office 2003 excel
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
	        if((data.get(0).length-2)!=templateList.size()) {//��ͷ�����ж�
	        	return AjaxResult.error("���������¹��ʵ�ģ��������ϴ���");
	        }else {
		        for (int i = 0; i < (data.get(0).length-2); i++) {
					if(!data.get(0)[i+2].equals(templateList.get(i).getName())) {//��ͷ�����ж�
						return AjaxResult.error("���������¹��ʵ�ģ��������ϴ���");
					}
				}
	        }
	        if(data.size()>SessionParamConstant.rowsLimit) {
	        	return AjaxResult.error("�ù��ܽ�֧�ֵ�������ϴ�����������!");
	        }
			oaSalary.setImportTime(new Date());//����ʱ��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date issueTime;
			issueTime = sdf.parse(data.get(1)[1]);
			oaSalary.setIssueTime(issueTime);//���ʷ���ʱ��
			String fileName = file.getName();
			String arr[] = fileName.split("\\.");
			String excelName = arr[0];
			oaSalary.setExcelName(excelName);
			//����excel������ѭ��
			String 	batchNo = UUIDUtils.getGuid();
			String companyId = templateList.get(0).getCompanyId();
			for(int i = 1;i< data.size();i++){
				String specialInfoJson =""; 
				Map<String, Object> specialInfoMap = new HashMap<String, Object>();
				Map<String, Object> TRevenue = new LinkedHashMap<String, Object>();//����ϼ�
				Map<String, Object> TExpenditure = new LinkedHashMap<String, Object>();//֧���ϼ�
				Map<String, Object> SDeduction = new LinkedHashMap<String, Object>();//ר��۳�
				Map<String, Object> UExpenditure = new LinkedHashMap<String, Object>();//��λ֧��
				SalaryImport oaSalaryImport =new  SalaryImport();
				ErrorInfo eInfo  = new  ErrorInfo();
				String mbl = data.get(i)[0];
				if (StringUtils.isEmpty(mbl)) {
					return AjaxResult.error("��"+i+"���ֻ���Ϊ�գ�����д�������ϴ���");
				}
				long mobile = Long.valueOf(data.get(i)[0]);
				if(mobileList.contains(mobile)) {
					eInfo.setMobile(String.valueOf(mobile));
					eInfo.setReason("�ظ��ֻ����룡");
					errorSalaryList.add(eInfo);
					continue;
				}else if(!MobileUtil.checkGeneralPhone(mbl)){
					eInfo.setMobile(String.valueOf(mobile));
					eInfo.setReason("�ֻ������ʽ����ȷ��");
					errorSalaryList.add(eInfo);
					continue;
				}else {
					mobileList.add(mobile);
				}
/*			boolean flag = checkStaffIsExist(staffMobList,mobile);
			if(flag) {
				eInfo.setMobile(String.valueOf(mobile));
				eInfo.setReason("�����ڸ��ֻ�����Ĺ���Ա����Ϣ��");
				errorSalaryList.add(eInfo);
				continue;
			}*/
				

			//����ģ����Ϣȥȡ��Ҫ����Ϣ
				for(int j=0;j<templateList.size();j++) {
					//�к�
					int colIndex = templateList.get(j).getColIndex();
					String  name = data.get(0)[colIndex];
					String  value = data.get(i)[colIndex];
					if(!data.get(0)[colIndex].equals("��ע")&&StringUtils.isEmpty(value)) {
						value="0";
					}
					// TODO ���жϴ�������?
					if(data.get(0)[colIndex].equals("ʵ������")) {
						oaSalaryImport.setRealIncome(value);
					}else if (data.get(0)[colIndex].equals("����ϼ�")) {
						oaSalaryImport.setTotalRevenue(value);
					}else if (data.get(0)[colIndex].equals("֧���ϼ�")) {
						oaSalaryImport.setTotalExpenditure(value);
					}else if (data.get(0)[colIndex].equals("ר��ӿ۳�")) {
						oaSalaryImport.setSpecialDeduction(value);
					}else if (data.get(0)[colIndex].equals("��λ֧��")) {
						oaSalaryImport.setUnitExpenditure(value);
					}else if (data.get(0)[colIndex].equals("��ע")) {
						oaSalaryImport.setSalaryRemark(value);
					}
						
					//���Ի���Ϣ�����װΪjson {"ʵ������":{"area":"����","smsCheckType":"white"},"����ϼ�":{"initTotal":"0","whiteEffect":"1"}}
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
	 			return AjaxResult.success("�����ϴ��ɹ�"+rightRowsCount+"����¼��",resultMap);
	 		}
	 		return AjaxResult.warn("�����ϴ��ɹ�"+rightRowsCount+"����¼��",resultMap);
	    }
	    /**
	     * ��ȡ�ֶη���
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
	     * ��ȡOffice 2007 excel
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
//			    oaSalary.setImportTime(new Date());//����ʱ��
//			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			    Date issueTime;
//			    issueTime = sdf.parse(String.valueOf((int)sheet.getRow(1).getCell(4).getNumericCellValue()));
//			    oaSalary.setIssueTime(issueTime);//���ʷ���ʱ��
//			    //����excel������ѭ��
//			    for(int i = 1;i<= sheet.getLastRowNum();i++){
//			    	row = sheet.getRow(i);
//			    	//����ģ����Ϣȥȡ��Ҫ����Ϣ
//			    	for(int j=0;j<templateList.size();j++) {
//			    		SalaryImport oaSalaryImport =new  SalaryImport();
//			    		//�к�
//			    		int ColIndex = templateList.get(j).getColIndex();
//			    		cell = row.getCell(ColIndex);
//			    		oaSalaryImport.setImportAmount(cell.getStringCellValue());//����ֵ
//			    		oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(ColIndex).getStringCellValue());//����
//			    		oaSalaryImport.setSalaryItemId(j);//Ԫ��id
//			    		oaSalaryImport.setTemplateId(templateList.get(j).getCompanyId());//ģ��id
//			    		oaSalaryImport.setUserId((long) row.getCell(2).getNumericCellValue());//Ա�����(�̶��Ҿ����ĳһ��)
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
	 * ģ�嵼��
	 */
	@Override
	public void export(HttpServletRequest request,HttpServletResponse response,String companyId) {
		List<SalaryCustomTemplate> oaSalaryImportTemplates =salaryMapper.getSalaryTemplate(companyId);
		List<SalaryCommonTemplate>  salaryCommonTemplate=salaryCommonMapper.getCommonTemplate();
		sort(oaSalaryImportTemplates);
		sort1(salaryCommonTemplate);
		ServletOutputStream os = null;
		try {
			String name="������Ϣ����ģ��"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
			String userAgent = request.getHeader("user-agent").toLowerCase();  
			  
			if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			        // win10 ie edge ����� ������ϵͳ��ie  
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
	 * �����ֻ����뵼��
	 */
	@Override
	public  void exportErrPhone(HttpServletRequest request,HttpServletResponse response,List<SalaryStaff> errList) {
		ServletOutputStream os = null;
		try {
			String name="�����ֻ�����"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
			String userAgent = request.getHeader("user-agent").toLowerCase();  
			  
			if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			        // win10 ie edge ����� ������ϵͳ��ie  
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
			logger.error("�����ֻ����뵼��service", e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				logger.error("�����ֻ����뵼��service", e);
			}
		}
		
	}
	
	
	
	/**
	 * �����ʵ���Ϣ����
	 */
	@Override
	public  void exportErrSalInfo(HttpServletRequest request,HttpServletResponse response,List<ErrorInfo> errList) {
		ServletOutputStream os = null;
		try {
			String name="�����ʵ��ϴ���Ϣ"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
			String userAgent = request.getHeader("user-agent").toLowerCase();  
			  
			if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
			        // win10 ie edge ����� ������ϵͳ��ie  
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
	 * �����ֻ�����
	 * @param salaryCommonTemplate
	 * @param oaSalaryImportTemplates
	 * @return
	 */
	private  HSSFWorkbook createErrWorkbook(List<SalaryStaff> errList){
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFFont font = workBook.createFont();//�����������
			font.setFontHeightInPoints((short) 12);//���������С      
			font.setFontName("����_GB2312");
			HSSFCellStyle cellStyle = workBook.createCellStyle();//�����е���ʽ����
			cellStyle.setFont(font);
			HSSFSheet sheet = workBook.createSheet("Page");//ʹ��workbook���󴴽�sheet����
			HSSFRow rowsTitle = sheet.createRow((short) 0);
			HSSFCell cellTitle = rowsTitle.createCell(0);
			cellTitle.setCellStyle(cellStyle);
			cellTitle.setCellValue("����");
			HSSFCell cellTitle1 = rowsTitle.createCell(1);
			cellTitle1.setCellStyle(cellStyle);
			cellTitle1.setCellValue("Ա��");
			HSSFCell cellTitle2 = rowsTitle.createCell(2);
			cellTitle2.setCellStyle(cellStyle);
			cellTitle2.setCellValue("�ֻ�����");
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
	 * �����ʵ���Ϣ
	 * @param salaryCommonTemplate
	 * @param oaSalaryImportTemplates
	 * @return
	 */
	private  HSSFWorkbook createSalWorkbook(List<ErrorInfo> errList){
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFFont font = workBook.createFont();//�����������
			font.setFontHeightInPoints((short) 12);//���������С      
			font.setFontName("����_GB2312");
			HSSFCellStyle cellStyle = workBook.createCellStyle();//�����е���ʽ����
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//���Ҿ���    
			HSSFSheet sheet = workBook.createSheet("Page");//ʹ��workbook���󴴽�sheet����
			
			HSSFRow rowsTitle = sheet.createRow((short) 0);
			HSSFCell cellTitle = rowsTitle.createCell(0);
			cellTitle.setCellStyle(cellStyle);
			cellTitle.setCellValue("�ֻ�����");
			HSSFCell cellTitle1 = rowsTitle.createCell(1);
			cellTitle1.setCellStyle(cellStyle);
			cellTitle1.setCellValue("����ԭ��");
			for (int i = 0; i < errList.size(); i++) {
				//�����еĿ�� 
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
		
		List<String> fieldName=new ArrayList<String>();  //excel���ݱ���
		for(int i=0;i<oaSalaryImportTemplates.size();i++) {
			fieldName.add(oaSalaryImportTemplates.get(i).getName());
		}
		//ȷ��sheet�����
		HSSFCellStyle cellStyle = workBook.createCellStyle();//�����е���ʽ����
			HSSFSheet sheet = workBook.createSheet("Page " );//ʹ��workbook���󴴽�sheet����
			HSSFRow headRow = sheet.createRow((short) 0); //�����У�0��ʾ��һ�У�������excel�ı��⣩
			HSSFRow headRow1 = sheet.createRow((short) 1); //�����У�1��ʾ�ڶ��У�������excel���ݵ�ʾ����
			sheet.setColumnWidth((short) 0, (short) 3000) ;
			sheet.setColumnWidth((short) 1, (short) 3000) ;
			sheet.setColumnWidth((short) 2, (short) 3000) ;
			sheet.setColumnWidth((short) 3, (short) 3000) ;
			sheet.setColumnWidth((short) 4, (short) 3000) ;
			HSSFFont font = workBook.createFont();//�����������
			font.setFontHeightInPoints((short) 12);//���������С      
			font.setFontName("����_GB2312");
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
			//���ñ���
			int comSize =  salaryCommonTemplate.size();
			for (int j =comSize; j < fieldName.size()+comSize; j++) {//ѭ��excel�ı���
				HSSFCell cell = headRow.createCell( (short) j);//ʹ���ж��󴴽��ж���0��ʾ��1��
				//�����еĿ��/
				sheet.setColumnWidth((short) j, (short) 3000) ;
				if(fieldName.get(j-comSize) != null){
					//�������õ���ʽ���õ���Ӧ�ĵ�Ԫ����
					cell.setCellStyle(cellStyle);
					cell.setCellValue((String) fieldName.get(j-comSize));//Ϊ�����еĵ�Ԫ������ֵ
				}else{
					cell.setCellValue("-");
				}
				
			}
			
		return workBook;
	}
	/**
	 * Ա����Ϣģ��
	 */
	@Override
	public void staffExport(HttpServletRequest request,HttpServletResponse response) {
		HSSFWorkbook workBook = new HSSFWorkbook();
		List<String> fieldName=new ArrayList<String>();  //excel���ݱ���
		fieldName.add("����");
		fieldName.add("����");
		fieldName.add("�ֻ�����");
		//ȷ��sheet�����
		HSSFCellStyle cellStyle = workBook.createCellStyle();//�����е���ʽ����
		HSSFDataFormat format = workBook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
			HSSFSheet sheet = workBook.createSheet("Page " );//ʹ��workbook���󴴽�sheet����
			HSSFRow headRow = sheet.createRow((short) 0); //�����У�0��ʾ��һ�У�������excel�ı��⣩
			sheet.setColumnWidth( 0, (short) 3000) ;
			sheet.setColumnWidth( 1, (short) 3000) ;
			sheet.setColumnWidth( 2, (short) 3000) ;
			sheet.setDefaultColumnStyle(0,cellStyle);
			sheet.setDefaultColumnStyle(1,cellStyle);
			sheet.setDefaultColumnStyle(2,cellStyle);
			HSSFFont font = workBook.createFont();//�����������
			font.setFontHeightInPoints((short) 12);//���������С      
			font.setFontName("����_GB2312");
			cellStyle.setFont(font);
			//���ñ���
			for (int j = 0; j < fieldName.size(); j++) {//ѭ��excel�ı���
				HSSFCell cell = headRow.createCell( (short) j);//ʹ���ж��󴴽��ж���0��ʾ��1��
				//�����еĿ��/
				sheet.setColumnWidth((short) j, (short) 3000) ;
				if(fieldName.get(j) != null){
					//�������õ���ʽ���õ���Ӧ�ĵ�Ԫ����
					cell.setCellStyle(cellStyle);
					cell.setCellValue((String) fieldName.get(j));//Ϊ�����еĵ�Ԫ������ֵ
				}else{
					cell.setCellValue("-");
				}
				
			}
			ServletOutputStream os;
			try {
				String name="Ա����Ϣ����ģ��"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
				String userAgent = request.getHeader("user-agent").toLowerCase();  
				  
				if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
				        // win10 ie edge ����� ������ϵͳ��ie  
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
	 * ����
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<SalaryCustomTemplate> oaSalaryImportTemplates){
		 Collections.sort(oaSalaryImportTemplates, new Comparator<SalaryCustomTemplate>() {  
			  
	            @Override  
	            public int compare(SalaryCustomTemplate o1, SalaryCustomTemplate o2) {  
	                //��������  
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
	 * ����2
	 * @param salaryCommonTemplate
	 */
	private static void sort1(List<SalaryCommonTemplate> salaryCommonTemplate){
		 Collections.sort(salaryCommonTemplate, new Comparator<SalaryCommonTemplate>() {  
			  
	            @Override  
	            public int compare(SalaryCommonTemplate o1, SalaryCommonTemplate o2) {  
	                //��������  
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
		// �����ֵ���String�������������1����1.0�����
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date d = cell.getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = formater.format(d);
				cell.setCellValue(cellValue);
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
			
		}
		// �ж����ݵ�����
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // ����
			cellValue = String.valueOf(cell.getNumericCellValue());

			break;
		case Cell.CELL_TYPE_STRING: // �ַ���
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // ��ʽ
			cellValue =NumberToTextConverter.toText(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK: // ��ֵ
			cellValue = "-";
			break;
		case Cell.CELL_TYPE_ERROR: // ����
			cellValue = "�Ƿ��ַ�";
			break;

		default:
			cellValue = "δ֪����";
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
     * ���ļ�ת����byte����
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
        //����ת��
        Integer pnum=Integer.valueOf(1);
        Integer psize=Integer.valueOf(1);
        //����PageHelper��ȡ��1ҳ��10�����ݣ�Ĭ�ϲ�ѯ����count
        PageHelper.startPage(pnum,psize);
		return salaryMapper.getOldData();
	}


	@Override
	public List<SalaryOld> getOldUpLoadLog(String companyId) {
//		int count = salaryMapper.getCount();
//        //����ת��
//        Integer pnum=Integer.valueOf(1);
//        Integer psize=Integer.valueOf(1);
//        //����PageHelper��ȡ��1ҳ��10�����ݣ�Ĭ�ϲ�ѯ����count
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
	    	        		logger.info("������ϢǨ����=========>>>>>>>");
	    	            	List<SalaryImport> importList = new ArrayList<SalaryImport>();
	    	        		String specialInfoJson =""; 
	    	            	Map<String, List<SalaryImportOld>> resultMap= new HashMap<String,List<SalaryImportOld>>(); // ����Ҫ�Ľ��
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
	    	            		Map<String, Object> TRevenue = new HashMap<String, Object>();//����ϼ�
	    	            		Map<String, Object> TExpenditure = new HashMap<String, Object>();//֧���ϼ�
	    	            		Map<String, Object> SDeduction = new HashMap<String, Object>();//ר��۳�
	    	            		Map<String, Object> UExpenditure = new HashMap<String, Object>();//��λ֧��
	    	                	SalaryImport salaryImport = new SalaryImport();
	    	                    System.out.println(entry.getKey() + " ==> " + entry.getValue());
	    	                    List<SalaryImportOld> salaryImportOlds = entry.getValue();
	    	                    for (int k = 0; k < salaryImportOlds.size(); k++) {
	    	                    	String templateColName = salaryImportOlds.get(k).getTemplateColName();
	    	                    	String importAmount = salaryImportOlds.get(k).getImportAmount();
	    	        				if(templateColName.equals("ʵ������")) {
	    	        					salaryImport.setRealIncome(importAmount);
	    	        				}else if (templateColName.equals("����ϼ�")) {
	    	        					salaryImport.setTotalRevenue(importAmount);
	    	        				}else if (templateColName.equals("֧���ϼ�")) {
	    	        					salaryImport.setTotalExpenditure(importAmount);
	    	        				}else if (templateColName.equals("ר��ӿ۳�")) {
	    	        					salaryImport.setSpecialDeduction(importAmount);
	    	        				}else if (templateColName.equals("��λ֧��")) {
	    	        					salaryImport.setUnitExpenditure(importAmount);
	    	        				}else if (templateColName.equals("��ע")) {
	    	        					salaryImport.setSalaryRemark(importAmount);
	    	        				}
	    	        				//���Ի���Ϣ�����װΪjson {"ʵ������":{"area":"����","smsCheckType":"white"},"����ϼ�":{"initTotal":"0","whiteEffect":"1"}}
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
	    			logger.info("������ϢǨ�����=========>>>>>>>");
	    			return true;
	    		}else {
		    		logger.info("û�й�����Ϣ��ҪǨ��=========>>>>>>>");
		    		return true;
		    	}
	    	}
	    	logger.info("������ϢǨ��,��˾id�Ѵ���Ǩ�����=========>>>>>>>");
			return true;
		} catch (Exception e) {
			logger.error("��˾:"+companyId+",������ϢǨ�Ƴ���",e);
			return false;
		}
    	
	}

    private static String dateToString(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//���ڸ�ʽ
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
