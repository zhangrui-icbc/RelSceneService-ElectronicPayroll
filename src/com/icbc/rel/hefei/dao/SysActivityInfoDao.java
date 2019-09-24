package com.icbc.rel.hefei.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SysActivityInfo;

public interface SysActivityInfoDao {

	/*
	 * 插入活动信息
	 */
	void insert(SysActivityInfo info);
	
	/*
	 * 更新活动信息
	 */
	void update(SysActivityInfo info);
	/*
	 * 删除活动信息（逻辑删除）
	 */
	void del(String activityUid);
	/*
	 * 更新活动名称                                        
	 */
	void updateName(SysActivityInfo info);
	/*
	 * 获取我的场景信息
	 */
	List<SysActivityInfo> getMyScene(@Param("mpId")String mpId,@Param("activityName")String activityName);
	/*
	 * 获取指定活动信息
	 */
    List<SysActivityInfo> getMyActivity(@Param("mpId")String mpId,@Param("sceneType")String sceneType) ;
	/*
	 * 获取指定场景的信息
	 */
	SysActivityInfo getSceneByUid(String activityUid);
	
	
	List<SysActivityInfo> getActivityByRelScenUid(@Param("start")int start,@Param("limit")int limit,@Param("RelScenUid")String RelScenUid);
	 
	int getcountByRelScenUid(@Param("RelScenUid")String RelScenUid);
	
	/*
	 * 更新不为空的活动字段
	 */
	void updateNoNull(SysActivityInfo info);
}
