package com.icbc.rel.hefei.entity.order;

import java.util.Date;

public class OrdImportInfo {
	private int IID;
	private String FileUid;//�ļ���� 
	private String ActivityUid;//����
	private String FileName;//�ļ�����
	private int Status;//״̬ 1��������-1 ��ʾ����
	private Date ImportTime;
	public int getIID() {
		return IID;
	}
	public void setIID(int iID) {
		IID = iID;
	}
	
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	
	public Date getImportTime() {
		return ImportTime;
	}
	public void setImportTime(Date importTime) {
		ImportTime = importTime;
	}
	
	public String getFileUid() {
		return FileUid;
	}
	public void setFileUid(String fileUid) {
		FileUid = fileUid;
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
	

}
