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
		int flag=0;//0������  1���༭  
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
	 * Ԥ������ҳ��
	 */
	@RequestMapping(value="/orderAnalysis")
	public ModelAndView analysis(HttpServletRequest request,String activityUid) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("activityUid", "\""+activityUid+"\"");
		mav.setViewName("order/orderAnalysis");
		
		return mav;
	}
	
	
	/*
	 * ���涩�Ͳ�������
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
				logService.transforlog(activity, null, 1, EnumUtil.sceneType.order.getSceneName(), activity.getMpName(),activity.getActivityName(),"��������");
				
			}else {
				//���»����
				activity.setActivityName(info.getActivityName());
				activity.setActivityDesc(info.getActivityDesc());
				activity.setActivityUid(info.getActivityUid());
				activity.setMpId(mpId);
				activity.setMpName(mpName);
				SysActivityInfo oldactivity=sysActivityService.getSceneByUid(activity.getActivityUid());
				url=oldactivity.getActivityUrl();
				sysActivityService.updateName(activity);	
				logService.transforlog(activity, oldactivity, 2, EnumUtil.sceneType.order.getSceneName(),  activity.getMpName(),activity.getActivityName(),"��������");
			}
			info.setActivityUid(activity.getActivityUid());
		if(info.getiID()==0) {
			//��Ӳ������ü�¼
			paraService.insertPara(info);
			logService.transforlog(info, null, 1, EnumUtil.sceneType.order.getSceneName(), activity.getMpName(),activity.getActivityName(),"��������");
		}else {
			//���²������ü�¼
			OrdParaInfo oldinfo=paraService.getOrderPara(info.getActivityUid());
			paraService.updatePara(info);
			logService.transforlog(info, oldinfo, 2, EnumUtil.sceneType.order.getSceneName(), activity.getMpName(), activity.getActivityName(),"��������");
		}
		}catch(Exception ex) {
			msg.setCode(-1);
			msg.setMessage("�����Ͳ������ñ���"+ex.getMessage());
			logger.error("�����Ͳ������ñ���",ex);
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
	
	/*
	 * ��ѯԤ���������
	 */
	@RequestMapping(value="/getOrderReportInfos")
	@ResponseBody
	public Msg getLotteryAnalysis(String activityUid) {
		Msg msg=new Msg();
		JSONObject json=new JSONObject();
		//1���ܷ������������û����ۼƶ����������¶�������
        int yearOrderNum=reportService.getCurrentYearOrderAmt(activityUid);
        int monthOrderNum=reportService.getCurrentMonthOrderAmt(activityUid);
        json.put("yearOrderNum", yearOrderNum);
        json.put("monthOrderNum", monthOrderNum);
        
        OrdParaInfo info=getParaInfo(activityUid);
        
        String note="";
        note+="1.������Ԥ��ʱ���Ϊ:"+(info.getOrderTimeType()==-1?"ǰһ��":"����") +CommonUtil.DateConvertStr(info.getOrderBeginTime(), "HH:mm")+"-����"+CommonUtil.DateConvertStr(info.getOrderEndTime(), "HH:mm")+";\n";
        note+="2.����ȡ�������ڵ���"+CommonUtil.DateConvertStr(info.getCancelTime(), "HH:mm")+"ǰȡ��;\n";
        note+="3.������ȡʱ��Ϊ:"+info.getTakeTime()+";\n";
        note+="4.������ȡ�ص�Ϊ:"+info.getTakelocation()+";\n";
        note+="5.�������ʣ�����ϵ��"+info.getTelephone()+"��\n";
        json.put("note", note);
        json.put("activityName", info.getActivityName());
        msg.setData(json);
		//3�����ø���
		return msg;
	}
	/*
	 * ��ѯ���ں�ָ�����ڶ�������
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
			msg.setMessage("��ѯ���ն�������"+ex.getMessage());
			logger.error("��ѯ���ն�������activityUid:"+activityUid,ex);
		}
		return msg;
	}
	
	/*
	 * ��ѯ���ں�ָ������������Ʒʣ�����
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
			msg.setMessage("��ѯ���ն�������"+ex.getMessage());
			logger.error("��ѯ���ն�������activityUid:"+activityUid,ex);
		}
		return msg;
	}

	/*
	 * ����ȫ��������Ϣ
	 */
	@RequestMapping(value="/exportAllOrders")
	@ResponseBody
	public Msg exportAllOrders(HttpServletRequest request,HttpServletResponse response,String activityUid,String dateStr,String mobileNo) {
		//��ѯȫ��������Ϣ
		Date date=CommonUtil.parseDate(dateStr);
		List<OrdOrderInfo> result=orderService.GetHistoryOrder(activityUid, date, mobileNo);
		//�����ı���
		String title = "������Ϣ��";
		//����������
		String[] rowsName = {"������","�ǳ�","Ԥ������","Ԥ��ʱ��"};
		//����������
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> dataList = new ArrayList<Object[]>();
		
		for (int i = 0; i < result.size(); i++) {
			Object[] objects = new Object[rowsName.length];
			//���������
			objects[0] = result.get(i).getOrderId();
			objects[1] = result.get(i).getUserName();
			objects[2] = result.get(i).getOrderDesc();
			objects[3] = CommonUtil.DateConvertStr(result.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss");
			dataList.add(objects);
		}	
		try {
			String name = "OrderInfo_"+dateStr+".xlsx";
			String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");//����word�ļ����ļ���
			excelUtil.exportExcel(title, rowsName, dataList, fileName, response);
			// returnInfo.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Msg msg = new Msg(0,"��ѯ�ɹ�");
		return msg;
	}
	
	/*
	 * ����ȫ����Ʒ��Ϣ
	 */
	@RequestMapping(value="/exportAllDishes")
	@ResponseBody
	public Msg exportAllDishes(HttpServletRequest request,HttpServletResponse response,String activityUid,String dateStr,String mobileNo) {
		//��ѯȫ��ʣ���Ʒ��Ϣ
		Date date=CommonUtil.parseDate(dateStr);
		List<OrdMenuInfo> infos= menuInfoService.getMenuByDate(date, activityUid);
		int j=1;
		for(OrdMenuInfo info:infos) {
			info.AvailableAmt=info.getAvailableAmount();
			info.setIID(j++);
		}
		//�����ı���
		String title = "��Ʒ��Ϣ��";
		//����������
		String[] rowsName = {"���","���","��Ʒ����","�ܷ���","�Ѷ�����"};
		//����������
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> dataList = new ArrayList<Object[]>();
		
		for (int i = 0; i < infos.size(); i++) {
			Object[] objects = new Object[rowsName.length];
			//���������
			objects[0] = infos.get(i).getIID();
			objects[1] = infos.get(i).getClassifyName();
			objects[2] = infos.get(i).getDishName();
			objects[3] = infos.get(i).getAmount();
			objects[4] = infos.get(i).getUsedAmount();
			dataList.add(objects);
		}	
		try {
			String name = "DishInfo_"+dateStr+".xlsx";
			String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");//����word�ļ����ļ���
			excelUtil.exportExcel(title, rowsName, dataList, fileName, response);
			// returnInfo.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Msg msg = new Msg(0,"��ѯ�ɹ�");
		return msg;
	}
}
