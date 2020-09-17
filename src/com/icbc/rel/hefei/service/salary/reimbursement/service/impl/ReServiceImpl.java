package com.icbc.rel.hefei.service.salary.reimbursement.service.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Case;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.portable.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.icbc.rel.hefei.dao.salary.SalaryUserMapper;
import com.icbc.rel.hefei.dao.salary.reimbursement.ReCommonMapper;
import com.icbc.rel.hefei.dao.salary.reimbursement.ReCustomMapper;
import com.icbc.rel.hefei.dao.salary.reimbursement.ReMapper;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.ErrorInfo;
import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryImport;
import com.icbc.rel.hefei.entity.salary.SalaryOld;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReImport;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReImportOld;
import com.icbc.rel.hefei.entity.salary.reimbursement.Reimbursement;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReimbursementOld;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReService;
import com.icbc.rel.hefei.service.salary.service.impl.SalaryServiceImpl;
import com.icbc.rel.hefei.util.DateUtils;
import com.icbc.rel.hefei.util.ExcelUtil;
import com.icbc.rel.hefei.util.MobileUtil;
import com.icbc.rel.hefei.util.SalaryExcelUtil;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.UUIDUtils;

/**
 * �ļ��ϴ�����
 * @author fc
 *
 */
@Transactional
@Service
public class ReServiceImpl implements ReService {
	private static Logger logger = Logger.getLogger(SalaryServiceImpl.class);
	@Autowired
	ReMapper reMapper;
	
	@Autowired
	ReCustomMapper reCustomMapper;
	@Autowired
	ReCommonMapper reCommonMapper;
	@Autowired
	SalaryUserMapper salaryUserMapper;
	
	/**
	 * �����ϴ���Excel�ļ�
	 */
//	@Override
//	public AjaxResult uploadSalary(File file,String companyId)throws FileNotFoundException, IOException, ParseException, NullPointerException {
//		String fileName = file.getName();
//        if(!(fileName.contains("xls"))){
//        	return AjaxResult.warn("��ʽ����!��֧��xls��ʽ�ļ�.");
//        }
//        //ָ���ļ����·�������������·�����߾���·��
//        String filePath = "./src/main/resources/templates/";
//        try {
//        	uploadFile(File2byte(file), filePath, fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//        //���ݹ�˾idȡ���ù�˾�����ģ����Ϣ
//        List<ReCustomTemplate> templateList = reMapper.getReTemplate(companyId);
//        List<String> staffMobList = salaryUserMapper.getMobByCompanyId(companyId);	
//        AjaxResult ajaxResult= read2003Excel(file,templateList,staffMobList);
//        int code = (int) ajaxResult.get("code");
//        if(code==500) {
//        	 return ajaxResult;
//        }else {
//        	Reimbursement reimbursement =(Reimbursement)ajaxResult.get("data");  
//        	reimbursement.setId(UUIDUtils.getGuid());
//        	if(reimbursement!=null) {
//        		reMapper.insertReimbursement(reimbursement);
//        		reMapper.insertReimbursementImport(reimbursement);
//        	}
//        	return ajaxResult;
//        }
//	}
	
