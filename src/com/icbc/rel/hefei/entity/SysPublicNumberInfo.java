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

}
