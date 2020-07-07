package com.icbc.rel.hefei.dao.salary;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.salary.Salary;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryImport;
import com.icbc.rel.hefei.entity.salary.SalaryStaff;


/**
 * 工资明细
 * @author fc
 *
 */
public interface SalaryMapper{
	/**
	 * 根据公司id查出模板信息
	 * @param companyId
	 * @return
	 */
	List<SalaryCustomTemplate> getSalaryTemplate(@Param("companyId") String companyId);
	/**
	 * 保存工资信息
	 * @param oaSalary
	 */
	void insertOaSalary(Salary oaSalary);
	
	/**
	 * 
	 * @param list
	 */
	void insertOaSalaryImport(@Param("importList")List<SalaryImport> list);
	
	
	/**
	 * 共用模板
	 * @return
	 */
	List<SalaryCustomTemplate> getCommonTemplate();
	/**
	 * 员工信息保存
	 * @param staffList
	 */
	void insertStaffInfo(@Param("List")List<SalaryStaff> staffList);
	/**
	 * 修改员工登录密码
	 * @param userName
	 * @param companyId 
	 */
	void updatePwd(@Param("id")int id);
	/**
	 * 
	 * @param userName
	 * @return
	 */
	int getMobile(@Param("userName")String userName);
	
	/**
	 * 查询该公司旗下手机号码
	 * @param companyId 
	 * @param companyId
	 * @return
	 */
	List<SalaryStaff> getMobileList(@Param("companyId")String companyId);
	
	List<SalaryStaff> getStaffInfo(@Param("companyId")String companyId,@Param("mobile") String mobile);
	
	List<Salary> getUpLoadLog(@Param("params")Map<String, Object> paramsMap);
	
	void delLog(@Param("salaryId")String salaryId);
	
	int delStaff(@Param("id")int id);
	
	int updateMobile(@Param("userName")String userName,@Param("newUserName") String newUserName,@Param("companyId") String companyId);
	
	void delLog1(@Param("salaryId")String salaryId);
	void updateAddStaffInfo(SalaryStaff salaryStaff);
	List<String> getExcelNameList(@Param("companyId")String companyId);
	

	
	
	
	
	
	
}