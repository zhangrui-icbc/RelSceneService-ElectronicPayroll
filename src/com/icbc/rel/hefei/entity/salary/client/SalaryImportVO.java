package com.icbc.rel.hefei.entity.salary.client;



/**
 * ��������ʵ����
 * @author fc
 *
 */
public class SalaryImportVO {
	//����Ҫ�����ֶ�,��Ҫʱ����ģ����ѯ
	private int salaryItemId;//һ����������Ŀ
	
	private String salaryId;//һ��һ��
	
	private String templateId;//ģ��ID��Ϊ��˾id
	
	private String templateColType;//ģ���ֶ�����
	
	private String templateColName;//ģ������
	
	private String importAmount;//������
	
	private Long userId;//Ա�����
	
	private int colIndex;//�к�
	
	private int category;//����
	
	
	
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
	
	
}
