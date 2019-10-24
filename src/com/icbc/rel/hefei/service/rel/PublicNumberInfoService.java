package com.icbc.rel.hefei.service.rel;

import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.XML;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;
import com.icbc.rel.hefei.util.DateUtil;
import com.icbc.rel.hefei.util.SocketUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;

public class PublicNumberInfoService {
	private static final Logger logger = Logger.getLogger(PublicNumberInfoService.class);
	
	/*
	 * 	公众号信息查询
	 */
	public static SysPublicNumberInfo FetchPubAddrInfo(SysPublicNumberInfo publicNumberInfo) {
		if (SystemConfigUtil.isDebug) {
			if (publicNumberInfo.getPublicNumberId().equals("10090076")) {
				publicNumberInfo.setStruName("浙江分行");
				publicNumberInfo.setOrgName("中国工商银行股份有限公司");
				publicNumberInfo.setStru_ID("0120100000");
				publicNumberInfo.setUserType("3");
				publicNumberInfo.setMpSubFlag("1");
			}
			return publicNumberInfo;
		}
		String ip = SocketUtil.getLocalIp();
		String date = DateUtil.getFdate();
		String time = DateUtil.getFMtime();
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" + 
				"<APPROOT>" + 
					"<PUBLIC>" + 
						"<SERVER_IP>" + ip + "</SERVER_IP>" + 
						"<DT>" + date + "</DT>" + 
						"<TM>" + time + "</TM>" + 
						"<CHANNELIDENTIFIER></CHANNELIDENTIFIER>" + 
						"<CHANNELCODE>F-MIMS</CHANNELCODE>" + 
					"</PUBLIC>" + 
					"<PRIVATE>" + 
						"<MPID>"+publicNumberInfo.getPublicNumberId()+"</MPID>" + 
					"</PRIVATE>" +
				"</APPROOT>";
		String returnstr = null;// 返回结果的字符串
		try {

			String apiUrl = SystemConfigUtil.apiUrl;
			String url = apiUrl + "IMServiceServer/servlet/ThirdPartyServlet?Channel=IM&fCode=HF006&xmlString="
					+ URLEncoder.encode(xml, "GBK");
			logger.info("获取公众号信息url:" + url);
			HttpGet httpget = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse result = client.execute(httpget);
			byte[] returnByte = IOUtils.toByteArray(result.getEntity().getContent());
			returnstr = new String(returnByte, "GBK");
			if (returnstr == null || returnstr.equals("")) {
				return null;
			}
			returnstr = URLDecoder.decode(returnstr, "GBK");
			logger.info("return:" + returnstr);
			//Document doc = DocumentHelper.parseText(returnstr);
			JSONObject jsonret = XML.toJSONObject(returnstr);
	        jsonret = jsonret.getJSONObject("APPROOT");
	        JSONObject err = jsonret.getJSONObject("PUBLIC");
			JSONObject data = jsonret.getJSONObject("PRIVATE").getJSONObject("RECODE");
			// errcode返回值0表示成功，其余均为失败
			if (err.optString("RETCODE").trim().equals("0")) {
				publicNumberInfo.setStruName(data.optString("STRUNAME"));
				publicNumberInfo.setOrgName(data.optString("ORGNAME"));
				publicNumberInfo.setStru_ID(data.optString("STRU_ID"));
				publicNumberInfo.setUserType(data.optString("USERTYPE"));
				publicNumberInfo.setMpSubFlag(data.optString("MPSUBFLAG"));
				logger.info(JSON.toJSON(publicNumberInfo));
				return publicNumberInfo;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("拉取公众号信息报错", e);
			return null;
		}
	}

}
