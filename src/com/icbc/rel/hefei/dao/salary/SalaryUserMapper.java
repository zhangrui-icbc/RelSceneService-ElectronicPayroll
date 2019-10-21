package com.icbc.rel.hefei.dao.salary;

import org.apache.ibatis.annotations.Param;


/**
 * ”√ªß
 * @author fc
 *
 */
public interface SalaryUserMapper{

	int login(@Param("username")String username, @Param("password")String password);

	void resetPassword(@Param("username") String username,  @Param("password1") String newPassword1);

	String getCompanyIdByMobile(@Param("username") String username);

	
	
}