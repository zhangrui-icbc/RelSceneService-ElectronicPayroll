package com.icbc.rel.hefei.util;

import java.util.Map;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.apache.log4j.Logger;

public class anaylsisXmlUtil {
	
	 private static Logger logger = Logger.getLogger(anaylsisXmlUtil.class);
	 public static Map getXml(String text) throws DocumentException {		
	        Map<Object, Object> map = new HashMap<Object, Object>();
	        Document doc = null;
	        try {
	            doc = DocumentHelper.parseText(text);
	        } catch (DocumentException e) {
	            //解析失败
	            e.printStackTrace();
	        }
	        if(doc==null)
	            return map;
	        //获取跟节点
	        Element element = doc.getRootElement();
	        //获得根节点所有属性值
	        List<?> iList = element.attributes();
	      
	        for(int i=0;i<iList.size();i++){
	            Attribute attribute = (Attribute)iList.get(i);
	            map.put(attribute.getName(), attribute.getValue());
	           System.out.println(attribute.getName()+":"+attribute.getName());	            
	        }
	        
  
	        //遍历根节点下属性为property的子节点
	        Iterator<?> pIterator = element.elementIterator("FromUserName");//获取关注时间的openid
	        while(pIterator.hasNext()){
	            Element ele = (Element)pIterator.next();
	            //子节点的name的值，和Text
	            map.put("FromUserName", ele.getText());
	        }
	       
	        
	        Iterator<?> pIterator2 = element.elementIterator("MsgType");
	        while(pIterator2.hasNext()){
	            Element ele = (Element)pIterator2.next();
	            //子节点的name的值，和Text
	            map.put("MsgType", ele.getText());
	        }
	              
	       String MsgTypeT=(String) map.get("MsgType");
	        logger.info("********"+MsgTypeT+"********");
	       
	        if(MsgTypeT.equals("text")){
	        	
	        	  Iterator<?> pIterator4 = element.elementIterator("Content");//获取关注时间的openid
	              while(pIterator4.hasNext()){
	                  Element ele = (Element)pIterator4.next();
	                  //子节点的name的值，和Text
	                  map.put("Content", ele.getText());
	              }	
	        	
	        }else if(MsgTypeT.equals("event")){
	        	
	        	 Iterator<?> pIterator1 = element.elementIterator("event");
	             while(pIterator1.hasNext()){
	                 Element ele = (Element)pIterator1.next();
	                 //子节点的name的值，和Text
	                 map.put("event", ele.getText());
	             }
	        	
	        }
	             
	        Iterator<?> pIterator3 = element.elementIterator("CreateTime");//获取关注时间的openid
	        while(pIterator3.hasNext()){
	            Element ele = (Element)pIterator3.next();
	            //子节点的name的值，和Text
	            map.put("CreateTime", ele.getText());
	        }
	           
	        //循环输出
	        Iterator<Entry<Object, Object>> iterator = map.entrySet().iterator();
	        while(iterator.hasNext()){
	            Entry<Object, Object> entry = iterator.next();
	            logger.info(entry.getKey()+":"+entry.getValue());
	        }            
	        return map;
	    }
	 

