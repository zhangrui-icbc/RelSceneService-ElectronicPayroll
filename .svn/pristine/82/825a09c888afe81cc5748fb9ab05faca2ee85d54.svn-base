package com.icbc.rel.hefei.util;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UUIDUtils {
    /**
     * 随机生成32位GUID（大写）
     * 
     * @return
     */
    public static String getGuid() {
        // 随机生成32位GUID
        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String guid = uuid.toString();
        // 转换为大写
        guid = guid.toUpperCase();
        // 替换 -
        guid = guid.replaceAll("-", "");
        return guid;
    }
    /**
     * 订单编号
     * @return
     */
   public static String getOrderId() {
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate=sdf.format(new Date());
		String result="";
		Random random=new Random();
		for(int i=0;i<6;i++){
			result+=random.nextInt(10);
		 	}
		return newDate+result;
   }
    public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			System.out.println(getGuid());
		}
	}
}
