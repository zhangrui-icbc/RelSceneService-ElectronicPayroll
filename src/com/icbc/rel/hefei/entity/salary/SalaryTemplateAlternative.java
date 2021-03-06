package com.icbc.rel.hefei.entity.salary;



/**
 * 模板字段备选表 oa_salary_template_alternative
 * 
 * @author ruoyi
 * @date 2019-06-10
 */
public class SalaryTemplateAlternative 
{
	
	/** 主键id */
	private Integer id;
	
	private String companyId;
	
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
}
