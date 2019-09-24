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

import com.alibaba.fastjson.JSONArray;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.TO.ParaTO;
import com.icbc.rel.hefei.TO.ParaTO.para;
import com.icbc.rel.hefei.entity.SysLogDataInfo;
import com.icbc.rel.hefei.entity.SysLogOperation;
import com.icbc.rel.hefei.entity.SysLogTableInfo;
import com.icbc.rel.hefei.entity.SysParaDesc;
import com.icbc.rel.hefei.util.CommonUtil;
import com.icbc.rel.hefei.service.sys.SysLogInfoService;
import com.icbc.rel.hefei.service.sys.SysParaInfoService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/ad")
public class paraController {
	private static final Logger logger = Logger.getLogger(paraController.class);
	@Autowired
	private SysParaInfoService paraInfoService;
	
	@RequestMapping(value="/parameter")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		logger.info("正在访问系统配置");
		mav.setViewName("parameter");
		return mav;
	}
	

	/*
	 * 获取参数配置
	 */
	@RequestMapping(value="/getParameters",method=RequestMethod.POST)
	public @ResponseBody Msg Getparamters()
	{
		Msg msg=new Msg();
		List<SysParaDesc> results=paraInfoService.GetAllParameters();
		msg.setData(results);
		return msg;
	}
	
	/*
	 * 获取参数配置
	 */
	@RequestMapping(value="/getParaHtml")
	public @ResponseBody Msg GetParaHtml(HttpServletRequest req,String key)
	{
		return paraInfoService.GetParaHtml(CommonUtil.parseInteger(key));
	}
	/*
	 * 修改参数
	 */
	@RequestMapping("/updatePara")
	public Msg updatePara(@RequestBody ParaTO info) {
		
		int key=0;
		for(para item:info.paras ) {
			key=item.getKey();
			paraInfoService.UpdateSysParaInfo(item.getKey(), item.getName(),item.getValue());
		}
		paraInfoService.UpdateSysParaDesc(key);
		Msg msg=new Msg(1,"修改成功");
		logger.info("修改成功");
		
		
		return msg;
	} 
	
	

}
