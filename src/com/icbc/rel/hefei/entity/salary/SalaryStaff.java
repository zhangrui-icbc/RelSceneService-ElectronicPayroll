package com.icbc.rel.hefei.entity.salary;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SalaryStaff {
	private int id;
	private String  mobile;
	
	private String companyId;
	
	private String name;
	
	private String password;
	
	private String openId;
	
	private String dept;
	//创建时间
	private Date createTime;

	private Date updateTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "SalaryStaff [id=" + id + ", mobile=" + mobile + ", companyId=" + companyId + ", name=" + name
				+ ", password=" + password + ", openId=" + openId + ", dept=" + dept + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
