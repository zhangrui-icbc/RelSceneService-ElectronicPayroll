package com.icbc.rel.hefei.entity.todo.reimbursement;



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
	/** �ֶ�����:ʵ������:real_income;����ϼ�:total_income;֧���ϼ�:total_expenditure;˰:tax;��λ����:post_salary;��Ч����:merit_pay;�Ӱ��:overtime_pay;ȫ�ڽ�:attendance_bonus;��ͨ��:car_fare;���ڷ�:festival_bonus;��ʳ����:food_allowance;ס��������:housing_provident_fund;���ϱ���:endowment_insurance;ҽ����:medical_insurance_fund;ʧҵ��:unemployment_compensation;����:rent;ˮ���:electricity_water */
	private String type;
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
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
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
