package com.icbc.rel.hefei.entity.salary.reimbursement;


import java.util.Date;

/**
 * ��������ʵ����
 * @author fc
 *
 */
public class ReImportOld {
	
	//����Ҫ�����ֶ�,��Ҫʱ����ģ����ѯ
	private int reimbursementItemId;//һ����������Ŀ
	
	private String reimbursementId;//һ��һ��
	
	private String templateId;//ģ��ID��Ϊ��˾id
	
	private String templateColName;//ģ������
	
	private String  importAmount;//��������  //������
	
	private Long userId;//Ա�����
	
	private int colIndex;//�к�
	
	private int category;//����
	
	
	
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
