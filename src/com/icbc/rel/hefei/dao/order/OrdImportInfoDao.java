package com.icbc.rel.hefei.dao.order;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.order.OrdImportInfo;

public interface OrdImportInfoDao {

	void add(OrdImportInfo info);
	
	List<OrdImportInfo> selectAll(@Param("activityUid")String activityUid,@Param("start")int start,@Param("limit")int limit);
	
	List<Integer> selectAllNum(String activityUid);
	
	OrdImportInfo selectOne(String uid);
	
	void updateStatus(String fileUid);
	
}
