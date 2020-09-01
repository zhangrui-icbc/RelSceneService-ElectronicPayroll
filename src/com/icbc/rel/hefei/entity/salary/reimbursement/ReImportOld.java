package com.icbc.rel.hefei.entity.salary.reimbursement;


import java.util.Date;

/**
 * 导入数据实体类
 * @author fc
 *
 */
public class ReImportOld {
	
	//不需要分组字段,必要时关联模板表查询
	private int reimbursementItemId;//一批多条的项目
	
	private String reimbursementId;//一批一条
	
	private String templateId;//模板ID即为公司id
	
	private String templateColName;//模板名称
	
	private String  importAmount;//导入内容  //导入金额
	
	private Long userId;//员工编号
	
	private int colIndex;//列号
	
	private int category;//分组
	
	
	
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	
	public int getReimbursementItemId() {
		return reimbursementItemId;
	}

	public void setReimbursementItemId(int reimbursementItemId) {
		this.reimbursementItemId = reimbursementItemId;
	}

	public String getReimbursementId() {
		return reimbursementId;
	}

	public void setReimbursementId(String reimbursementId) {
		this.reimbursementId = reimbursementId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}


	public String getTemplateColName() {
		return templateColName;
	}

	public void setTemplateColName(String templateColName) {
		this.templateColName = templateColName;
	}

	public String getImportAmount() {
		return importAmount;
	}

	public void setImportAmount(String importAmount) {
		this.importAmount = importAmount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	
}
