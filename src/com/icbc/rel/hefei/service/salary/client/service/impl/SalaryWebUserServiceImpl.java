package com.icbc.rel.hefei.service.salary.client.service.impl;



import java.util.List;

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
			//�ж��û��Ƿ���
			List<SalaryUser> salaryUser = salaryWebUserMapper.login(username,companyId);
			if (salaryUser.size()>0) {
				if(salaryUser.get(0).getPassword().equals(password)) {
					//�ж��Ƿ����opendid
					if(StringUtils.isEmpty(salaryUser.get(0).getOpenId())) {//������openid
						salaryUser.get(0).setOpenId(iMUserId);
						salaryWebUserMapper.saveUserKey(username,iMUserId, companyId);;
					}
					logger.info("��¼�ɹ�");
					return AjaxResult.success("��¼�ɹ�", salaryUser.get(0));
				}else {
					logger.error("��¼ʧ�ܣ��û������ڻ������벻��ȷ��");
					return AjaxResult.error("���������Ƿ���ȷ���л��������µ�¼��");
				}
			} else {
				logger.error("���û���Ϣ�����ڣ�");
				return AjaxResult.error("�˺�δ����,�봴���˺ź��¼��");
			}
		
	}
	@Override
	public AjaxResult resetPassword(String username, String newPassword1) {
		salaryWebUserMapper.resetPassword(username,newPassword1);
		logger.info("��������ɹ���");
		return AjaxResult.success("���óɹ�");
	}
	@Override
	public SalaryUser getUserByOpenId(String activityUid,String openId) {
		// TODO Auto-generated method stub
		return salaryWebUserMapper.getUserByOpenId(activityUid,openId);
	}


}
