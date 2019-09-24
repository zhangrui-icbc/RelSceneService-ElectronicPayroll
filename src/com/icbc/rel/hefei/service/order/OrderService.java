package com.icbc.rel.hefei.service.order;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.order.OrdOrderDetailInfoDao;
import com.icbc.rel.hefei.dao.order.OrdOrderInfoDao;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderDetailInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderInfo;
import com.icbc.rel.hefei.util.CommonUtil;

@Service
public class OrderService {
	private static Logger logger = Logger.getLogger(OrderService.class);

	@Autowired
	private OrdOrderInfoDao orderInfoDao;
	@Autowired
	private OrdOrderDetailInfoDao orderDetailInfoDao;
	
	/*
	 *插入新的订单
	 */
	public void InsertOrderInfo(OrdOrderInfo info){
		orderInfoDao.add(info);	
	}
	
	/*
	 * 插入订单详情
	 */
	public void InsertOrderDetailInfo(OrdOrderDetailInfo info) {
		orderDetailInfoDao.insertOrderDetail(info);
	}
	/*
	 * 查询名下所有订单
	 */
	public List<OrdOrderInfo> getOrderInfos(String openid,String activityUid){
		return orderInfoDao.getOrderInfos(openid,activityUid);
	}
	/*
	 * 根据订单编号查询订单信息
	 */
	public  OrdOrderInfo getOrderInfobyId(String orderId,String activityUid){
		return orderInfoDao.getOrderInfoByOrderId(orderId, activityUid);
	}
	/*
	 * 根据订单编号查询订单详情
	 */
	public List<OrdOrderDetailInfo> GetOrderDetailInfo(String orderId,String activityUid){
		return orderDetailInfoDao.getOrderDetail(orderId,activityUid);
	}
	
	/*
	 * 查询预订情况（预订人角度）
	 */
	public List<OrdOrderInfo> GetHistoryOrder(String activityUid,Date date,String mobileNo)
	{
		List<OrdOrderInfo> result=orderInfoDao.getOrderInfoByDate(activityUid,CommonUtil.DateConvertStr(date, "yyyy-MM-dd"),mobileNo);
		List<OrdOrderDetailInfo> details=orderDetailInfoDao.getOrderDetailByDate(activityUid,CommonUtil.DateConvertStr(date, "yyyy-MM-dd"));
		for(int i=0;i<result.size();i++) {
			OrdOrderInfo info=result.get(i);
			info.setIID(i+1);
			String str="";
			List<OrdOrderDetailInfo> items=details.stream().filter(x->x.getOrderId().equals(info.getOrderId())).collect(Collectors.toList());
			for(int j=0;j<items.size();j++) {
				OrdOrderDetailInfo item=items.get(j);
				if(j==0)  
				{
					str=item.getDishName()+"   ×"+item.getDishAmount();
				}else {
				str+="\r\n"+item.getDishName()+"   ×"+item.getDishAmount();}
			}
			info.setOrderDesc(str);
		}
		
		return result;
	}
	
	
	/*
	 * 取消订单
	 */
	public void updateStatusByOrderId(String orderId){
		orderInfoDao.updateStatusByOrderId(orderId);
	}
	
	
	
}
