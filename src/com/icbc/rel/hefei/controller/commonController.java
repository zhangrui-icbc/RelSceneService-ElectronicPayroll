package com.icbc.rel.hefei.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icbc.mims.thirdparty.filter.util.MimsDes;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.UserDetailInfo;
import com.icbc.rel.hefei.service.rel.ImUserService;
import com.icbc.rel.hefei.util.SessionUtil;
@Controller
@RequestMapping(value="/com")
public class commonController {
	
	private static final Logger logger = Logger.getLogger(commonController.class);
	
	
	
	@RequestMapping(value="/F5Check")
	public ModelAndView health() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("health");
		return mav;
	}
	
	@RequestMapping(value="/verifyCode")
	public ModelAndView verifyCode() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("verify-code");
		return mav;
	}

	@RequestMapping(value="/getImUserId")
	@ResponseBody
	public Msg getMySencene(HttpServletRequest request,String imuserid){
		try {
		logger.info("获取原始IMUserId："+imuserid);
		String IMUserId = MimsDes.decryptFixed(imuserid);
		SessionUtil.setImUserId(request.getSession(), IMUserId);
		logger.info("解密后IMUserId："+IMUserId);
		String a=SessionUtil.getImUserId(request.getSession());
		logger.info("session："+a);
		//拉取用户详情
		UserDetailInfo userinfo=ImUserService.FetchUserInfo(IMUserId);
		SessionUtil.setUserInfo(request.getSession(), userinfo.getCustomerType(), userinfo.getNickName(), userinfo.getMobileNo());
		Msg msg=new Msg(0,"保存成功");
		msg.setData(userinfo);
		return msg;
		}catch(Exception ex) {
			logger.error("获取useid报错:",ex);
			return new  Msg(0,"解密失败");
		}
		
	}
}
