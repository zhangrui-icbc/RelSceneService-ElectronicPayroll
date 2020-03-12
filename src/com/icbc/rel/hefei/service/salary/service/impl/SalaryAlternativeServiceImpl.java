package com.icbc.rel.hefei.service.salary.service.impl;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.rel.hefei.dao.salary.SalaryAlternativeMapper;
import com.icbc.rel.hefei.dao.salary.SalaryCustomMapper;
import com.icbc.rel.hefei.entity.salary.SalaryCustomTemplate;
import com.icbc.rel.hefei.entity.salary.SalaryTemplateAlternative;
import com.icbc.rel.hefei.service.salary.service.SalaryAlternativeService;

@Service
public class SalaryAlternativeServiceImpl implements SalaryAlternativeService {
	@Autowired
	SalaryAlternativeMapper salaryAlternativeMapper;
	@Autowired 
	SalaryCustomMapper  salaryCustomMapper;
	/**
	 * ��ѡ�ֶ�
	 */
	@Override
	public Map<String, Object> getAlternativeInfo(String companyId) {
		//��ѯ���ù�˾��ѡ��ģ���ֶ�\
		Map<String, Object> result = new HashMap<String, Object>();
    	List<SalaryCustomTemplate> downList= salaryCustomMapper.getCustomTemplate(companyId);
    	result.put("down", downList);
    	//��ѯ�����еı�ѡ�ֶ�
    	List<SalaryTemplateAlternative> upList = salaryAlternativeMapper.getAlternativeInfo(companyId);
    	for (int i = 0; i < upList.size(); i++) {
    		if(upList.get(i).getName().equals("ʵ������")) {
    			upList.get(i).setId(-4);
    		}else if(upList.get(i).getName().equals("����ϼ�")){
    			upList.get(i).setId(-3);
    		}else if (upList.get(i).getName().equals("֧���ϼ�")) {
    			upList.get(i).setId(-2);
			}else if(upList.get(i).getName().equals("��λ֧��")) {
				upList.get(i).setId(-1);
			}
		}
    	sort(upList);
    	//�޳���ѡ
    	result.put("up", upList);
    	return result;
	}


	@Override
	public SalaryTemplateAlternative addAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative) {
		//�ж��Ƿ����
		List<SalaryTemplateAlternative> list= salaryAlternativeMapper.judge(oaSalaryTemplateAlternative);
		if(null == list || list.size() ==0) {
			salaryAlternativeMapper.addAlternative(oaSalaryTemplateAlternative);
			return oaSalaryTemplateAlternative;
		}else {
			return null;
		}
		
	}

	@Override
	public void delAlternative(int id) {
		salaryAlternativeMapper.delAlternative(id);
	}


	@Override
	public SalaryTemplateAlternative updateAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative) {
		//�ж��Ƿ����
		List<SalaryTemplateAlternative> list= salaryAlternativeMapper.judge(oaSalaryTemplateAlternative);
		if(null == list || list.size() ==0) {
			salaryAlternativeMapper.updateAlternative(oaSalaryTemplateAlternative);
			return oaSalaryTemplateAlternative;
		}else {
			return null;
		}
	}
	
	/**
	 * ����
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<SalaryTemplateAlternative> oaSalaryImportTemplates){
		 Collections.sort(oaSalaryImportTemplates, new Comparator<SalaryTemplateAlternative>() {  
			  
	            @Override  
	            public int compare(SalaryTemplateAlternative o1, SalaryTemplateAlternative o2) {  
	                //��������  
	                if (o1.getCategory() < o2.getCategory()) {  
	                    return 1;  
	                }  
	                if (o1.getCategory() == o2.getCategory()) {  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        }); 
	}
	
	

}
