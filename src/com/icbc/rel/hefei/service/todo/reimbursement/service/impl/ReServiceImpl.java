package com.icbc.rel.hefei.service.todo.reimbursement.service.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Case;
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

import com.icbc.rel.hefei.dao.todo.reimbursement.ReCommonMapper;
import com.icbc.rel.hefei.dao.todo.reimbursement.ReCustomMapper;
import com.icbc.rel.hefei.dao.todo.reimbursement.ReMapper;
import com.icbc.rel.hefei.entity.todo.reimbursement.ReCommonTemplate;
import com.icbc.rel.hefei.entity.todo.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.entity.todo.reimbursement.ReImport;
import com.icbc.rel.hefei.entity.todo.reimbursement.Reimbursement;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.service.todo.reimbursement.service.ReService;
import com.icbc.rel.hefei.util.DateUtils;
import com.icbc.rel.hefei.util.UUIDUtils;

/**
 * �ļ��ϴ�����
 * @author fc
 *
 */
@Transactional
@Service
public class ReServiceImpl implements ReService {
	@Autowired
	ReMapper reMapper;
	
	@Autowired
	ReCustomMapper reCustomMapper;
	@Autowired
	ReCommonMapper reCommonMapper;
	
	/**
	 * �����ϴ���Excel�ļ�
	 */
	@Override
	public AjaxResult uploadSalary(File file,String companyId)throws FileNotFoundException, IOException, ParseException, NullPointerException {
		String fileName = file.getName();
        if(!(fileName.contains("xls"))){
        	return AjaxResult.warn("��ʽ����!��֧��xls��ʽ�ļ�.");
        }
        //ָ���ļ����·�������������·�����߾���·��
        String filePath = "./src/main/resources/templates/";
        try {
        	uploadFile(File2byte(file), filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        //���ݹ�˾idȡ���ù�˾�����ģ����Ϣ
        List<ReCustomTemplate> templateList = reMapper.getReTemplate(companyId);
        AjaxResult ajaxResult= read2003Excel(file,templateList);
        int code = (int) ajaxResult.get("code");
        if(code==500) {
        	 return ajaxResult;
        }else {
        	Reimbursement reimbursement =(Reimbursement)ajaxResult.get("data");  
        	reimbursement.setId(UUIDUtils.getGuid());
        	if(reimbursement!=null) {
        		reMapper.insertReimbursement(reimbursement);
        		reMapper.insertReimbursementImport(reimbursement);
        	}
        	return ajaxResult;
        }
	}
	
	
	/**
	 * ���湤����Ϣ
	 */
	 @Override
	public void insertReimbursement(Reimbursement reimbursement) {
		  reMapper.insertReimbursement(reimbursement);
		 reMapper.insertReimbursementImport(reimbursement);
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
	    private static AjaxResult read2003Excel(File file,List<ReCustomTemplate> templateList) throws FileNotFoundException, IOException, ParseException, NullPointerException {
	    	HSSFWorkbook hwb;
	        HSSFSheet sheet=null;
	        Reimbursement oaSalary =new Reimbursement();
	        List<ReImport>  oaSalaryImportList = new ArrayList<ReImport>();
				hwb = new HSSFWorkbook(new FileInputStream(file));
			    sheet = hwb.getSheetAt(0);
			    HSSFRow row = null;
			    HSSFCell cell = null;
			    oaSalary.setImportTime(new Date());//����ʱ��
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			    Date issueTime;
			    issueTime = sdf.parse(String.valueOf((int)sheet.getRow(1).getCell(1).getNumericCellValue()));
			    oaSalary.setIssueTime(issueTime);//���ʷ���ʱ��
			    String fileName = file.getName();
			    String arr[] = fileName.split("\\.");
			    String excelName = arr[0];
			    oaSalary.setExcelName(excelName);
			    List<Long> mobileList = new ArrayList<Long>();
			    //����excel������ѭ��
			    for(int i = 1;i<= sheet.getLastRowNum();i++){
			    	String companyId = templateList.get(0).getCompanyId();
			    	row = sheet.getRow(i);
			    	long mobile = (long) row.getCell(0).getNumericCellValue();
					if(mobileList.contains(mobile)) {
						return AjaxResult.error("�ֻ���:"+mobile+"��excel�����ظ�!");
					}else {
						mobileList.add(mobile);
					}
			    	//����ģ����Ϣȥȡ��Ҫ����Ϣ
			    	for(int j=0;j<templateList.size();j++) {
			    		ReImport oaSalaryImport =new  ReImport();
			    		//�к�
			    		int colIndex = templateList.get(j).getColIndex();
			    		cell = row.getCell(colIndex);
			    		switch (cell.getCellType()) {
				        	case Cell.CELL_TYPE_NUMERIC:
				        		Double toBeString =cell.getNumericCellValue();
				        		String temp= String.format("%.2f",toBeString);
				        		oaSalaryImport.setImportAmount(temp);//����ֵ
				        		break;
				        	case Cell.CELL_TYPE_STRING:
				        		oaSalaryImport.setImportAmount(cell.getStringCellValue());//����ֵ
				        		break;
				        	default:
				        		return AjaxResult.error("excel��"+(colIndex+1)+"�е�"+(i+1)+"�и�ʽ����,������ٴε���!");
//				        		oaSalaryImport.setImportAmount("��ʽ����");
//				        		break;
						}
			    		oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(colIndex).getStringCellValue());//����
			    		oaSalaryImport.setReimbursementItemId(j);//Ԫ��id
			    		oaSalaryImport.setTemplateColType(templateList.get(j).getType());//�ֶ�����
			    		oaSalaryImport.setTemplateId(templateList.get(j).getCompanyId());//��˾id��ģ��idһ��
			    		oaSalaryImport.setUserId((long) row.getCell(0).getNumericCellValue());//Ա�����(�̶��Ҿ����ĳһ��)
			    		oaSalaryImport.setColIndex(colIndex);
			    		int category = getCategory(templateList,sheet.getRow(0).getCell(colIndex).getStringCellValue());
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
	    private static Integer getCategory(List<ReCustomTemplate> templateList , String name)  {
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
	    private static Reimbursement read2007Excel(File file,List<ReCustomTemplate> templateList)  {
	    	XSSFWorkbook xwb;
	        XSSFSheet sheet=null;
	        Reimbursement oaSalary =new Reimbursement();
	        List<ReImport>  oaSalaryImportList = new ArrayList<ReImport>();
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
			    		ReImport oaSalaryImport =new  ReImport();
			    		//�к�
			    		int ColIndex = templateList.get(j).getColIndex();
			    		cell = row.getCell(ColIndex);
			    		oaSalaryImport.setImportAmount(cell.getStringCellValue());//����ֵ
			    		oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(ColIndex).getStringCellValue());//����
			    		oaSalaryImport.setReimbursementItemId(j);//Ԫ��id
			    		oaSalaryImport.setTemplateColType(templateList.get(j).getType());//�ֶ�����
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
	public void export(HttpServletResponse response,String companyId) {
		// TODO Auto-generated method stub
		List<ReCustomTemplate> reImportTemplates =reMapper.getReTemplate(companyId);
		List<ReCommonTemplate>  reCommonTemplate=reCommonMapper.getCommonTemplate();
		sort(reImportTemplates);
		sort1(reCommonTemplate);
		ServletOutputStream os = null;
		try {
			String name="������ģ��"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
			 response.setContentType("text/html;charset=UTF-8");
			 response.addHeader("content-type", "application/x-msdownload");
			 response.addHeader("Content-Disposition", "attachment;filename="+ new String(name.getBytes(), "iso-8859-1"));
			os = response.getOutputStream();
			createWorkbook(reCommonTemplate,reImportTemplates).write(os);
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
	public void delLog(String salaryId) {
	    reMapper.delLog(salaryId);
	    reMapper.delLog1(salaryId);
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
	            e.printStackTrace();
	        }catch (IOException e){
	            e.printStackTrace();
	        }
	        return buffer;
	    }
	
	  
	  
	  
}
