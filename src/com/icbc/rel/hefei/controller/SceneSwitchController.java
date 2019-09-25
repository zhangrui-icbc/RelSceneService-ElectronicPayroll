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
import com.icbc.rel.hefei.service.sys.SceneSwitchService;

@Controller
@RequestMapping(value="/ad")
public class SceneSwitchController {
	private static final Logger logger = Logger.getLogger(SceneSwitchController.class);
	
	@Autowired
	private SceneSwitchService service;
	
	@RequestMapping(value="/sceneSwitch")
	public ModelAndView sceneSwitch() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sceneSwitch");
		return mav;
	}
	
	/*
	 * 查询全部场景状态
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
	 * 查询单个场景状态
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
	 * 更新场景开关状态
	 */
	@RequestMapping("/updateStatus")
	public Msg updateStatus(@RequestBody SceneSwitch info) {
		//Msg msg = new Msg();
		info.setModifyTime(new Date());
		service.update(info);
		Msg msg = new Msg(1,"修改成功");
		logger.info("修改场景 "+info.getSceneName()+" 状态为"+info.getStatus()+"成功");
		return msg;
	}
}
