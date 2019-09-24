package com.icbc.rel.hefei.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.icbc.mims.thirdparty.filter.util.MimsDes;


public class TransImg2SHA1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TransImg2SHA1.class);
	public static final String[] Ch = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	private static final List<String> SUFFIXLIST = Arrays.asList("jpg","jpeg","bmp","png","gif");
	public static Random random = new Random();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		request.setCharacterEncoding("UTF-8");
		String imgDivId = "";
		String hadoopAddress ="";
		//imgDivId = request.getParameter("fileImgDiv");
		
		hadoopAddress = "122.20.221.99:80";//imhdfs.dccnet.com.cn
		
		// 新增上传图片出错的页面提示（lxq）
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			
			//HttpPost httppost = null;
			FileItem fileItem = null;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if(item.isFormField()){
					imgDivId = item.getString();
				}else{
					fileItem = item;
					
				}
				
			}
			// 对上传图片流的大小做判断
			if ((fileItem != null )&& (fileItem.getSize() < 300 * 1024)&&(checkIsChar(imgDivId))) {
				uploadImg(hadoopAddress,fileItem, imgDivId, request, response);
			} else {
				out.write("<script type='text/javascript'>(function(){document.domain='icbc.com.cn';})();parent.callbackUploadInfo('size overflow')</script>");
			}
		} catch (Exception e) {
			System.out.println("问题异常：" + e);
			logger.error("img upload path error:" + e);
		}
		
	}

	
		private void uploadImg(String hadoopAddress,FileItem item, String imgDivId,	HttpServletRequest request, HttpServletResponse response) {

		String mpid = null;
		String policy = "P0002";
		String sha1Code = null;
		String url =  "http://"+hadoopAddress+"/savefile.action";
		//String url = "http://imhdfs.dccnet.com.cn/savefile.action";
		InputStream uploadedStream = null;
		//修改后的java方法
		HttpMethod method = null;
		HttpConnection connection = null;
		
		HttpSession session = (HttpSession)request.getSession();
		if(session.getAttribute("dse_session")!=null){
			Map ICBCINBSSettings = ((Map) session.getAttribute("dse_session"));
			mpid = (String) ICBCINBSSettings.get("ss_mpid");
		}
		
		String fileName = item.getName();
		//待修改
		//policy = IEPA3DESUtil.encrypt3DES(policy,keyfile,1);
		mpid = MimsDes.encryptFixed(mpid);
		fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
		fileName = UUID.randomUUID().toString()+fileName.substring(fileName.indexOf("."));
		
		System.out.println("fileName:"+fileName);
		try{
			//新增判断文件类型9月修改（lxq）
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			//检验上传文件是否是图片类型			
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			if(!isImage(item.get())||!isImageType(suffix)){
				out.write("<script type='text/javascript'>parent.callbackUploadInfo('type error')</script>");
				return;
			};
			System.out.println("MimsDes.getSHA1Data(item.getString())=="+SHA1(item.getInputStream()));
			sha1Code =  MimsDes.encryptFixed(SHA1(item.getInputStream()));
			
			uploadedStream = item.getInputStream();
			String imgName = MimsDes.encryptFixed(fileName);
			url += "?u=" + mpid+ "&f=" + imgName+ "&c=" + sha1Code+ "&p=" + policy;
			
			//修改方法
			method = new PostMethod(url);
			ByteArrayRequestEntity entity = new ByteArrayRequestEntity(item.get());
			((PostMethod) method).setRequestEntity(entity);
			method.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + "UTF-8");
			method.setRequestHeader("keep-alive", "false");
			/*if (header != null) {
				for (String key : header.keySet()) {
					method.setRequestHeader(key, header.get(key));
				}
			}*/
			URL _url = new URL(url);
			System.out.println(_url);
			connection = new HttpConnection(_url.getHost(), _url.getPort());
			connection.getParams().setConnectionTimeout(180000);
			connection.getParams().setSoTimeout(360000);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					this);
			connection.open();

			int status = method.execute(new HttpState(), connection);
			InputStream inStream = null;
			StringBuffer sBuffer = new StringBuffer();
			try {
				inStream = method.getResponseBodyAsStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((
						len = inStream.read(buf)) != -1) {
					byte[] tmp = new byte[len];
					System.arraycopy(buf, 0, tmp, 0, len);
					sBuffer.append(new String(tmp, "gbk"));
				}
				String resBuffer = sBuffer.substring(2);
				JSONObject obJson = new JSONObject(resBuffer);
				String imgUri = obJson.getString("d");
				String codeNum = obJson.getString("c");
				System.out.println(imgUri);
				System.out.println(codeNum);
				/*if(codeNum=="0"){
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
				}*/
				
				//9月修改（lxq）
				/*response.setContentType("text/html");
				PrintWriter out = response.getWriter();*/
				if(!codeNum.equals("0"))
				{
					out.write("<script type='text/javascript'>(function(){document.domain='icbc.com.cn';})();parent.callbackUploadInfo('type error')</script>");
				}
				else
				{
					
					//String httpURL = "http://imhdfs.icbc.com.cn/userfiles";
					String httpURL = "http://"+hadoopAddress+"/userfiles";
					String path= httpURL + imgUri;
					out.write("<script type='text/javascript'>(function(){document.domain='icbc.com.cn';})();parent.document.getElementById('"+imgDivId+"_img').src='"+path.replace('\\', '/')+"';parent.document.getElementById('"+imgDivId+"_img').parentNode.style.display='block'; </script>");
					//out.write("<script type='text/javascript'>(function(){document.domain='icbc.com.cn';})();parent.document.getElementById('absolutePath_val').value='"+path.replace('\\', '/')+"';</script>");
					out.flush();
				}
				
				
				
				
				
				System.out.println(sBuffer);
			} finally {
				if (inStream != null) {
					inStream.close();
				}
			}

		} catch (Exception e) {
			System.out.println("问题异常：" + e);
			logger.error("img upload path error:" + e);
			response.setContentType("text/html");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.write("<script type='text/javascript'>(function(){document.domain='icbc.com.cn';})();parent.callbackUploadInfo('upload error')</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} finally {
		}
	
	}
	private static String parseResponse(HttpEntity resEntity, String uplaodURL) throws ParseException, IOException {
		String result = null;
		
		String resEntityMsg = EntityUtils.toString(resEntity, "UTF-8");
		logger.info("upload file response: " + resEntityMsg);
		String[] resultMsgs = resEntityMsg.substring(3, resEntityMsg.length() - 1).split(",");
		//测试数据
		System.out.println(resultMsgs[1]);
		if ("'c':'0'".equals(resultMsgs[1])) {
			//result = resultMsgs[0].substring(5, resultMsgs[0].length() - 1);
			//System.out.println();
			
		} else {
			return "-1";
		}
		
		String fileUrl = uplaodURL.substring(0, uplaodURL.lastIndexOf('/'));
		result = fileUrl + "/userfiles" + result;
		logger.info("upload file result: " + result);
		return result;
	}
	private void responseMsg(HttpServletResponse response,String msg) throws IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("text/json; charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		pw.println(msg);
		pw.flush();
	}
	public static String getNowDate(){
		Calendar calendar2= Calendar.getInstance();
		calendar2.add(Calendar.DATE,0);
		String nowDate=new SimpleDateFormat("yyyyMMdd").format(calendar2.getTime());
		return nowDate;
	}
	
	//生成时间戳＋4位随机数的文件名
	/*	public static String getImageString(String img){
			String imgname = img.substring(img.lastIndexOf("."));
			System.out.println(imgname);
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < 4; i++) {
				buffer.append(Ch[random.nextInt(Ch.length)]);
			}
			return "IMG_"+getNowDate()+System.currentTimeMillis()+buffer.toString()+imgname;
		}*/
		
	//转化成SHA1
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
		
		public static boolean checkIsChar(String str){
			String reg = "^[a-z0-9A-Z_\\-]+$";
			return str.matches(reg);
		}
		
		public static boolean isImage(byte [] b){
			if(b==null||b.length<=0){
				return false;
			}
			int len = b.length<4?b.length:4;
			StringBuffer sb = new StringBuffer();
			String str;
			for (int i = 0; i < len; i++) {
				str = Integer.toHexString(b[i]&0xFF).toUpperCase();
				if(str.length()<2){
					sb.append(0);
				}
				sb.append(str);
			}
			str = sb.toString();		
			if(str.contains("FFD8FF")||str.contains("89504E47")||str.contains("47494638")||str.contains("424D")){
				return true;
			}
			return false;
		}
		
		public static boolean isImageType(String suffix){	
			return suffix==null?false:SUFFIXLIST.contains(suffix.toLowerCase());
		}
}
