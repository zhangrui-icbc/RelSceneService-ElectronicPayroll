package com.icbc.rel.hefei.entity.order;

import java.util.Date;

public class OrdOrderDetailInfo {
	
	private int IID;
	private Date OrderDate;
	private String OrderId;
	private String DishUid;
	private String DishName;
	private float DishPrice;
	private int DishAmount;
	private Date CreateTime;
	private String activityUid;
	public int getIID() {
		return IID;
	}
	public void setIID(int iID) {
		IID = iID;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getDishUid() {
		return DishUid;
	}
	public void setDishUid(String dishUid) {
		DishUid = dishUid;
	}
	public String getDishName() {
		return DishName;
	}
	public void setDishName(String dishName) {
		DishName = dishName;
	}
	public float getDishPrice() {
		return DishPrice;
	}
	public void setDishPrice(float dishPrice) {
		DishPrice = dishPrice;
	}
	public int getDishAmount() {
		return DishAmount;
	}
	public void setDishAmount(int dishAmount) {
		DishAmount = dishAmount;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	public Date getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}
	public String getActivityUid() {
		return activityUid;
	}
	public void setActivityUid(String activityUid) {
		this.activityUid = activityUid;
	}

}
