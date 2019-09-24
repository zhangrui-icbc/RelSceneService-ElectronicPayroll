package com.icbc.rel.hefei.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.order.DinnerInfo;
import com.icbc.rel.hefei.entity.order.OrdImportPicInfo;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderDetailInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderInfo;
import com.icbc.rel.hefei.entity.order.OrdParaInfo;
import com.icbc.rel.hefei.entity.order.OrdShoppingCar;
import com.icbc.rel.hefei.service.order.ImportPicService;
import com.icbc.rel.hefei.service.order.MenuInfoService;
import com.icbc.rel.hefei.service.order.OrderService;
import com.icbc.rel.hefei.service.order.ParaService;
import com.icbc.rel.hefei.service.order.ShoppingCarService;
import com.icbc.rel.hefei.service.rel.MessageHelper;
import com.icbc.rel.hefei.service.rel.MessageService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.service.sys.SysService;
import com.icbc.rel.hefei.util.CommonUtil;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;
@Controller
@RequestMapping(value="/com")
public class orderController {
	private static Logger logger = Logger.getLogger(orderController.class);
	
	@Autowired
	private MenuInfoService menuInfoService;
	@Autowired
	private ImportPicService importPicService;
	@Autowired
	private ParaService paraService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShoppingCarService carService;
	@Autowired
	private SysService  sysService;
	@Autowired
	private SysActivityService sysActivityService;
	/*
	 * Ԥ��ҳ��
	 */
	@RequestMapping(value="/order")
	public ModelAndView analysis(String activityUid) {
		ModelAndView mav = new ModelAndView();
		SysActivityInfo sysActivityInfo = sysActivityService.getSceneByUid(activityUid);
		if (sysActivityInfo.getStatus() == -1) {
			mav.setViewName("empty");
			return mav;
		}
		mav.addObject("activityUid", "\""+activityUid+"\"");
		mav.setViewName("order/order");
		return mav;
	}
	
