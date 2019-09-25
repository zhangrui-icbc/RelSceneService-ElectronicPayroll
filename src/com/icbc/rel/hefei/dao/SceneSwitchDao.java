package com.icbc.rel.hefei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SceneSwitch;

public interface SceneSwitchDao {
	
	/*
	 * ��ѯȫ������״̬
	 */
	List<SceneSwitch> select();
	
	/*
	 * ��ѯ��������״̬
	 */
	SceneSwitch selectByScene(@Param("Scene")String Scene);
	
	/*
	 * ���³���״̬
	 */
	void update(SceneSwitch info);
}
