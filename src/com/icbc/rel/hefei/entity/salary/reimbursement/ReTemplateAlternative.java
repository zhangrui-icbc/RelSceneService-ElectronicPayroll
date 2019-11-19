package com.icbc.rel.hefei.entity.salary.reimbursement;



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
	/** 列名称 */
	private String name;
	//TODO 备用字段删除的话,对应数据库字段也需要删除
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
