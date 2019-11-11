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
	
	/*
	 * �齨HF005�ӿڷ��͵�ͼ����Ϣ
	 * ʾ����"content":{
	 * "attachid":"ec3f360405f74bedbe0ffe00ef1025a7",
	 * "articles":[
	 * {"picurl":"http://imhdfs.icbc.com.cn/userfiles/public/static/0a48d5d9-686e-4859-ac4e-6024539fc5e4.jpg",
	 * "attachno":"1","title":"������ʹ��ȫ����","description":"","isAllowSendToOther":"1",
	 * "url":"http://imhdfs.icbc.com.cn/userfiles/public/static/ec3f360405f74bedbe0ffe00ef1025a7.html"}]}
	 * 
	 * attachid �ز�id���ڱ�ʶ���༭��ͼ���ز� ��J_MP_MATERIAL���л�ȡ����
     articles �����زķ���ͼ���ӣ��������ӵ�
     
     	   attachno ���ڱ�ǵڼ���ͼ����Ϣ ��������ʹ��ȫ����ͼ�� attachno��1���ڶ���������ͼ����������
         title    ��ͼ�ı��� ��������ʹ��ȫ����ͼ��  title��������ʹ��ȫ����
         description д���Ϳ�
         isAllowSendToOther �Ƿ�����ת�� ��e������������ 1���� 0������ ��д��1
         url�� ͼ����Ϣ�������ӣ��������ͼ����Ϣ����չʾ��ҳ������ ��J_MP_MATERIAL���л�ȡ
	 */
	public static JSONObject getPicArticlesForHF005(String title, String picurl, String url) {
		JSONObject obj = new JSONObject();//������Ҫ���͵�content������
		JSONObject result = new JSONObject();//������Ҫ���͵�content������
		JSONArray jsondetailArray = new JSONArray();//����articles������
		JSONObject obj1 = new JSONObject();
		obj1.put("attachno", "1");
		obj1.put("title", title);
		obj1.put("description", "");
		obj1.put("isAllowSendToOther", "0");//�Ƿ��������ת��,0����  1���� 
		obj1.put("url", url);
		obj1.put("picurl", picurl);
		jsondetailArray.add(obj1);
		
		String attatchId = UUID.randomUUID().toString().replace("-", "");
		obj.put("attachid", attatchId);//ͼ����Ϣ�ز�id
		obj.put("articles", jsondetailArray);
		result.put("news", obj);
		result.put("msgtype", "news");
		return result;
	}
}
