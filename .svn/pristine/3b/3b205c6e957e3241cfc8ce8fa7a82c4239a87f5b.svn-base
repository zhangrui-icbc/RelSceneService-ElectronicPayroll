package com.icbc.rel.hefei.dao;

import com.icbc.rel.hefei.entity.SysMessageInfo;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface SysMessageInfoDao {
	
	 List<SysMessageInfo> queryInfo(@Param("activityUid")String activityUid,@Param("start")int start,@Param("limit")int limit);
	  
	  void insert(SysMessageInfo info);
	  
	  List<Integer> queryInfoNum(String activityUid);
}
