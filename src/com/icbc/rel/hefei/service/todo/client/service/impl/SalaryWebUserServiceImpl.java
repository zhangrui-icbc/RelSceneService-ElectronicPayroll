package com.icbc.rel.hefei.service.todo.client.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.icbc.rel.hefei.dao.todo.client.SalaryWebUserMapper;
import com.icbc.rel.hefei.entity.todo.client.SalaryUser;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.service.todo.client.service.SalaryWebUserService;

@Service
public class SalaryWebUserServiceImpl implements SalaryWebUserService {
	@Autowired
	private SalaryWebUserMapper salaryWebUserMapper;
	@Override
	public AjaxResult login(String username, String password,String openId) {
		SalaryUser salaryUser = salaryWebUserMapper.login(username,password);
		
		SalaryUser salaryUserByOpenId = salaryWebUserMapper.getSalaryByOpenId(openId);
		if(salaryUser!=null) {
			if(salaryUserByOpenId !=null) {
				if(salaryUserByOpenId.getMobile().equals(username)) {
					return AjaxResult.success("��½�ɹ�", salaryUser);
				}else{
					return AjaxResult.error("���˺��Ѻ���e���˺Ű󶨣������������ֻ��ź����룡", salaryUser);
				}
			}else{
				if(!StringUtils.isEmpty(salaryUser.getOpenId())) {
					return AjaxResult.error("���˺��Ѻ���e���˺Ű󶨣������������ֻ��ź����룡", salaryUser);
				}else {
					return AjaxResult.success("��½�ɹ�", salaryUser);
				}
			}
		}else {
			return AjaxResult.error("��½ʧ��,���������Ƿ��д���!");
		}
		
	}
	@Override
	public AjaxResult resetPassword(String username, String newPassword1) {
		salaryWebUserMapper.resetPassword(username,newPassword1);
		return AjaxResult.success("���óɹ�");
	}
	@Override
	public void saveUserKey(String username, String userKey) {
		// TODO Auto-generated method stub
		SalaryUser salaryUser =new SalaryUser();
		salaryUser = salaryWebUserMapper.getUserByName(username);
		if(salaryUser!=null) {
			if(StringUtils.isEmpty(salaryUser.getOpenId())) {
				salaryWebUserMapper.saveUserKey(username,userKey);
			}
		}
	}
	@Override
	public SalaryUser getUserByOpenId(String openId) {
		// TODO Auto-generated method stub
		return salaryWebUserMapper.getUserByOpenId(openId);
	}


}
