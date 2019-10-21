package com.icbc.rel.hefei.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysPublicNumberInfo {

	private Integer IID;
	private String PublicNumberName;// ���ں�����
	private String PublicNumberId;// ���ں�ID
	private String PublicNumberAccount;// ���ں��˺ţ���¼�����������˺ţ�
	private String Password;// ����
	private Integer Type;// ���� 2����ʶ�ɹ��ںŹ���ƽ̨���룻1��ע�����Ա
	private Integer IcbcFlag;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date CreateTime;
	
	private String Stru_ID;//��������,��0020000000
	private String StruName;//�����������籱������
	private String OrgName;//��֯���ƣ���������������ҵ�������޹�˾
	private String UserType;//�û����ͣ�1-��������ҵ��λ���ͣ�2-ý�����ͣ�3-��ҵ���ͣ�4-������֯���ͣ�5-�������ͣ�6-���ڣ�7-��e����8-e���9-ע����֯��10-����
	private String MpSubFlag;//���ں����ͣ�1-����ţ�2-���ĺţ�3-��ҵ��

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