	/*
	 * ���ﳵҳ��
	 */
	@RequestMapping(value="/shoppingCar")
	public ModelAndView shoppingCar(String activityUid) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("activityUid", "\""+activityUid+"\"");
		mav.setViewName("order/shoppingCar");
		return mav;
	}
	
	/*
	 * �ҵĶ���ҳ��
	 */
	@RequestMapping(value="/myOrder")
	public ModelAndView myOrder(String activityUid) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("activityUid", "\""+activityUid+"\"");
		mav.setViewName("order/myOrder");
		return mav;
	}
	
	/*
	 * �ҵĶ�������ҳ��
	 */
	@RequestMapping(value="/myOrderDetail")
	public ModelAndView myOrderDetail(String activityUid,String orderId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("activityUid", "\""+activityUid+"\"");
		SysActivityInfo  activity=sysActivityService.getSceneByUid(activityUid);
		String activityName=(activity==null?"":activity.getActivityName());
		mav.addObject("activityName", "\""+activityName+"\"");
		mav.addObject("orderId", "\""+orderId+"\"");
		mav.setViewName("order/myOrderDetail");
		return mav;
	}
	
	/*
	 * �ҵĶ�������ҳ��
	 */
	@RequestMapping(value="/orderSuccess")
	public ModelAndView orderSuccess(String activityUid,String orderId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("activityUid", "\""+activityUid+"\"");
		mav.addObject("orderId", "\""+orderId+"\"");
		mav.setViewName("order/orderSuccess");
		return mav;
	}
	
	
	
	/*
	 * ��ȡ��ǰ�����˵�
	 */
	@RequestMapping("/getTodayDinner")
	@ResponseBody
	public Msg  getTodayDinner(HttpServletRequest request,String activityUid) throws FileUploadException {
		Msg msg = new Msg();
		try {
			JSONObject json=new JSONObject();
			OrdParaInfo para=paraService.getOrderPara(activityUid);//��ȡ��������
			Date today=new  Date();
			int prenum=para.getOrderTimeType();
			Date current=(prenum==0?today:new Date(today.getTime() - 1000l * 24l * 60l * 60l));
			Date min=CommonUtil.parseTime(CommonUtil.DateConvertStr(current, "yyyy-MM-dd")+" "+ CommonUtil.DateConvertStr(para.getOrderBeginTime(),"HH:mm:ss"));//Ԥ����ʼʱ��
			Date max=CommonUtil.parseTime(CommonUtil.DateConvertStr(today, "yyyy-MM-dd")+" "+ CommonUtil.DateConvertStr(para.getOrderEndTime(),"HH:mm:ss"));//Ԥ������ʱ��
			int flag=(today.getTime()>=min.getTime() && today.getTime()<=max.getTime())?1:-1;
			json.put("canOrder", flag);

			List<OrdMenuInfo> infos= menuInfoService.getMenuByDate(today, activityUid);
			List<DinnerInfo> result=new ArrayList<DinnerInfo>();
			List<String> classify=new ArrayList<String>();
			for(int i=0;i<infos.size();i++)
			{
				OrdMenuInfo dish1=infos.get(i);
				if(classify.contains(dish1.getClassifyName()))
				{
					continue;
				}
				//������ķ���
				classify.add(dish1.getClassifyName());
				DinnerInfo item=new DinnerInfo();
				item.setClassifyName(dish1.getClassifyName());
				List<OrdMenuInfo> details=new ArrayList<OrdMenuInfo>();
				for(int j=0;j<infos.size();j++)
				{
					OrdMenuInfo dish2=infos.get(j);
					if(dish1.getClassifyName().equals(dish2.getClassifyName()))
					{
							
							List<OrdImportPicInfo> picInfos = importPicService.getAllPic(activityUid);
							for (OrdImportPicInfo picInfo : picInfos) {
								if (dish2.getDishName().contains(picInfo.getDishName())) {
									dish2.setPicUrl(picInfo.getPicUrl());
								}
							}				

							details.add(dish2);
						
							
						
					}
				}
				item.setDetails(details);
				result.add(item);
			}
			String ImUserId=SessionUtil.getImUserId(request.getSession());
			List<OrdShoppingCar> cars=carService.getShoppingCar(ImUserId, activityUid);
			int count=0;
			for(int i=0;i<cars.size();i++) {
				//�����ﳵ������@ILNIQ
				count+=cars.get(i).getDishAmount();
			}
			
			json.put("menuDate", CommonUtil.DateConvertStr(today, "yyyy-MM-dd"));
			json.put("data", result);
			json.put("count", count);
			msg.setData(json);
			
			msg.setCode(1);
			msg.setMessage(activityUid);
		} catch (Exception ex) {
			logger.error("��ѯ��ǰ�˵�����",ex);
			msg.setCode(-1);
			msg.setMessage("��ѯ��ǰ�˵�����"+ex.getMessage());
		}
		return msg;

	}

	/*
	 * ���빺�ﳵ
	 */
	@RequestMapping("/addShoppingCar")
	@ResponseBody
	public Msg addShoppingCar(String dishUid,String dishName,float dishPrice,int flag,HttpServletRequest request) {
		Msg msg=new Msg();
		try {
		String ImUserId=SessionUtil.getImUserId(request.getSession());
		if(ImUserId==null) {
			msg.setCode(-1);
			msg.setMessage("δʶ��������e���˺���Ϣ");
			return msg;
			}
		msg=carService.ChangeDishShoppingCar(ImUserId, dishUid, dishName, dishPrice, flag);
		}catch(Exception ex) {
			logger.error("��ӹ��ﳵ����",ex);
			msg.setCode(-1);
			msg.setMessage("��ӹ��ﳵ����"+ex.getMessage());
		}
		return msg;
	}
	/*
	 * ��ѯ���ﳵ
	 */
	@RequestMapping("/getShoppingCar")
	@ResponseBody
    public Msg getShoppingCar(String activityUid,HttpServletRequest request) {
		Msg msg=new Msg();
		try {
		String ImUserId=SessionUtil.getImUserId(request.getSession());
		List<OrdShoppingCar> infos=carService.getShoppingCar(ImUserId, activityUid);
		int count=0;
		for(int i=0;i<infos.size();i++) {
			//�����ﳵ������@ILNIQ
			count+=infos.get(i).getDishAmount();
		}
		
		
		JSONObject json=new JSONObject();
		json.put("data", infos);
		json.put("count", count);
		json.put("totalPrice", infos.stream().mapToDouble(x->x.getDishPrice()).sum());
		msg.setData(json);
		msg.setCode(1);
		}catch(Exception ex) {
			logger.error("��ѯ���ﳵ����",ex);
			msg.setCode(-1);
			msg.setMessage("��ѯ���ﳵ����"+ex.getMessage());
		}
		return msg;
    }

	/*
	 * �ύ����
	 * ���涩��������Ϣ����ϸ��Ϣ����չ��ﳵ
	 */
	@RequestMapping("/submitorder")
	@ResponseBody
    public Msg  SubmitOrder(HttpServletRequest request,String activityUid)
    {
		Msg msg=new Msg();
		try {
			
		Date today=new Date();
		OrdParaInfo para=paraService.getOrderPara(activityUid);//��ȡ��������
		int prenum=para.getOrderTimeType();
		Date current=(prenum==0?today:new Date(today.getTime() - 1000l * 24l * 60l * 60l));
		Date min=CommonUtil.parseTime(CommonUtil.DateConvertStr(current, "yyyy-MM-dd")+" "+ CommonUtil.DateConvertStr(para.getOrderBeginTime(),"HH:mm:ss"));//Ԥ����ʼʱ��
		Date max=CommonUtil.parseTime(CommonUtil.DateConvertStr(today, "yyyy-MM-dd")+" "+ CommonUtil.DateConvertStr(para.getOrderEndTime(),"HH:mm:ss"));//Ԥ������ʱ��
		
		int flag=(today.getTime()>=min.getTime() && today.getTime()<=max.getTime())?1:-1;
		
		
		if(flag==-1) {
			msg.setCode(-1);
			msg.setMessage("��ǰʱ�䲻��Ԥ��");
			return msg;
		}
		String ImUserId=SessionUtil.getImUserId(request.getSession());
		if(ImUserId==null) {
			msg.setCode(-1);
			msg.setMessage("δʶ��������e���˺���Ϣ");
			return msg;
			}
		String moblieNo=SessionUtil.getMobileNo(request.getSession());
		String nickname=SessionUtil.getNickName(request.getSession());
		
		if(null==moblieNo) {
			moblieNo="--";
			
		}
		
		if(null==nickname) {
			nickname="--";
			
		}
		
		
    	//��ѯ���ﳵ��Ϣ
    	List<OrdShoppingCar> result=carService.getShoppingCar(ImUserId, activityUid);
    	Date menuDte=menuInfoService.getMenuDate(result.get(0).getDishUid());
    	//��������������Ϣ���������ݿ�
    	OrdOrderInfo info=new OrdOrderInfo();
    	info.setOpenId(ImUserId);
    	info.setMoblieNo(moblieNo);
    	info.setUniNo("");
    	info.setUserStruName("");
    	info.setUserName(nickname);

    	info.setCreateTime(new Date());
    	double amt=result.stream().mapToDouble(x->x.getDishPrice()).sum();
    	info.setOrderAmt((float)amt);
    	String orderDesc=result.get(0).getDishName();
    	if(result.size()>1)
    	{
    		orderDesc+="��"+result.size()+"�ֲ�Ʒ";
    	}
    	info.setOrderDate(menuDte);
    	info.setOrderDesc(orderDesc);
    	info.setOrderStatus(1);
    	String orderId=sysService.generateSn("SN",menuDte,activityUid);
    	info.setOrderId(orderId);
    	info.setActivityUid(activityUid);
    	orderService.InsertOrderInfo(info);
        //������������������ݿ�
    	for(int i=0;i<result.size();i++)
    	{
    		OrdOrderDetailInfo item=new OrdOrderDetailInfo();
    		OrdShoppingCar car=result.get(i);
    		item.setOrderDate(menuDte);
    		item.setDishUid(car.getDishUid());
    		item.setDishName(car.getDishName());
    		item.setDishAmount(car.getDishAmount());
    		item.setDishPrice(car.getDishPrice());
    		item.setCreateTime(new Date());
    		item.setOrderId(orderId);
    		item.setActivityUid(activityUid);
    		orderService.InsertOrderDetailInfo(item);
    	}
    	//ɾ�����ﳵ����
    	carService.deleteShoppingCar(ImUserId, activityUid);
    	msg.setCode(1);
    	msg.setMessage(orderId);
    	//���Ͷ�������
    	if(!SystemConfigUtil.isDebug) {
    	try {
    	SysActivityInfo activity = sysActivityService.getSceneByUid(activityUid);
		String domainUrl = SystemConfigUtil.domainName;
		net.sf.json.JSONArray articles = MessageHelper.GetArticles("���ѳɹ��µ�", "����ʳΪ��",
				domainUrl + "RelSceneService/image/order/supper.jpg",
				domainUrl + "RelSceneService/com/myOrderDetail?activityUid="+activityUid+"&orderId=" + orderId);
		JSONObject obj = MessageHelper.getPicMessage(activity.getMpId(), ImUserId, "���ѳɹ��µ�", "����ʳΪ��",
				articles);
		MessageService.SendMessage(obj);
		logger.info("���Ͷ�����Ϣ�ɹ�");
    	}catch(Exception ex) {
    		logger.error("���Ͷ������鱨��",ex);
    		msg.setCode(-1);
    		msg.setMessage("���Ͷ������鱨��");
    	}}
    	return msg;
		}
		catch(Exception ex) {
			logger.error("�ύ��������",ex);
			msg.setCode(-1);
			msg.setMessage("�ύ��������");
			return msg;
		}
		
    }
	/*
	 * ��ѯ�ҵĶ���
	 */
	@RequestMapping("/getOrderInfo")
	@ResponseBody
    public Msg getOrderInfo(HttpServletRequest request,String activityUid) {
    	Msg msg=new Msg();
    	String ImUserId="";
    	try {
    		ImUserId=SessionUtil.getImUserId(request.getSession());
    	List<OrdOrderInfo> infos=orderService.getOrderInfos(ImUserId, activityUid);
    	for(OrdOrderInfo info:infos) {
    		info.setCreateTimeDesc();
    	}
    	msg.setCode(1);
    	msg.setData(infos);}
    	catch(Exception ex) {
    		logger.error("��ѯ�ҵĶ�������relId:"+ImUserId,ex);
			msg.setCode(-1);
			msg.setMessage("��ѯ�ҵĶ�������!"+ex.getMessage());
			return msg;
    	}
    	return msg;
    }
	/*
	 * ��ѯ�ҵĶ�������
	 */
	@RequestMapping("/getOrderDetail")
	@ResponseBody
	public Msg getOrderDetail(String orderId,String activityUid) {
		Msg msg=new Msg();
		try {
			JSONObject obj=new  JSONObject();
			OrdParaInfo para=paraService.getOrderPara(activityUid);
			List<OrdOrderDetailInfo> infos=orderService.GetOrderDetailInfo(orderId,activityUid);
			OrdOrderInfo orderInfo=orderService.getOrderInfobyId(orderId, activityUid);
			int status=orderInfo.getOrderStatus();
			String time1=para.getTakeTime();
			String location=para.getTakelocation();
			String telephNo=para.getTelephone();
			int flag=0;
			//�ж��Ƿ����ȡ���������Ա������ʾȡ��������ť��
			if(status==1 ) {
				String date=CommonUtil.DateConvertStr(orderInfo.getOrderDate(), "yyyy-MM-dd");
				String now=CommonUtil.DateConvertStr(new Date(), "yyyy-MM-dd");
				Date limit=CommonUtil.parseTime(CommonUtil.DateConvertStr(new Date(), "yyyy-MM-dd")+" "+ CommonUtil.DateConvertStr(para.getCancelTime(),"HH:mm:ss"));//ȡ��ʱ��@ILNIQ
								
				if(new Date().getTime()<limit.getTime() && date.equals(now)) {
					flag=1;
				}
			}
			obj.put("infos", infos);
			obj.put("orderId", orderId);
			obj.put("time", CommonUtil.DateConvertStr(orderInfo.getOrderDate(), "yyyy-MM-dd")+' '+time1);
			obj.put("flag", flag);
			obj.put("status",orderInfo.getOrderStatusDesc());
			obj.put("userName",orderInfo.getUserName());
			obj.put("mobileNo", orderInfo.getMoblieNo());
			obj.put("userStruName", orderInfo.getUserStruName());
			obj.put("orderDateDesc",CommonUtil.DateConvertStr(orderInfo.getOrderDate(), "yyyy-MM-dd"));
			obj.put("location", location);
			obj.put("telephNo", telephNo);
			obj.put("totalAmt", infos.stream().mapToDouble(x->x.getDishPrice()).sum());
			obj.put("orderTimeDesc", CommonUtil.DateConvertStr(infos.get(0).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            msg.setData(obj);	
            msg.setCode(1);
 		}catch(Exception ex) {
			msg.setCode(-1);
			msg.setMessage("��ѯ�������鱨��"+ex.getMessage());
			logger.error("��ѯ�������鱨��:orderId:"+orderId,ex);
		}
		return msg;
	}
	
	/*
	 * ��ѯ���ﳵ@ILNIQ
	 */
	@RequestMapping("/updateOrderStatus")
	@ResponseBody
    public Msg updateOrderStatus(String activityUid,String orderId,HttpServletRequest request) {
				
		Msg msg=new Msg();
		try {
			logger.info("activityUid:"+activityUid);
			logger.info("orderId:"+orderId);
		    
			
			List<OrdOrderDetailInfo> infos=orderService.GetOrderDetailInfo(orderId,activityUid);
					
			for(int i=0;i<infos.size();i++) {
				logger.info("��ƷUid��"+infos.get(i).getDishUid());
				logger.info("�����Ĳ�Ʒ������"+infos.get(i).getDishAmount());
				menuInfoService.updateMenuBydishUid(infos.get(i).getDishUid(),infos.get(i).getDishAmount());
			}
		    
		    
			orderService.updateStatusByOrderId(orderId);
			msg.setMessage("ȡ�������ɹ�");
			msg.setCode(1);
		}catch(Exception ex) {
			logger.error("��ѯ���ﳵ����",ex);
			msg.setCode(-1);
			msg.setMessage("��ѯ���ﳵ����"+ex.getMessage());
		}
		return msg;
    }

	
	
	
	
	
	
	
}
