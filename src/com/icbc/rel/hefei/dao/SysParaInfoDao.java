package com.icbc.rel.hefei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SysParaInfo;

public interface SysParaInfoDao {
	/*
	 * 查询所有参数配置
	 */
	List<SysParaInfo> GetAllSysParaInfos();
	
	/*
	 * 根据key查询参数配置
	 */
	List<SysParaInfo> GetSysParaInfoByKey(int key);
	/*
	 * 更新参数值
	 */
	void UpdateSystemPara(@Param("key")int key,@Param("name")String name,@Param("value")String value);

}
