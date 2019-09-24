package com.icbc.rel.hefei.dao.order;

import com.icbc.rel.hefei.entity.order.OrdParaInfo;

public interface OrdParaInfoDao {
	
	
	/*
	 * 查询系统参数详细配置
	 */
	 OrdParaInfo getOrderPara(String activityUid);
	
	/*
	 * 添加订餐参数详细配置
	 */
	 void insertPara(OrdParaInfo info);
    
	/*
	 * 更新参数配置
	 */
	 void updatePara(OrdParaInfo info);
	

}
