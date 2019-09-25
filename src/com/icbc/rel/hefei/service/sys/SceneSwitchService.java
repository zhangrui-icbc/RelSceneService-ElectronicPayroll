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
	 * ��ѯȫ������״̬
	 */
	public List<SceneSwitch> select(){
		return dao.select();
	}
	
	/*
	 * ��ѯ��������״̬
	 */
	public SceneSwitch selectByScene(String Scene) {
		return dao.selectByScene(Scene);
	}
	
	/*
	 * ���³���״̬
	 */
	public void update(SceneSwitch info) {
		dao.update(info);
	}
}
