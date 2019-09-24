package com.icbc.rel.hefei.util;
/**
 * @Description: 概率转化
 * @author xujunjie
 * @date 2018年12月7日
 */

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProbTransform {

	public static Map<String, Double> probTransform(HashMap<String, Double> map){
		Double probTotal = 0.0;
		Double probTransTotal = 0.0;
		Object maxKey = getMaxKey(map);
		for(String s:map.keySet()){
            probTotal = probTotal +map.get(s);
        }
		for(String s:map.keySet()){
			if(s != (String) maxKey) {
				double prob = map.get(s);
				prob = prob/probTotal;
				BigDecimal b = new BigDecimal(prob);
				double f1 = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(f1 < 0.0001) {
					f1 = 0.0001;
				}
				probTransTotal = probTransTotal + f1;
				map.put(s,f1);
			}
        }
		map.put((String) maxKey, 1.0-probTransTotal);
		for(String s:map.keySet()){
            System.out.println("key : "+s+" value : "+map.get(s));
        }
		return map;
		
	}
	
	public static Object getMaxValue(Map<String, Double> map) {
		if (map == null) return null;
		Collection<Double> c = map.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		return obj[obj.length-1];
	}
	
	public static Object getMaxKey(Map<String, Double> map) {
		if (map == null) return null;
		Set<String> set = map.keySet();
		Object[] obj = set.toArray();
		Arrays.sort(obj);
		return obj[obj.length-1];
	}
	
	 public static void main(String[] args) {
		 HashMap<String, Double> map = new HashMap();
		 map.put("1等奖", 0.0001);
		 map.put("2等奖", 0.2001);
		 map.put("3等奖", 0.1001);
		 map.put("5等奖", 0.3);
		 probTransform(map);
	 }
}
