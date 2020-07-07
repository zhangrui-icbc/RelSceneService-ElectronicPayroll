package com.icbc.rel.hefei.entity.salary.client;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * ��������ʵ����
 * @author fc
 *
 */
public class ReImportVO {
	
	private int id;
	
	//���κ�
	private String batchNo;
	
	//Ա�����/�ֻ�����
	private String userId;
	
	//���ʷ���ʱ��
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date issueTime;
	
	//����ʱ��
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	
	//ʵ������
	private String totalReim;
	
	//���Ի���Ϣ
	private String  specialInfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTotalReim() {
		return totalReim;
	}

	public void setTotalReim(String totalReim) {
		this.totalReim = totalReim;
	}

	public String getSpecialInfo() {
		return specialInfo;
	}

	public void setSpecialInfo(String specialInfo) {
		this.specialInfo = specialInfo;
	}


	
}
