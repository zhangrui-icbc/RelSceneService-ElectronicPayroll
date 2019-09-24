package com.icbc.rel.hefei.entity.order;

import java.util.Date;

import com.icbc.rel.hefei.util.CommonUtil;

public class OrdOrderInfo {
	
	private int IID;
	private Date OrderDate;
	private String OrderId;
	private String OpenId;
	private String MoblieNo;
	private String UserName;
	private String UserStruName;
	private String UniNo;
	private String OrderDesc;
	private float OrderAmt;
	private Date CreateTime;
	private String activityUid;
	/*
	 * 订单状态
	 */
	private int OrderStatus;// 1 已完成 -1 已取消
	
	/*
	 * 取消订单的时间
	 */
	private Date ModifyTime;
	private String createTimeDesc;

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
	public String getOpenId() {
		return OpenId;
	}
	public void setOpenId(String openId) {
		OpenId = openId;
	}
	public String getMoblieNo() {
		return MoblieNo;
	}
	public void setMoblieNo(String moblieNo) {
		MoblieNo = moblieNo;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUniNo() {
		return UniNo;
	}
	public void setUniNo(String uniNo) {
		UniNo = uniNo;
	}
	public int getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		OrderStatus = orderStatus;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	public Date getModifyTime() {
		return ModifyTime;
	}
	public void setModifyTime(Date cancelTime) {
		ModifyTime = cancelTime;
	}
	public String getOrderDesc() {
		return OrderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		OrderDesc = orderDesc;
	}
	public float getOrderAmt() {
		return OrderAmt;
	}
	public void setOrderAmt(float orderAmt) {
		OrderAmt = orderAmt;
	}
	public String getOrderStatusDesc() {
		if(this.OrderStatus==1) {
			return "已完成";
		}else if(this.OrderStatus==-1) {
			return "已取消";
		}
		return "未领取";
	}

	public String getUserStruName() {
		return UserStruName;
	}
	public void setUserStruName(String userStruName) {
		UserStruName = userStruName;
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
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc() {
		this.createTimeDesc = CommonUtil.DateConvertStr(this.CreateTime, "yyyy-MM-dd HH:mm:ss");
	}

}
