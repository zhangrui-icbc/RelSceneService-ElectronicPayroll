package com.icbc.rel.hefei.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.icbc.rel.hefei.entity.SceneSwitch;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;
import com.icbc.rel.hefei.service.rel.ImUserService;
import com.icbc.rel.hefei.service.rel.PublicNumberInfoService;
import com.icbc.rel.hefei.service.sys.SceneSwitchService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.service.sys.SysBankOrgInfoService;
import com.icbc.rel.hefei.service.sys.SysLogInfoService;
import com.icbc.rel.hefei.service.sys.SysPublicNumberInfoService;
import com.icbc.rel.hefei.util.CommonUtil;
import com.icbc.rel.hefei.util.EnumUtil;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.RSA.DecryptMpInfo;

@Controller
@RequestMapping(value = "/mp")
public class addSenceneController {
	/**
	 * @Description: 新增场景：包括我的场景、新增场景
	 * @author ILNIQ
	 * @date 2019年2月20日
	 */
	private static final Logger logger = Logger.getLogger(addSenceneController.class);

	@Autowired
	private SysActivityService sysActivityService;
	@Autowired
	private SysPublicNumberInfoService mpService;
	@Autowired
	private SysLogInfoService logService;

	@Autowired
	private SceneSwitchService service;
	

	/*
	 * 进入我的场景页面 demo:
	 * encTranData=BqdlMj/yXNbstTZb9csU9LAgENRzxsXzLrESvcnDzo8fr3XfU2C/
	 * hWs8ZUiZgNYj15jro1ToPxLq6UwMHoQ8vA==&encSignMsg=YYBrqWVmVJpoY/0B6C1/
	 * zmFvYMcZgvFQD2zFSiJipHope1V4ATBx7wv42cnuHBIE6PAg3Bgn1oGuDV1uAyYmLgj77Eu3APJLtDdPbfHbC
	 * +Uw+L5GYu+iiRCFUPTnmdfNB7xy4oV3+0o+
	 * tURjAIFvQsKkJHTQ8mgEDhA5LRXCn0fNuskGSszbh0vnJ1VbiA3aVJLErjGUi9L9s4Xm7CjSA7Ac0nOge4QwWO1s98AzhVEtdkIYLuJs
	 * +iIoelqELJ6SaR8Lh9yVDdFX5ehqZZjnwFf+ZzQNvRSD6gOkzm63UWNJDVMJOdOG9wzmPqm80/
	 * Q4cqmxHeQiD94jbJ35OTmHew==
	 */
	@RequestMapping(value = "/myscene")
	public ModelAndView MySencene(HttpServletRequest request, String encTranData, String encSignMsg) {
		ModelAndView mav = new ModelAndView();

		// 这里要解密传过来的参数
		// 得到的demo：mpid=90000000&loginid=123@126.com&icbcflag=1&username=客户服务787
		try {
			if (encTranData != null && encSignMsg != null) {
				String para = DecryptMpInfo.DecryptMpInfo(encTranData, encSignMsg);
				if (para == null) {
					return null;
				}
				String[] paralist = para.split("&");
				String mpid = "";
				String loginid = "";
				String username = "";
				int icbcflag = 0;
				for (String item : paralist) {
					String[] val = item.split("=");
					if (val[0].equals("mpid")) {
						mpid = val[1];
					} else if (val[0].equals("loginid")) {
						loginid = val[1];
					} else if (val[0].equals("username")) {
						username = val[1];
					} else if (val[0].equals("icbcflag")) {
						icbcflag = CommonUtil.parseInteger(val[1]);
					}
				}

				// 公众号名称还有&字符
				if (paralist.length == 5) {
					username += "&" + paralist[4];
				}
				SysPublicNumberInfo info = new SysPublicNumberInfo();
				info.setPublicNumberId(mpid);
				info.setPublicNumberAccount(loginid);
				info.setPublicNumberName(username);
				info.setPassword("");
				info.setType(2);
				info.setIcbcFlag(icbcflag);
				info.setCreateTime(new Date());
				//拉取公众号信息并保存
			    info = PublicNumberInfoService.FetchPubAddrInfo(info);
				mpService.insert(info);
				logger.info("解密上送参数获得公众号mpid:" + mpid + ";公众号名称:" + username);
				SessionUtil.setMpSession(request.getSession(), mpid, username);
				mav.setViewName("myscene");
				return mav;
			}
			if (!SessionUtil.getMpId(request.getSession()).equals("")) {
				mav.setViewName("myscene");
				return mav;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("解密上送参数报错：", e);
		}
		return null;

	}

	/*
	 * 查询所有场景活动
	 */
	@RequestMapping(value = "/getMySencene")
	@ResponseBody
	public Msg getMySencene(HttpServletRequest request, String activityName) {
		// page 为当前页，limit为每页显示多少条
		String mpId = SessionUtil.getMpId(request.getSession());
		List<SysActivityInfo> result;
		List<SysActivityInfo> result1 =new ArrayList<SysActivityInfo>();
		result = sysActivityService.getMyScene(mpId, activityName);
		logger.info("处理前的"+JSON.toJSONString(result));
		//在此判断地区是否应该显示.参数只有openid
		SysPublicNumberInfo info = new SysPublicNumberInfo();
		info.setPublicNumberId(mpId);
		info = PublicNumberInfoService.FetchPubAddrInfo(info);
		String  paramStruId =  info.getStru_ID();//机构代码
		SceneSwitch  sceneSwitch = service.selectByScene("salary");
		if(!sceneSwitch.getVisibleAreas().contains(paramStruId)) {
			for (int i = 0; i < result.size(); i++) {
				if(result.get(i).getRelSceneUid().equals("salary")) {
					result.remove(i);
				}
			}
		}
		/*//关闭存量转盘抽奖的数据：ILNIQ
		for(int i=0;i<result.size();i++) {
			if(!(result.get(i).getRelSceneUid().equals("lottery"))) {				
				result1.add(result.get(i));
			}
		}*/
		logger.info("处理后的"+JSON.toJSONString(result1));
		
		Msg msg = new Msg(0, "查询成功");
		msg.setData(result);
		return msg;

	}

	/*
	 * 删除场景活动
	 */
	@RequestMapping(value = "/delScene")
	@ResponseBody
	public Msg delScene(HttpServletRequest request, String activityUid) {
		SysActivityInfo oldactivity = sysActivityService.getSceneByUid(activityUid);
		sysActivityService.delete(activityUid);
		SysActivityInfo activity = (SysActivityInfo) oldactivity.clone();
		activity.setStatus(-1);
		
		logService.transforlog(activity, oldactivity, 2, oldactivity.getRelSceneName(), activity.getMpName(),
		activity.getActivityName(), "更新配置");
		Msg msg = new Msg(0, "删除成功");
		return msg;
	}

	/*
	 * 配置成功页面
	 */
	@RequestMapping(value = "/success")
	public ModelAndView success(String activityUid) {
		ModelAndView mav = new ModelAndView();
		SysActivityInfo activity = sysActivityService.getSceneByUid(activityUid);
		if(activity!=null) {
		mav.addObject("activityUrl", "\"" + activity.getActivityUrl() + "\"");}
		mav.setViewName("success");
		return mav;
	}

	/*
	 * 查询全部场景状态
	 */
	@RequestMapping(value="/getAllSceneStatus",method=RequestMethod.POST)
	public @ResponseBody Msg getAllSceneStatus() {
		Msg msg = new Msg();
		List<SceneSwitch> results = service.select();
		msg.setData(results);
		return msg;
	}
}
