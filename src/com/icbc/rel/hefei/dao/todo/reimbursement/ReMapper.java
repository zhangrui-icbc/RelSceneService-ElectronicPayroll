package com.icbc.rel.hefei.dao.todo.reimbursement;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.todo.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.entity.todo.reimbursement.Reimbursement;
import com.icbc.rel.hefei.entity.todo.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.todo.salary.SalaryStaff;


/**
 * ������ϸ
 * @author fc
 *
 */
public interface ReMapper{
	/**
	 * ���ݹ�˾id���ģ����Ϣ
	 * @param companyId
	 * @return
	 */
	List<ReCustomTemplate> getReTemplate(@Param("companyId") String companyId);
	/**
	 * ���湤����Ϣ
	 * @param reimbursement
	 */
	void insertReimbursement(Reimbursement reimbursement);
	
	/**
	 * 
	 * @param reimbursement
	 */
	void insertReimbursementImport(Reimbursement reimbursement);
	
	
	/**
	 * ����ģ��
	 * @return
	 */
	List<SalaryCustomTemplate> getCommonTemplate();
	/**
	 * Ա����Ϣ����
	 * @param staffList
	 */
	void insertStaffInfo(@Param("List")List<SalaryStaff> staffList);
	/**
	 * �޸�Ա����¼����
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
	 * ��ѯ�ù�˾�����ֻ�����
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