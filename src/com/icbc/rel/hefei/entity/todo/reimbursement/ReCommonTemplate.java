package com.icbc.rel.hefei.entity.todo.reimbursement;

public class ReCommonTemplate {
private int  id;
	
	//实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;税:tax;岗位工资:post_salary;绩效工资:merit_pay;加班费:overtime_pay;全勤奖:attendance_bonus;交通费:car_fare;过节费:festival_bonus;伙食补贴:food_allowance;住房公积金:housing_provident_fund;养老保险:endowment_insurance;医保金:medical_insurance_fund;失业金:unemployment_compensation;房补:rent_assistance;水电费:electricity_water
	private String type;
	
	private String name; //列名称
	
	private int colIndex; //列号
	
	private String remark;//备注
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	
	

}
