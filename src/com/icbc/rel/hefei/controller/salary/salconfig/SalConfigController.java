package com.icbc.rel.hefei.controller.salary.salconfig;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SceneSwitch;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.SysBankOrgInfo;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;
import com.icbc.rel.hefei.entity.order.OrdParaInfo;
import com.icbc.rel.hefei.entity.salary.SalParaInfo;
import com.icbc.rel.hefei.service.order.ParaService;
import com.icbc.rel.hefei.service.rel.PublicNumberInfoService;
import com.icbc.rel.hefei.service.sys.SceneSwitchService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.service.sys.SysBankOrgInfoService;
import com.icbc.rel.hefei.service.sys.SysLogInfoService;
import com.icbc.rel.hefei.service.sys.SysService;
import com.icbc.rel.hefei.util.EnumUtil;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;

@Controller
@RequestMapping(value="/mp")
public class SalConfigController {
	private static Logger logger = Logger.getLogger(SalConfigController.class);
	@Autowired
	private SysActivityService activityService;
	@Autowired
	private SysService  sysService;
	@Autowired
	private SysLogInfoService logService;
	@Autowired
	private SceneSwitchService service;
	/**
	 * 工资单页面	
	 */
	@RequestMapping(value="/salary")
	public ModelAndView salary(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("salary/salary");
		return mav;
	}
	
	/**
	 * 报销单页面	
	 */
	@RequestMapping(value="/reimbursement")
	public ModelAndView reimbursement(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("salary/reimbursement");
		return mav;
	}
	/**
	 * 平台使用说明
	 */
	@RequestMapping(value="/explain")
	public ModelAndView explain(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("salary/explain");
		return mav;
	}
	
	/**
	 * 配置页
	 */
	@RequestMapping(value="/salConfig")
	public ModelAndView salConfig(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		String activityUid=request.getParameter("activityUid");
		String mpId=SessionUtil.getMpId(request.getSession());
		int flag=0;//0：新增  1：编辑  
		////////////////////
		if(activityUid!=null) {
			SysActivityInfo activity=activityService.getSceneByUid(activityUid);
			JSONObject obj=(JSONObject) JSON.toJSON(activity);
			flag=1;
			mav.addObject("data", obj);
			request.getSession().setAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID, activity.getActivityUid());
		}else {
			SysActivityInfo activity=activityService.getMyActivity(mpId,EnumUtil.sceneType.salary.name());
			if(activity!=null) {
				request.getSession().setAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID, activity.getActivityUid());
				activityUid = activity.getActivityUid();
				JSONObject obj=(JSONObject) JSON.toJSON(activity);
				flag=1;
				mav.addObject("data", obj);
			}else{
				mav.addObject("data", "\"\"");
			}
		}
		
		SysPublicNumberInfo info = new SysPublicNumberInfo();
		info.setPublicNumberId(mpId);
		info = PublicNumberInfoService.FetchPubAddrInfo(info);
		String  paramStruId =  info.getStru_ID();//机构代码
		SceneSwitch  sceneSwitch = service.selectByScene("salary");
		if(sceneSwitch.getStatus()==1) {
			mav.addObject("flag", flag);
			mav.addObject("activityUid","\""+activityUid+"\"");
			mav.setViewName("salary/salReim");
		}else {
			if(!sceneSwitch.getVisibleAreas().contains(paramStruId)) {
				mav.setViewName("notOpen");
			}else {
				mav.addObject("flag", flag);
				mav.addObject("activityUid","\""+activityUid+"\"");
				mav.setViewName("salary/salReim");
			}
		}
		return mav;
	}
	/*
	 * 保存工资单参数配置
	 */
	@RequestMapping(value="/saveSalInfo")
	@ResponseBody
	public Msg saveParaInfo(HttpServletRequest request, @RequestBody SalParaInfo info)
	{
		Msg  msg=new Msg();
		SysActivityInfo activity=new SysActivityInfo();
		String mpId=SessionUtil.getMpId(request.getSession());
		String mpName=SessionUtil.getMpName(request.getSession());
	
		String url="";
		try {
			if(info.getActivityUid()==null ||info.getActivityUid().equals("null")) {
				String activityUid=sysService.generateSn("activity_",new Date(),null);
				request.getSession().setAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID, activityUid);
				activity.setActivityName(info.getActivityName());
				url=SystemConfigUtil.domainName+"RelSceneService/com/salaryWebUser/jumpLogin?activityUid="+activityUid+"&67f977b1ad597511737fff13a2909c1614c41391=0";
				activity.setActivityUid(activityUid);
				activity.setCreateTime(new Date());
				activity.setModifyTime(new Date());
				activity.setActivityUrl(url);
				activity.setMpId(mpId);
				activity.setActivityDesc(info.getActivityDesc());
				activity.setMpName(mpName);
				activity.setRelSceneUid(EnumUtil.sceneType.salary.name());
				activity.setBeginTime(new Date());
				activityService.insert(activity);
				logService.transforlog(activity, null, 1, EnumUtil.sceneType.salary.getSceneName(), activity.getMpName(),activity.getActivityName(),"新增配置");
				
			}else {
				//更新活动名称
				activity.setActivityName(info.getActivityName());
				activity.setActivityDesc(info.getActivityDesc());
				activity.setActivityUid(info.getActivityUid());
				activity.setMpId(mpId);
				activity.setMpName(mpName);
				SysActivityInfo oldactivity=activityService.getSceneByUid(activity.getActivityUid());
				url=oldactivity.getActivityUrl();
				activityService.updateName(activity);	
				logService.transforlog(activity, oldactivity, 2, EnumUtil.sceneType.salary.getSceneName(),  activity.getMpName(),activity.getActivityName(),"更新配置");
			}
			info.setActivityUid(activity.getActivityUid());
		}catch(Exception ex) {
			msg.setCode(-1);
			msg.setMessage("保存工资单参数配置报错："+ex.getMessage());
			logger.error("保存工资单参数配置报错：",ex);
			
			return msg;
		}
		
		msg.setCode(1);
		msg.setMessage(activity.getActivityUid());
		return msg;
	}
	

}
