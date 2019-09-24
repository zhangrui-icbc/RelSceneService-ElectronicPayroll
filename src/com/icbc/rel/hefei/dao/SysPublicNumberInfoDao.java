package com.icbc.rel.hefei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.entity.SysPublicNumberInfo;

public interface SysPublicNumberInfoDao {
	
	 List<SysPublicNumberInfo> getPublicNumberInfo(@Param("account")String account,@Param("psd")String psd) ;
	 
	 List<SysPublicNumberInfo>  queryAllSYSPublicNumberInfo();
		
		void deleteSYSPublicNumberInfo();
		
		void addSYSPublicNumberInfo(SysPublicNumberInfo info);

		List<SysPublicNumberInfo> getPublicNumberInfoByMpid(@Param("mpid")String mpid);
	 
}
