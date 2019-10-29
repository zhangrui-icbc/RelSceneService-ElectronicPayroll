package com.icbc.rel.hefei.service.salary.service.impl;


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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SystemConfigUtil;
import com.icbc.rel.hefei.util.UUIDUtils;

/**
 * 文件上传处理
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
	@Autowired
	private SysActivityService sysActivityService;
	
	/**
	 * 处理上传的Excel文件
	 */
	@Override
	public AjaxResult uploadSalary(/*HttpServletRequest request,*/File file,String companyId) throws FileNotFoundException, IOException, ParseException, NullPointerException {
		String fileName =  file.getName();
        if(!(fileName.contains("xls"))){
        	return AjaxResult.warn("格式错误!仅支持xls格式文件.");
        }
        //指定文件存放路径，可以是相对路径或者绝对路径
        String filePath = "./src/main/resources/templates/";
        try {
			uploadFile(File2byte(file), filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        File serverFile=new File(filePath+fileName);
        //根据公司id取出该公司对面的模板信息
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
        		//信使消息推送
        		   /*    		String activityUid = (String) request.getSession().getAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID);
        		SysActivityInfo activity = sysActivityService.getSceneByUid(activityUid);
     		//根据userid查出所有的ImUserId
        		String domainUrl = SystemConfigUtil.domainName;
        		net.sf.json.JSONArray articles = MessageHelper.GetArticles("您已成功下单", "民以食为天",
        				domainUrl + "RelSceneService/image/order/supper.jpg",
        				domainUrl + "RelSceneService/com/myOrderDetail?activityUid="+activityUid);
        		JSONObject obj = MessageHelper.getPicMessage(activity.getMpId(), ImUserId, "您已成功下单", "民以食为天",
        				articles);
        		MessageService.SendMessage(obj);
        		*/
        	}
        	 return ajaxResult;
        }
       
	}
	
	/**
	 * 上传员工信息
	 */
	@Override
	public  List<SalaryStaff> uploadStaff(File file, String companyId) {
		String fileName = file.getName();
        //指定文件存放路径，可以是相对路径或者绝对路径
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
		    //根据excel的行数循环
		    for(int i = 1;i<= temp;i++){
		    	SalaryStaff salaryStaff =new SalaryStaff();
		    	row = sheet.getRow(i);
		    	salaryStaff.setCompanyId(companyId);
		    	salaryStaff.setName(getValue(row.getCell(0)));
		    	salaryStaff.setMobile(getValue(row.getCell(1)));
		    	staffList.add(salaryStaff);
		    }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return staffList;
	}
	
	
	/**
	 * 保存工资信息
	 */
