package com.icbc.rel.hefei.service.salary.client.service.impl;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.icbc.rel.hefei.controller.salary.client.SalaryWebUserController;
import com.icbc.rel.hefei.dao.salary.client.SalaryWebUserMapper;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;
import com.icbc.rel.hefei.service.salary.client.service.SalaryWebUserService;

@Service
public class SalaryWebUserServiceImpl implements SalaryWebUserService {
	private static Logger logger = Logger.getLogger(SalaryWebUserServiceImpl.class);
	@Autowired
	private SalaryWebUserMapper salaryWebUserMapper;
	@Override
	public AjaxResult login(String username, String password,String companyId, String iMUserId) {
			logger.info("username:"+username+",password:"+password+",companyId:"+companyId+",iMUserId:"+iMUserId);
			//判断用户是否导入
			List<SalaryUser> salaryUser = salaryWebUserMapper.login(username,companyId);
			if (salaryUser.size()>0) {
				if(salaryUser.get(0).getPassword().equals(password)) {
					//判断是否插入opendid
					if(StringUtils.isEmpty(salaryUser.get(0).getOpenId())) {//不存在openid
						salaryUser.get(0).setOpenId(iMUserId);
						salaryWebUserMapper.saveUserKey(username,iMUserId, companyId);;
					}
					logger.info("登录成功");
					return AjaxResult.success("登录成功", salaryUser.get(0));
				}else {
					logger.error("登录失败，用户不存在或者密码不正确！");
					return AjaxResult.error("请检查密码是否正确或切换网络重新登录！");
				}
			} else {
				logger.error("该用户信息不存在！");
				return AjaxResult.error("账号未创建,请创建账号后登录！");
			}
		
	}
	@Override
	public AjaxResult resetPassword(String username, String newPassword1) {
		salaryWebUserMapper.resetPassword(username,newPassword1);
		logger.info("重置密码成功！");
		return AjaxResult.success("重置成功");
	}
	@Override
	public SalaryUser getUserByOpenId(String activityUid,String openId) {
		// TODO Auto-generated method stub
		return salaryWebUserMapper.getUserByOpenId(activityUid,openId);
	}


}
