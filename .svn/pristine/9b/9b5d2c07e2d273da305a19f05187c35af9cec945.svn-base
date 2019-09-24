package com.icbc.rel.hefei.service.sys;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.SysMessageInfoDao;
import com.icbc.rel.hefei.entity.SysMessageInfo;

@Service
public class SysMassageInfoService {
	
	@Autowired
	private SysMessageInfoDao dao;
	
	public void insert(SysMessageInfo info) {
		dao.insert(info);
	}
	
	public List<SysMessageInfo> queryInfo(String activityUid,int page,int limit) {
		return dao.queryInfo(activityUid,(page-1)*limit,limit);
	}
	
	//查询消息记录条数
	public int queryInfoNum(String activityUid) {
		List<Integer> result= dao.queryInfoNum(activityUid);
		if(result!=null && result.size()>0) {
			return result.get(0)==null? 0:result.get(0);
		 }else
		 {
			 return 0;
		 }
	}
	

}
