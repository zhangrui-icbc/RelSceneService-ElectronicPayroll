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
	 * ��ѯ��ǰ��µ�����ͼƬ��Ϣ(��ҳ)
	 */
	public List<OrdImportPicInfo> selectAll(String activityUid, int page, int limit) {
		return importPicDao.selectAll(activityUid, (page - 1) * limit, limit);
	}

	/*
	 * ��ѯ��ǰ��µ�����ͼƬ��Ϣ(����ҳ)
	 */
	public List<OrdImportPicInfo> getAllPic(String activityUid) {
		return importPicDao.getAllPic(activityUid);
	}

	/*
	 * ��ѯ��ǰ��µ�����ͼƬ��Ϣ��¼�����������ڷ�ҳ��
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
	 * ���ͼƬ
	 */
	public void add(OrdImportPicInfo info) {
		importPicDao.add(info);
	}

	/*
	 * ɾ��ͼƬ
	 */
	public void deletePic(String iid) {
		importPicDao.delete(iid);
	}

}
