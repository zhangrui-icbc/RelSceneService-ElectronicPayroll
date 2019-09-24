package com.icbc.rel.hefei.entity;

/*
 * 数据日志表
 */
public class SysLogTableInfo {
private int IID;
private String LogUid;
private String TableName;//表名
private int PrimaryKey;//主键
public int getIID() {
	return IID;
}
public void setIID(int iID) {
	IID = iID;
}
public String getTableName() {
	return TableName;
}
public void setTableName(String tableName) {
	TableName = tableName;
}
public int getPrimaryKey() {
	return PrimaryKey;
}
public void setPrimaryKey(int primaryKey) {
	PrimaryKey = primaryKey;
}
public String getLogUid() {
	return LogUid;
}
public void setLogUid(String logUid) {
	LogUid = logUid;
}
}
