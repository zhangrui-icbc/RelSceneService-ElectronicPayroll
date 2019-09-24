package com.icbc.rel.hefei.service.order;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.icbc.rel.hefei.dao.order.OrdImportPicDao;
import com.icbc.rel.hefei.dao.order.OrdMenuInfoDao;
import com.icbc.rel.hefei.entity.order.OrdImportPicInfo;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.util.CommonUtil;
@Service
public class MenuInfoService {
	private static Logger logger = Logger.getLogger(MenuInfoService.class);

	@Autowired
	private OrdMenuInfoDao menuInfoDao;
	
	/*
	 * 查询导入的菜单
	 */
	public List<OrdMenuInfo> getMenuDetail(String uid){
		return menuInfoDao.selectByuid(uid);
	}
	
	/*
	 * 查询菜品日期
	 */
	public Date getMenuDate(String dishUid){
		return menuInfoDao.selectDate(dishUid);
	}
	
	/*
	 * 根据指定日期来查询外卖菜单
	 */
	public List<OrdMenuInfo> getMenuByDate(Date date,String activityUid){
		return menuInfoDao.getDinnerByDate(CommonUtil.DateConvertStr(date, "yyyy-MM-dd"),activityUid);
	}
	
	
	/*
	 * 取消订单后更新菜品数量@ILNIQ
	 */
	public void updateMenuBydishUid(String dishUid,int num){
				
		OrdMenuInfo  dish=menuInfoDao.getLockedMenuInfo(dishUid);
		int oldNum=dish.getUsedAmount();
		//更新数据
		int UsedNum=dish.getUsedAmount()-num;
		dish.setUsedAmount(UsedNum);
		logger.info("菜品"+dishUid+"已使用数量由："+oldNum+"变为："+UsedNum);
		
		try {			
			menuInfoDao.updateUsedAmount(dish);
			logger.info("更新成功");
			}catch(Exception ex){
				logger.info("更新失败",ex);
		}
		
			
	}
	
	
	
	
	
	
}
