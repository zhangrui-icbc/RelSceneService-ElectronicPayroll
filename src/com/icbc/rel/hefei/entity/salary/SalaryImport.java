package com.icbc.rel.hefei.entity.salary;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 导入数据实体类
 * @author fc
 *
 */
public class SalaryImport {
	
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
	private String realIncome;
	
	//收入合计
	private String  totalRevenue;
	
	//支出合计
	private String  totalExpenditure;

	//工资备注
	private String  salaryRemark;
	
	//专项扣除
	private String  specialDeduction;
	
	//单位支出
	private String  unitExpenditure;
	
	//个性化信息
	private String  specialInfo;
	
	//公司id
	private String companyId;

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


	public String getRealIncome() {
		return realIncome;
	}

	public void setRealIncome(String realIncome) {
		this.realIncome = realIncome;
	}

	public String getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(String totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getTotalExpenditure() {
		return totalExpenditure;
	}

	public void setTotalExpenditure(String totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
	}

	public String getSalaryRemark() {
		return salaryRemark;
	}

	public void setSalaryRemark(String salaryRemark) {
		this.salaryRemark = salaryRemark;
	}

	public String getSpecialDeduction() {
		return specialDeduction;
	}

	public void setSpecialDeduction(String specialDeduction) {
		this.specialDeduction = specialDeduction;
	}

	public String getUnitExpenditure() {
		return unitExpenditure;
	}

	public void setUnitExpenditure(String unitExpenditure) {
		this.unitExpenditure = unitExpenditure;
	}

	public String getSpecialInfo() {
		return specialInfo;
	}

	public void setSpecialInfo(String specialInfo) {
		this.specialInfo = specialInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	

	
}
