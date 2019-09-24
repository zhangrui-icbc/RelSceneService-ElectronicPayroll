package com.icbc.rel.hefei.dao.todo.reimbursement;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.entity.todo.reimbursement.Reimbursement;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.todo.salary.SalaryStaff;


/**
 * 工资明细
 * @author fc
 *
 */
public interface ReMapper{
	/**
	 * 根据公司id查出模板信息
	 * @param companyId
	 * @return
	 */
	List<ReCustomTemplate> getReTemplate(@Param("companyId") String companyId);
	/**
	 * 保存工资信息
	 * @param reimbursement
	 */
	void insertReimbursement(Reimbursement reimbursement);
	
	/**
	 * 
	 * @param reimbursement
	 */
	void insertReimbursementImport(Reimbursement reimbursement);
	
	
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
	void updatePwd(@Param("userName")String userName,@Param("companyId")String companyId);
	/**
	 * 
	 * @param userName
	 * @return
	 */
	int getMobile(@Param("userName")String userName);
	
	/**
	 * 查询该公司旗下手机号码
	 * @param companyId
	 * @return
	 */
	List<SalaryStaff> getMobileList();
	
	SalaryStaff getStaffInfo(@Param("userName")String userName,  @Param("companyId")String companyId);
	
	List<Reimbursement> getUpLoadLog(@Param("params")Map<String, Object> paramsMap);
	
	void delLog(@Param("reId")String salaryId);
	
	int delStaff(@Param("userName")String userName, @Param("companyId") String companyId);
	
	int updateMobile(@Param("userName")String userName,@Param("newUserName") String newUserName,@Param("companyId") String companyId);
	
	void delLog1(@Param("reId")String salaryId);
	

	
	
	
	
	
	
}