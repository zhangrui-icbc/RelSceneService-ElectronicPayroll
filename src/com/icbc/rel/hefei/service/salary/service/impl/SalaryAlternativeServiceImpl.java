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
	 * 备选字段
	 */
	@Override
	public Map<String, Object> getAlternativeInfo(String companyId) {
		//查询出该公司已选的模板字段\
		Map<String, Object> result = new HashMap<String, Object>();
    	List<SalaryCustomTemplate> downList= salaryCustomMapper.getCustomTemplate(companyId);
    	result.put("down", downList);
    	//查询出所有的备选字段
    	List<SalaryTemplateAlternative> upList = salaryAlternativeMapper.getAlternativeInfo(companyId);
    	for (int i = 0; i < upList.size(); i++) {
    		if(upList.get(i).getName().equals("实际收入")) {
    			upList.get(i).setId(-4);
    		}else if(upList.get(i).getName().equals("收入合计")){
    			upList.get(i).setId(-3);
    		}else if (upList.get(i).getName().equals("支出合计")) {
    			upList.get(i).setId(-2);
			}else if(upList.get(i).getName().equals("单位支出")) {
				upList.get(i).setId(-1);
			}
		}
    	sort(upList);
    	//剔除已选
    	result.put("up", upList);
    	return result;
	}


	@Override
	public SalaryTemplateAlternative addAlternative(SalaryTemplateAlternative oaSalaryTemplateAlternative) {
		//判断是否存在
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
		//判断是否存在
		List<SalaryTemplateAlternative> list= salaryAlternativeMapper.judge(oaSalaryTemplateAlternative);
		if(null == list || list.size() ==0) {
			salaryAlternativeMapper.updateAlternative(oaSalaryTemplateAlternative);
			return oaSalaryTemplateAlternative;
		}else {
			return null;
		}
	}
	
	/**
	 * 排序
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<SalaryTemplateAlternative> oaSalaryImportTemplates){
		 Collections.sort(oaSalaryImportTemplates, new Comparator<SalaryTemplateAlternative>() {  
			  
	            @Override  
	            public int compare(SalaryTemplateAlternative o1, SalaryTemplateAlternative o2) {  
	                //升序排列  
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
