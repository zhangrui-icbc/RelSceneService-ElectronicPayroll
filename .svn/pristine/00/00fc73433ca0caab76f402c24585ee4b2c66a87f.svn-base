package com.icbc.rel.hefei.service.rel;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.util.SystemConfigUtil;

/*
 * 融e联消息推送接口服务
 */
public class MessageService {
	private static final Logger logger = Logger.getLogger(MessageService.class);
	/*
	 * 向指定融e联用户发送消息（文本、图文）
	 * @param imUserId 融e联id
	 * @result 1/0
	 */
	public static boolean SendMessage(JSONObject msgBody) {
		String returnstr = null;// 返回结果的字符串
		String uuid=UUID.randomUUID().toString().replace("-", "");
		try {
			
			String apiUrl=SystemConfigUtil.apiUrl;
//				String url = apiUrl+"ICBCMPServer/servlet/ThirdPartyServlet?Channel=IM&fCode=HF002&msgFrom="
//						+URLEncoder.encode(mpid, "utf-8")
//						+"&msgToIMUserId="
//						+ URLEncoder.encode(imUserId, "utf-8") 
//						+"&in_content="
//						+URLEncoder.encode(msg, "utf-8") 
//						+"&in_msgid="
//						+URLEncoder.encode(uuid, "utf-8") ;
				String url = apiUrl+"IMServiceServer/servlet/ThirdPartyServlet?Channel=IM&fCode=HF002&msgBody="+URLEncoder.encode(msgBody.toString(), "utf-8");
				logger.info("发送消息url"+url);
				HttpGet httpget = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				HttpResponse result = client.execute(httpget);
				byte[] returnByte=IOUtils.toByteArray(result.getEntity().getContent());
				returnstr=new String(returnByte,"utf-8");
				logger.info("return:" + returnstr);
			    if (returnstr == null || returnstr.equals("")) {
				  return false;
			    }
			    returnstr=URLDecoder.decode(returnstr,"utf-8");
				JSONObject jsonret = JSONObject.parseObject(returnstr);
				JSONObject err=jsonret.getJSONObject("PUBLIC");
				if(err.getString("RETCODE").trim().equals("0000")) {
					logger.info("发送成功");
					return true;
				}else {
					logger.info("发送失败");
					return false;
					
				}
		
		} catch (Exception e) {
			logger.error("发送图文消息报错", e);
			return false;
		}
	}

}
