package com.icbc.rel.hefei.entity.salary.reimbursement;



/**
 * ģ���ֶα�ѡ�� oa_salary_template_alternative
 * 
 * @author ruoyi
 * @date 2019-06-10
 */
public class ReTemplateAlternative 
{
	
	/** ����id */
	private Integer id;
	/** ������ */
	private String name;
	/** ����.1����ϼ�,2֧���ϼ�,3ʵ������,0�޷��� */
	private Integer category;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

}
