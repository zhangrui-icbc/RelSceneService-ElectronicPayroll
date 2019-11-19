package com.icbc.rel.hefei.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * �ֻ���У�鹤����
 *
 */
public class MobileUtil {

    /**
     * �й����ź����ʽ��֤ �ֻ��Σ� 133,149,153,173,177,180,181,189,199,1349,1410,1700,1701,1702
     **/
    private static final String CHINA_TELECOM_PATTERN = "(?:^(?:\\+86)?1(?:33|49|53|7[37]|8[019]|99)\\d{8}$)|(?:^(?:\\+86)?1349\\d{7}$)|(?:^(?:\\+86)?1410\\d{7}$)|(?:^(?:\\+86)?170[0-2]\\d{7}$)";

    /**
     * �й���ͨ�����ʽ��֤ �ֻ��Σ�130,131,132,145,146,155,156,166,171,175,176,185,186,1704,1707,1708,1709
     **/
    private static final String CHINA_UNICOM_PATTERN = "(?:^(?:\\+86)?1(?:3[0-2]|4[56]|5[56]|66|7[156]|8[56])\\d{8}$)|(?:^(?:\\+86)?170[47-9]\\d{7}$)";

    /**
     * �й��ƶ������ʽ��֤
     * �ֻ��Σ�134,135,136,137,138,139,147,148,150,151,152,157,158,159,178,182,183,184,187,188,198,1440,1703,1705,1706
     **/
    private static final String CHINA_MOBILE_PATTERN = "(?:^(?:\\+86)?1(?:3[4-9]|4[78]|5[0-27-9]|78|8[2-478]|98)\\d{8}$)|(?:^(?:\\+86)?1440\\d{7}$)|(?:^(?:\\+86)?170[356]\\d{7}$)";

    /**
     * �й���½�ֻ�����У��
     *
     * @param phone
     *
     * @return
     */
    public static boolean checkPhone(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            if (checkChinaMobile(phone) || checkChinaUnicom(phone) || checkChinaTelecom(phone)) {
                return true;
            }
        }
        return false;
    }

    /**
     * �й��ƶ��ֻ�����У��
     *
     * @param phone
     *
     * @return
     */
    public static boolean checkChinaMobile(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_MOBILE_PATTERN);
            if (regexp.matcher(phone).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * �й���ͨ�ֻ�����У��
     *
     * @param phone
     *
     * @return
     */
    public static boolean checkChinaUnicom(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_UNICOM_PATTERN);
            if (regexp.matcher(phone).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * �й������ֻ�����У��
     *
     * @param phone
     *
     * @return
     */
    public static boolean checkChinaTelecom(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_TELECOM_PATTERN);
            if (regexp.matcher(phone).matches()) {
                return true;
            }
        }
        return false;
    }

    
    
    /**
     * ͨ�ô�½�ֻ�����У��(1��ͷ11λ)
     *
     * @param phone
     *
     * @return
     */
    public static boolean checkGeneralPhone(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            if (phone.trim().length()==11&&phone.startsWith("1")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * �����ֻ����м���λ
     *
     * @param phone
     *
     * @return java.lang.String
     */
    public static String hideMiddleMobile(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phone;
    }

    public static void main(String[] args) {
    	if(!checkPhone("13800000001")) {
    		System.out.println(checkPhone("11800000001"));
    	}
    }

}