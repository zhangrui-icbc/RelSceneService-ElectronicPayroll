package com.icbc.rel.hefei.service.salary.client.service;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;

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
	 * 根据公司id和openId获得用户信息
	 * @param openId
	 * @param activityUid 
	 * @return
	 */
	SalaryUser getUserByOpenId(String activityUid,String openId);

}
