package com.icbc.rel.hefei.util;


import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * ʱ�乤����
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static String YYYY = "yyyy";

	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * ��ȡ��ǰDate������
	 * 
	 * @return Date() ��ǰ����
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * ��ȡ��ǰ����, Ĭ�ϸ�ʽΪyyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����·�� ����/��/�� ��2018/08/08
	 */
	public static final String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * ����·�� ����/��/�� ��20180808
	 */
	public static final String dateTime() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	/**
	 * �������ַ���ת��Ϊ���� ��ʽ
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * ��ȡ����������ʱ��
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * ��������ʱ���
	 */
	public static String getTimePoor(Date endDate, Date nowDate) {
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// �������ʱ��ĺ���ʱ�����
		long diff = endDate.getTime() - nowDate.getTime();
		// ��������Сʱ
		long hour = diff / nh;
		// �������ٷ���
		long min = diff % nh / nm;
		// ����������//������
		// long sec = diff % nd % nh % nm / ns;
		if (hour <= 0) {
			return min + "����";
		}
		return hour + "Сʱ" + min + "����";
	}

	/**
	 * ��������ʱ���
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// �������ʱ��ĺ���ʱ�����
		long diff = endDate.getTime() - nowDate.getTime();
		// ����������
		long day = diff / nd;
		// ��������Сʱ
		long hour = diff % nd / nh;
		// �������ٷ���
		long min = diff % nd % nh / nm;
		// ����������//������
		// long sec = diff % nd % nh % nm / ns;
		return day + "��" + hour + "Сʱ" + min + "����";
	}

	/**
	 * ��ȡ�������������
	 * 
	 * @param hour
	 * @param minute
	 * @param delay
	 * @return
	 */
	public static String getCalculateDate(Date date, int hour, int minute, int delay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);

		calendar.add(Calendar.SECOND, delay);

		return parseDateToStr(YYYY_MM_DD_HH_MM_SS, calendar.getTime());
	}

	/**
	 * �Ƚ�����ʱ��Ĵ�С
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareTime(String date1, String date2) {
		Date oDate1 = dateTime(YYYY_MM_DD_HH_MM_SS, date1);
		Date oDate2 = dateTime(YYYY_MM_DD_HH_MM_SS, date2);
		return oDate1.compareTo(oDate2) > 0;
	}

	/**
	 * ��ȡ�����µ��������
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMaxDayByYearMonth(int year, int month) {
		int maxDay = 0;
		int day = 1;
		/**
		 * ���������Ի���������һ����Calendar �ṩ��һ���෽�� getInstance�� �Ի�ô����͵�һ��ͨ�õĶ���Calendar ��
		 * getInstance ��������һ �� Calendar �����������ֶ����ɵ�ǰ���ں�ʱ���ʼ����
		 */
		Calendar calendar = Calendar.getInstance();
		/**
		 * ʵ�������������ֶ�,�����dayΪʵ����ʹ��
		 */
		calendar.set(year, month - 1, day);
		/**
		 * Calendar.Date:��ʾһ�����е�ĳ�� calendar.getActualMaximum(int field):����ָ�������ֶο���ӵ�е����ֵ
		 */
		maxDay = calendar.getActualMaximum(Calendar.DATE);
		return maxDay;
	}

	/**
	 * ����ĸ�ʽ���������� 2019-06
	 * 
	 * @param yearMonth
	 * @return
	 */
	public static int getMaxDayByYearMonth(String yearMonth) {
		String[] arr = yearMonth.split("-");
		if (arr != null && arr.length == 2) {
			return getMaxDayByYearMonth(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
		}
		return 1;
	}
	
    /**
    * ��ȡĳ���һ������
    * @param year ���
    * @return Date
    */
    public static Date getYearFirst(int year){
	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.set(Calendar.YEAR, year);
	    Date currYearFirst = calendar.getTime();
	    return currYearFirst;
    }
    /**
    * ��ȡĳ�����һ������
    * @param year ���
    * @return Date
    */
    public static Date getYearLast(int year){
	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.set(Calendar.YEAR, year);
	    calendar.roll(Calendar.DAY_OF_YEAR, -1);
	    Date currYearLast = calendar.getTime();
	    return currYearLast;
    }
}
