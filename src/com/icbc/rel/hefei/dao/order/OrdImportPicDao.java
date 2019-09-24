package com.icbc.rel.hefei.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.order.OrdImportPicInfo;

public interface OrdImportPicDao {
	/*
	 * 添加图片
	 */
	void add(OrdImportPicInfo info);

	/*
	 * 查询所有上传图片
	 */
	List<OrdImportPicInfo> selectAll(@Param("activityUid") String activityUid, @Param("start") int start,
			@Param("limit") int limit);

	/*
	 * 查询所有上传图片
	 */
	List<Integer> selectAllNum(@Param("activityUid") String activityUid);

	/*
	 * 查询所有上传图片(不分页)
	 */
	List<OrdImportPicInfo> getAllPic(String activityUid);

	/*
	 * 删除图片
	 */
	void delete(String iid);
}
