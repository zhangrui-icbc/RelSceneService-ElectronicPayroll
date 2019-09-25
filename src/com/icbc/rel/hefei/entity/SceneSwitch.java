package com.icbc.rel.hefei.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SceneSwitch {

	private Integer IID;
	
	@FieldMeta(name="����")
	private String Scene;//����
	
	@FieldMeta(name="��������")
	private String SceneName;//��������
	
	@FieldMeta(name="����״̬")
	private int Status;//����״̬
	
	@FieldMeta(name="�ɼ�������Χ")
	private String VisibleAreas;//��������

	@FieldMeta(name="����ʱ��")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date CreateTime;
	
	@FieldMeta(name="�޸�ʱ��")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date ModifyTime;

	public Integer getIID() {
		return IID;
	}

	public void setIID(Integer iID) {
		IID = iID;
	}

	public String getScene() {
		return Scene;
	}

	public void setScene(String scene) {
		Scene = scene;
	}

	public String getSceneName() {
		return SceneName;
	}

	public void setSceneName(String sceneName) {
		SceneName = sceneName;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getVisibleAreas() {
		return VisibleAreas;
	}

	public void setVisibleAreas(String visibleAreas) {
		VisibleAreas = visibleAreas;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public Date getModifyTime() {
		return ModifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		ModifyTime = modifyTime;
	}
}
