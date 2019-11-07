package com.icbc.rel.hefei.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.AnalysisTO;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderInfo;
import com.icbc.rel.hefei.entity.order.OrdParaInfo;
import com.icbc.rel.hefei.service.order.MenuInfoService;
import com.icbc.rel.hefei.service.order.OrderService;
import com.icbc.rel.hefei.service.order.ParaService;
import com.icbc.rel.hefei.service.order.ReportService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.service.sys.SysLogInfoService;
import com.icbc.rel.hefei.service.sys.SysService;
import com.icbc.rel.hefei.util.CommonUtil;
import com.icbc.rel.hefei.util.EnumUtil;
import com.icbc.rel.hefei.util.ExcelUtil;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;
@Controller
@RequestMapping(value="/mp")
public class configController {
	private static Logger logger = Logger.getLogger(configController.class);
	@Autowired
	private ParaService paraService;
	@Autowired
	private SysActivityService activityService;
	@Autowired
	private SysActivityService sysActivityService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private SysService  sysService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MenuInfoService menuInfoService;
	@Autowired
	private SysLogInfoService logService;
	
	@RequestMapping(value="/ordercfg")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String activityUid=request.getParameter("activityUid");
		//LotteryTO to=getLotteryConfig(activityUid);
		String mpId=SessionUtil.getMpId(request.getSession());
		int flag=0;//0：新增  1：编辑  
		if(activityUid!=null) {
			
			OrdParaInfo info=getParaInfo(activityUid);
			
			JSONObject obj=(JSONObject) JSON.toJSON(info);
			if(info!=null) {
			activityUid=info.getActivityUid();
			obj.put("orderBeginTime",CommonUtil.DateConvertStr(info.getOrderBeginTime(), "HH:mm:ss"));
			obj.put("orderEndTime",CommonUtil.DateConvertStr(info.getOrderEndTime(), "HH:mm:ss"));
			obj.put("cancelTime",CommonUtil.DateConvertStr(info.getCancelTime(), "HH:mm:ss"));}else {
			activityUid="";
			}
			//obj.put("sendTime",CommonUtil.DateConvertStr(info.getSendTime(), "HH:mm:ss"));
			flag=1;
			mav.addObject("data", obj);
		}else {
			
			SysActivityInfo activity=sysActivityService.getMyActivity(mpId,EnumUtil.sceneType.order.name());
			if(activity!=null) {
				activityUid=activity.getActivityUid();
				OrdParaInfo info=getParaInfo(activityUid);
				JSONObject obj=(JSONObject) JSON.toJSON(info);
				if(info!=null) {
				obj.put("orderBeginTime",CommonUtil.DateConvertStr(info.getOrderBeginTime(), "HH:mm:ss"));
				obj.put("orderEndTime",CommonUtil.DateConvertStr(info.getOrderEndTime(), "HH:mm:ss"));
				obj.put("cancelTime",CommonUtil.DateConvertStr(info.getCancelTime(), "HH:mm:ss"));}
				//obj.put("sendTime",CommonUtil.DateConvertStr(info.getSendTime(), "HH:mm:ss"));
				flag=1;
				mav.addObject("data", info==null?"\"\"":obj);
			}else{
				mav.addObject("data", "\"\"");
			}}
		mav.addObject("flag", flag);
		mav.addObject("activityUid","\""+activityUid+"\"");
		mav.setViewName("order/orderCfg");
		
