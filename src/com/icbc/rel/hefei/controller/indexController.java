package com.icbc.rel.hefei.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.service.sys.SysActivityService;

@Controller
public class indexController {
	
	private static final Logger logger = Logger.getLogger(indexController.class);
	
	
	@Autowired
	private SysActivityService sysActivityService;
	
	/**
	 * @Description: 首页，包括全部场景和活动使用情况
	 * @author ILNIQ
	 * @date 2019年2月20日
	 */
	@RequestMapping(value="/ad/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();	
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * @Description: 首页，包括全部场景和活动使用情况
	 * @author ILNIQ
	 * @date 2019年2月20日
	 */
	@RequestMapping(value="/mp/index")
	public ModelAndView index2() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	
	/*
	 * 查询所有场景活动
	 */
	@RequestMapping(value="/com/getAllSencene",method=RequestMethod.POST)
	@ResponseBody
	public Msg GetallSencene(HttpServletRequest request,String sceneUid,int page,int limit){
		sceneUid="000".equals(sceneUid)?null:sceneUid;
		List<SysActivityInfo> result;
		List<SysActivityInfo> result1 =new ArrayList<SysActivityInfo>();
		String adminId=SessionUtil.getAdminId(request.getSession());
		String mpId=SessionUtil.getMpId(request.getSession());
		Msg msg=new Msg(0,"查询成功");
		if(adminId!=null || mpId!=null) {
			result=sysActivityService.getActivityByRelScenUid(page, limit,sceneUid);
			//关闭存量转盘抽奖的数据：ILNIQ
		  /*  for(int i=0;i<result.size();i++) {
				if(!(result.get(i).getRelSceneUid().equals("lottery"))) {				
					result1.add(result.get(i));
				}
			}*/
			
			int	count =sysActivityService.getcountByRelScenUid(sceneUid);
			msg.setData(result);
			msg.setCount(count);
		}
		
		
		
		
		return msg;		
	}
	


}
