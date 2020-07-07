package com.icbc.rel.hefei.entity.salary.client;


import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * ≈˙¥Œ¿‡
 * @author fc
 *
 */
public class SalaryVO {
	private String id;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date importTime;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date issueTime;
	
	private String excelName;
	
	private List<SalaryImportVO> importList;
	
	private Long companyId;
	
	private String remark;
	

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

	public List<SalaryImportVO> getImportList() {
		return importList;
	}

	public void setImportList(List<SalaryImportVO> importList) {
		this.importList = importList;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