		return mav;
	}
	/*
	 * 预订分析页面
	 */
	@RequestMapping(value="/orderAnalysis")
	public ModelAndView analysis(HttpServletRequest request,String activityUid) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("activityUid", "\""+activityUid+"\"");
		mav.setViewName("order/orderAnalysis");
		
		return mav;
	}
	
	
	/*
	 * 保存订餐参数配置
	 */
	@RequestMapping(value="/saveParaInfo")
	@ResponseBody
	public Msg saveParaInfo(HttpServletRequest request, @RequestBody OrdParaInfo info)
	{
		Msg  msg=new Msg();
		SysActivityInfo activity=new SysActivityInfo();
		String mpId=SessionUtil.getMpId(request.getSession());
		String mpName=SessionUtil.getMpName(request.getSession());
	
		String url="";
		try {
			if(info.getActivityUid()==null ||info.getActivityUid().equals("null")) {
				String activityUid=sysService.generateSn("activity_",new Date(),null);
				activity.setActivityName(info.getActivityName());
				url=SystemConfigUtil.domainName+"RelSceneService/com/order?activityUid="+activityUid+"&67f977b1ad597511737fff13a2909c1614c41391=0";
				activity.setActivityUid(activityUid);
				activity.setCreateTime(new Date());
				activity.setModifyTime(new Date());
				activity.setActivityUrl(url);
				activity.setMpId(mpId);
				activity.setActivityDesc(info.getActivityDesc());
				activity.setMpName(mpName);
				activity.setRelSceneUid(EnumUtil.sceneType.order.name());
				activity.setBeginTime(new Date());
				sysActivityService.insert(activity);
				logService.transforlog(activity, null, 1, EnumUtil.sceneType.order.getSceneName(), activity.getMpName(),activity.getActivityName(),"新增配置");
				
			}else {
				//更新活动名称
				activity.setActivityName(info.getActivityName());
				activity.setActivityDesc(info.getActivityDesc());
				activity.setActivityUid(info.getActivityUid());
				activity.setMpId(mpId);
				activity.setMpName(mpName);
				SysActivityInfo oldactivity=sysActivityService.getSceneByUid(activity.getActivityUid());
				url=oldactivity.getActivityUrl();
				sysActivityService.updateName(activity);	
				logService.transforlog(activity, oldactivity, 2, EnumUtil.sceneType.order.getSceneName(),  activity.getMpName(),activity.getActivityName(),"更新配置");
			}
			info.setActivityUid(activity.getActivityUid());
		if(info.getiID()==0) {
			//添加参数配置记录
			paraService.insertPara(info);
			logService.transforlog(info, null, 1, EnumUtil.sceneType.order.getSceneName(), activity.getMpName(),activity.getActivityName(),"更新配置");
		}else {
			//更新参数配置记录
			OrdParaInfo oldinfo=paraService.getOrderPara(info.getActivityUid());
			paraService.updatePara(info);
			logService.transforlog(info, oldinfo, 2, EnumUtil.sceneType.order.getSceneName(), activity.getMpName(), activity.getActivityName(),"更新配置");
		}
		}catch(Exception ex) {
			msg.setCode(-1);
			msg.setMessage("保存点餐参数配置报错："+ex.getMessage());
			logger.error("保存点餐参数配置报错：",ex);
			return msg;
		}
		
		msg.setCode(1);
		msg.setMessage(activity.getActivityUid());
		return msg;
	}
	
	/*
	 * 查询参数配置
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
	
	/*
	 * 查询预订情况分析
	 */
	@RequestMapping(value="/getOrderReportInfos")
	@ResponseBody
	public Msg getLotteryAnalysis(String activityUid) {
		Msg msg=new Msg();
		JSONObject json=new JSONObject();
		//1、总访问量；访问用户；累计订单量；本月订单量；
        int yearOrderNum=reportService.getCurrentYearOrderAmt(activityUid);
        int monthOrderNum=reportService.getCurrentMonthOrderAmt(activityUid);
        json.put("yearOrderNum", yearOrderNum);
        json.put("monthOrderNum", monthOrderNum);
        
        OrdParaInfo info=getParaInfo(activityUid);
        
        String note="";
        note+="1.外卖可预订时间段为:"+(info.getOrderTimeType()==-1?"前一日":"当日") +CommonUtil.DateConvertStr(info.getOrderBeginTime(), "HH:mm")+"-当日"+CommonUtil.DateConvertStr(info.getOrderEndTime(), "HH:mm")+";\n";
        note+="2.外卖取消：可在当日"+CommonUtil.DateConvertStr(info.getCancelTime(), "HH:mm")+"前取消;\n";
        note+="3.外卖领取时间为:"+info.getTakeTime()+";\n";
        note+="4.外卖领取地点为:"+info.getTakelocation()+";\n";
        note+="5.如有疑问，请联系："+info.getTelephone()+"。\n";
        json.put("note", note);
        json.put("activityName", info.getActivityName());
        msg.setData(json);
		//3、配置概览
		return msg;
	}
	/*
	 * 查询公众号指定日期订单详情
	 */
	@RequestMapping("/getTodayOrders")
	@ResponseBody
	public Msg getTodayOrder(String activityUid,String dateStr,String mobileNo)
	{
		Msg msg=new Msg();
		try {
		Date date=CommonUtil.parseDate(dateStr);
		List<OrdOrderInfo> results=orderService.GetHistoryOrder(activityUid, date, mobileNo);
		msg.setData(results);
		}catch(Exception ex) {
			msg.setCode(-1);
			msg.setMessage("查询今日订单报错："+ex.getMessage());
			logger.error("查询今日订单报错：activityUid:"+activityUid,ex);
		}
		return msg;
	}
	
	/*
	 * 查询公众号指定日期外卖菜品剩余情况
	 */
	@RequestMapping("/getAvailableOrders")
	@ResponseBody
	public Msg getAvailableOrders(String activityUid,String dateStr)
	{
		Msg msg=new Msg();
		try {
			Date date=CommonUtil.parseDate(dateStr);
		List<OrdMenuInfo> infos= menuInfoService.getMenuByDate(date, activityUid);
		int i=1;
		for(OrdMenuInfo info:infos) {
			info.AvailableAmt=info.getAvailableAmount();
			info.setIID(i++);
		}
		msg.setData(infos);
		}catch(Exception ex) {
			msg.setCode(-1);
			msg.setMessage("查询今日订单报错："+ex.getMessage());
			logger.error("查询今日订单报错：activityUid:"+activityUid,ex);
		}
		return msg;
	}

	/*
	 * 导出全部订单信息
	 */
	@RequestMapping(value="/exportAllOrders")
	@ResponseBody
	public Msg exportAllOrders(HttpServletRequest request,HttpServletResponse response,String activityUid,String dateStr,String mobileNo) {
		//查询全部订单信息
		Date date=CommonUtil.parseDate(dateStr);
		List<OrdOrderInfo> result=orderService.GetHistoryOrder(activityUid, date, mobileNo);
		//定义表的标题
		String title = "订单信息表";
		//定义表的列名
		String[] rowsName = {"订单号","昵称","预订详情","预订时间"};
		//定义表的内容
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> dataList = new ArrayList<Object[]>();
		
		for (int i = 0; i < result.size(); i++) {
			Object[] objects = new Object[rowsName.length];
			//填充行数据
			objects[0] = result.get(i).getOrderId();
			objects[1] = result.get(i).getUserName();
			objects[2] = result.get(i).getOrderDesc();
			objects[3] = CommonUtil.DateConvertStr(result.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss");
			dataList.add(objects);
		}	
		try {
			String name = "OrderInfo_"+dateStr+".xlsx";
			String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");//生成word文件的文件名
			excelUtil.exportExcel(title, rowsName, dataList, fileName, response);
			// returnInfo.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Msg msg = new Msg(0,"查询成功");
		return msg;
	}
	
	/*
	 * 导出全部菜品信息
	 */
	@RequestMapping(value="/exportAllDishes")
	@ResponseBody
	public Msg exportAllDishes(HttpServletRequest request,HttpServletResponse response,String activityUid,String dateStr,String mobileNo) {
		//查询全部剩余菜品信息
		Date date=CommonUtil.parseDate(dateStr);
		List<OrdMenuInfo> infos= menuInfoService.getMenuByDate(date, activityUid);
		int j=1;
		for(OrdMenuInfo info:infos) {
			info.AvailableAmt=info.getAvailableAmount();
			info.setIID(j++);
		}
		//定义表的标题
		String title = "菜品信息表";
		//定义表的列名
		String[] rowsName = {"序号","类别","菜品名称","总份数","已订份数"};
		//定义表的内容
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> dataList = new ArrayList<Object[]>();
		
		for (int i = 0; i < infos.size(); i++) {
			Object[] objects = new Object[rowsName.length];
			//填充行数据
			objects[0] = infos.get(i).getIID();
			objects[1] = infos.get(i).getClassifyName();
			objects[2] = infos.get(i).getDishName();
			objects[3] = infos.get(i).getAmount();
			objects[4] = infos.get(i).getUsedAmount();
			dataList.add(objects);
		}	
		try {
			String name = "DishInfo_"+dateStr+".xlsx";
			String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");//生成word文件的文件名
			excelUtil.exportExcel(title, rowsName, dataList, fileName, response);
			// returnInfo.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Msg msg = new Msg(0,"查询成功");
		return msg;
	}
}
