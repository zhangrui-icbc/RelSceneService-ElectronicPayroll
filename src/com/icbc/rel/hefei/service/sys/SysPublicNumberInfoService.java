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
	 * �����û����������ѯ��¼
	 */
	 public SysPublicNumberInfo getPublicNumberInfo(String account,String psd) {
		List<SysPublicNumberInfo> infos= dao.getPublicNumberInfo(account,psd);
		if(infos.size()>0) {
			return infos.get(0);
		}
		return null;
	 }
	 
	 /*
	  * ���빫�ں���Ϣ
	  */
	 public void insert(SysPublicNumberInfo info) {
		 dao.addSYSPublicNumberInfo(info);
	 }
	 
	 /*
	  * ����mpid��ѯ���ں���Ϣ
	  */
	 public SysPublicNumberInfo getPublicNumberInfoByMpid(String mpid) {
		 List<SysPublicNumberInfo> infos= dao.getPublicNumberInfoByMpid(mpid);
		 if(infos.size()>0) {
			 return infos.get(0);
		 }
		 return null;
	 }
	 
}
