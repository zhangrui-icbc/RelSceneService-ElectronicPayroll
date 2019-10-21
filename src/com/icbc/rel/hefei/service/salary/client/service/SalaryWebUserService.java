package com.icbc.rel.hefei.service.salary.client.service;

import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;

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
	 * ���ݹ�˾id��openId����û���Ϣ
	 * @param openId
	 * @param activityUid 
	 * @return
	 */
	SalaryUser getUserByOpenId(String activityUid,String openId);

}
