package com.icbc.rel.hefei.service.order;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.order.OrdImportPicDao;
import com.icbc.rel.hefei.entity.order.OrdImportPicInfo;

@Service
public class ImportPicService {
	private static Logger logger = Logger.getLogger(ImportPicService.class);

	@Autowired
	private OrdImportPicDao importPicDao;

	/*
	 * 查询当前活动下的所有图片信息(分页)
	 */
	public List<OrdImportPicInfo> selectAll(String activityUid, int page, int limit) {
		return importPicDao.selectAll(activityUid, (page - 1) * limit, limit);
	}

	/*
	 * 查询当前活动下的所有图片信息(不分页)
	 */
	public List<OrdImportPicInfo> getAllPic(String activityUid) {
		return importPicDao.getAllPic(activityUid);
	}

	/*
	 * 查询当前活动下的所有图片信息记录的条数（用于分页）
	 */
	public int selectAllNum(String activityUid) {
		List<Integer> result = importPicDao.selectAllNum(activityUid);
		if (result != null && result.size() > 0) {
			return result.get(0) == null ? 0 : result.get(0);
		} else {
			return 0;
		}
	}

	/*
	 * 添加图片
	 */
	public void add(OrdImportPicInfo info) {
		importPicDao.add(info);
	}

	/*
	 * 删除图片
	 */
	public void deletePic(String iid) {
		importPicDao.delete(iid);
	}

}
