package com.icbc.rel.hefei.service.rel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import com.alibaba.druid.util.Base64;
import com.ibm.misc.BASE64Encoder;
import com.icbc.mims.thirdparty.filter.util.MimsDes;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.TO.TwoTupleTO;
import com.icbc.rel.hefei.util.ExcelUtil;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;

public class UploadFile2HadoopService {
	private static Logger logger = Logger.getLogger(UploadFile2HadoopService.class);
	public static final String[] Ch = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	
	private static final List<String> FileSUFFIXLIST = Arrays.asList("xlsx","xls");
	//private static String filePath = "/home/RelSceneService/config/temp/excel/";
	private static String filePath = "D:/test/";
	
	/*
	 * 支持批量上传和单个上传
	 */
	public static Msg getUploadFile(HttpServletRequest request) {
		Msg msg=new Msg();
		try {			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List<TwoTupleTO> result = new ArrayList<TwoTupleTO>();
			if (!ServletFileUpload.isMultipartContent(request)) {
               msg.setCode(-1);
               msg.setMessage("文件上传失败");
			}
			
			List<FileItem> list = upload.parseRequest(request);
			
			String hadoopAddress = SystemConfigUtil.hadoopAddress;
			String mpid = SessionUtil.getMpId(request.getSession());						
			for (FileItem item : list) {				
				// 如果fileitem中封装的是普通输入项的数据
				if (!item.isFormField()) {				
					String url = uploadFile(hadoopAddress, item, mpid);
					if (url != null) {
						TwoTupleTO to = new TwoTupleTO();
						to.setValue(url);
						String fileName = item.getName();
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						//fileName = fileName.substring(0, fileName.indexOf("."));
						to.setName(fileName);
						result.add(to);
					}
				} else {// 
					logger.info("item.isFormField()");
					logger.info("else");
				}

			}
			msg.setData(result);
			msg.setCode(1);
			return msg;
		} catch (FileUploadException e) {
			logger.error("文件上传报错",e);
			msg.setCode(-1);
			msg.setMessage("文件上传失败！");
			return msg;
		}

	}
	
