package com.icbc.rel.hefei.service.rel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.json.JSONObject;


import com.icbc.mims.thirdparty.filter.util.MimsDes;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.TO.TwoTupleTO;
import com.icbc.rel.hefei.util.SystemConfigUtil;

public class UpLoadFileDemo {
	
	private static Logger logger = Logger.getLogger(UpLoadFileDemo.class);
	
	/**
	 * 把一个文件转换成二进制数组
	 * @param f
	 * @return
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		 String path="//D:/test/tmp\\e2b88a90ecdc4da589c8699725a15727l.xls";//D:/test/tmp\e2b88a90ecdc4da589c8699725a15727l.xls
		 uploadHadoop(path, "1000123", "e2b88a90ecdc4da589c8699725a15727l.xls", "P0015", null);
	}
	public static String uploadHadoop(String filePath,String mpid,String msgid,String policy,Map<String, String> header) throws Exception{		 
		FileInputStream file= new FileInputStream(filePath);
		String mpid1 = MimsDes.encryptFixed(mpid);
		String msgid1 = MimsDes.encryptFixed(msgid);
		String sha1Code1 =  MimsDes.encryptFixed(SHA1(file));
		String HadoophAddress ="imhdfs.icbc.com.cn";
		String url = "http://"+HadoophAddress+"/savefile.action?u=" + mpid1+ "&f=" + msgid1+ "&c=" + sha1Code1+ "&p="+policy;
		//String url = "http://imhdfs.icbc.com.cn  /savefile.action?u=" + mpid1+ "&f=" + msgid1+ "&c=" + sha1Code1+ "&p="+policy;
		//logger.error("uploadHadoop url--------"+url);
		HttpMethod method = null;
        HttpConnection connection = null;
        method = new PostMethod(url);
        ByteArrayRequestEntity entity = new ByteArrayRequestEntity(getBytesFromFile(filePath));
        ((PostMethod) method).setRequestEntity(entity);
        String errorPage  ="";
        method.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=" + "UTF-8");
        if (header != null) {
            for (String key : header.keySet()) {
                method.setRequestHeader(key, header.get(key));
            }
        }
        String imgUri = "";
		String codeNum = "";
		String resb = "";
		int status = 0;
        try {
        URL _url = new URL(url);
        connection = new HttpConnection(_url.getHost(), _url.getPort());
        connection.getParams().setConnectionTimeout(5000);
        connection.getParams().setSoTimeout(30000);
        //method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER);
        connection.open();

        status = method.execute(new HttpState(), connection);
        InputStream inStream = null;
        StringBuffer sb = new StringBuffer();

            inStream = method.getResponseBodyAsStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buf)) != -1) {
                byte[] tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                sb.append(new String(tmp, "UTF-8"));
            }
 
            System.out.println("上传成功后返回地址："+sb.toString());
            resb = sb.substring(2);
            //sysLogger.error("uploadHadoop status:"+status+"-----result:"+resb);
			JSONObject obJson = new JSONObject(resb);
			imgUri = obJson.getString("d");
			codeNum = obJson.getString("c");
			String httpURL = "http://"+HadoophAddress+"/userfiles";
			String path= httpURL + imgUri;
			obJson.put("result", path);
			//logger.debug(obJson.toString());
			if("0".equals(codeNum)){
				return obJson.toString();
			}
            
        } catch (Exception e) {
        	//sysLogger.error("uploadHadoop error  status:"+status+"-----url:"+url+"-----result:"+resb);
            return "FAILD"+e.getMessage();
        }finally{
        	File f = new File(filePath);
        	if(f.exists()&&!filePath.contains("errorpage.html")){
        		f.delete();
        	}
        }
        return "FAILD　return code:"+codeNum;
	}	
	public static byte[] getBytesFromFile(String filePath){
		try{
			FileInputStream stream=new FileInputStream(filePath);
			ByteArrayOutputStream out=new ByteArrayOutputStream(1000);
			byte[] b=new byte[1000];
			int n;
			while((n=stream.read(b))!=-1)
				out.write(b,0,n);
				stream.close();
				out.close();
			return out.toByteArray();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 转化成SHA1
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public final static String SHA1(InputStream in) throws IOException {
		if (in == null) {
			return null;
		}
		StringBuffer strResult = new StringBuffer();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		byte[] b = new byte[1024 * 1024];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			md.update(b, 0, len);
		}
		if (in != null) {
			in.close();
		}
		byte[] encryptedBytes = md.digest();
		String stmp;
		for (int n = 0; n < encryptedBytes.length; n++) {
			stmp = (Integer.toHexString(encryptedBytes[n] & 0XFF));
			if (stmp.length() == 1) {
				strResult.append("0");
				strResult.append(stmp);
			} else {
				strResult.append(stmp);
			}
		}
		return strResult.toString().toLowerCase();
	}
	
}


		  

