package com.icbc.rel.hefei.service.order;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.order.OrdImportInfoDao;
import com.icbc.rel.hefei.dao.order.OrdMenuInfoDao;
import com.icbc.rel.hefei.entity.order.OrdImportInfo;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.util.CommonUtil;
import com.icbc.rel.hefei.util.ExcelUtil;

@Service
public class ImportService {
	private static Logger logger = Logger.getLogger(ImportService.class);

	@Autowired
	private OrdImportInfoDao importInfoDao;
	@Autowired
	private OrdMenuInfoDao menuInfoDao;
	/*
	 * 查询文件导入记录（分页）
	 */
	public List<OrdImportInfo> getallFileInfo(String activityUid,int page,int limit){
		return importInfoDao.selectAll(activityUid,(page-1)*limit,limit);
	}
	/*
	 * 查询文件导入记录的条数（用于分页）
	 */
	public int getallFileInfoNum(String activityUid){
		List<Integer> result=importInfoDao.selectAllNum(activityUid);
		if(result!=null && result.size()>0) {
			return result.get(0)==null? 0:result.get(0);
		 }else
		 {
			 return 0;
		 }
	}
	/*
	 * 添加文件导入记录
	 */
	public void AddImportInfo(OrdImportInfo info) {
		importInfoDao.add(info);
	}
	
	/*
	 * 作废该条记录
	 */
	public void updateStatus(String fileUid) {
		importInfoDao.updateStatus(fileUid);
	}
	
	/*
	 * 上传外卖菜单
	 */
	public  String Readwmsupper(String filepath,String uid,String activityUid) throws IOException
	{
		Workbook workbook =ExcelUtil.getWorkBook(filepath);
		List<String[]> data = ExcelUtil.ReadExcel(workbook, 0);
		String str=checkExcel(data);
		if(str!=null) {
			return str;
		}
		List<OrdMenuInfo> dishes = new ArrayList<OrdMenuInfo>();
		String title=data.get(0)[0];
		 String sheetName=workbook.getSheetAt(0).getSheetName();
		String lastClassify = "";
		String lastSetmeal = "";
		int iid=1;
		for (int i = 3; i < data.size(); i++) {
			OrdMenuInfo dish = new OrdMenuInfo();
			String[] item = data.get(i);
			if (!item[1].equals("")) {
				lastSetmeal = "";
			}
			lastClassify = item[0].equals("") ? lastClassify : item[0];
			lastSetmeal = item[1].equals("") ? lastSetmeal : item[1];
			dish.setMenuUid(uid);
			dish.setActivityUid(activityUid);
			dish.setCreateTime(new Date());
			dish.setSetmealName("");
			dish.setSheetName(sheetName);
			dish.setTitle(title);
			dish.setClassifyName(lastClassify);
			dish.setMenuType(0);
/*			if(lastClassify.contains("套餐")) {
			dish.setSetmealName(lastSetmeal+i);}*/
			for (int k = 1; k < item.length; k++) {
				if(!data.get(2)[k].equals("菜品"))
				{
					continue;
				}
				String dishuid = UUID.randomUUID().toString().replaceAll("-", "");
				dish.setDishUid(dishuid);
				float price=0;
				int amount=0;
				String unit="";
				int limit=0;
				
				if(item.length>k+1 && data.get(2)[k+1].equals("价格")) {
					price=CommonUtil.parseFloat(item[k+1]);
				}
			
			   if(item.length>k+2 && data.get(2)[k+2].equals("份数")){
					amount=getAmount(item[k+2]);
					unit=getUnit(item[k+2]);
				}
				
				if(item.length>k+3 && data.get(2)[k+3].equals("预订上限"))
				{
					limit=CommonUtil.parseInteger(item[k+3]);
				}
				
				amount= amount==0? 1000000:amount;//如没有份数 默认一个较大值；没有金额默认0
				
				
				OrdMenuInfo info = (OrdMenuInfo) dish.clone();
				info.setDishPrice(price);
				info.setAmount(amount);
				info.setDishName(item[k].trim());
                info.setMenuLimit(limit);
                if(info.getDishName().length()>20) {
                	info.setDishName(info.getDishName().substring(0,20));
                }
                if(info.getClassifyName().length()>20) {
                	info.setClassifyName(info.getClassifyName().substring(0,20));
                }
				info.setDishUnit(unit);
				info.setMenuDate(CommonUtil.parseDate(data.get(1)[k]));
				if (!info.getDishName().equals("")) {
					dishes.add(info);
					info.setIID(iid);
					iid++;
				}
				
			}

		}

		dishes.sort((OrdMenuInfo x, OrdMenuInfo y) -> (x.getMenuDate().getTime() > y.getMenuDate().getTime() || 
				(x.getMenuDate().getTime() == y.getMenuDate().getTime() && x.getIID()>y.getIID()) )? 1 : -1);

		for (int i = 0; i < dishes.size(); i++) {
			menuInfoDao.add(dishes.get(i));
		}

		return null;
	}
	
	
	
	private int getAmount(String str) {
		if(str.indexOf("/")>-1) {
		str=str.substring(0,str.indexOf("/"));
		}
		return CommonUtil.parseInteger(str);
	}
	private String getUnit(String str) {
		if(str.indexOf("/")>-1) {
		str=str.substring(str.indexOf("/")+1);
		}else {
			str="份";
		}
		return str;
	}
	
	/*
	 * 校验列头
	 */
	private String checkExcel(List<String[]> data) {
		String result;
		String[] item = data.get(1);
		if(data.size()<3) {
			result="上传文件内容格式不正确！";
			return result;
		}
		
        if(data.size()>2 && item!=null) {
        	for(int i=1;i<item.length;i++) {
        		if(!item[i].equals("") && CommonUtil.parseDate(item[i])==null) {
        			result="上传菜单日期格式不正确！";
        			return result;
        		}
        	}
        }
		String[] item2 = data.get(2);
		String[] title= {"菜品","价格","份数","预订上限"};
		for(int i=1;i<item2.length;i++) {
    		if(!item2[i].equals(title[(i-1)%4])) {
    			result="上传文件第三行第"+(i+1)+"列不正确！每日菜单均需填写菜品、价格、份数、预订上限";
    			return result;
    		}
    	}
		return null;
	}

}
