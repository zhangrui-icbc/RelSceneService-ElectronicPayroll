package com.icbc.rel.hefei.entity.todo.reimbursement;



/**
 * 模板字段备选表 oa_salary_template_alternative
 * 
 * @author ruoyi
 * @date 2019-06-10
 */
public class ReTemplateAlternative 
{
	
	/** 主键id */
	private Integer id;
	/** 字段类型:实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;税:tax;岗位工资:post_salary;绩效工资:merit_pay;加班费:overtime_pay;全勤奖:attendance_bonus;交通费:car_fare;过节费:festival_bonus;伙食补贴:food_allowance;住房公积金:housing_provident_fund;养老保险:endowment_insurance;医保金:medical_insurance_fund;失业金:unemployment_compensation;房租:rent;水电费:electricity_water */
	private String type;
	/** 列名称 */
	private String name;
	/** 分组.1收入合计,2支出合计,3实际收入,0无分组 */
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
