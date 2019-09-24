package com.icbc.rel.hefei.service.order;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.dao.order.OrdMenuInfoDao;
import com.icbc.rel.hefei.dao.order.OrdOrderInfoDao;
import com.icbc.rel.hefei.dao.order.OrdParaInfoDao;
import com.icbc.rel.hefei.dao.order.OrdShoppingCarDao;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.entity.order.OrdParaInfo;
import com.icbc.rel.hefei.entity.order.OrdShoppingCar;

@Service
public class ShoppingCarService {
	@Autowired
	private OrdShoppingCarDao shoppingCarDao;
	@Autowired
	private OrdMenuInfoDao  menuInfoDao;
	@Autowired
	private OrdOrderInfoDao orderInfoDao;
	/*
	 * 修改购物车内容，包含添加，调整数量
	 * flag=1 新加购物车或者数量加1，flag=-1：数量减1
	 */
	public Msg ChangeDishShoppingCar(String relId,String dishUid,String dishName,float price,int flag)
	{
		Msg msg=new Msg();
		OrdMenuInfo  dish=menuInfoDao.getLockedMenuInfo(dishUid);
		//购物车变更数量，需要检查可用是否充足
		if(flag==1) {
			
			if(dish.getAvailableAmount()<1)
			{
				msg.setCode(-1);
				msg.setMessage("菜品剩余数量不足");
				 return msg;
			}
			int amount=shoppingCarDao.getOrderAmount(relId,dishUid);
			amount+=orderInfoDao.getOrderAmount(relId, dishUid);
			if(amount>=dish.getMenuLimit() && dish.getMenuLimit()!=0)
			{
				msg.setCode(-1);
				msg.setMessage("该菜品每人最高只可预订"+dish.getMenuLimit()+"份");
			    return msg;
			}
		}
		shoppingCarDao.deleteDeadline();
		List<OrdShoppingCar> result=shoppingCarDao.selectByOpenid(relId,dish.getActivityUid());
		Optional<OrdShoppingCar> item=result.stream().filter(x->x.getDishUid().equals(dishUid)).findFirst();
		Date now=new Date();
		Date deadlineTime=new Date(now.getTime()+1000*60*20);
		if(item.isPresent())
		{
			OrdShoppingCar info=item.orElse(null);
			//更新数量加一
			if(flag==1)
			{
			    info.setDishPrice(info.getDishPrice()/info.getDishAmount()*(info.getDishAmount()+1));
			    info.setDishAmount(info.getDishAmount()+1);
			    shoppingCarDao.Update(info);
			}
			else {
				//移除该菜品
				if(info.getDishAmount()==1)
				{
					shoppingCarDao.delete(info);
				}else {
					info.setDishPrice(info.getDishPrice()/info.getDishAmount()*(info.getDishAmount()-1));
				    info.setDishAmount(info.getDishAmount()-1);
				    shoppingCarDao.Update(info);
				}
			}
		}
		else
		{
			msg.setCount(result.size()+1);
			//新加入购物车
			if(flag==1)
			{
				OrdShoppingCar info=new OrdShoppingCar();
				info.setDishName(dishName);
				info.setDishUid(dishUid);
				info.setDishAmount(1);
				info.setDishPrice(price);
				info.setOpenId(relId);
				info.setCreateTime(now);
				info.setActivityUid(dish.getActivityUid());
				info.setDeadlineTime(deadlineTime);
				shoppingCarDao.Insert(info);
			}
		}
		//更新剩余数量
		dish.setUsedAmount(dish.getUsedAmount()+flag);
		menuInfoDao.updateUsedAmount(dish);
		//更新失效时间（先加入购物车尚未失效的菜品，失效时间更新为最后一个加入购物车菜品的失效时间）
		OrdShoppingCar car=new OrdShoppingCar();
		car.setOpenId(relId);
		car.setDeadlineTime(deadlineTime);
		shoppingCarDao.updateDeadline(car);
		result=shoppingCarDao.selectByOpenid(relId,dish.getActivityUid());
		int count=0;
		for(int i=0;i<result.size();i++) {
			//处理购物车的数量@ILNIQ
			count+=result.get(i).getDishAmount();
		}
		
		
		JSONObject json=new JSONObject();
		json.put("data", result);
		json.put("count", count);
		json.put("totalPrice", result.stream().mapToDouble(x->x.getDishPrice()).sum());
		msg.setData(json);
		msg.setCode(1);
		msg.setMessage("修改成功");
	    return msg;
	}
	/*
	 * 查询购物车菜品
	 */
    public List<OrdShoppingCar> getShoppingCar(String relId,String activityUid) {
    	return shoppingCarDao.selectByOpenid(relId, activityUid);
    }
    
    /*
     * 清空购物车内容
     */
    public void deleteShoppingCar(String relId,String activityUid) {
    	shoppingCarDao.deleteAll(relId,activityUid);
    }
}
