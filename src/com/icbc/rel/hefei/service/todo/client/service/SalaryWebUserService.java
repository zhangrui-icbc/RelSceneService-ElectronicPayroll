package com.icbc.rel.hefei.service.todo.client.service;

import com.icbc.rel.hefei.entity.todo.client.SalaryUser;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;

public interface SalaryWebUserService {
	/**
	 * 用户登录
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
	 * 存入userKey
	 * @param username
	 * @param userKey
	 */
//	void saveUserKey(String username, String userKey);
	/**
	 * 根据openId获得用户信息
	 * @param openId
	 * @return
	 */
	SalaryUser getUserByOpenId(String openId);

}
