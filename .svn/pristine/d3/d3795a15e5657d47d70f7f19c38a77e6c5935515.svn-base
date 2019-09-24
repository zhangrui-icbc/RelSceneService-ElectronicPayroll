package com.icbc.rel.hefei.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * 针对用户ip做限流
 * 采用计数器限流法，监控任意5秒钟内，接口/页面/静态资源的访问次数，超过20则丢掉，不予响应。且需要定时移除5秒外的记录。
 */
public class  IplimitUtil {

	
	private static Map<String,List<Long>> accessDatas=new ConcurrentHashMap<>();
	private static long timeWindowMillis=5*1000;//设置监控5秒内的访问次数
	private static int permits=20;//访问次数限制 
	public static void start() {
		Runnable runnable=new Runnable() {
	        @Override 
	        public void run() {
	          long currentTime = System.currentTimeMillis();
              List < Long > accessPoints = null;
	          Iterator<String> idsIterator = accessDatas.keySet().iterator();
	          //检查每个键值对，移除过期的数据
	          while (idsIterator.hasNext()) {
	        	String key=idsIterator.next();
	            accessPoints = accessDatas.get(key);
	            removeExpirePoints(accessPoints, currentTime);
	            List < Long > points = accessDatas.get(key);
                if (points.isEmpty()) {
                    points = null;
                    accessDatas.remove(key);
                }
	        }
	    }

	};
	    //开启定时任务（1秒的频率）
		ScheduledExecutorService service=Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable,1000, 1000, TimeUnit.MILLISECONDS);
	}
	//移除过期的记录
	private  static void removeExpirePoints(List<Long> ponits,long currentTimeMillis){
        if(ponits == null || ponits.isEmpty()){
            return ;
        }
        Iterator<Long> pointsIterator = ponits.iterator(); 
        //移除时间超出范围的记录
        while (pointsIterator.hasNext()) {
            if(pointsIterator.next().compareTo(currentTimeMillis - timeWindowMillis) <= 0){
                pointsIterator.remove();
            }else{
                break;
            }
        }  
   }
	
	/**
	 * 访问频率检查
	 * @param identification 用户标识(ip or sessionId)
	 * @param uri
	 * @return 
	*/
	public  static boolean requestFrequencyCheck(String ip, String uri) {
	    boolean result = false;
	    String identification=ip+"_"+uri;
	    long currentTime = System.currentTimeMillis();
	    List < Long > accessPoints = accessDatas.get(identification);
	    try {
	    //先移除失效的记录
	    removeExpirePoints(accessPoints, currentTime);
	    //再判断当前记录数量是否超限
	    accessPoints = accessDatas.get(identification);
	    if (accessPoints == null) {
	            accessPoints = new Vector<>(permits);
	            accessDatas.put(identification, accessPoints);
	    }
	    if (accessPoints.size() < permits) {
	            result = true;
	    } else {
	           result=false;
	    }
	    return result;
	    } finally {
	        accessPoints.add(currentTime);
	    }
	}
	
}
