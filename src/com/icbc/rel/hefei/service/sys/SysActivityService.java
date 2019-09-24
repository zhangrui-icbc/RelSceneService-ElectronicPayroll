package com.icbc.rel.hefei.service.sys;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.SysActivityInfoDao;
import com.icbc.rel.hefei.entity.SysActivityInfo;

@Service
public class SysActivityService {
	
	@Autowired
	private SysActivityInfoDao dao;
	/*
	 * 插入场景信息
	 */
	public void insert(SysActivityInfo info) {
		dao.insert(info);
	}
	/*
	 * 更新场景全部信息
	 */
	public void update(SysActivityInfo info) {
		dao.update(info);
	}
	/*
	 * 删除活动信息
	 */
	public void delete(String activityUid) {
		dao.del(activityUid);
	}
	
	/*
	 * 更新场景的名称信息
	 */
	public void updateName(SysActivityInfo info) {
		info.setModifyTime(new Date());
		dao.updateName(info);
	}
	
	
	
	/*public String generateId(String pre) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("result", "");
		map.put("pre",pre);
		dao.generateId(map);
		String result=map.get("result");
		
		return result;
	}*/
	
	public List<SysActivityInfo> getMyScene(String publicNumberId,String activityName){
		return dao.getMyScene(publicNumberId,activityName);
	}
	
	public SysActivityInfo getSceneByUid(String activityUid){
		return dao.getSceneByUid(activityUid);
	}
	
	
	public SysActivityInfo getMyActivity(String publicNumberId,String sceneType) {
		List<SysActivityInfo> result=dao.getMyActivity(publicNumberId, sceneType);
		return result.size()>0?result.get(0):null;
	}
	
	//查询活动场景BY场景的uid ：ILNIQ
	public List<SysActivityInfo> getActivityByRelScenUid(int page,int limit,String RelScenUid){
		return dao.getActivityByRelScenUid((page-1)*limit,limit,RelScenUid);			
	}
	
	//查询数据条数:ILNIQ
	public int getcountByRelScenUid(String RelScenUid){
		return dao.getcountByRelScenUid(RelScenUid);
	}
	
	/*
	 * 更新不为空的活动字段
	 */
	public void updateNoNull(SysActivityInfo info){
		info.setModifyTime(new Date());
		dao.updateNoNull(info);
	}	
	
}