	@Override
	public AjaxResult uploadRe1(String value, String companyId)throws FileNotFoundException, IOException, ParseException, NullPointerException {
		 List<ReCustomTemplate> templateList = reMapper.getReTemplate(companyId);
		 File serverFile=new File(value);
	     List<String> staffMobList = salaryUserMapper.getMobByCompanyId(companyId);	
		 AjaxResult ajaxResult= read2003Excel(serverFile,templateList,staffMobList);
	        int code = (int) ajaxResult.get("code");
	        if(code==500) {
	        	 return ajaxResult;
	        }else {
	        	Map<String,Object> map = (Map<String, Object>) ajaxResult.get("data");
	        	Reimbursement reimbursement =(Reimbursement)map.get("reimbursement");  
	        	if(reimbursement.getImportList()!=null&&reimbursement.getImportList().size()>0) {
	        		Long startTime = System.currentTimeMillis();
	        		logger.info("��ʼ���뱨����Ϣ-----------------------");
	        		reMapper.insertReimbursement(reimbursement);
	        		List<ReImport> importList = reimbursement.getImportList();
	        		int size = importList.size();
	        		int index = 0;
	        		int limit =3000;
	        		while(true) {
	        			if(index+limit>=size) {
	        				reMapper.insertReimbursementImport(importList.subList(index, size));
	        				break;
	        			}else {
	        				reMapper.insertReimbursementImport(importList.subList(index, index+limit));
	        				index = index+limit;
	        			}
	        			
	        		}
	        		Long endTime = System.currentTimeMillis();
	        		logger.info("���뱨����Ϣ���-----------------------");
	        		long count = endTime-startTime;
	        		logger.info("���뱨����Ϣ��ʱ:"+count+"����");
	        	}
	        	return ajaxResult;
	        }
	}
	/**
	 * ���汨����Ϣ
	 */
//	 @Override
//	public void insertReimbursement(Reimbursement reimbursement) {
//		  reMapper.insertReimbursement(reimbursement);
//		 reMapper.insertReimbursementImport(reimbursement);
//		}
	
	 	
	
	
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
				logger.error("uploadFile()", e);
			}
	    }
	  
		
		 /**
	     * ��ȡ office 2003 excel
		 * @param staffMobList 
	     * @throws IOException
	     * @throws FileNotFoundException 
		 * @throws ParseException */
	    private static AjaxResult read2003Excel(File file,List<ReCustomTemplate> templateList, List<String> staffMobList) throws FileNotFoundException, IOException, ParseException, NullPointerException {
	    		HSSFWorkbook hwb;
	    		Reimbursement oaRe =new Reimbursement();
	    		Map<String, Object> resultMap = new HashMap<String, Object>();
	    		List<ReImport>  oaReImportList = new ArrayList<ReImport>();
	    		List<ErrorInfo>  errorReList = new ArrayList<ErrorInfo>();
				hwb = new HSSFWorkbook(new FileInputStream(file));
				List<String[]> data = SalaryExcelUtil.ReadExcel(hwb, 0);
		        if((data.get(0).length-2)!=templateList.size()) {
		        	return AjaxResult.error("���������±�����ģ��������ϴ���");
		        }else {
			        for (int i = 0; i < (data.get(0).length-2); i++) {
						if(!data.get(0)[i+2].equals(templateList.get(i).getName())) {
							return AjaxResult.error("���������±�����ģ��������ϴ���");
						}
					}
		        }
		        if(data.size()>SessionParamConstant.rowsLimit) {
		        	return AjaxResult.error("�ù��ܽ�֧�ֵ�������ϴ�����������!");
		        }
		        oaRe.setImportTime(new Date());//����ʱ��
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			    Date issueTime;
			    issueTime = sdf.parse(data.get(1)[1]);
			    oaRe.setIssueTime(issueTime);//���ʷ���ʱ��
			    String fileName = file.getName();
			    String arr[] = fileName.split("\\.");
			    String excelName = arr[0];
			    oaRe.setExcelName(excelName);
			    List<Long> mobileList = new ArrayList<Long>();
			    //����excel������ѭ��
				String 	batchNo = UUIDUtils.getGuid();
				String companyId = templateList.get(0).getCompanyId();
			    for(int i = 1;i< data.size();i++){
					Map<String, Object> TReim = new LinkedHashMap<String, Object>();//������Ϣ
					ReImport oaReImport =new  ReImport();
					String mbl = data.get(i)[0];
			    	ErrorInfo eInfo  = new  ErrorInfo();
					if (StringUtils.isEmpty(mbl)) {
						return AjaxResult.error("��"+i+"���ֻ���Ϊ�գ�����д�������ϴ���");
					}
			    	long mobile = Long.valueOf(mbl);
					if(mobileList.contains(mobile)) {
						eInfo.setMobile(String.valueOf(mobile));
						eInfo.setReason("�ظ��ֻ����룡");
						errorReList.add(eInfo);
						continue;
					}else if(!MobileUtil.checkGeneralPhone(mbl)){
						eInfo.setMobile(String.valueOf(mobile));
						eInfo.setReason("�ֻ������ʽ����ȷ��");
						errorReList.add(eInfo);
						continue;
					}else {
						mobileList.add(mobile);
					}
/*					boolean flag = checkStaffIsExist(staffMobList,mobile);
					if(flag) {  
						eInfo.setMobile(String.valueOf(mobile));
						eInfo.setReason("�����ڸ��ֻ�����Ĺ���Ա����Ϣ��");
						errorReList.add(eInfo);
						continue;
					}*/
			    	//����ģ����Ϣȥȡ��Ҫ����Ϣ
			    	for(int j=0;j<templateList.size();j++) {
			    		//�к�
			    		int colIndex = templateList.get(j).getColIndex();
			    		String  name = data.get(0)[colIndex];
						String  value = data.get(i)[colIndex];
						if(StringUtils.isEmpty(value)) {
							value="0";
						}
						if(data.get(0)[colIndex].equals("�����ϼ�")) {
							oaReImport.setTotalReim(value);
						}else {
							TReim.put(name,value);
						}
			    	}
			    	if(TReim.size()>0) {
			    		oaReImport.setSpecialInfo(JSON.toJSON(TReim).toString());;
			    	}
						oaReImport.setBatchNo(batchNo);
						oaReImport.setCreateTime(new Date());
						oaReImport.setIssueTime(issueTime);
						oaReImport.setUserId(mbl);
						oaReImport.setCompanyId(companyId);
						oaReImportList.add(oaReImport);
			    }
			    oaRe.setCompanyId(companyId);
			    oaRe.setId(batchNo);
			    oaRe.setImportList(oaReImportList);
		 		resultMap.put("errorReList" , errorReList);
		 		resultMap.put("reimbursement" , oaRe);
		 		resultMap.put("mobileList" , mobileList);
		 		int rightRowsCount = oaReImportList.size();
		 		if(oaReImportList!=null&&oaReImportList.size()>0) {
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
	    private static Integer getCategory(List<ReCustomTemplate> templateList , String name)  {
	    	int category =0;
	    	for (int i = 0; i < templateList.size(); i++) {
				if(templateList.get(i).getName().equals(name)) {
					category = templateList.get(i).getCategory();
				}
			}
	    	return category;
	    }
	   
	    
	    

//	    /**
//	     * ��ȡOffice 2007 excel
//	     * */
//	    private static Reimbursement read2007Excel(File file,List<ReCustomTemplate> templateList)  {
//	    	XSSFWorkbook xwb;
//	        XSSFSheet sheet=null;
//	        Reimbursement oaSalary =new Reimbursement();
//	        List<ReImport>  oaSalaryImportList = new ArrayList<ReImport>();
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
//			    		ReImport oaSalaryImport =new  ReImport();
//			    		//�к�
//			    		int ColIndex = templateList.get(j).getColIndex();
//			    		cell = row.getCell(ColIndex);
//			    		oaSalaryImport.setImportAmount(cell.getStringCellValue());//����ֵ
//			    		oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(ColIndex).getStringCellValue());//����
//			    		oaSalaryImport.setReimbursementItemId(j);//Ԫ��id
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
		List<ReCustomTemplate> reImportTemplates =reMapper.getReTemplate(companyId);
		List<ReCommonTemplate>  reCommonTemplate=reCommonMapper.getCommonTemplate();
		sort(reImportTemplates);
		sort1(reCommonTemplate);
		ServletOutputStream os = null;
		try {
			String name="������ģ��"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
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
			createWorkbook(reCommonTemplate,reImportTemplates).write(os);
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
	private HSSFWorkbook createWorkbook(List<ReCommonTemplate> salaryCommonTemplate,List<ReCustomTemplate> oaSalaryImportTemplates){
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
			cell1.setCellValue(20200101);
			//���ñ���
			int comSize =  salaryCommonTemplate.size();
			for (int j =comSize; j < fieldName.size()+comSize; j++) {//ѭ��excel�ı���
				HSSFCell cell = headRow.createCell( (short) j);//ʹ���ж��󴴽��ж���0��ʾ��1��
				//�����еĿ��/
				sheet.setColumnWidth((short) j, (short) 3000);
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
	 * ����
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<ReCustomTemplate> reImportTemplates){
		 Collections.sort(reImportTemplates, new Comparator<ReCustomTemplate>() {  
			  
	            @Override  
	            public int compare(ReCustomTemplate o1, ReCustomTemplate o2) {  
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
	private static void sort1(List<ReCommonTemplate> reCommonTemplate){
		 Collections.sort(reCommonTemplate, new Comparator<ReCommonTemplate>() {  
			  
	            @Override  
	            public int compare(ReCommonTemplate o1, ReCommonTemplate o2) {  
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


	
	
	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
	         if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
	             return String.valueOf(hssfCell.getBooleanCellValue());
	         } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
	        	 DecimalFormat df = new DecimalFormat("0");
	        	 return df.format(hssfCell.getNumericCellValue());
	         } else {
	             return String.valueOf(hssfCell.getStringCellValue());
	         }
	     }


	@Override
	public List<Reimbursement> getUpLoadLog(Map<String, Object> paramsMap) {
		return reMapper.getUpLoadLog(paramsMap);
	}

	@Override
	public void delLog(String reId) {
	    reMapper.delLog(reId);
	    reMapper.delLog1(reId);
	}

	@Override
	public int delStaff(String userName, String companyId) {
		// TODO Auto-generated method stub
		return reMapper.delStaff(userName,companyId);
	}

	@Override
	public int updateMobile(String userName, String newUserName, String companyId) {
		// TODO Auto-generated method stub
		 return reMapper.updateMobile(userName,newUserName,companyId);
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
		public void exportErrReInfo(HttpServletRequest request, HttpServletResponse response, List<ErrorInfo> errList) {
			ServletOutputStream os = null;
			try {
				String name="���������ϴ���Ϣ"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
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
				createReWorkbook(errList).write(os);
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
		 * ����������Ϣ
		 * @param salaryCommonTemplate
		 * @param oaSalaryImportTemplates
		 * @return
		 */
		private  HSSFWorkbook createReWorkbook(List<ErrorInfo> errList){
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

		@Override
		public List<String> getExcelNameList(String companyId) {
			return reMapper.getExcelNameList(companyId);
		}
		@Override
		public List<ReimbursementOld> getOldData() {
			return reMapper.getOldData();
		}
		@Override
		public List<ReimbursementOld> getOldUpLoadLog(String companyId) {
			return reMapper.getOldUpLoadLog(companyId);
		}
		@Override
		public void addReCompanyId(String companyId) {
			reMapper.addReCompanyId(companyId);
		}
		@Transactional
		@Override
		public boolean reDM(String companyId) {
			try {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
		    	paramsMap.put("companyId", companyId);
		    	List<Reimbursement> reLists = reMapper.getUpLoadLog(paramsMap);
		    	if(reLists.size()==0||reLists.isEmpty()) {
		    		List<String> reIds  = reMapper.getReIds(companyId);
		    		if(reIds.size()!=0) {
		    			for (int ii = 0; ii < reIds.size(); ii++) {
		    				List<ReimbursementOld> reList = reMapper.getOldUpLoadLog(reIds.get(ii));
				           	if(reList.size()!=0&&!reList.isEmpty()) {
				           		logger.info("������ϢǨ����=========>>>>>>>");
					           	List<ReImport> importList = new ArrayList<ReImport>();
					           	Map<String, List<ReImportOld>> resultMap= new HashMap<String,List<ReImportOld>>(); // ����Ҫ�Ľ��
					           	for (int i = 0; i < reList.size(); i++) {
					           		List<ReImportOld> oldImportList = reList.get(i).getImportList();
					       			for (int j = 0; j < oldImportList.size(); j++) {
					    	   				if(resultMap.containsKey(oldImportList.get(j).getReimbursementId()+oldImportList.get(j).getUserId()+"#"+dateToString(reList.get(i).getIssueTime()))){
					    	   					resultMap.get(oldImportList.get(j).getReimbursementId()+oldImportList.get(j).getUserId()+"#"+dateToString(reList.get(i).getIssueTime())).add(oldImportList.get(j));
					    	   				}else{
					    	   					List<ReImportOld> list = new ArrayList<ReImportOld>();
					    	   					list.add(oldImportList.get(j));
					    	   					resultMap.put(oldImportList.get(j).getReimbursementId()+oldImportList.get(j).getUserId()+"#"+dateToString(reList.get(i).getIssueTime()),list);
					    	   				}
					    	   			}
					           		}
					       		  for (Map.Entry<String, List<ReImportOld>> entry : resultMap.entrySet()) {
					       			  	Date issueTime = java.sql.Date.valueOf(entry.getKey().split("#")[1]);
					       			  	Map<String, Object> TReim = new HashMap<String, Object>();//������Ϣ
					       			  	System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
					       	        	ReImport reImport = new ReImport();
					       	            System.out.println(entry.getKey() + " ==> " + entry.getValue());
					       	            List<ReImportOld> reImportOlds = entry.getValue();
					       	            for (int k = 0; k < reImportOlds.size(); k++) {
					       	            	String templateColName = reImportOlds.get(k).getTemplateColName();
					       	            	String importAmount = reImportOlds.get(k).getImportAmount();
					       					if(templateColName.equals("�����ϼ�")) {
					       						reImport.setTotalReim(importAmount);
					       					}else {
					    						TReim.put(templateColName,importAmount);
					    					}
					       					
					    			    	if(TReim.size()>0) {
					    			    		reImport.setSpecialInfo(JSON.toJSON(TReim).toString());;
					    			    	}
					    			    	reImport.setBatchNo(reImportOlds.get(0).getReimbursementId());
					    			    	reImport.setCreateTime(new Date());
					    			    	reImport.setIssueTime(issueTime);
					    			    	reImport.setUserId(String.valueOf(reImportOlds.get(0).getUserId()));
					    			    	reImport.setCompanyId(reImportOlds.get(0).getTemplateId());
					       				}
					       	            importList.add(reImport);
					       	        }
					           				
					       		
					        		int size = importList.size();
					        		int index = 0;
					        		int limit =3000;
					        		List<ReImport> importList1 = new ArrayList<ReImport>();
					        		while(true) {
					        			if(index+limit>=size) {
					        				importList1 = importList.subList(index, size);
					        				reMapper.insertReimbursementImport(importList1);
					        				break;
					        			}else {
					        				reMapper.insertReimbursementImport(importList.subList(index, index+limit));
					        				index = index+limit;
					        			}
					        			
					        		}
				           	}
		    			}
		    			reMapper.addReCompanyId(companyId);
		    			logger.info("������ϢǨ�����=========>>>>>>>");
		    			return true;
		    		}else {
		           		logger.info("û�б�����Ϣ��ҪǨ��=========>>>>>>>");
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
		public List<String> getNotNull() {
			// TODO Auto-generated method stub
			return reMapper.getNotNull();
		}
}
