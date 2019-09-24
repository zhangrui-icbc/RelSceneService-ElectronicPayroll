package com.icbc.rel.hefei.service.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.SysActivityInfoDao;
import com.icbc.rel.hefei.dao.SysSnDictionaryDao;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.SysSnDictionary;
import com.icbc.rel.hefei.util.CommonUtil;

@Service
public class SysService {
	
	@Autowired
	private SysSnDictionaryDao dao;
	
	
	
	
	/*
	 * 根据前缀日期生成流水号
	 */
	public String generateSn(String prefix,Date date,String activityUid) {
		List<SysSnDictionary> result=dao.getLock(prefix, CommonUtil.DateConvertStr(date, "yyyy-MM-dd"),activityUid);
		String sn=prefix+CommonUtil.DateConvertStr(date, "yyyyMMdd");
		if(result.size()==0) {
			SysSnDictionary item=new SysSnDictionary();
			item.setPrefix(prefix);
			item.setAvailableDate(date);
			item.setSerialNumber(1);
			item.setCreateTime(new Date());
			item.setActivityUid(activityUid);
			dao.insert(item);
			return sn+String.format("%04d", 1);
		}else {
			SysSnDictionary item=result.get(0);
			item.setSerialNumber(item.getSerialNumber()+1);
			dao.update(item);
			return sn+String.format("%04d", item.getSerialNumber());
		}
			
	}
	
	
	
}
