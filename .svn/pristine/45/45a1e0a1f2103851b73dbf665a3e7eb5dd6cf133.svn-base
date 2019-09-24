package com.icbc.rel.hefei.service.order;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.icbc.rel.hefei.dao.order.OrdOrderInfoDao;
import com.icbc.rel.hefei.util.CommonUtil;

@Service
public class ReportService {
	@Autowired
	private OrdOrderInfoDao orderInfoDao;

	
	
	/*
	 * 查询年度订单数
	 */
	public Integer getCurrentYearOrderAmt(String activityUid) {
		return orderInfoDao.getCurrentMonthOrderAmt(CommonUtil.DateConvertStr(new Date(), "yyyy"),activityUid,"%Y");
	}
	 /*
	  * 查询当月订单量
	  */
	 public Integer getCurrentMonthOrderAmt(String activityUid) {
		 return orderInfoDao.getCurrentMonthOrderAmt(CommonUtil.DateConvertStr(new Date(), "yyyy-MM"),activityUid,"%Y-%m");
	 }
	
}
