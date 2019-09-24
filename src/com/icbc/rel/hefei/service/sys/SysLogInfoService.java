package com.icbc.rel.hefei.service.sys;


import java.util.ArrayList;
import java.util.Date;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.SysLogDataInfoDao;
import com.icbc.rel.hefei.dao.SysLogOperationDao;
import com.icbc.rel.hefei.dao.SysLogTableInfoDao;
import com.icbc.rel.hefei.entity.FieldMeta;
import com.icbc.rel.hefei.entity.SysLogDataInfo;
import com.icbc.rel.hefei.entity.SysLogOperation;
import com.icbc.rel.hefei.entity.SysLogTableInfo;
import com.icbc.rel.hefei.util.CommonUtil;




@Service
public class SysLogInfoService {
	private static final Logger logger = Logger.getLogger(SysLogInfoService.class);
	@Autowired
	private SysLogDataInfoDao datalog;
	@Autowired
	private SysLogTableInfoDao tablelog;
	@Autowired
	private SysLogOperationDao operatelog;

	/*
	  * 查询平台操作日志
	  */
	 public List<SysLogOperation> getlog(Date startDate,Date endDate,int type,String mpuser,int page,int limit){
		return operatelog.getLogs(CommonUtil.DateConvertStr(startDate, "yyyy-MM-dd"),CommonUtil.DateConvertStr(endDate, "yyyy-MM-dd"),type,mpuser,(page-1)*limit,limit);
	 }
	 
	 /*
	  * 查询平台操作日志条数
	  */
	 public int getcount(Date startDate,Date endDate,int type,String mpuser){
		return operatelog.getcount(CommonUtil.DateConvertStr(startDate, "yyyy-MM-dd"),CommonUtil.DateConvertStr(endDate, "yyyy-MM-dd"),type,mpuser);
	 }
	 /*
	  * 获取日志表信息
	  */
	 public List<SysLogDataInfo> getDatalog(String logUid){
		 return datalog.getlog(logUid);
	 }
	 /*
	  * 获取数据变动信息
	  */
	 public SysLogTableInfo getTablelog(String logUid){
		 return tablelog.getlog(logUid);
	 }
	 
	 /*
	  * 转化数据日志接口
	  */
	 public void transforlog(Object obj,Object oldobj,int operateType,String sceneName,String mpName, String activityName,String operation) {
		 String logUid=UUID.randomUUID().toString().replaceFirst("-", "");
		 List<SysLogDataInfo> datas=new ArrayList<SysLogDataInfo>();
		 //构造操作日志类
		 SysLogOperation operate=new SysLogOperation();
		 operate.setActivityName(activityName);
		 operate.setCreateUser(mpName);
		 operate.setOperateType(operateType);
		 operate.setSceneName(sceneName);
		 operate.setOperation(operation);
		 operate.setCreateTime(new Date());
		 operate.setLogUid(logUid);
          //构造数据日志表类
		 SysLogTableInfo table=new SysLogTableInfo();
		 if(operateType==3) {
			 obj=oldobj;
		 }
		 Class objclass = obj.getClass();
		 logger.info(objclass.getName());
		 String name=objclass.getName();
		 table.setTableName(name.substring(name.lastIndexOf(".")+1));
		 table.setLogUid(logUid);
		 //构造数据明细日志
		 //利用反射机制获取字段名
	     Field[] at = objclass.getDeclaredFields();
	     for (Field fd : at) {
	        SysLogDataInfo item=new SysLogDataInfo();
	        item.setDataType(fd.getType().getName());
	        item.setLogUid(logUid);
	        item.setColumnName(fd.getName());//列名
	        fd.setAccessible(true); // 设置些属性是可以访问的
	        try {
				Object val = fd.get(obj);
				String valstr=(val==null?"null":val.toString());
				if(operateType==3) {
					Object oldval = fd.get(oldobj);
					String oldvalstr=(oldval==null?"null":oldval.toString());
					item.setOldValue(oldvalstr);
				}else {
				item.setValue(valstr);}
				if(fd.getName().equals("IID")) {
				   table.setPrimaryKey(CommonUtil.parseInteger(valstr));
				   continue;
		        }
				if(operateType==2) {
				Object oldval = fd.get(oldobj);
				String oldvalstr=(oldval==null?"null":oldval.toString());
				//只存储变更的数据
				if(oldvalstr.equals(valstr)) {
					continue;
				}
				item.setOldValue(oldvalstr);}
			    } catch (Exception ex) {
			    	logger.error("取值报错：",ex);
			    }
	        if (fd.isAnnotationPresent(FieldMeta.class)) {
	            //利用注解获取字段的含义
	            FieldMeta d = fd.getAnnotation(FieldMeta.class);
	            item.setColumnText(d.name());
	            datas.add(item);
	            }
	        }
	     if(operateType==2 && datas.size()==0) {
	    	 return;
	     }
	     
	        //保存日志到数据库
	        for(SysLogDataInfo item:datas) {
	        	datalog.insert(item);
	        }
	        tablelog.insert(table);
	        operatelog.insert(operate);
	 }
	 

	 
	 
	 
	 
}
