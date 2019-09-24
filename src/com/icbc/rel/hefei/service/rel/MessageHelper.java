package com.icbc.rel.hefei.service.rel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.PicmessageTO;

import net.sf.json.JSONArray;

public class MessageHelper {
	private static Logger logger = Logger.getLogger(MessageHelper.class);
	
	
	
	
	/*
	 * 文本消息
	 */
	public static JSONObject getTextMessage(String mpid, String IMUserId, String msgTitle, String msgContent) {
		JSONObject obj = new JSONObject();
		String uuid=UUID.randomUUID().toString().replace("-", "");	
		obj.put("msgId", uuid);//消息id
		obj.put("msgFrom", mpid);//消息发送方的公众平台id
		obj.put("msgToIMUserId", IMUserId);//消息接收方的IMUserId
		obj.put("msgTitle", msgTitle);//消息title
		obj.put("msgType", "text");//消息类型, 文本消息：text
		obj.put("msgContent", msgContent);//消息内容
		obj.put("isAllowSendToOther", "0");//是否允许分享转发,0：否  1：是  2：行内允许
		obj.put("attatchId", "");//图文消息素材id
		obj.put("articles", "");
		return obj;
	}

	/*
	 * 图文消息
	 */
	public static JSONObject getPicMessage(String mpid, String IMUserId, String msgTitle, String msgContent, JSONArray articles) {
		JSONObject obj = new JSONObject();
		String uuid=UUID.randomUUID().toString().replace("-", "");
		String attatchId = UUID.randomUUID().toString().replace("-", "");
		obj.put("msgId", uuid);//消息id
		obj.put("msgFrom", mpid);//消息发送方的公众平台id
		obj.put("msgToIMUserId", IMUserId);//消息接收方的IMUserId
		obj.put("msgTitle", "");//消息title
		obj.put("msgType", "news");//消息类型,图文消息:news
		obj.put("msgContent", "");//消息内容
		obj.put("isAllowSendToOther", "0");//是否允许分享转发,0：否  1：是  2：行内允许
		obj.put("attatchId", attatchId);//图文消息素材id
		obj.put("articles", articles);
		return obj;
	}
	
	/*
	 * 组建图文消息
	 */
	public static JSONArray GetArticles( String title, String description,  String picurl,String url) {
		
		JSONArray jsondetailArray = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("newsTitle", title);
		obj1.put("description", description);
		obj1.put("contentUrl", url);
		obj1.put("picUrl", picurl);
		jsondetailArray.add(obj1);
		
		return jsondetailArray;
	}
	
}
