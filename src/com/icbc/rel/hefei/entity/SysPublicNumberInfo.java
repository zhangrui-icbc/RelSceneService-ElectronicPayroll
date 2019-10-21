package com.icbc.rel.hefei.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysPublicNumberInfo {

	private Integer IID;
	private String PublicNumberName;// 公众号名称
	private String PublicNumberId;// 公众号ID
	private String PublicNumberAccount;// 公众号账号（登录名，即邮箱账号）
	private String Password;// 密码
	private Integer Type;// 类型 2：标识由公众号管理平台进入；1：注册管理员
	private Integer IcbcFlag;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date CreateTime;
	
	private String Stru_ID;//机构代码,如0020000000
	private String StruName;//所属机构，如北京分行
	private String OrgName;//组织名称，如德阳洋洋君安物业管理有限公司
	private String UserType;//用户类型，1-政府及事业单位类型，2-媒体类型，3-企业类型，4-其他组织类型，5-个人类型，6-行内，7-融e购，8-e生活，9-注册组织，10-团体
	private String MpSubFlag;//公众号类型，1-服务号，2-订阅号，3-企业号

	public Integer getIID() {
		return IID;
	}

	public void setIID(Integer iID) {
		IID = iID;
	}

	public String getPublicNumberName() {
		return PublicNumberName;
	}

	public void setPublicNumberName(String publicNumberName) {
		PublicNumberName = publicNumberName;
	}

	public String getPublicNumberId() {
		return PublicNumberId;
	}

	public void setPublicNumberId(String publicNumberId) {
		PublicNumberId = publicNumberId;
	}

	public String getPublicNumberAccount() {
		return PublicNumberAccount;
	}

	public void setPublicNumberAccount(String publicNumberAccount) {
		PublicNumberAccount = publicNumberAccount;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Integer getType() {
		return Type;
	}

	public void setType(Integer type) {
		Type = type;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public Integer getIcbcFlag() {
		return IcbcFlag;
	}

	public void setIcbcFlag(Integer icbcFlag) {
		IcbcFlag = icbcFlag;
	}

	public String getStru_ID() {
		return Stru_ID;
	}

	public void setStru_ID(String stru_ID) {
		Stru_ID = stru_ID;
	}

	public String getStruName() {
		return StruName;
	}

	public void setStruName(String struName) {
		StruName = struName;
	}

	public String getOrgName() {
		return OrgName;
	}

	public void setOrgName(String orgName) {
		OrgName = orgName;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public String getMpSubFlag() {
		return MpSubFlag;
	}

	public void setMpSubFlag(String mpSubFlag) {
		MpSubFlag = mpSubFlag;
	}

}
