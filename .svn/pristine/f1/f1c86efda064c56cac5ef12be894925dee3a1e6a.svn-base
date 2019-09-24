package com.icbc.rel.hefei.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SysLogDataInfo;
import com.icbc.rel.hefei.entity.SysLogOperation;
import com.icbc.rel.hefei.entity.SysLogTableInfo;
import com.icbc.rel.hefei.util.CommonUtil;
import com.icbc.rel.hefei.service.sys.SysLogInfoService;

@Controller
@RequestMapping(value="/ad")
public class loggerController {
	private static final Logger logger = Logger.getLogger(loggerController.class);
	@Autowired
	private SysLogInfoService logDataService;
	
	@RequestMapping(value="/logger")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("logger");
		return mav;
	}
	

	/*
	 * 查询平台日志信息
	 */
	@RequestMapping(value="/getPlatFormLog2",method=RequestMethod.POST)
	@ResponseBody
	public Msg GetPlatFormLog2(String startDate,String endDate,Integer type,String mpuser,int page,int limit){
		logger.info("来查数据啦");
		Date start=CommonUtil.parseDate(startDate);
		Date end=CommonUtil.parseDate(endDate);
		type=(type==null?0:type);
		List<SysLogOperation> result= logDataService.getlog(start, end, type, mpuser, page, limit);
		int count=logDataService.getcount(start, end, type, mpuser);
		Msg msg=new Msg(0,"查询成功");
		msg.setData(result);
		msg.setCount(count);
		return msg;
	}
	
	/*
	 * 查询平台日志信息明细
	 */
	@RequestMapping(value="/getLogData",method=RequestMethod.GET)
	@ResponseBody
	public Msg getLogData(String logUid){
	
		List<SysLogDataInfo> data= logDataService.getDatalog(logUid);
		SysLogTableInfo table=logDataService.getTablelog(logUid);
		JSONObject obj=new JSONObject();
		obj.put("data", data);
		obj.put("table", table);
		Msg msg=new Msg(0,"查询成功");
		msg.setData(obj);
		return msg;
	}
	
	

}
