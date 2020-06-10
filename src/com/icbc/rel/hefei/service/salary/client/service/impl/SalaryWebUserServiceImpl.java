package com.icbc.rel.hefei.service.salary.client.service.impl;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.icbc.rel.hefei.controller.salary.client.SalaryWebUserController;
import com.icbc.rel.hefei.dao.salary.client.SalaryWebUserMapper;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;
import com.icbc.rel.hefei.service.salary.client.service.SalaryWebUserService;

@Service
public class SalaryWebUserServiceImpl implements SalaryWebUserService {
	private static Logger logger = Logger.getLogger(SalaryWebUserServiceImpl.class);
	@Autowired
	private SalaryWebUserMapper salaryWebUserMapper;
	@Override
	public AjaxResult login(String username, String password,String companyId, String iMUserId) {
			logger.info("username:"+username+",password:"+password+",companyId:"+companyId+",iMUserId:"+iMUserId);
			SalaryUser salaryUser = salaryWebUserMapper.login(username,password,companyId);
			if(salaryUser!=null) {
				//�ж��Ƿ����opendid
				if(StringUtils.isEmpty(salaryUser.getOpenId())) {//������openid
					salaryUser.setOpenId(iMUserId);
					salaryWebUserMapper.saveUserKey(username,iMUserId, companyId);;
				}
				logger.info("��¼�ɹ�");
				return AjaxResult.success("��¼�ɹ�", salaryUser);
			}else {
				logger.error("��¼ʧ�ܣ��û������ڻ������벻��ȷ��");
			}
		return AjaxResult.error("���������Ƿ���ȷ���л��������µ�¼��");

		
	}
	@Override
	public AjaxResult resetPassword(String username, String newPassword1) {
		salaryWebUserMapper.resetPassword(username,newPassword1);
		logger.info("��������ɹ���");
		return AjaxResult.success("���óɹ�");
	}
/*	@Override
	public void saveUserKey(String username, String userKey) {
		// TODO Auto-generated method stub
		SalaryUser salaryUser =new SalaryUser();
		salaryUser = salaryWebUserMapper.getUserByName(username);
		if(salaryUser!=null) {
			if(StringUtils.isEmpty(salaryUser.getOpenId())) {
				salaryWebUserMapper.saveUserKey(username,userKey);
			}
		}
	}*/
	@Override
	public SalaryUser getUserByOpenId(String activityUid,String openId) {
		// TODO Auto-generated method stub
		return salaryWebUserMapper.getUserByOpenId(activityUid,openId);
	}


}
