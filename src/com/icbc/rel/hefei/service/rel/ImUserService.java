package com.icbc.rel.hefei.service.rel;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;
import com.icbc.rel.hefei.entity.UserDetailInfo;
import com.icbc.rel.hefei.util.SystemConfigUtil;

/*
 * 获取融e联用户详情信息
 */
public class ImUserService {
	private static final Logger logger = Logger.getLogger(ImUserService.class);

	/*
	 * 获取用户信息 return:昵称、省城市、注册时间、星级、手机号、是否实名等信息 1.请求数据中不要有多余的空格。 2.要求使用字符集UTF-8。
	 * 3.注意请求地址以http开头，而不是https。 4.请求数据的值要做URL编码处理，否则对于一些特殊字符可能会在URL解码时发生错误。
	 * 5.返回的数据要做URL解码处理。
	 */
	public static UserDetailInfo FetchUserInfo(String imUserId) {
		UserDetailInfo userDetail = new UserDetailInfo();
		if (SystemConfigUtil.isDebug) {
			if (imUserId.equals("123")) {
				userDetail.setImUserId("123");
				userDetail.setMobileNo("15056938758");
			}else if(imUserId.equals("456")) {
				userDetail.setImUserId("456");
				userDetail.setMobileNo("13546987452");
			}else if(imUserId.equals("789")) {
				userDetail.setImUserId("789");
				userDetail.setMobileNo("15011111111");
			}
			return userDetail;
		}
		String returnstr = null;// 返回结果的字符串
		try {

			String apiUrl = SystemConfigUtil.apiUrl;
			String url = apiUrl + "IMServiceServer/servlet/ThirdPartyServlet?Channel=IM&fCode=HF001&userid="
					+ URLEncoder.encode(imUserId, "utf-8");
			logger.info("获取用户信息url" + url);
			HttpGet httpget = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse result = client.execute(httpget);
			byte[] returnByte = IOUtils.toByteArray(result.getEntity().getContent());
			returnstr = new String(returnByte, "utf-8");
			logger.info("return:" + returnstr);
			if (returnstr == null || returnstr.equals("")) {
				return null;
			}
			returnstr = URLDecoder.decode(returnstr, "utf-8");
			JSONObject jsonret = JSONObject.parseObject(returnstr);
			JSONObject err = jsonret.getJSONObject("PUBLIC");
			JSONObject data = jsonret.getJSONObject("PRIVATE");
			// errcode返回值0表示成功，其余均为失败
			if (err.getString("errcode").trim().equals("0")) {
				userDetail.setCisno(data.getString("cisno"));
				userDetail.setCity(data.getString("city"));
				userDetail.setCreateTime(new Date());
				userDetail.setIcbcUserId(data.getString("ICBCUserid"));
				userDetail.setImUserId(imUserId);
				userDetail.setMobileNo(data.getString("mobileno"));
				userDetail.setMpId("");
				userDetail.setNickName(data.getString("nickname"));
				userDetail.setCustomerType(data.getString("customerType"));
				userDetail.setProvince(data.getString("province"));
				userDetail.setRegisterTime(data.getString("cisno"));
				userDetail.setSex(data.getString("sex"));
				userDetail.setStarLevel(data.getString("starLevel"));
				userDetail.setUnino(data.getString("unino"));
				logger.info(JSON.toJSON(userDetail));
				return userDetail;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("拉取用户信息报错", e);
			return null;
		}
	}
	
	/*
	 * 	公众号信息查询
	 */
	public static SysPublicNumberInfo FetchPubAddrInfo(SysPublicNumberInfo publicNumberInfo) {
		if (SystemConfigUtil.isDebug) {
			if (publicNumberInfo.getPublicNumberId().equals("10090076")) {
				publicNumberInfo.setStruName("北京分行");
				publicNumberInfo.setOrgName("北京洋洋君安物业管理有限公司");
				publicNumberInfo.setStru_ID("0020000000");
				publicNumberInfo.setUserType("1");
				publicNumberInfo.setMpSubFlag("1");
			}
			return publicNumberInfo;
		}
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n" + 
				"<APPROOT>\r\n" + 
				"　　<PUBLIC>\r\n" + 
				"<fcode>Y0001</fcode>\r\n" + 
				"<server_ip>交易发起终端号（应用服务器IP）</server_ip>\r\n" + 
				"<dt>日期（格式：yyyy-mm-dd）</dt>\r\n" + 
				"<tm>前端开始时间（格式为时分秒，如：083033）</tm>\r\n" + 
				"        <channelIdentifier></channelIdentifier>\r\n" + 
				"        <channeLcODE>渠道代码（F-MIMS）</channeLcODE>\r\n" + 
				"</PUBLIC>\r\n" + 
				"<PRIVATE>\r\n" + 
				"   <MPId>"+publicNumberInfo.getPublicNumberId()+"</MpId>\r\n" + 
				"   </PRIVATE>";
		String returnstr = null;// 返回结果的字符串
		try {

			String apiUrl = SystemConfigUtil.apiUrl;
			String url = apiUrl + "IMServiceServer/servlet/ThirdPartyServlet?Channel=IM&fCode=HF006&xmlString="
					+ URLEncoder.encode(xml, "GBK");
			logger.info("获取公众号信息url" + url);
			HttpGet httpget = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse result = client.execute(httpget);
			byte[] returnByte = IOUtils.toByteArray(result.getEntity().getContent());
			returnstr = new String(returnByte, "utf-8");
			logger.info("return:" + returnstr);
			if (returnstr == null || returnstr.equals("")) {
				return null;
			}
			returnstr = URLDecoder.decode(returnstr, "utf-8");
			JSONObject jsonret = JSONObject.parseObject(returnstr);
			JSONObject err = jsonret.getJSONObject("PUBLIC");
			JSONObject data = jsonret.getJSONObject("PRIVATE");
			// errcode返回值0表示成功，其余均为失败
			if (err.getString("errcode").trim().equals("0")) {
				publicNumberInfo.setStruName(data.getString("struName"));
				publicNumberInfo.setOrgName(data.getString("orgName"));
				publicNumberInfo.setStru_ID(data.getString("Stru_ID"));
				publicNumberInfo.setUserType(data.getString("userType"));
				publicNumberInfo.setMpSubFlag(data.getString("mpSubFlag"));
				logger.info(JSON.toJSON(publicNumberInfo));
				return publicNumberInfo;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("拉取用户信息报错", e);
			return null;
		}
	}
	
	
}
