package com.icbc.rel.hefei.entity;

import java.util.Date;

public class SysSnDictionary {
	private String iID;
	private String prefix; //前缀
	private Date availableDate;//日期
	private Integer serialNumber;//序号
	private Date createTime;
	private String activityUid;//活动编号
	public String getiID() {
		return iID;
	}
	public void setiID(String iID) {
		this.iID = iID;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Date getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getActivityUid() {
		return activityUid;
	}
	public void setActivityUid(String activityUid) {
		this.activityUid = activityUid;
	}
	

}
