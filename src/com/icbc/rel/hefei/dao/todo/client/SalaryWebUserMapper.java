package com.icbc.rel.hefei.dao.todo.client;


import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.client.SalaryUser;


/**
 * ”√ªß
 * @author fc
 *
 */
public interface SalaryWebUserMapper{

	SalaryUser login(@Param("username") String username, @Param("password")String password, @Param("companyId")String companyId);

	void resetPassword(@Param("username") String username,  @Param("password1") String newPassword1);

	void saveUserKey(@Param("username")String username,@Param("userKey") String userKey, @Param("companyId")String companyId);

	SalaryUser getUserByOpenId(@Param("openId")String openId);

	SalaryUser getUserByName(@Param("username")String username);

	SalaryUser getSalaryByOpenId(@Param("openId")String openId);


	
	
}