package com.icbc.rel.hefei.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.order.OrdParaInfoDao;
import com.icbc.rel.hefei.entity.order.OrdParaInfo;
@Service
public class ParaService {
	@Autowired
	private OrdParaInfoDao paraInfoDao;
	
	/*
	 * 查询指定活动编号的订餐参数配置
	 */
	public  OrdParaInfo getOrderPara(String activityUid) {
		return paraInfoDao.getOrderPara(activityUid);
	}
	
	/*
	 * 添加订餐参数配置
	 */
	public void insertPara(OrdParaInfo info) {
		 paraInfoDao.insertPara(info);
	}
	/*
	 * 更新订餐参数配置
	 */
	public void updatePara(OrdParaInfo info) {
		paraInfoDao.updatePara(info);
	}

}
