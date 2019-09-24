package com.icbc.rel.hefei.dao.order;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderDetailInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderInfo;


 public interface OrdOrderInfoDao {
	
	
	
	/*
	 * 查询个人所有订单
	 */
	 List<OrdOrderInfo> getOrderInfos(@Param("openid")String openid,@Param("activityUid")String activityUid);
	
	/*
	 * 查询指定日期所有订单
	 */
	 List<OrdOrderInfo> getOrderInfoByDate(@Param("activityUid")String activityUid,@Param("date")String date,@Param("mobileNo")String mobileNo);

	
	
	/*
	 * 根据订单编号查询订单详情
	 */
	 OrdOrderInfo getOrderInfoByOrderId(@Param("orderId")String orderId,@Param("activityUid")String activityUid);
	
	 /*
	* 查询用户菜品已订份数(订单)
	*/
	int getOrderAmount(@Param("openid")String openid,@Param("dishUid")String dishUid);
	/*
	 * 保存下单记录到数据库
	 */
	 void add(OrdOrderInfo info);
	
	/*
	 * 更新订单状态（试用场景：取消订单、订单标识未领取）
	 */
	 void update(OrdOrderInfo info);

	
	/*
	 * 查询当日所有下单的用户openid
	 */
	 List<String> selectOpenId();
	 
	 /*
	  * 查询订单量
	  */
	 int getCurrentMonthOrderAmt(@Param("date")String date,@Param("activityUid")String activityUid,@Param("formatStr")String formatStr);
	
	 /*
	  * 取消订单
	  */
	 void updateStatusByOrderId(@Param("orderId")String orderId);

}
