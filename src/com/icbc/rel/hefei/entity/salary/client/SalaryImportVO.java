package com.icbc.rel.hefei.entity.salary.client;

import com.ibm.math.BigDecimal;

/**
 * 导入数据实体类
 * @author fc
 *
 */
public class SalaryImportVO {
	//不需要分组字段,必要时关联模板表查询
	private int salaryItemId;//一批多条的项目
	
	private String salaryId;//一批一条
	
	private String templateId;//模板ID即为公司id
	
	private String templateColType;//模板字段类型
	
	private String templateColName;//模板名称
	
	private String importAmount;//导入金额
	
	private Long userId;//员工编号
	
	private int colIndex;//列号
	
	private int category;//分组
	
	
	
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSalaryItemId() {
		return salaryItemId;
	}

	public void setSalaryItemId(int salaryItemId) {
		this.salaryItemId = salaryItemId;
	}

	public String getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateColType() {
		return templateColType;
	}

	public void setTemplateColType(String templateColType) {
		this.templateColType = templateColType;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + category;
		result = prime * result + colIndex;
		result = prime * result + ((importAmount == null) ? 0 : importAmount.hashCode());
		result = prime * result + ((salaryId == null) ? 0 : salaryId.hashCode());
		result = prime * result + salaryItemId;
		result = prime * result + ((templateColName == null) ? 0 : templateColName.hashCode());
		result = prime * result + ((templateColType == null) ? 0 : templateColType.hashCode());
		result = prime * result + ((templateId == null) ? 0 : templateId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryImportVO other = (SalaryImportVO) obj;
		if (category != other.category)
			return false;
		if (colIndex != other.colIndex)
			return false;
		if (importAmount == null) {
			if (other.importAmount != null)
				return false;
		} else if (!importAmount.equals(other.importAmount))
			return false;
		if (salaryId == null) {
			if (other.salaryId != null)
				return false;
		} else if (!salaryId.equals(other.salaryId))
			return false;
		if (salaryItemId != other.salaryItemId)
			return false;
		if (templateColName == null) {
			if (other.templateColName != null)
				return false;
		} else if (!templateColName.equals(other.templateColName))
			return false;
		if (templateColType == null) {
			if (other.templateColType != null)
				return false;
		} else if (!templateColType.equals(other.templateColType))
			return false;
		if (templateId == null) {
			if (other.templateId != null)
				return false;
		} else if (!templateId.equals(other.templateId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}


	
}