	   /**
	     * 上送HF005?的xml报文	
	     * @param Mpid   发送方Mpid  
	     * @param multisend   0:非全发，1：全发  
	     * @param channel   推送渠道（0：融e联ID；1：cis编号；3：手机号）     
	     * @param  id     接受消息方，多个id用“,”隔开即可
	     * @param type    图文消息：news；multisend为1；其他为raw
	     * @param Content     Json字符，utf-8编码
	     * @return 
	     */
	public String makeXmlByHf005(String Mpid,String multisend,String channel,String ID,String msgType,String content) throws DocumentException {
		    SocketUtil t=new SocketUtil();
		    String date=DateUtil.getYDate();
		    String tm=DateUtil.tmToStr(new Date());		    
		    String IP = t.getLocalIp();//获取本机（服务器）的ip地址
		    String TransNo = UUID.randomUUID().toString();//随机数据
		    StringBuffer  xml=new StringBuffer();
		    xml.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		    xml.append("<APPROOT>");
		    xml.append("<PUBLIC>");
		    xml.append("<FCODE>HF005</FCODE>");
		    xml.append("<SERVER_IP>"+IP+"</SERVER_IP>");//
		  /*  xml.append("<dt>"+date+"</dt>");
		    xml.append("<tm>"+tm+"</tm>");*/
		    xml.append("<DT>"+CommonUtil.DateConvertStr(new Date(), "yyyy-MM-dd")+"</DT>");//置空
		    xml.append("<TM>"+CommonUtil.DateConvertStr(new Date(), "HHmmss")+"</TM>");//置空
		    xml.append("<CHANNELIDENTIFIER>cjy</CHANNELIDENTIFIER>");//
		    xml.append("<CHANNELCODE>F-MIMS</CHANNELCODE>");
		    xml.append("</PUBLIC>");
		    xml.append("<PRIVATE>");
		    xml.append(" <VERSION>1.0</VERSION>");
		    xml.append("<APPNAME>F-MIMS</APPNAME>");//
		    xml.append("<MSGS>");
		    xml.append("<MSG>");
		    xml.append("<TRANSNO>"+System.currentTimeMillis()+"</TRANSNO>");//随机数
		    xml.append(" <KEY></KEY>");//置空
		    xml.append(" <PRI>0</PRI>");
		    xml.append("<MPID>"+Mpid+"</MPID> ");
		    xml.append(" <MSGTOS>");
		    xml.append("<MSGTO>");
		    xml.append(" <MULTISEND>"+multisend+"</MULTISEND>");
		    xml.append("<CHANNEL>"+channel+"</CHANNEL>");
		    xml.append("<ID>"+ID+"</ID>");
		    xml.append("</MSGTO>");
		    xml.append(" </MSGTOS>");		    
		    xml.append("<SWITCH>0</SWITCH>");
		    xml.append(" <PUSHSTR></PUSHSTR>");//置空
		    xml.append(" <MSGTYPE>"+msgType+"</MSGTYPE>");
		    xml.append(" <CONTENT>"+URLEncoder.encode(content)+"</CONTENT>  ");
		    xml.append(" <RESERVE></RESERVE>");//置空
		    xml.append(" </MSG>");
		    xml.append(" </MSGS>");
		    xml.append(" </PRIVATE>");
		    xml.append(" </APPROOT>");
		    logger.info(xml.toString());
	        return xml.toString();
	    }
	
	
	
	 public  Map getXmlByHF005(String text) throws DocumentException {
		 
		 Map<Object, Object> map = new HashMap<Object, Object>();
		 
		    //1.创建Reader对象
	        SAXReader reader = new SAXReader();
	        //2.加载xml
	        Document document = null;
	        try {
	        	document = DocumentHelper.parseText(text);
	        } catch (DocumentException e) {
	            //解析失败
	            e.printStackTrace();
	        }
	        //3.获取根节点
	        Element rootElement = document.getRootElement();
	        Iterator iterator = rootElement.elementIterator();
	        while (iterator.hasNext()){
	            Element stu = (Element) iterator.next();
	            List<Attribute> attributes = stu.attributes();
	            System.out.println("======获取属性值======");
	            for (Attribute attribute : attributes) {
	                System.out.println(attribute.getValue());
	            }
	            System.out.println("======遍历子节点======");
	            Iterator iterator1 = stu.elementIterator();
	            while (iterator1.hasNext()){
	                Element stuChild = (Element) iterator1.next();
	                System.out.println("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
	                map.put(stuChild.getName(), stuChild.getStringValue());
	            
	            }
	        
	    }
		  
		 return map;
	 }	
	 
	 
    
	
     public static void main(String[] args) throws DocumentException {
 		
 		//String fff="<?xml version=\"1.0\" encoding=\"GBK\"?><im><public><retcode>0000</retcode><retmsg></retmsg></public></im>";
 		String te="<?xml version=\"1.0\" encoding=\"GBK\"?><APPROOT>"
 				+ "<PRIVATE><RECODE><STRUNAME>杭州北景园支行</STRUNAME><ORGNAME>中国工商银行股份有限公司</ORGNAME><STRU_ID>0120200086</STRU_ID><USERTYPE>3</USERTYPE><MPSUBFLAG>1</MPSUBFLAG></RECODE></PRIVATE>"
 				+"<PUBLIC><RETCODE>0</RETCODE><RETMSG></RETMSG></PUBLIC>"
 				+ "</APPROOT>";
 		
 		Map<Object,Object> resultMap=new HashMap<Object,Object>();
 		System.out.println(te);	
 		//resultMap=getXmlByHF005(te);
 		
 		
 	}


}
