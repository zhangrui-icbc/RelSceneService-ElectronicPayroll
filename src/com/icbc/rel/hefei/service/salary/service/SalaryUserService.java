package com.icbc.rel.hefei.service.salary.service;

import com.icbc.rel.hefei.entity.salary.AjaxResult;

public interface SalaryUserService {
	/**
	 * �û���¼
	 * @param username
	 * @param password
	 */
	AjaxResult login(String username, String password);
	/**
	 * 
	 * @param username
	 * @param newPassword1
	 * @return
	 */
	AjaxResult resetPassword(String username, String newPassword1);
	/**
	 * ͨ���ֻ����ֻ�����ȥ��ȡ����ʱ��Ĺ�˾id
	 * @param username
	 * @return
	 */
	String getCompanyIdByMobile(String username);

}
