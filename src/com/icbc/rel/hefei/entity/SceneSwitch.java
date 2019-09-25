package com.icbc.rel.hefei.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SceneSwitch {

	private Integer IID;
	
	@FieldMeta(name="场景")
	private String Scene;//场景
	
	@FieldMeta(name="场景名称")
	private String SceneName;//场景名称
	
	@FieldMeta(name="场景状态")
	private int Status;//场景状态
	
	@FieldMeta(name="可见地区范围")
	private String VisibleAreas;//场景名称

	@FieldMeta(name="创建时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date CreateTime;
	
	@FieldMeta(name="修改时间")
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
