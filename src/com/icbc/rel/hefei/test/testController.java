package com.icbc.rel.hefei.test;




import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ibm.security.pkcs7.Data;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.dao.salary.SalaryMapper;
import com.icbc.rel.hefei.entity.salary.SalaryImport;
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
	@Autowired
	SalaryMapper salaryMapper;
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
	
	
	@RequestMapping(value="/ins")
	@ResponseBody
	public  void ins() {
		System.out.println("开始时间:"+new Data().toString());
		List<SalaryImport> importList = new ArrayList<SalaryImport>();
		List<SalaryImport> importList1 = new ArrayList<SalaryImport>();
		Date date = new Date();
		for(int i=0;i<60000;i++) {
			SalaryImport salaryImport = new  SalaryImport();
			salaryImport.setBatchNo("20200707");
			salaryImport.setUserId("13111111111");
			salaryImport.setIssueTime(date);
			salaryImport.setRealIncome("1");
			salaryImport.setSpecialInfo("111");
			salaryImport.setTotalExpenditure("11");
			salaryImport.setTotalRevenue("11");
			salaryImport.setUnitExpenditure("12");
			salaryImport.setSalaryRemark("ha");
			salaryImport.setCreateTime(date);
			importList.add(salaryImport);
		}
 		int size = importList.size();
 		int index = 0;
 		int limit =3000;
 		while(true) {
 			if(index+limit>=size) {
 				importList1 = importList.subList(index, size);
 				salaryMapper.insertOaSalaryImport(importList1);
 				break;
 			}else {
 				salaryMapper.insertOaSalaryImport(importList.subList(index, index+limit));
 				index = index+limit;
 			}
 			System.out.println("插入"+(index*3000)+"条");
 			
 		}
 		System.out.println("结束时间:"+new Data().toString());
	}
	
	public static void main(String[] args) {
		testController	tController = new  testController();
		tController.ins();
	}
}
