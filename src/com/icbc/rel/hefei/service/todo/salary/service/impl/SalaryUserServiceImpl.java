package com.icbc.rel.hefei.service.todo.salary.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.todo.salary.SalaryUserMapper;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.service.todo.salary.service.SalaryUserService;

@Service
public class SalaryUserServiceImpl implements SalaryUserService {
	@Autowired
	private SalaryUserMapper salaryUserMapper;
	@Override
	public AjaxResult login(String username, String password) {
		// TODO Auto-generated method stub
		int count = salaryUserMapper.login(username,password);
		if(count==1) {
			return AjaxResult.success("��½�ɹ�!");
		}else {
			return AjaxResult.success("��½ʧ��,�����û������Ƿ��д���!");
		}
		
	}
	@Override
	public AjaxResult resetPassword(String username, String newPassword1) {
		// TODO Auto-generated method stub
		salaryUserMapper.resetPassword(username,newPassword1);
		return AjaxResult.success("���óɹ�");
	}
	@Override
	public String getCompanyIdByMobile(String username) {
		// TODO Auto-generated method stub
		String companyId = salaryUserMapper.getCompanyIdByMobile(username);
		return companyId;
	}


}
