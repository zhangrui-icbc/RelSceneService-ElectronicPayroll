package com.icbc.rel.hefei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.SysBankOrgInfo;

public interface SysBankOrgInfoDao {

	List<SysBankOrgInfo> getBankorginfo();

	
}
