package com.icbc.rel.hefei.entity.order;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icbc.rel.hefei.entity.FieldMeta;

//点餐参数配置表
public class OrdParaInfo {
    private int iID;
    @FieldMeta(name="活动编号")
     private String activityUid;//活动编号
    @FieldMeta(name="活动名称")
     private String activityName;//活动名称
    @FieldMeta(name="预订时间类型")
     private int orderTimeType;//预订时间类型
     @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
     @FieldMeta(name="开始预订时间")
     private Date orderBeginTime;//开始预订时间
     @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
     @FieldMeta(name="结束预订时间")
     private Date orderEndTime;//结束预订时间
    /* @FieldMeta(name="预定上限")
     private int orderLimit;//每人每天预订单个菜品数量上限
*/     @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
     @FieldMeta(name="可取消预订的时间")
     private Date cancelTime;//可取消预订的时间
     @FieldMeta(name="领取时间")
     private String takeTime;//领取时间
     @FieldMeta(name="领取地点")
     private String takeLocation;//领取地点
     @FieldMeta(name="联系电话")
     private String telephone;//联系电话
     private String activityDesc;
     
	public String getActivityUid() {
		return activityUid;
	}
	public void setActivityUid(String activityUid) {
		this.activityUid = activityUid;
	}
	public int getOrderTimeType() {
		return orderTimeType;
	}
	public void setOrderTimeType(int orderTimeType) {
		this.orderTimeType = orderTimeType;
	}
	public Date getOrderBeginTime() {
		return orderBeginTime;
	}
	public void setOrderBeginTime(Date orderBeginTime) {
		this.orderBeginTime = orderBeginTime;
	}
	public Date getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	/*public int getOrderLimit() {
		return orderLimit;
	}
	public void setOrderLimit(int orderLimit) {
		this.orderLimit = orderLimit;
	}*/
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public int getiID() {
		return iID;
	}
	public void setiID(int iID) {
		this.iID = iID;
	}
	public String getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}
	public String getTakelocation() {
		return takeLocation;
	}
	public void setTakelocation(String takelocation) {
		this.takeLocation = takelocation;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	
}
