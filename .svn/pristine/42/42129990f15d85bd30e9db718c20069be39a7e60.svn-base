package com.icbc.rel.hefei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SysSnDictionary;


public interface SysSnDictionaryDao {
    
    List<SysSnDictionary> getLock(@Param("prefix")String prefix,@Param("date")String date,@Param("activityUid")String activityUid);
	
	void insert(SysSnDictionary info);
	
	void update(SysSnDictionary info);
	

	
}
