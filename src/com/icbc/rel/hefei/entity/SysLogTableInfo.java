package com.icbc.rel.hefei.entity;

/*
 * ������־��
 */
public class SysLogTableInfo {
private int IID;
private String LogUid;
private String TableName;//����
private int PrimaryKey;//����
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
