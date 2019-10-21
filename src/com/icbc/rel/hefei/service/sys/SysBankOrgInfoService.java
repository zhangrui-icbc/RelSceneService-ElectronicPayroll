package com.icbc.rel.hefei.service.sys;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.SysBankOrgInfoDao;
import com.icbc.rel.hefei.entity.SysBankOrgInfo;

@Service
public class SysBankOrgInfoService {
	@Autowired
	private SysBankOrgInfoDao dao;

	public List<SysBankOrgInfo> getBankorginfo() {
		// TODO Auto-generated method stub
		return dao.getBankorginfo();
	}

	public String getOrgId(String orgId) {
		// TODO Auto-generated method stub
		return dao.getOrgId(orgId);
	}
	
}
