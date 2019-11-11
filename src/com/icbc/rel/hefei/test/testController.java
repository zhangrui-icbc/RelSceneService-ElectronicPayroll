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
 * @Description: 测试接口
 * @author ILNIQ
 * @date 2019年1月29日
 */

@Controller
@RequestMapping(value="/test")
public class testController {
	
	private static final Logger logger = Logger.getLogger(testController.class);
	
	
	/*
	 * 群发图文消息推送接口（HF005）示例
	 */
	
	@RequestMapping(value="/testSendMessageByHF005")
	@ResponseBody
	public Msg SendMessageByHF005() throws UnsupportedEncodingException{
		anaylsisXmlUtil t=new anaylsisXmlUtil(); 
		logger.info("群发图文消息接口示例");
		String content;//上送的消息内容，需要是string
		Msg msg = new Msg();
		String fanalXmlStr;
		try {
			String domainUrl = SystemConfigUtil.domainName;
			String title = "薪资消息";//图文消息显示的标题
			String picurl = domainUrl + "RelSceneService/image/order/supper.jpg";//图文消息的图片地址
			String url = domainUrl + "RelSceneService/com/myOrderDetail?activityUid=";//图文消息的正文链接
			JSONObject picMessage = MessageHelper.getPicArticlesForHF005(title, picurl, url);
			content = URLEncoder.encode(picMessage.toString(),"utf-8");
			//fanalXmlStr = t.makeXmlByHf005("12345678", "0", "3", "18100000014", "raw", content);
			fanalXmlStr = t.makeXmlByHf005("12345678", "1", "0","", "raw", content);
			logger.info("上送得xml字符");
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
