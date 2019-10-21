package com.icbc.rel.hefei.service.salary.reimbursement.service.impl;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.salary.reimbursement.ReAlternativeMapper;
import com.icbc.rel.hefei.dao.salary.reimbursement.ReCustomMapper;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReCustomTemplate;
import com.icbc.rel.hefei.entity.salary.reimbursement.ReTemplateAlternative;
import com.icbc.rel.hefei.service.salary.reimbursement.service.ReAlternativeService;

@Service
public class ReAlternativeServiceImpl implements ReAlternativeService {
	@Autowired
	ReAlternativeMapper reAlternativeMapper;
	@Autowired 
	ReCustomMapper  reCustomMapper;
	/**
	 * ��ѡ�ֶ�
	 */
	@Override
	public Map<String, Object> getAlternativeInfo(String companyId) {
		//��ѯ���ù�˾��ѡ��ģ���ֶ�\
		Map<String, Object> result = new HashMap<String, Object>();
    	List<ReCustomTemplate> downList= reCustomMapper.getCustomTemplate(companyId);
    	result.put("down", downList);
    	//��ѯ�����еı�ѡ�ֶ�
    	List<ReTemplateAlternative> upList = reAlternativeMapper.getAlternativeInfo();
    	for (int i = 0; i < upList.size(); i++) {
    		if(upList.get(i).getName().equals("ʵ������")) {
    			upList.get(i).setId(-3);
    		}else if(upList.get(i).getName().equals("����ϼ�")){
    			upList.get(i).setId(-2);
    		}else if (upList.get(i).getName().equals("֧���ϼ�")) {
    			upList.get(i).setId(-1);
			}
		}
    	sort(upList);
    	//�޳���ѡ
    	result.put("up", upList);
    	return result;
	}


	@Override
	public ReTemplateAlternative addAlternative(ReTemplateAlternative reTemplateAlternative) {
		//�ж��Ƿ����
		List<ReTemplateAlternative> list= reAlternativeMapper.judge(reTemplateAlternative);
		if(null == list || list.size() ==0) {
			reAlternativeMapper.addAlternative(reTemplateAlternative);
			return reTemplateAlternative;
		}else {
			return null;
		}
		
	}

	@Override
	public void delAlternative(int id) {
		reAlternativeMapper.delAlternative(id);
	}


	@Override
	public ReTemplateAlternative updateAlternative(ReTemplateAlternative reTemplateAlternative) {
		//�ж��Ƿ����
		List<ReTemplateAlternative> list= reAlternativeMapper.judge(reTemplateAlternative);
		if(null == list || list.size() ==0) {
			reAlternativeMapper.updateAlternative(reTemplateAlternative);
			return reTemplateAlternative;
		}else {
			return null;
		}
	}
	
	/**
	 * ����
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<ReTemplateAlternative> reImportTemplates){
		 Collections.sort(reImportTemplates, new Comparator<ReTemplateAlternative>() {  
			  
	            @Override  
	            public int compare(ReTemplateAlternative o1, ReTemplateAlternative o2) {  
	                //��������  
	                if (o1.getId() > o2.getId()) {  
	                    return 1;  
	                }  
	                if (o1.getId() == o2.getId()) {  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        }); 
	}
	
	

}
