package com.icbc.rel.hefei.dao;


import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SysReturnInfo;

public interface SysReturnInfoDao {
	
	
	void add(SysReturnInfo info);//新增营销平台返回数据
	
	void update(SysReturnInfo info);//更新营销平台返回数据
	
	SysReturnInfo getpromotionId(@Param("ActivityUid") String ActivityUid);//查询存量的promotionId

}