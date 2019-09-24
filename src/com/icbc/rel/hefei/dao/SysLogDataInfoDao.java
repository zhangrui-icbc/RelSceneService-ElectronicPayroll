package com.icbc.rel.hefei.dao;

import java.util.List;

import com.icbc.rel.hefei.entity.SysLogDataInfo;

public interface SysLogDataInfoDao {
    /*
     * 添加日志
     */
	void insert(SysLogDataInfo log);
	/*
	 * 查询日志
	 */
	List<SysLogDataInfo> getlog(String logUid);
}