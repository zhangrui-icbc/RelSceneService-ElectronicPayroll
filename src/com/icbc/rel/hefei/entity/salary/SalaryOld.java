package com.icbc.rel.hefei.entity.salary;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * ������
 * @author fc
 *
 */
public class SalaryOld {
	private String id;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date importTime;

	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date issueTime;
	
	private String excelName;
	
	private String companyId;
	
	private String remark;
	
	private List<SalaryImportOld> importList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public List<SalaryImportOld> getImportList() {
		return importList;
	}

	public void setImportList(List<SalaryImportOld> importList) {
		this.importList = importList;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
