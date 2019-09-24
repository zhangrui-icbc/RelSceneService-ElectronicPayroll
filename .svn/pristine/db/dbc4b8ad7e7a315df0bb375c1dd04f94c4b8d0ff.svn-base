package com.icbc.rel.hefei.dao.order;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderDetailInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderInfo;


 public interface OrdOrderDetailInfoDao {
	
	/*
	 * 查询指定日期所有订单详情
	 */
	 List<OrdOrderDetailInfo> getOrderDetailByDate(@Param("activityUid")String activityUid,@Param("date")String date);
	
	/*
	 * 查询订单详情
	 */
	 List<OrdOrderDetailInfo> getOrderDetail(@Param("orderId")String orderId,@Param("activityUid")String activityUid);
	
	/*
	 * 保存下单明细记录到数据库
	 */
	 void insertOrderDetail(OrdOrderDetailInfo info);	
	
	/*
	 * 查询今日菜品预订统计情况
	 */
	 List<OrdMenuInfo> selectOrderReport(Date beginDate,Date endDate,@Param("activityUid")String activityUid);
		

}
