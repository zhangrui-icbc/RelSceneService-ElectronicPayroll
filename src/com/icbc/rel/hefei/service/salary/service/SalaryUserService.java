package com.icbc.rel.hefei.service.salary.service;

import com.icbc.rel.hefei.entity.salary.AjaxResult;

public interface SalaryUserService {
	/**
	 * 用户登录
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
	 * 通过手机端手机号码去获取导入时候的公司id
	 * @param username
	 * @return
	 */
	String getCompanyIdByMobile(String username);

}
