package com.icbc.rel.hefei.service.rel;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.icbc.mims.thirdparty.filter.util.MimsDes;
import com.icbc.rel.hefei.util.SystemConfigUtil;

/*
 * 融e联消息礼券接口服务
 */
@Service
public class CouponService {
	private static final Logger logger = Logger.getLogger(CouponService.class);
	/*
	 * 向指定融e联用户发送礼券
	 * @param imUserId 融e联id
	 * @result 1/0
	 */
	public boolean SendCoupon(String userid,String actno,String mpid) {
		String returnstr = null;// 返回结果的字符串
		try {
			
				String apiUrl=SystemConfigUtil.apiUrl;
				String custom=MimsDes.encryptFixed(userid);
				//"IMServiceServer/servlet/ThirdPartyServlet"
				String url = apiUrl+"IMServiceServer/servlet/ThirdPartyServlet?fCode=HF003&Channel=HF"
						+"&in_actno="
						+ URLEncoder.encode(actno, "utf-8") 
						+"&in_mpid="
						+URLEncoder.encode(mpid, "utf-8")
						+"&in_custom="
						+URLEncoder.encode(custom, "utf-8")
						+"&in_manager="
						+URLEncoder.encode(custom, "utf-8");
				logger.info("发放礼券url:"+url);
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
				if(jsonret.getString("RETCODE").trim().equals("0")) {
					logger.info("礼券发送成功");
					return true;
				}else {
					return false;
				}
		
		} catch (Exception e) {
			//logger.error("发送礼券报错", e);
			return false;
		}
	}

}
