package com.icbc.rel.hefei.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间处理工具类
 */
public class TimeUtil {

	/**
	 * 转10位时间输出,时间为当前时间
	 *
	 * @return long 返回10位
	 */
	public static long getTime(){
		Date date=new Date();
		long time = date.getTime();
		System.out.println("date.getTime():"+time);
		/*System.out.println("System.currentTimeMillis():"+System.currentTimeMillis());
		SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("df.format(date):"+df.format(date));
		*/
		System.out.println("date.getTime()/1000:"+time/1000);
		//return time;
		return time/1000;	//除去毫秒
	}

	/**
	 * 返回 yyyy-MM-dd HH:mm:ss 格式时间
	 * @return String
	 */
	public static String getAlipayTime(){
		SimpleDateFormat dateformat2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat2.format(new Date());
	}

	/**
	 * 输入2000-02-16格式时间，输出10数组字符串
	 * @param times 输入2000-02-16格式
	 * @return long 返回10位
	 */
	public static long getTime(String times){
		 SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
	     Date date;
		 long time = 0;
		try {
			date = simpleDateFormat .parse(times);
			time = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return time/1000;	
	}


	/**
	 * 当天0点的时间戳
	 * @return 10位
	 */
	public static long day0(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis()/1000;
	}


	public static String[] dayByNum(int num){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String[] time = new  String[num];
		for(int i=0;i<num;i++){
			time[i] = sdf.format(new Date(day0()*1000 - (num - i - 1) * 60 * 1000));
		}
        return time;
	}


	/**
	 * 获取日期
	 *
	 * @param times  时间戳
	 * @param format 日期字格式字符串
	 * @return 日期字符串
	 */
	public static String getDate(long times, String format) {
		times *= 1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date(times));
	}


	/**
	 * 将时间根据类型转换为间隔和次数
	 * @return
	 */
	public static Map timr2data(int starttime,String tpye){
		Map map = new HashMap();
		if("day".equals(tpye) ){
			map.put("time_interval",3600);
			map.put("time_num",24);
		}else if("week".equals(tpye)){
			map.put("time_interval",86400);
			map.put("time_num",7);
		}else if("month".equals(tpye)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sd = sdf.format(new Date(Long.parseLong(starttime+"000"))); // 时间戳转换日期
			int year = Integer.parseInt(sd.substring(0,4));
			int month = Integer.parseInt(sd.substring(5,7));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR,year);
			cal.set(Calendar.MONTH,month-1);//7月
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			map.put("time_interval",86400);
			map.put("time_num",maxDate);
		}else if("year".equals(tpye)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sd = sdf.format(new Date(Long.parseLong(starttime+"000"))); // 时间戳转换日期
			int year = Integer.parseInt(sd.substring(0,4));
			int month = Integer.parseInt(sd.substring(5,7));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR,year);
			List list = new ArrayList();
			for(int i=0;i<12;i++){
				cal.set(Calendar.MONTH,i);//月
				list.add(cal.getActualMaximum(Calendar.DAY_OF_MONTH)*86400);
			}
			map.put("time_interval",list);
			map.put("time_num",12);
		}
		return map;
	}


	public static void main(String[] arg ){
		 //day0();
	}
	
	
	
}
