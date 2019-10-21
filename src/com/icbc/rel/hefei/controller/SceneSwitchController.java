package com.icbc.rel.hefei.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SceneSwitch;
import com.icbc.rel.hefei.entity.SysBankOrgInfo;
import com.icbc.rel.hefei.service.sys.SceneSwitchService;
import com.icbc.rel.hefei.service.sys.SysBankOrgInfoService;

@Controller
@RequestMapping(value="/ad")
public class SceneSwitchController {
	private static final Logger logger = Logger.getLogger(SceneSwitchController.class);
	
	@Autowired
	private SceneSwitchService service;
	@Autowired
	private SysBankOrgInfoService sysBankOrgInfoService;
	
	@RequestMapping(value="/sceneSwitch")
	public ModelAndView sceneSwitch() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sceneSwitch");
		return mav;
	}
	
	/*
	 * ��ѯȫ������״̬
	 */
	@RequestMapping(value="/getSceneStatus",method=RequestMethod.POST)
	public @ResponseBody Msg getSceneStatus() {
		Msg msg = new Msg();
		List<SceneSwitch> results = service.select();
		logger.info("results:"+results);
		msg.setData(results);
		return msg;
	}
	
	/*
	 * ��ѯ��������״̬
	 */
	@RequestMapping(value="/getScene")
	public @ResponseBody Msg getScene(HttpServletRequest request, String scene){
		Msg msg = new Msg();
		logger.info(scene);
		SceneSwitch result = service.selectByScene(scene);
		msg.setData(result);
		return msg;
	}
	
	/*
	 * ���³�������״̬
	 */
	@RequestMapping("/updateStatus")
	public Msg updateStatus(@RequestBody SceneSwitch info) {
		//Msg msg = new Msg();
		info.setModifyTime(new Date());
		service.update(info);
		Msg msg = new Msg(1,"�޸ĳɹ�");
		logger.info("�޸ĳ��� "+info.getSceneName()+" ״̬Ϊ"+info.getStatus()+"�ɹ�");
		return msg;
	}
	
	/**
	 * ���е���
	 */
	@RequestMapping(value="/bankorginfo")
	@ResponseBody
	public List<SysBankOrgInfo>  bankorginfo(HttpServletRequest request) {
		List<SysBankOrgInfo> list = sysBankOrgInfoService.getBankorginfo();
		return list;
	}
}