//	 @Override
//	public void insertSalaryInfo(Salary oaSalary) {
//		  salaryMapper.insertOaSalary(oaSalary);
//		 salaryMapper.insertOaSalaryImport(oaSalary);
//		}
	
	 /**
	  * 保存员工信息
	  */
	 	@Override
	 	public AjaxResult insertStaffInfo(List<SalaryStaff> staffList, String companyId) {
	 		List<SalaryStaff> mobileList = salaryMapper.getMobileList(companyId);
	 		for (int i = 0; i < mobileList.size(); i++) {
				for (int j = 0; j < staffList.size(); j++) {
					if(mobileList.get(i).getMobile().equals(staffList.get(j).getMobile())) {
						return AjaxResult.warn("手机号:"+mobileList.get(i).getMobile()+"已经存在");
					}
				}
			}
	 		salaryMapper.insertStaffInfo(staffList);
	 		return AjaxResult.success("导入成功!",staffList);
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
	     * 读取 office 2003 excel
	     * @throws IOException
	     * @throws FileNotFoundException 
		 * @throws ParseException */
	    private static AjaxResult read2003Excel(File file,List<SalaryCustomTemplate> templateList) throws FileNotFoundException, IOException, ParseException, NullPointerException {
	    	HSSFWorkbook hwb;
	        HSSFSheet sheet=null;
	        Salary oaSalary =new Salary();
	        List<SalaryImport>  oaSalaryImportList = new ArrayList<SalaryImport>();
	        List<Long> mobileList = new ArrayList<Long>();
	        hwb = new HSSFWorkbook(new FileInputStream(file));
			sheet = hwb.getSheetAt(0);
			HSSFRow row = null;
			HSSFCell cell = null;
			oaSalary.setImportTime(new Date());//创建时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date issueTime;
			issueTime = sdf.parse(String.valueOf((int)sheet.getRow(1).getCell(1).getNumericCellValue()));
			oaSalary.setIssueTime(issueTime);//工资发放时间
			String fileName = file.getName();
			String arr[] = fileName.split("\\.");
			String excelName = arr[0];
			oaSalary.setExcelName(excelName);
			//根据excel的行数循环
			for(int i = 1;i<= sheet.getLastRowNum();i++){
			//	String companyId = templateList.get(0).getCompanyId();
				row = sheet.getRow(i);
				long mobile = (long) row.getCell(0).getNumericCellValue();
				if(mobileList.contains(mobile)) {
					return AjaxResult.error("手机号:"+mobile+"在excel中有重复!");
				}else {
					mobileList.add(mobile);
				}
				//根据模板信息去取想要的信息
				for(int j=0;j<templateList.size();j++) {
					SalaryImport oaSalaryImport =new  SalaryImport();
					//列号
					int colIndex = templateList.get(j).getColIndex();
					cell = row.getCell(colIndex);
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							Double toBeString =cell.getNumericCellValue();
							String temp= String.format("%.2f",toBeString);
							oaSalaryImport.setImportAmount(temp);//具体值
							break;
						case Cell.CELL_TYPE_STRING:
							oaSalaryImport.setImportAmount(cell.getStringCellValue());//具体值
							break;
						default:
							return AjaxResult.error("excel第"+(colIndex+1)+"列第"+(i+1)+"行格式错误,请检查后再次导入!");
							//oaSalaryImport.setImportAmount("格式错误");
							//break;
				}
					oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(colIndex).getStringCellValue());//名称
					oaSalaryImport.setSalaryItemId(j);//元素id
					oaSalaryImport.setTemplateId(templateList.get(j).getCompanyId());//公司id与模板id一致
					oaSalaryImport.setUserId((long) row.getCell(0).getNumericCellValue());//员工编号(固定且具体的某一列)
					oaSalaryImport.setColIndex(colIndex);
					int category = getCategory(templateList,sheet.getRow(0).getCell(colIndex).getStringCellValue());
					oaSalaryImport.setCategory(category);
					oaSalaryImportList.add(oaSalaryImport);
			    	}
			    }
	        oaSalary.setImportList(oaSalaryImportList);
	        return AjaxResult.success("导入成功!", oaSalary);
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
			    oaSalary.setImportTime(new Date());//创建时间
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			    Date issueTime;
			    issueTime = sdf.parse(String.valueOf((int)sheet.getRow(1).getCell(4).getNumericCellValue()));
			    oaSalary.setIssueTime(issueTime);//工资发放时间
			    //根据excel的行数循环
			    for(int i = 1;i<= sheet.getLastRowNum();i++){
			    	row = sheet.getRow(i);
			    	//根据模板信息去取想要的信息
			    	for(int j=0;j<templateList.size();j++) {
			    		SalaryImport oaSalaryImport =new  SalaryImport();
			    		//列号
			    		int ColIndex = templateList.get(j).getColIndex();
			    		cell = row.getCell(ColIndex);
			    		oaSalaryImport.setImportAmount(cell.getStringCellValue());//具体值
			    		oaSalaryImport.setTemplateColName(sheet.getRow(0).getCell(ColIndex).getStringCellValue());//名称
			    		oaSalaryImport.setSalaryItemId(j);//元素id
			    		oaSalaryImport.setTemplateId(templateList.get(j).getCompanyId());//模板id
			    		oaSalaryImport.setUserId((long) row.getCell(2).getNumericCellValue());//员工编号(固定且具体的某一列)
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
	 * 模板导出
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
			String name="工资条模板"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
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
			cell0.setCellValue(Long.valueOf("18088880000"));
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
		fieldName.add("姓名 ");
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
				String name="人员导入模板"+DateUtils.parseDateToStr("yyyyMMddHHmmss",new Date())+".xls";
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
				e.printStackTrace();
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
	public void updatePwd(String userName, String companyId) {
		// TODO Auto-generated method stub
		salaryMapper.updatePwd(userName,companyId);
	}

	@Override
	public int getMobile(String userName) {
		// TODO Auto-generated method stub
		return salaryMapper.getMobile(userName);
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
	public SalaryStaff getStaffInfo(String userName, String companyId) {
		return salaryMapper.getStaffInfo(userName,companyId);
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
	public int delStaff(String userName, String companyId) {
		// TODO Auto-generated method stub
		return salaryMapper.delStaff(userName,companyId);
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
	            e.printStackTrace();
	        }catch (IOException e){
	            e.printStackTrace();
	        }
	        return buffer;
	    }
	  
	  
	  
}
