package com.icbc.rel.hefei.controller.todo.salconfig;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.order.OrdParaInfo;
import com.icbc.rel.hefei.entity.todo.salary.SalParaInfo;
import com.icbc.rel.hefei.service.order.ParaService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
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
	private ParaService paraService;
	@Autowired
	private SysActivityService activityService;
	@Autowired
	private SysService  sysService;
	@Autowired
	private SysLogInfoService logService;
	/**
	 * ���ʵ�ҳ��	
	 */
	@RequestMapping(value="/salary")
	public ModelAndView salary(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("todo/salary");
		return mav;
	}
	
	/**
	 * ������ҳ��	
	 */
	@RequestMapping(value="/reimbursement")
	public ModelAndView reimbursement(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("todo/reimbursement");
		return mav;
	}
	
	/**
	 * ���ʵ�ҳ��	
	 */
	@RequestMapping(value="/salReim")
	public ModelAndView salReim(HttpServletRequest request) {
		String mpId=SessionUtil.getMpId(request.getSession());
		SysActivityInfo activity=activityService.getMyActivity(mpId,EnumUtil.sceneType.salary.name());
		int flag=0;//0������  1���༭  
		ModelAndView mav = new ModelAndView();
			if(activity!=null) {
				request.getSession().setAttribute(SessionParamConstant.PC_SESSION_PARAM_COMPANYID, activity.getActivityUid());
				mav.setViewName("todo/salReim");
			}else {
				mav.addObject("flag", flag);
				mav.addObject("activityUid","\""+null+"\"");
				mav.addObject("data","\"\"");
				mav.setViewName("todo/salaryCfg");
			}
		return mav;
	}
	
	
	/**
	 * ����ҳ
	 */
	@RequestMapping(value="/salConfig")
	public ModelAndView salConfig(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		String activityUid=request.getParameter("activityUid");
		String mpId=SessionUtil.getMpId(request.getSession());
		int flag=0;//0������  1���༭  
		////////////////////
		if(activityUid!=null) {
			
			SysActivityInfo activity=activityService.getSceneByUid(activityUid);
			JSONObject obj=(JSONObject) JSON.toJSON(activity);
			flag=1;
			mav.addObject("data", obj);
		}else {
			SysActivityInfo activity=activityService.getMyActivity(mpId,EnumUtil.sceneType.salary.name());
			if(activity!=null) {
				activityUid=activity.getActivityUid();
				OrdParaInfo info=getParaInfo(activityUid);
				JSONObject obj=(JSONObject) JSON.toJSON(info);
				flag=1;
				mav.addObject("data", info==null?"\"\"":obj);
			}else{
				mav.addObject("data", "\"\"");
			}}
		mav.addObject("flag", flag);
		mav.addObject("activityUid","\""+activityUid+"\"");
		mav.setViewName("todo/salaryCfg");
		return mav;
	}
	/*
	 * ���涩�Ͳ�������
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
				url=SystemConfigUtil.domainName+"RelSceneService/salaryWebUser/jumpLogin?activityUid="+activityUid+"&67f977b1ad597511737fff13a2909c1614c41391=0";
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
				logService.transforlog(activity, null, 1, EnumUtil.sceneType.salary.getSceneName(), activity.getMpName(),activity.getActivityName(),"��������");
				
			}else {
				//���»����
				activity.setActivityName(info.getActivityName());
				activity.setActivityDesc(info.getActivityDesc());
				activity.setActivityUid(info.getActivityUid());
				activity.setMpId(mpId);
				activity.setMpName(mpName);
				SysActivityInfo oldactivity=activityService.getSceneByUid(activity.getActivityUid());
				url=oldactivity.getActivityUrl();
				activityService.updateName(activity);	
				logService.transforlog(activity, oldactivity, 2, EnumUtil.sceneType.salary.getSceneName(),  activity.getMpName(),activity.getActivityName(),"��������");
			}
			info.setActivityUid(activity.getActivityUid());
		}catch(Exception ex) {
			msg.setCode(-1);
			msg.setMessage("���湤�ʵ��������ñ�����"+ex.getMessage());
			logger.error("���湤�ʵ��������ñ�����",ex);
			
			return msg;
		}
		
		msg.setCode(1);
		msg.setMessage(activity.getActivityUid());
		return msg;
	}
	
	/*
	 * ��ѯ��������
	 */
	public OrdParaInfo getParaInfo(String activityUid) {
		OrdParaInfo info=paraService.getOrderPara(activityUid);
		SysActivityInfo activity=activityService.getSceneByUid(activityUid);
		if(info!=null) {
			info.setActivityName(activity.getActivityName());
			info.setActivityDesc(activity.getActivityDesc());
		}
		
		return info;
	}
	
}