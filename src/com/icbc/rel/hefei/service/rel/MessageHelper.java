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
	
	/*
	 * 组建HF005接口发送的图文消息
	 * 示例："content":{
	 * "attachid":"ec3f360405f74bedbe0ffe00ef1025a7",
	 * "articles":[
	 * {"picurl":"http://imhdfs.icbc.com.cn/userfiles/public/static/0a48d5d9-686e-4859-ac4e-6024539fc5e4.jpg",
	 * "attachno":"1","title":"密码器使用全攻略","description":"","isAllowSendToOther":"1",
	 * "url":"http://imhdfs.icbc.com.cn/userfiles/public/static/ec3f360405f74bedbe0ffe00ef1025a7.html"}]}
	 * 
	 * attachid 素材id用于标识所编辑的图文素材 由J_MP_MATERIAL表中获取内容
     articles 内容素材封面图链接，正文链接等
     
     	   attachno 用于标记第几个图文消息 如密码器使用全攻略图文 attachno送1，第二，第三条图文依次类推
         title    单图文标题 如密码器使用全攻略图文  title：密码器使用全攻略
         description 写死送空
         isAllowSendToOther 是否允许转发 融e联独立版适用 1允许 0不允许 可写死1
         url： 图文消息正文链接，即点进该图文消息后所展示的页面内容 由J_MP_MATERIAL表中获取
	 */
	public static JSONObject getPicArticlesForHF005(String title, String picurl, String url) {
		JSONObject obj = new JSONObject();//这是需要上送的content的内容
		JSONObject result = new JSONObject();//这是需要上送的content的内容
		JSONArray jsondetailArray = new JSONArray();//这是articles的内容
		JSONObject obj1 = new JSONObject();
		obj1.put("attachno", "1");
		obj1.put("title", title);
		obj1.put("description", "");
		obj1.put("isAllowSendToOther", "0");//是否允许分享转发,0：否  1：是 
		obj1.put("url", url);
		obj1.put("picurl", picurl);
		jsondetailArray.add(obj1);
		
		String attatchId = UUID.randomUUID().toString().replace("-", "");
		obj.put("attachid", attatchId);//图文消息素材id
		obj.put("articles", jsondetailArray);
		result.put("news", obj);
		result.put("msgtype", "news");
		return result;
	}
}
