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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Case;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.dao.salary.SalaryCommonMapper;
import com.icbc.rel.hefei.dao.salary.SalaryCustomMapper;
import com.icbc.rel.hefei.dao.salary.SalaryMapper;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryCommonTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryImport;
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

/**
 * �ļ��ϴ�����
 * @author fc
 *
 */
@Transactional
@Service
public class SalaryServiceImpl implements SalaryService {
	@Autowired
	SalaryMapper salaryMapper;
	
	@Autowired
	SalaryCustomMapper salaryCustomMapper;
	@Autowired
	SalaryCommonMapper salaryCommonMapper;
	
	/**
	 * �����ϴ���Excel�ļ�
	 */
	@Override
	public AjaxResult uploadSalary(File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException {
		String fileName =  file.getName();
        if(!(fileName.contains("xls"))){
        	return AjaxResult.warn("��ʽ����!��֧��xls��ʽ�ļ�.");
        }
        //ָ���ļ����·�������������·�����߾���·��
        String filePath = "./src/main/resources/templates/";
        try {
//			uploadFile(File2byte(file), filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        File serverFile=new File(filePath+fileName);
        //���ݹ�˾idȡ���ù�˾�����ģ����Ϣ
        List<SalaryCustomTemplate> templateList = salaryMapper.getSalaryTemplate(companyId);
        AjaxResult ajaxResult= read2003Excel(serverFile,templateList);
        int code = (int) ajaxResult.get("code");
        if(code==500) {
        	 return ajaxResult;
        }else {
        	Salary oaSalary = (Salary)ajaxResult.get("data");  
        	oaSalary.setId(UUIDUtils.getGuid());
        	if(oaSalary!=null) {
        		salaryMapper.insertOaSalary(oaSalary);
        		salaryMapper.insertOaSalaryImport(oaSalary);
        	}
        	 return ajaxResult;
        }
       
	}
	

	@Override
	public AjaxResult uploadSalary1(String value, String companyId)throws FileNotFoundException, IOException, ParseException, NullPointerException {
		 List<SalaryCustomTemplate> templateList = salaryMapper.getSalaryTemplate(companyId);
		 File serverFile=new File(value);
		 AjaxResult ajaxResult= read2003Excel(serverFile,templateList);
	        int code = (int) ajaxResult.get("code");
	        if(code==500) {
	        	 return ajaxResult;
	        }else {
	        	Salary oaSalary = (Salary)ajaxResult.get("data");  
	        	oaSalary.setId(UUIDUtils.getGuid());
	        	if(oaSalary!=null) {
	        		salaryMapper.insertOaSalary(oaSalary);
	        		salaryMapper.insertOaSalaryImport(oaSalary);
	        	}
	        	 return ajaxResult;
	        }
	}
	
	
	/**
	 * �ϴ�Ա����Ϣ
	 */
	@Override
	public  List<SalaryStaff> uploadStaff(File file, String companyId) {
		HSSFWorkbook hwb;
		List<SalaryStaff>  staffList = new ArrayList<SalaryStaff>();
		try {
			hwb = new HSSFWorkbook(new FileInputStream(file));
			List<String[]> data = SalaryExcelUtil.ReadExcel(hwb, 0);
		    for(int i = 1;i< data.size();i++){
		    	SalaryStaff salaryStaff =new SalaryStaff();
		    	String[] row = data.get(i);
		    	salaryStaff.setCompanyId(companyId);
		    	salaryStaff.setDept(row[0]);
		    	salaryStaff.setName(row[1]);
		    	salaryStaff.setMobile(row[2]);
		    	staffList.add(salaryStaff);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
/*		String fileName = file.getName();
        //ָ���ļ����·�������������·�����߾���·��
        String filePath = "./src/main/resources/templates/";
        try {
			uploadFile(File2byte(file), filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        File serverFile=new File(filePath+fileName);
        HSSFWorkbook hwb;
        HSSFSheet sheet=null;
        List<SalaryStaff>  staffList = new ArrayList<SalaryStaff>();
        try {
			hwb = new HSSFWorkbook(new FileInputStream(serverFile));
		    sheet = hwb.getSheetAt(0);
		    HSSFRow row = null;
		    int temp =sheet.getLastRowNum();
		    //����excel������ѭ��
		    for(int i = 1;i<= temp;i++){
		    	SalaryStaff salaryStaff =new SalaryStaff();
		    	row = sheet.getRow(i);
		    	salaryStaff.setCompanyId(companyId);
		    	salaryStaff.setDept(getCellValue(row.getCell(0)));
		    	salaryStaff.setName(getCellValue(row.getCell(1)));
		    	salaryStaff.setMobile(getCellValue(row.getCell(2)));
		    	staffList.add(salaryStaff);
		    }
        } catch (Exception e) {
			e.printStackTrace();
		}*/
        return staffList;
	}
	
	
	
	 /**
	  * ����Ա����Ϣ
	  */
	 	@Override
	 	public AjaxResult insertStaffInfo(List<SalaryStaff> staffList, String companyId) {
	 		//�жϵ�����ֻ����Ƿ��ظ�
	 		for (int i = 0; i < staffList.size(); i++) {
	 			for (int j = staffList.size()-1; j >i; j--) {
	 				String  sPhone = staffList.get(i).getMobile();
	 				String  ePhone = staffList.get(j).getMobile();
	 	           if  (ePhone.equals(sPhone))  {  
	 	        	  return AjaxResult.warn("Excel���ֻ���:"+ePhone+"�ظ�,�������!");
	 	            } 
				}
			}
	 		List<SalaryStaff> mobileList = salaryMapper.getMobileList(companyId);
	 		for (int i = 0; i < mobileList.size(); i++) {
				for (int j = 0; j < staffList.size(); j++) {
					if(mobileList.get(i).getMobile().equals(staffList.get(j).getMobile())) {
//						return AjaxResult.warn("�ֻ���:"+mobileList.get(i).getMobile()+"�Ѿ�����");
						//ȥ���Ѵ��ڵ�Ա���ֻ�����
						staffList.remove(j);
						j--;
						continue;
					}
					
					//�ֻ������ʽУ��,���˷��ֻ���
					if(!MobileUtil.checkGeneralPhone(staffList.get(j).getMobile())) {
						staffList.remove(j);
						j--;
					}
					
				}
			}
	 		if(staffList.size()>0) {
	 			salaryMapper.insertStaffInfo(staffList);
	 		}
	 		return AjaxResult.success("�ϴ��ɹ�����������"+staffList.size()+"��Ա���ֻ����롣",staffList);
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
				e.printStackTrace();
			}
	    }
	  
		
		 /**
	     * ��ȡ office 2003 excel
	     * @throws IOException
	     * @throws FileNotFoundException 
		 * @throws ParseException */
	    private static AjaxResult read2003Excel(File file,List<SalaryCustomTemplate> templateList) throws FileNotFoundException, IOException, ParseException, NullPointerException {
	    	HSSFWorkbook hwb;
	        Salary oaSalary =new Salary();
	        List<SalaryImport>  oaSalaryImportList = new ArrayList<SalaryImport>();
	        List<Long> mobileList = new ArrayList<Long>();
	        hwb = new HSSFWorkbook(new FileInputStream(file));
	        List<String[]> data = SalaryExcelUtil.ReadExcel(hwb, 0);
	        if((data.get(0).length-2)!=templateList.size()) {
	        	return AjaxResult.error("�ϴ��ļ����ݸ�ʽ����ȷ��");
	        }else {
		        for (int i = 0; i < (data.get(0).length-2); i++) {
					if(!data.get(0)[i+2].equals(templateList.get(i).getName())) {
						return AjaxResult.error("�ϴ��ļ����ݸ�ʽ����ȷ��123");
					}
				}
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
			for(int i = 1;i< data.size();i++){
				long mobile = Long.valueOf(data.get(i)[0]);
				if(mobileList.contains(mobile)) {
					return AjaxResult.error("�ֻ���:"+mobile+"��excel�����ظ�!");
				}else {
					mobileList.add(mobile);
				}
				//����ģ����Ϣȥȡ��Ҫ����Ϣ
				for(int j=0;j<templateList.size();j++) {
					SalaryImport oaSalaryImport =new  SalaryImport();
					//�к�
					int colIndex = templateList.get(j).getColIndex();
					oaSalaryImport.setImportAmount(data.get(i)[colIndex]);//����ֵ
					oaSalaryImport.setTemplateColName(data.get(0)[colIndex]);//����
					oaSalaryImport.setSalaryItemId(j);//Ԫ��id
					oaSalaryImport.setTemplateId(templateList.get(j).getCompanyId());//��˾id��ģ��idһ��
					oaSalaryImport.setUserId(mobile);//Ա�����(�̶��Ҿ����ĳһ��)
					oaSalaryImport.setColIndex(colIndex);
					int category = getCategory(templateList,data.get(0)[colIndex]);
					oaSalaryImport.setCategory(category);
					oaSalaryImportList.add(oaSalaryImport);
			    	}
			    }
	        oaSalary.setImportList(oaSalaryImportList);
	        return AjaxResult.success("����ɹ�!", oaSalary);
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
	    private static Salary read2007Excel(File file,List<SalaryCustomTemplate> templateList)  {
	    	XSSFWorkbook xwb;
	        XSSFSheet sheet=null;
	    	Salary oaSalary =new Salary();
	        List<SalaryImport>  oaSalaryImportList = new ArrayList<SalaryImport>();
	        try {
	        	xwb = new XSSFWorkbook(new FileInputStream(file));
				sheet = xwb.getSheetAt(0);
				XSSFRow row = null;
			    XSSFCell cell = null;
			    oaSalary.setImportTime(new Date());//����ʱ��
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			    Date issueTime;
			    issueTime = sdf.parse(String.valueOf((int)sheet.getRow(1).getCell(4).getNumericCellValue()));
			    oaSalary.setIssueTime(issueTime);//���ʷ���ʱ��
			    //����excel������ѭ��
			    for(int i = 1;i<= sheet.getLastRowNum();i++){
			    	row = sheet.getRow(i);
			    	//����ģ����Ϣȥȡ��Ҫ����Ϣ
			    	for(int j=0;j<templateList.size();j++) {
			    		SalaryImport oaSalaryImport =new  SalaryImport();
			    		//�к�
			    		int ColIndex = templateList.get(j).getColIndex();
			    		cell = row.getCell(ColIndex);
			    		oaSalaryImport.setImportAmount(cell.getStringCellValue());//����ֵ
			    		oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(ColIndex).getStringCellValue());//����
			    		oaSalaryImport.setSalaryItemId(j);//Ԫ��id
			    		oaSalaryImport.setTemplateId(templateList.get(j).getCompanyId());//ģ��id
			    		oaSalaryImport.setUserId((long) row.getCell(2).getNumericCellValue());//Ա�����(�̶��Ҿ����ĳһ��)
			    		oaSalaryImportList.add(oaSalaryImport);
			    	}
			    }
	        } catch (Exception e) {
				e.printStackTrace();
			}
	        oaSalary.setImportList(oaSalaryImportList);
	        return oaSalary;
	    }
	    
	
	
	/**
	 * ģ�嵼��
	 */
	@Override
	public void export(HttpServletRequest request,HttpServletResponse response,String companyId) {
		// TODO Auto-generated method stub
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
			e.printStackTrace();
		}finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
			cell0.setCellValue(Long.valueOf("18088880000"));
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
				e.printStackTrace();
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
	            e.printStackTrace();
	        }catch (IOException e){
	            e.printStackTrace();
	        }
	        return buffer;
	    }

	@Override
	public void updateAddStaffInfo(SalaryStaff salaryStaff) {
		// TODO Auto-generated method stub
		salaryMapper.updateAddStaffInfo(salaryStaff);
	}

	  
	  
	  
}
