package com.icbc.rel.hefei.test;




import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.service.rel.MessageHelper;
import com.icbc.rel.hefei.service.rel.MessageService;
import com.icbc.rel.hefei.util.SystemConfigUtil;
import com.icbc.rel.hefei.util.anaylsisXmlUtil;


/**
 * @Description: ���Խӿ�
 * @author ILNIQ
 * @date 2019��1��29��
 */

@Controller
@RequestMapping(value="/test")
public class testController {
	
	private static final Logger logger = Logger.getLogger(testController.class);
	
	
	/*
	 * Ⱥ��ͼ����Ϣ���ͽӿڣ�HF005��ʾ��
	 */
	
	@RequestMapping(value="/testSendMessageByHF005")
	@ResponseBody
	public Msg SendMessageByHF005() throws UnsupportedEncodingException{
		anaylsisXmlUtil t=new anaylsisXmlUtil(); 
		logger.info("Ⱥ��ͼ����Ϣ�ӿ�ʾ��");
		String content;//���͵���Ϣ���ݣ���Ҫ��string
		Msg msg = new Msg();
		String fanalXmlStr;
		try {
			String domainUrl = SystemConfigUtil.domainName;
			String title = "н����Ϣ";//ͼ����Ϣ��ʾ�ı���
			String picurl = domainUrl + "RelSceneService/image/order/supper.jpg";//ͼ����Ϣ��ͼƬ��ַ
			String url = domainUrl + "RelSceneService/com/myOrderDetail?activityUid=";//ͼ����Ϣ����������
			JSONObject picMessage = MessageHelper.getPicArticlesForHF005(title, picurl, url);
			content = URLEncoder.encode(picMessage.toString(),"utf-8");
			//fanalXmlStr = t.makeXmlByHf005("12345678", "0", "3", "18100000014", "raw", content);
			fanalXmlStr = t.makeXmlByHf005("12345678", "1", "0","", "raw", content);
			logger.info("���͵�xml�ַ�");
			logger.info(fanalXmlStr);
			Boolean isSend=MessageService.sendRtfByHf500(fanalXmlStr);
			msg.setData(isSend);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	
	
}
