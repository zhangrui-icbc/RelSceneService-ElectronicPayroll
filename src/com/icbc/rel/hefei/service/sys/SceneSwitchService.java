package com.icbc.rel.hefei.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.SceneSwitchDao;
import com.icbc.rel.hefei.entity.SceneSwitch;

@Service
public class SceneSwitchService {
	@Autowired
	private SceneSwitchDao dao;
	
	/*
	 * 查询全部场景状态
	 */
	public List<SceneSwitch> select(){
		return dao.select();
	}
	
	/*
	 * 查询单个场景状态
	 */
	public SceneSwitch selectByScene(String Scene) {
		return dao.selectByScene(Scene);
	}
	
	/*
	 * 更新场景状态
	 */
	public void update(SceneSwitch info) {
		dao.update(info);
	}
}
