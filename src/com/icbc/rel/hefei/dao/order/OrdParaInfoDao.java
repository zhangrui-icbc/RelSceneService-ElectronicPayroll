package com.icbc.rel.hefei.dao.order;

import com.icbc.rel.hefei.entity.order.OrdParaInfo;

public interface OrdParaInfoDao {
	
	
	/*
	 * ��ѯϵͳ������ϸ����
	 */
	 OrdParaInfo getOrderPara(String activityUid);
	
	/*
	 * ��Ӷ��Ͳ�����ϸ����
	 */
	 void insertPara(OrdParaInfo info);
    
	/*
	 * ���²�������
	 */
	 void updatePara(OrdParaInfo info);
	

}