	public static Msg uploadHadoop(String filePath,String mpid,String msgid,String policy,Map<String, String> header) throws Exception{		 
		Msg msg=new Msg();
		TwoTupleTO result = new TwoTupleTO();
		FileInputStream file= new FileInputStream(filePath);		
		String mpid1 = MimsDes.encryptFixed(mpid);
		String msgid1 = MimsDes.encryptFixed(msgid);
		String sha1Code1 =  MimsDes.encryptFixed(SHA1(file));
		String HadoophAddress =SystemConfigUtil.hadoopAddress;
		String url = HadoophAddress+"/savefile.action?u=" + mpid1+ "&f=" + msgid1+ "&c=" + sha1Code1+ "&p="+policy;
		//String url = "http://imhdfs.icbc.com.cn  /savefile.action?u=" + mpid1+ "&f=" + msgid1+ "&c=" + sha1Code1+ "&p="+policy;
		logger.error("uploadHadoop url--------"+url);
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
			if (!codeNum.equals("0")) {			
				msg.setCode(-1);
				msg.setMessage("文件上传失败！");
				return msg;		
			} else {
				// 上传成功
				  logger.info("本地上传文件的返回路径："+HadoophAddress + "userfiles" + imgUri);
				  msg.setCode(1);
				  msg.setMessage("文件上传成功！");
				  result.setName(msgid);
				  result.setValue(HadoophAddress + "userfiles" + imgUri);
				  msg.setData(result);
				  return msg;	
				//return "http://"+hadoopAddress+"/userfiles"+xlsUri;
			}
            
        } catch (Exception e) {
        	//sysLogger.error("uploadHadoop error  status:"+status+"-----url:"+url+"-----result:"+resb);
        	System.out.println("问题异常：" + e);
    		logger.error("img upload path error:" + e);
    		logger.error("文件上传报错",e);
    		msg.setCode(-1);
    		msg.setMessage("文件上传失败！");
    		return msg;		
        }

	}	
					
	/*
	 * 上传excle文件至hadoop服务器
	 */
	private static String uploadFile(String hadoopAddress, FileItem item, String mpid) {

		String policy = "P0015";
		String sha1Code = null;
		String url = hadoopAddress + "savefile.action";
		HttpMethod method = null;
		HttpConnection connection = null;
		try {
			String fileName = item.getName();
		
			mpid = MimsDes.encryptFixed(mpid);
			fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
			fileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));

			System.out.println("fileName:" + fileName);

			// 检验上传文件是否是excle类型
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!isXLS_DOC(item.get()) || !isXLS_DOCType(suffix)) {
				// 类型不正确
				return null;
			}			
			logger.info("MimsDes.getSHA1Data(item.getString())==" + SHA1(item.getInputStream()));
			sha1Code = MimsDes.encryptFixed(SHA1(item.getInputStream()));

			String filename = MimsDes.encryptFixed(fileName);
			url += "?u=" + mpid + "&f=" + filename + "&c=" + sha1Code + "&p=" + policy;
			logger.info("requestUrl:" + url);

			// 修改方法
			method = new PostMethod(url);
			ByteArrayRequestEntity entity = new ByteArrayRequestEntity(item.get());
			((PostMethod) method).setRequestEntity(entity);
			method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + "UTF-8");
			method.setRequestHeader("keep-alive", "false");
			URL _url = new URL(url);
			connection = new HttpConnection(_url.getHost(), _url.getPort());
			connection.getParams().setConnectionTimeout(180000);
			connection.getParams().setSoTimeout(360000);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			connection.open();

			method.execute(new HttpState(), connection);
			InputStream inStream = null;
			StringBuffer sBuffer = new StringBuffer();
			inStream = method.getResponseBodyAsStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buf)) != -1) {
				byte[] tmp = new byte[len];
				System.arraycopy(buf, 0, tmp, 0, len);
				sBuffer.append(new String(tmp, "gbk"));
			}
			String resBuffer = sBuffer.substring(2);
			JSONObject obJson = new JSONObject(resBuffer);
			String xlsUri = obJson.getString("d");
			String codeNum = obJson.getString("c");
			logger.info("returnStr:" + obJson);
			// returnStr:{"c":"0","d":"/public/static/d45efbd32870929a95edaf04d642ac9e0f61346c.xls"}
			if (!codeNum.equals("0")) {
				return null;
			} else {
				// 上传成功
				return hadoopAddress + "userfiles" + xlsUri;
				//return "http://"+hadoopAddress+"/userfiles"+xlsUri;
			}

		} catch (Exception e) {
			method.releaseConnection();
			System.out.println("问题异常：" + e);
			logger.error("img upload path error:" + e);
			return null;
		}

	}

	private final static String SHA1(InputStream in) throws IOException {
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
		byte[] b = new byte[1024 * 1024 * 10];
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
	
	
	private static boolean checkIsChar(String str) {
		String reg = "^[a-z0-9A-Z_\\-]+$";
		return str.matches(reg);
	}
	
	private static boolean isXLS_DOC(byte[] b) {
		if (b == null || b.length <= 0) {
			return false;
		}
		int len = b.length < 4 ? b.length : 4;
		StringBuffer sb = new StringBuffer();
		String str;
		for (int i = 0; i < len; i++) {
			str = Integer.toHexString(b[i] & 0xFF).toUpperCase();
			if (str.length() < 2) {
				sb.append(0);
			}
			sb.append(str);
		}
		str = sb.toString();
		if (str.contains("D0CF11E0") || str.contains("504B030414000600080000002100")) {
			//XLS_DOC("D0CF11E0"), XLSX_DOCX("504B030414000600080000002100");
			return true;
		}
		return false;
	}

	public static boolean isXLS_DOCType(String suffix) {
		return suffix == null ? false : FileSUFFIXLIST.contains(suffix.toLowerCase());
	}
			
	/**
	 * 获取文件的字节数组
	 * @param f
	 * @return
	 */
	public static byte[] getBytesFromFile(File f){
		if(f==null){
			return null;
		}
		try{
			FileInputStream stream=new FileInputStream(f);
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
	
	public final static String SHA1ByByte(byte[] bInput) {
		StringBuffer strResult = new StringBuffer();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		md.update(bInput);
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
	
}
