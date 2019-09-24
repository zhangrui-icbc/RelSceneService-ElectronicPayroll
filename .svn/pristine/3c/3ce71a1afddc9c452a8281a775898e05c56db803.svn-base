package com.icbc.rel.hefei.entity.order;

import java.util.Date;

public class OrdMenuInfo implements Cloneable  {
	
	
    private int IID;
    private String ActivityUid;
    private Date MenuDate;
    private String Title;
    private String ClassifyName;
    private String SetmealName;
    private String DishUid;
    private String DishName;
    private String DishDesc;
    private float DishPrice;
    private int Amount;
    private int UsedAmount;//使用数量
    public int AvailableAmt;//剩余数量
    private String MenuUid;
    private int  MenuType;//1-早餐 2-中餐 3-晚餐  0-外卖
    private String SheetName;
    private String DishUnit;
    private int MenuLimit;
    private Date CreateTime;
    
    private String PicUrl;
    
    public Object clone() 
    { 
     Object o=null; 
    try
     { 
     o=(OrdMenuInfo)super.clone();//Object 中的clone()识别出你要复制的是哪一个对象。 
     } 
    catch(CloneNotSupportedException e) 
     { 
      System.out.println(e.toString()); 
     } 
    return o; 
    } 
    
	public int getIID() {
		return IID;
	}
	public void setIID(int iID) {
		IID = iID;
	}
	public Date getMenuDate() {
		return MenuDate;
	}
	public void setMenuDate(Date menuDate) {
		MenuDate = menuDate;
	}

	public String getDishName() {
		return DishName;
	}
	public void setDishName(String dishName) {
		if(dishName==null) {
			DishName="";
		}else {
		DishName = dishName;}
	}
	public String getDishDesc() {
		return DishDesc;
	}
	public void setDishDesc(String dishDesc) {
		DishDesc = dishDesc;
	}
	public float getDishPrice() {
		return DishPrice;
	}
	public void setDishPrice(float dishPrice) {
		DishPrice = dishPrice;
	}
	public String getMenuUid() {
		return MenuUid;
	}
	public void setMenuUid(String menuUid) {
		MenuUid = menuUid;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	public String getClassifyName() {
		return ClassifyName;
	}
	public void setClassifyName(String classifyName) {
		ClassifyName = classifyName;
	}
	public String getSetmealName() {
		return SetmealName;
	}
	public void setSetmealName(String setmealName) {
		SetmealName = setmealName;
	}


	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	public String getDishUid() {
		return DishUid;
	}

	public void setDishUid(String dishUid) {
		DishUid = dishUid;
	}

	


	public int getMenuType() {
		return MenuType;
	}

	public void setMenuType(int menuType) {
		MenuType = menuType;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getSheetName() {
		return SheetName;
	}

	public void setSheetName(String sheetName) {
		SheetName = sheetName;
	}

	public String getDishUnit() {
		return DishUnit;
	}

	public void setDishUnit(String dishUnit) {
		DishUnit = dishUnit;
	}

	public String getActivityUid() {
		return ActivityUid;
	}

	public void setActivityUid(String activityUid) {
		ActivityUid = activityUid;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public int getUsedAmount() {
		return UsedAmount;
	}

	public void setUsedAmount(int usedAmount) {
		UsedAmount = usedAmount;
	}

	
	public int getAvailableAmount() {
		return this.Amount-this.UsedAmount;
	}

	public int getMenuLimit() {
		return MenuLimit;
	}

	public void setMenuLimit(int menuLimit) {
		MenuLimit = menuLimit;
	}

}
