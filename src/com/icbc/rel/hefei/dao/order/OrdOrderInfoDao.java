package com.icbc.rel.hefei.dao.order;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderDetailInfo;
import com.icbc.rel.hefei.entity.order.OrdOrderInfo;


 public interface OrdOrderInfoDao {
	
	
	
	/*
	 * ��ѯ�������ж���
	 */
	 List<OrdOrderInfo> getOrderInfos(@Param("openid")String openid,@Param("activityUid")String activityUid);
	
	/*
	 * ��ѯָ���������ж���
	 */
	 List<OrdOrderInfo> getOrderInfoByDate(@Param("activityUid")String activityUid,@Param("date")String date,@Param("mobileNo")String mobileNo);

	
	
	/*
	 * ���ݶ�����Ų�ѯ��������
	 */
	 OrdOrderInfo getOrderInfoByOrderId(@Param("orderId")String orderId,@Param("activityUid")String activityUid);
	
	 /*
	* ��ѯ�û���Ʒ�Ѷ�����(����)
	*/
	int getOrderAmount(@Param("openid")String openid,@Param("dishUid")String dishUid);
	/*
	 * �����µ���¼�����ݿ�
	 */
	 void add(OrdOrderInfo info);
	
	/*
	 * ���¶���״̬�����ó�����ȡ��������������ʶδ��ȡ��
	 */
	 void update(OrdOrderInfo info);

	
	/*
	 * ��ѯ���������µ����û�openid
	 */
	 List<String> selectOpenId();
	 
	 /*
	  * ��ѯ������
	  */
	 int getCurrentMonthOrderAmt(@Param("date")String date,@Param("activityUid")String activityUid,@Param("formatStr")String formatStr);
	
	 /*
	  * ȡ������
	  */
	 void updateStatusByOrderId(@Param("orderId")String orderId);

}
