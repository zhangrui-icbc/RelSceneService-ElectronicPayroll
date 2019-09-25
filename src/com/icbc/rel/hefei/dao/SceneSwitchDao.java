package com.icbc.rel.hefei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SceneSwitch;

public interface SceneSwitchDao {
	
	/*
	 * 查询全部场景状态
	 */
	List<SceneSwitch> select();
	
	/*
	 * 查询单个场景状态
	 */
	SceneSwitch selectByScene(@Param("Scene")String Scene);
	
	/*
	 * 更新场景状态
	 */
	void update(SceneSwitch info);
}
