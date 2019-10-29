package com.icbc.rel.hefei.test;




import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.service.rel.MessageService;
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
	 * 测试消息推送接口（HF005）
	 */
	
	@RequestMapping(value="/testSendMessageByHF005")
	@ResponseBody
	public Msg testSendMessageByHF005() throws UnsupportedEncodingException{
		anaylsisXmlUtil t=new anaylsisXmlUtil(); 
		logger.info("测试消息发送接口");
		String content;
		Msg msg = new Msg();
		
		String fanalXmlStr;
		try {
			content = URLEncoder.encode("ceshi","utf-8");
			//fanalXmlStr = t.makeXmlByHf005("12345678", "0", "3", "18100000014", "raw", content);
			fanalXmlStr = t.makeXmlByHf005("12345678", "0", "3", "18100000014,15255800002,18518113824", "raw", content);
			
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
