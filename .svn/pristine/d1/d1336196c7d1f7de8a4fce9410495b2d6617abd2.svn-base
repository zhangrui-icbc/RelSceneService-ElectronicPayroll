package com.icbc.rel.hefei.dao.order;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.order.OrdShoppingCar;

public interface OrdShoppingCarDao {

	/*
	 * 查询当前购物车菜品
	 */
	 List<OrdShoppingCar> selectByOpenid(@Param("openid")String openid,@Param("activityUid")String activityUid);
	
	/*
	 * 向购物车添加某个菜品
	 */
	 boolean Insert(OrdShoppingCar info);
	
	/*
	 * 更新购物车某个菜品的数量
	 */
	 boolean Update(OrdShoppingCar info);
	
	/*
	 * 更新购物车失效时间
	 */
	 boolean updateDeadline(OrdShoppingCar info);
	
	
    
	/*
	 * 从购物车删除指定菜品
	 */
	 boolean delete(OrdShoppingCar info);
	
	/*
	 * 删除失效的购物车菜品
	 */
	 boolean deleteDeadline();
	
	
	
	/*
	 * 清空购物车
	 */
	 boolean deleteAll(@Param("openid")String openid,@Param("activityUid")String activityUid);

	/*
	 * 查询用户菜品已订份数(购物车内)
	 */
	 int getOrderAmount(@Param("openid")String openid,@Param("dishUid")String dishUid);

}
