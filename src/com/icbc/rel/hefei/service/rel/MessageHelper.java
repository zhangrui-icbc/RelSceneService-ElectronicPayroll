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
	 * �ı���Ϣ
	 */
	public static JSONObject getTextMessage(String mpid, String IMUserId, String msgTitle, String msgContent) {
		JSONObject obj = new JSONObject();
		String uuid=UUID.randomUUID().toString().replace("-", "");	
		obj.put("msgId", uuid);//��Ϣid
		obj.put("msgFrom", mpid);//��Ϣ���ͷ��Ĺ���ƽ̨id
		obj.put("msgToIMUserId", IMUserId);//��Ϣ���շ���IMUserId
		obj.put("msgTitle", msgTitle);//��Ϣtitle
		obj.put("msgType", "text");//��Ϣ����, �ı���Ϣ��text
		obj.put("msgContent", msgContent);//��Ϣ����
		obj.put("isAllowSendToOther", "0");//�Ƿ��������ת��,0����  1����  2����������
		obj.put("attatchId", "");//ͼ����Ϣ�ز�id
		obj.put("articles", "");
		return obj;
	}

	/*
	 * ͼ����Ϣ
	 */
	public static JSONObject getPicMessage(String mpid, String IMUserId, String msgTitle, String msgContent, JSONArray articles) {
		JSONObject obj = new JSONObject();
		String uuid=UUID.randomUUID().toString().replace("-", "");
		String attatchId = UUID.randomUUID().toString().replace("-", "");
		obj.put("msgId", uuid);//��Ϣid
		obj.put("msgFrom", mpid);//��Ϣ���ͷ��Ĺ���ƽ̨id
		obj.put("msgToIMUserId", IMUserId);//��Ϣ���շ���IMUserId
		obj.put("msgTitle", "");//��Ϣtitle
		obj.put("msgType", "news");//��Ϣ����,ͼ����Ϣ:news
		obj.put("msgContent", "");//��Ϣ����
		obj.put("isAllowSendToOther", "0");//�Ƿ��������ת��,0����  1����  2����������
		obj.put("attatchId", attatchId);//ͼ����Ϣ�ز�id
		obj.put("articles", articles);
		return obj;
	}
	
	/*
	 * �齨ͼ����Ϣ
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
