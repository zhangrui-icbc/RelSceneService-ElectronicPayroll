package com.icbc.rel.hefei.entity.salary.reimbursement;

/**
 * ģ��ʵ����
 * @author fc
 *
 */
public class ReCustomTemplate {
	private int  id;
	
	private String companyId;
	
	private String name; //������
	
	private int colIndex; //�к�
	
	private int category;//����.1����ϼ�,2֧���ϼ�,3ʵ������,0�޷���

	
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
