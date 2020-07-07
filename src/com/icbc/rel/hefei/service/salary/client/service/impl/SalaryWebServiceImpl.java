package com.icbc.rel.hefei.service.salary.client.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.math.BigDecimal;
import com.icbc.rel.hefei.controller.salary.client.SalaryWebUserController;
import com.icbc.rel.hefei.dao.salary.client.SalaryWebMapper;
import com.icbc.rel.hefei.entity.salary.client.SalaryImportVO;
import com.icbc.rel.hefei.entity.salary.client.SalaryVO;
import com.icbc.rel.hefei.service.salary.client.service.SalaryWebService;

/**
 * 工资信息查询
 * @author fc
 *
 */
@Transactional
@Service
public class SalaryWebServiceImpl implements SalaryWebService {
	private static Logger logger = Logger.getLogger(SalaryWebServiceImpl.class);
	@Autowired
	SalaryWebMapper salaryWebMapper;
	    
	/**
	 * 获取工资信息 
	 */
	@SuppressWarnings({ "unused", "deprecation", "rawtypes", "unchecked" })
	@Override
	public List<SalaryImportVO> getSalaryInfo(Map<String, Object> paramsMap) {
		logger.info("获取工资信息 ---------------------");
		String startDate = (String) paramsMap.get("startDate");
		String endDate = (String) paramsMap.get("endDate");
		List<SalaryImportVO> list  = new ArrayList<SalaryImportVO>();
		long start = System.currentTimeMillis();
		list =salaryWebMapper.getSalaryInfo(paramsMap);
		long end = System.currentTimeMillis();
		System.out.println("查询工资信息的时间为:"+(end-start));
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)) {//汇总
			SalaryImportVO salaryImportVO = new SalaryImportVO();
			logger.info("工资汇总信息 ---------------------");
			try {
				//合并同类型
				for (int i = 0; i < list.size(); i++) {
					for (int j = list.size()-1;j>i; j--) {
						list.get(i).setRealIncome(new BigDecimal(getNotNull(list.get(i).getRealIncome())).add(new BigDecimal(getNotNull(list.get(j).getRealIncome()))).toString());
						list.get(i).setUnitExpenditure(new BigDecimal(getNotNull(list.get(i).getUnitExpenditure())).add(new BigDecimal(getNotNull(list.get(j).getUnitExpenditure()))).toString());
						list.get(i).setTotalRevenue(new BigDecimal(getNotNull(list.get(i).getTotalRevenue())).add(new BigDecimal(getNotNull(list.get(j).getTotalRevenue()))).toString());
						list.get(i).setTotalExpenditure(new BigDecimal(getNotNull(list.get(i).getTotalExpenditure())).add(new BigDecimal(getNotNull(list.get(j).getTotalExpenditure()))).toString());
						list.get(i).setSpecialDeduction(new BigDecimal(getNotNull(list.get(i).getSpecialDeduction())).add(new BigDecimal(getNotNull(list.get(j).getSpecialDeduction()))).toString());
						
						//json串信息相加合并 TODO
						String jsonI = list.get(i).getSpecialInfo();
						Map mapTypeI = (Map) JSON.parse(jsonI);
						
						String jsonJ = list.get(j).getSpecialInfo();
						Map mapTypeJ = (Map) JSON.parse(jsonJ);
						
						if(mapTypeI.containsKey("totalExpenditure")&&mapTypeJ.containsKey("totalExpenditure")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("totalExpenditure");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("totalExpenditure");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m1value = entry1.getValue() == null?"":entry1.getValue();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(), new BigDecimal(getNotNull(m1value)).add(new BigDecimal(getNotNull(m2value))).toString());    
					            }
						}else if (mapTypeJ.containsKey("totalExpenditure")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("totalExpenditure");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("totalExpenditure");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(),getNotNull(m2value));    
					            }
						}
						
						
						if(mapTypeI.containsKey("specialDeduction")||mapTypeJ.containsKey("specialDeduction")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("specialDeduction");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("specialDeduction");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m1value = entry1.getValue() == null?"":entry1.getValue();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":(String) mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(), new BigDecimal(getNotNull(m1value)).add(new BigDecimal(getNotNull(m2value))).toString());    
					            }
						}else if (mapTypeJ.containsKey("specialDeduction")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("specialDeduction");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("specialDeduction");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(),getNotNull(m2value));    
					            }
						}
						
						if(mapTypeI.containsKey("unitExpenditure")||mapTypeJ.containsKey("unitExpenditure")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("unitExpenditure");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("unitExpenditure");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m1value = entry1.getValue() == null?"":entry1.getValue();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":(String) mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(), new BigDecimal(getNotNull(m1value)).add(new BigDecimal(getNotNull(m2value))).toString());    
					            }
						}else if (mapTypeJ.containsKey("unitExpenditure")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("unitExpenditure");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("unitExpenditure");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(),getNotNull(m2value));    
					            }
						}
						
						if(mapTypeI.containsKey("totalRevenue")||mapTypeJ.containsKey("totalRevenue")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("totalRevenue");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("totalRevenue");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m1value = entry1.getValue() == null?"":entry1.getValue();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":(String) mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(), new BigDecimal(getNotNull(m1value)).add(new BigDecimal(getNotNull(m2value))).toString());    
					            }
						}else if (mapTypeJ.containsKey("totalRevenue")) {
							Map<String,String> mapII = (Map<String, String>) mapTypeI.get("totalRevenue");
							Map<String,String> mapJJ = (Map<String, String>) mapTypeJ.get("totalRevenue");
					        Iterator<Entry<String, String>> iter1 = mapII.entrySet().iterator();
					        while(iter1.hasNext()){
					            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
					            String m2value = mapJJ.get(entry1.getKey())==null?"":mapJJ.get(entry1.getKey());
					            mapII.put(entry1.getKey(),getNotNull(m2value));    
					            }
						}
						list.get(i).setSpecialInfo(mapTypeI.toString());
						list.remove(j);
					}
				}
			} catch (Exception e) {
	    		logger.error("工资汇总报错:"+e.getMessage(),e);
			}
		}
		return list;
	}

	@Override
	public SalaryImportVO getSalaryDetail(Map<String, Object> paramsMap) {
		return salaryWebMapper.getSalaryDetail(paramsMap);
	}
	
	/**
	 *  查询工资信息/汇总
	 */
	@Override
	public List<SalaryImportVO> getSalaryInfoList(Map<String, Object> paramsMap) {
		logger.info("查询工资信息/汇总 ");
		String startDate = (String) paramsMap.get("startDate");
		String endDate = (String) paramsMap.get("endDate");
		List<SalaryImportVO> list  = new ArrayList<SalaryImportVO>();
		if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)) {
			return salaryWebMapper.getSumSalaryInfoList(paramsMap);
		}else {
			 list =salaryWebMapper.getSalaryInfoList(paramsMap);
			return list;
		}
	}
	
	/**
	 * 排序
	 * @param oaSalaryImportTemplates
	 */
	private static void sort(List<SalaryVO> SalaryVO){
		 Collections.sort(SalaryVO, new Comparator<SalaryVO>() {  
			  
	            @Override  
	            public int compare(SalaryVO o1, SalaryVO o2) { 
	            	//升序
	            	return o2.getIssueTime().compareTo(o1.getIssueTime());
	            	}
	        }); 
	}
	
	
	private static String getNotNull(String str) {
		if(StringUtils.isEmpty(str)) {
			str="0";
		}
		return str;
		
	}
}
