package com.icbc.rel.hefei.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SysLogOperation;

public interface SysLogOperationDao {
	/*
	 * 插入日志
	 */
	void insert(SysLogOperation log);
	/*
	 * 查询记录条数
	 */
	int getcount(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("type")int type,@Param("mpName")String mpName);
	/*
	 * 查询logs
	 */
	List<SysLogOperation> getLogs(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("type")int type,@Param("mpName")String mpName,@Param("start")int start,@Param("limit")int limit);
}


