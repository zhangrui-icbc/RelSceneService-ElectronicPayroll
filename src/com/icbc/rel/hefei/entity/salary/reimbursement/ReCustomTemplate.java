package com.icbc.rel.hefei.entity.salary.reimbursement;

/**
 * 模板实体类
 * @author fc
 *
 */
public class ReCustomTemplate {
	private int  id;
	
	private String companyId;
	
	private String name; //列名称
	
	private int colIndex; //列号
	
	private int category;//分组.1收入合计,2支出合计,3实际收入,0无分组

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	
	
	
}
