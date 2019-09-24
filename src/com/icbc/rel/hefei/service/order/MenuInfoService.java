package com.icbc.rel.hefei.service.order;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.icbc.rel.hefei.dao.order.OrdImportPicDao;
import com.icbc.rel.hefei.dao.order.OrdMenuInfoDao;
import com.icbc.rel.hefei.entity.order.OrdImportPicInfo;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.util.CommonUtil;
@Service
public class MenuInfoService {
	private static Logger logger = Logger.getLogger(MenuInfoService.class);

	@Autowired
	private OrdMenuInfoDao menuInfoDao;
	
	/*
	 * ��ѯ����Ĳ˵�
	 */
	public List<OrdMenuInfo> getMenuDetail(String uid){
		return menuInfoDao.selectByuid(uid);
	}
	
	/*
	 * ��ѯ��Ʒ����
	 */
	public Date getMenuDate(String dishUid){
		return menuInfoDao.selectDate(dishUid);
	}
	
	/*
	 * ����ָ����������ѯ�����˵�
	 */
	public List<OrdMenuInfo> getMenuByDate(Date date,String activityUid){
		return menuInfoDao.getDinnerByDate(CommonUtil.DateConvertStr(date, "yyyy-MM-dd"),activityUid);
	}
	
	
	/*
	 * ȡ����������²�Ʒ����@ILNIQ
	 */
	public void updateMenuBydishUid(String dishUid,int num){
				
		OrdMenuInfo  dish=menuInfoDao.getLockedMenuInfo(dishUid);
		int oldNum=dish.getUsedAmount();
		//��������
		int UsedNum=dish.getUsedAmount()-num;
		dish.setUsedAmount(UsedNum);
		logger.info("��Ʒ"+dishUid+"��ʹ�������ɣ�"+oldNum+"��Ϊ��"+UsedNum);
		
		try {			
			menuInfoDao.updateUsedAmount(dish);
			logger.info("���³ɹ�");
			}catch(Exception ex){
				logger.info("����ʧ��",ex);
		}
		
			
	}
	
	
	
	
	
	
}
