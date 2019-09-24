package com.icbc.rel.hefei.entity;

import java.util.Date;

/*
 * 操作日志表
 */
public class SysLogOperation {
    private int IID;
    private String LogUid;
    private int OperateType;//1-新增 2-更新 3-删除
    private String ActivityName;//活动名称
    private String SceneName;//场景名称
    private String Operation;//用户行为
    private String CreateUser;
    private Date CreateTime;
    private String TableName;
	public int getIID() {
		return IID;
	}
	public void setIID(int iID) {
		IID = iID;
	}
	public String getLogUid() {
		return LogUid;
	}
	public void setLogUid(String logUid) {
		LogUid = logUid;
	}
	public int getOperateType() {
		return OperateType;
	}
	public void setOperateType(int operateType) {
		OperateType = operateType;
	}
	public String getActivityName() {
		return ActivityName;
	}
	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}
	public String getCreateUser() {
		return CreateUser;
	}
	public void setCreateUser(String createName) {
		CreateUser = createName;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	public String getSceneName() {
		return SceneName;
	}
	public void setSceneName(String sceneName) {
		SceneName = sceneName;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
}
