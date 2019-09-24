package com.icbc.rel.hefei.dao.order;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.order.OrdMenuInfo;

public interface OrdMenuInfoDao {
	/*
	 * 添加菜品
	 */
	void add(OrdMenuInfo dish);
	
	/*
	 * 查询导入的菜单
	 */
	List<OrdMenuInfo> selectByuid(String uid);
	
	/*
	 * 查询菜品信息（加锁）
	 */
	OrdMenuInfo getLockedMenuInfo(String dishUid);
	/*
	 * 更新菜品剩余数量
	 */
	void updateUsedAmount(OrdMenuInfo info);
	
	/*
	 * 查询菜品日期
	 */
	Date selectDate(String DishUid);

/*	
	 * 按日期查询当日午餐菜单
	 
	List<OrdMenuInfo> selectLunch(Date parm) ;
	
	
	 * 按日期查询当日晚餐菜单
	 
	List<OrdMenuInfo> selectSupper(Date parm);
	
	
	 * 按日期查询当日晚餐菜单
	 
	List<OrdMenuInfo> selectBreakfast(Date parm);*/
	
	/*
	 * 按date查询当日午餐菜名
	 */
	List<String> selectName(Date parm);
	
	/*
	 * 删除菜单
	 */
	void delete(String uid);
	
	/*
	 * 查询外卖菜单
	 */
	 List<OrdMenuInfo> getDinnerByDate(@Param("date")String date,@Param("activityUid")String activityUid);
	 
	 /*
	 * 查询外卖菜单
	 */
	 List<OrdMenuInfo> getDinnerByDate2(@Param("beginDate")Date beginDate,@Param("endDate")Date endDate);
	
}
