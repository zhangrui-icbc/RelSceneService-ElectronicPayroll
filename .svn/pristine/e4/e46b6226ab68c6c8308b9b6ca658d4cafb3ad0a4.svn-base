package com.icbc.rel.hefei.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.SysPublicNumberInfoDao;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;

@Service
public class SysPublicNumberInfoService {
	@Autowired
	private SysPublicNumberInfoDao dao;
	/*
	 * 根据用户名和密码查询记录
	 */
	 public SysPublicNumberInfo getPublicNumberInfo(String account,String psd) {
		List<SysPublicNumberInfo> infos= dao.getPublicNumberInfo(account,psd);
		if(infos.size()>0) {
			return infos.get(0);
		}
		return null;
	 }
	 
	 /*
	  * 插入公众号信息
	  */
	 public void insert(SysPublicNumberInfo info) {
		 dao.addSYSPublicNumberInfo(info);
	 }
	 
	 /*
	  * 根据mpid查询公众号信息
	  */
	 public SysPublicNumberInfo getPublicNumberInfoByMpid(String mpid) {
		 List<SysPublicNumberInfo> infos= dao.getPublicNumberInfoByMpid(mpid);
		 if(infos.size()>0) {
			 return infos.get(0);
		 }
		 return null;
	 }
	 
}
