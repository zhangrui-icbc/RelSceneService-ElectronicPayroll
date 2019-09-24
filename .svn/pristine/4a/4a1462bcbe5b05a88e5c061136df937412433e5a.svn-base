package com.icbc.rel.hefei.util.RSA;

import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.icbc.mims.thirdparty.filter.util.MimsDes;

public class DecryptMpInfo {

	private static final Logger logger = Logger.getLogger(DecryptMpInfo.class);
	
  public static String DecryptMpInfo(String encTranData,String encSignMsg) {
	  //1、解密上送参数
	  String para=ThirDescUtil.decrypt(encTranData);//data
	  logger.info("解密后参数为："+para);
	  //2.将解密后的参数进行sha1获取摘要
	  String abstractText=EncodeSHA1.getAbstract(para);//saone
	  logger.info("SHA1摘要："+abstractText);
	  //3.将拿到的摘要进行解密处理获取上送解密的摘要
	  String realAbstract=RSA.decrypt(encSignMsg);
	  logger.info("解密后摘要"+realAbstract);
	  //4.解密的摘要与本地摘要进行对比
	  if(abstractText.equals(realAbstract)) {
		  return para;
	  }
	  
	  return para;
  }
  
  public static void main(String[] args) {
	 
	/*  String a="http://122.19.173.159:10060/IMServiceServer/servlet/ThirdPartyServlet?Channel=IM&fCode=HF002&msgBody={\"msgToIMUserId\":\"1037667066\",\"msgType\":\"text\",\"msgContent\":\"world\",\"attatchId\":\"\",\"msgTitle\":\"hello\",\"msgId\":\"5a0e91587dc5470881e61372f27a0b25\",\"msgFrom\":\"10113546\",\"isAllowSendToOther\":\"0\",\"articles\":\"\"}";
	  a=a.substring(100);
	  System.out.println(a);
	  String test1 = "http://localhost:8080/IMServiceServer/servlet/ThirdPartyServlet?fCode=HF003&Channel=HF&in_actno=202405109000347&in_mpid=10003508&in_custom=E2B1C5DA2E0445FF66AE1B53B8083BF75CACE8E2CB08C30F&in_manager=E2B1C5DA2E0445FF66AE1B53B8083BF75CACE8E2CB08C30F";
	 String encryptStr = "E2B1C5DA2E0445FF66AE1B53B8083BF75CACE8E2CB08C30F";
	  String decrypStr = MimsDes.decryptFixed(encryptStr);
	  System.out.println("解密后id"+decrypStr);*/ 
	  String encSignMsg="YYBrqWVmVJpoY/0B6C1/zmFvYMcZgvFQD2zFSiJipHope1V4ATBx7wv42cnuHBIE6PAg3Bgn1oGuDV1uAyYmLgj77Eu3APJLtDdPbfHbC+Uw+L5GYu+iiRCFUPTnmdfNB7xy4oV3+0o+tURjAIFvQsKkJHTQ8mgEDhA5LRXCn0fNuskGSszbh0vnJ1VbiA3aVJLErjGUi9L9s4Xm7CjSA7Ac0nOge4QwWO1s98AzhVEtdkIYLuJs+iIoelqELJ6SaR8Lh9yVDdFX5ehqZZjnwFf+ZzQNvRSD6gOkzm63UWNJDVMJOdOG9wzmPqm80/Q4cqmxHeQiD94jbJ35OTmHew==";
	  String encTranData="BqdlMj/yXNbstTZb9csU9LAgENRzxsXzLrESvcnDzo8fr3XfU2C/hWs8ZUiZgNYj15jro1ToPxLq6UwMHoQ8vA==";
	  String result=URLEncoder.encode(encSignMsg);
	  System.out.println(result);
	  String result1=URLEncoder.encode(encTranData);
	  System.out.println(result1);
	  String para=ThirDescUtil.decrypt(encTranData);//data
	  System.out.println(para);
	 /* String abstractText=EncodeSHA1.getAbstract(para);//saone
	  System.out.println(abstractText);
	  String realAbstract=RSA.decrypt(encSignMsg);
	  System.out.println(realAbstract);*/
  }
}
