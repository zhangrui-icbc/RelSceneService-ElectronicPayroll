package com.icbc.rel.hefei.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

/*
 * 文件操作，包括读、写文件
 * save()	写文件
 */

public class FileOperateUtil {
	
	/**
	 * 保存内容到文件
	 * @param path  	保存文件的路径
	 * @param content   保存文件的内容
	 * @param isAppend  是否已追加等方式保存
	 * @return
	 */
	public static boolean save(String path, String content, boolean isAppend) {
        File file=new File(path);
        try {
        	if(!file.exists())
                file.createNewFile();
            FileOutputStream out=new FileOutputStream(file, isAppend); //如果追加方式用true        
            StringBuffer sb=new StringBuffer();
            sb.append(content);
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        } catch (Exception e) {
        	return false;
        }
		return true;
	}
	
	public static String read(String path) {
		File file=new File(path);
		String content = "";
        try {
        	if(!file.exists())
                return content;
        	BufferedReader reader = null;
        	reader = new BufferedReader(new FileReader(file));
        	content = reader.readLine();
            reader.close();
        } catch (Exception e) {
        	return content;
        }
		return content;
	}
}
