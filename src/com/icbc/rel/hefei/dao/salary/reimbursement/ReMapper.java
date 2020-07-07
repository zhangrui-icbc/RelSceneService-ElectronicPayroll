package com.icbc.rel.hefei.dao.salary.reimbursement;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryStaff;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReImport;
import com.icbc.rel.hefei.entity.salary.reimbursement.Reimbursement;


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
	 * @param list
	 */
	void insertReimbursementImport(@Param("importList")List<ReImport> list);
	
	
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
	
	void delLog(@Param("reId")String reId);
	
	int delStaff(@Param("userName")String userName, @Param("companyId") String companyId);
	
	int updateMobile(@Param("userName")String userName,@Param("newUserName") String newUserName,@Param("companyId") String companyId);
	
	void delLog1(@Param("reId")String reId);
	List<String> getExcelNameList(@Param("companyId")String companyId);
	

	
	
	
	
	
	
}