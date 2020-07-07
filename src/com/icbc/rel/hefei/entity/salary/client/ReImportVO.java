package com.icbc.rel.hefei.entity.salary.client;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 导入数据实体类
 * @author fc
 *
 */
public class ReImportVO {
	
	private int id;
	
	//批次号
	private String batchNo;
	
	//员工编号/手机号码
	private String userId;
	
	//工资发放时间
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date issueTime;
	
	//创建时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	
	//实际收入
	private String totalReim;
	
	//个性化信息
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
