package com.icbc.rel.hefei.service.rel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import org.json.JSONObject;

import com.icbc.mims.thirdparty.filter.util.MimsDes;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.TO.TwoTupleTO;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;

public class HadoopService {

	private static Logger logger = Logger.getLogger(HadoopService.class);
	public static final String[] Ch = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private static final List<String> SUFFIXLIST = Arrays.asList("jpg", "jpeg", "bmp", "png", "gif");

	/*
	 * 支持批量上传和单个上传
	 */
	public static Msg getUploadFile(HttpServletRequest request,int size) {
		Msg msg=new Msg();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List<TwoTupleTO> result = new ArrayList<TwoTupleTO>();
			if (!ServletFileUpload.isMultipartContent(request)) {
               msg.setCode(-1);
               msg.setMessage("图片上传上失败");
			}
			List<FileItem> list = upload.parseRequest(request);
			String hadoopAddress = SystemConfigUtil.hadoopAddress;
			String mpid = SessionUtil.getMpId(request.getSession());
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					
				} else {// 如果fileitem中封装的是上传文件
					// 对上传图片流的大小做判断
					if (item.getSize() < size*1024) {
						String url = uploadImg(hadoopAddress, item, mpid);
						if (url != null) {
							TwoTupleTO to = new TwoTupleTO();
							to.setValue(url);
							String fileName = item.getName();
							fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
							fileName = fileName.substring(0, fileName.indexOf("."));
							to.setName(fileName);
							result.add(to);
						}
					}else {
						msg.setCode(-1);
			            msg.setMessage("图片过大！");
			            return msg;
					}
				}

			}
			msg.setData(result);
			msg.setCode(1);
			return msg;
		} catch (FileUploadException e) {
			logger.error("图片上传报错",e);
			msg.setCode(-1);
			msg.setMessage("图片上传失败！");
			return msg;
		}

	}

	/*
	 * 上传图片至hadoop服务器
	 */
	private static String uploadImg(String hadoopAddress, FileItem item, String mpid) {

		String policy = "P0015";
		String sha1Code = null;
		String url = hadoopAddress + "savefile.action";
		// String url = "http://imhdfs.icbc.com.cn/savefile.action"
		// 修改后的java方法
		HttpMethod method = null;
		HttpConnection connection = null;
		try {
			String fileName = item.getName();

			mpid = MimsDes.encryptFixed(mpid);
			fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
			fileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));

			System.out.println("fileName:" + fileName);

			// 检验上传文件是否是图片类型
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!isImage(item.get()) || !isImageType(suffix)) {
				// 类型不正确
				return null;
			}
			;
			System.out.println("MimsDes.getSHA1Data(item.getString())==" + SHA1(item.getInputStream()));
			sha1Code = MimsDes.encryptFixed(SHA1(item.getInputStream()));

			String imgName = MimsDes.encryptFixed(fileName);
			url += "?u=" + mpid + "&f=" + imgName + "&c=" + sha1Code + "&p=" + policy;
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
			String imgUri = obJson.getString("d");
			String codeNum = obJson.getString("c");
			logger.info("returnStr:" + obJson);
			// returnStr:{"c":"0","d":"/public/static/d45efbd32870929a95edaf04d642ac9e0f61346c.png"}
			if (!codeNum.equals("0")) {
				return null;
			} else {
				// 上传成功
				return hadoopAddress + "userfiles" + imgUri;
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

	private static boolean checkIsChar(String str) {
		String reg = "^[a-z0-9A-Z_\\-]+$";
		return str.matches(reg);
	}

	private static boolean isImage(byte[] b) {
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
		if (str.contains("FFD8FF") || str.contains("89504E47") || str.contains("47494638") || str.contains("424D")) {
			return true;
		}
		return false;
	}

	public static boolean isImageType(String suffix) {
		return suffix == null ? false : SUFFIXLIST.contains(suffix.toLowerCase());
	}

}
