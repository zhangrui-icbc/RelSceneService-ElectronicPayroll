package com.icbc.rel.hefei.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.icbc.rel.hefei.util.EnumUtil;

public class SysActivityInfo implements Cloneable{
	
	private Integer IID;
	@FieldMeta(name="公众号Id")
	private String MpId;//公众号Id
	@FieldMeta(name="公众号名称")
	private String MpName;//公众号名称
	@FieldMeta(name="场景编号")
	private String RelSceneUid;//场景Uid
	@FieldMeta(name="活动编号")
	private String RelSceneName;//场景名称
	@FieldMeta(name="活动概述")
	private String ActivityDesc;//活动概述
	@FieldMeta(name="活动链接")
	private String ActivityUrl;//活动链接
	@FieldMeta(name="活动编号")
	private String ActivityUid;//活动编号
	@FieldMeta(name="活动名称")
	private String ActivityName;//活动名称
	@FieldMeta(name="活动状态")
	private int Status;//活动状态
	@FieldMeta(name="开始时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	 private Date BeginTime;
	@FieldMeta(name="结束时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	 private Date EndTime;
	@FieldMeta(name="创建时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	 private Date CreateTime;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	 private Date ModifyTime;
	@FieldMeta(name="配置参数集合")
	private String ConfigJson;//配置参数集合

	public Integer getIID() {
		return IID;
	}

	public void setIID(Integer iID) {
		IID = iID;
	}


	public String getRelSceneUid() {
		return RelSceneUid;
	}

	public void setRelSceneUid(String relSceneUid) {
		RelSceneUid = relSceneUid;
	}

	public String getMpId() {
		return MpId;
	}

	public void setMpId(String mpId) {
		MpId = mpId;
	}
	public String getMpName() {
		return MpName;
	}

	public void setMpName(String mpName) {
		MpName = mpName;
	}
	public String getActivityUid() {
		return ActivityUid;
	}

	public void setActivityUid(String activityUid) {
		ActivityUid = activityUid;
	}
	
	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getActivityName() {
		return ActivityName;
	}

	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}

	public Date getBeginTime() {
		return BeginTime;
	}

	public void setBeginTime(Date beginTime) {
		BeginTime = beginTime;
	}

	public Date getEndTime() {
		return EndTime;
	}

	public void setEndTime(Date endTime) {
		EndTime = endTime;
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

	public void setModifyTime(Date modifyTime) {
		ModifyTime = modifyTime;
	}

	
	public String getRelSceneName() {
		return EnumUtil.getSceneName(RelSceneUid);
	}

	public void setRelSceneName(String relSceneName) {
		RelSceneName = relSceneName;
	}


	public String getActivityDesc() {
		return ActivityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		ActivityDesc = activityDesc;
	}

	public String getActivityUrl() {
		return ActivityUrl;
	}

	public void setActivityUrl(String activityUrl) {
		ActivityUrl = activityUrl;
	}
	
	public Object clone() 
	{ 
	Object o=null; 
	try
	{ 
	o=(SysActivityInfo)super.clone();//Object 中的clone()识别出你要复制的是哪一个对象。 
	} 
	catch(CloneNotSupportedException e) 
	{ 
	System.out.println(e.toString()); 
	} 
	return o; 
	} 

	public String getConfigJson() {
		return ConfigJson;
	}

	public void setConfigJson(String configJson) {
		ConfigJson = configJson;
	}
	
}
