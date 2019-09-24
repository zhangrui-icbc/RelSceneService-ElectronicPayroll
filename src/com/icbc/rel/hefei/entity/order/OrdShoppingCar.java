package com.icbc.rel.hefei.entity.order;

import java.util.Date;

/*
 * 购物车实体类
 */
public class OrdShoppingCar {
	
	private int iID;
	private String openId;
	private String dishUid;
	private String dishName;
	private float dishPrice;
	private int dishAmount;
	private Date createTime;
	/*
	 * 失效时间
	 */
	private Date deadlineTime;
	private String activityUid;//活动编号
	public int getIID() {
		return iID;
	}
	public void setIID(int iID) {
		this.iID = iID;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getDishUid() {
		return dishUid;
	}
	public void setDishUid(String dishUid) {
		this.dishUid = dishUid;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public float getDishPrice() {
		return dishPrice;
	}
	public void setDishPrice(float dishPrice) {
		this.dishPrice = dishPrice;
	}
	public int getDishAmount() {
		return dishAmount;
	}
	public void setDishAmount(int dishAmount) {
		this.dishAmount = dishAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDeadlineTime() {
		return deadlineTime;
	}
	public void setDeadlineTime(Date deadlineTime) {
		this.deadlineTime = deadlineTime;
	}
	public String getActivityUid() {
		return activityUid;
	}
	public void setActivityUid(String activityUid) {
		this.activityUid = activityUid;
	}

}
