package com.icbc.rel.hefei.service.todo.client.service;

import com.icbc.rel.hefei.entity.todo.client.SalaryUser;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;

public interface SalaryWebUserService {
	/**
	 * �û���¼
	 * @param username
	 * @param password
	 * @param companyId 
	 * @param iMUserId 
	 * @param openId 
	 */
	AjaxResult login(String username, String password, String companyId, String iMUserId);
	/**
	 * 
	 * @param username
	 * @param newPassword1
	 * @return
	 */
	AjaxResult resetPassword(String username, String newPassword1);
	/**
	 * ����userKey
	 * @param username
	 * @param userKey
	 */
//	void saveUserKey(String username, String userKey);
	/**
	 * ����openId����û���Ϣ
	 * @param openId
	 * @return
	 */
	SalaryUser getUserByOpenId(String openId);

}
