package com.icbc.rel.hefei.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class SocketUtil {
	private static final Logger logger = Logger.getLogger(SocketUtil.class);
	
	/*
     * 采用udp方式发送报文
     */
    public static boolean sendAppxml() {
    	String xml=getXml("APP");
    	if (xml.contains("${IP}")) {
			String str = getLocalIp();
			xml = xml.replace("${IP}", str);
		}
    	String amcIp=SystemConfigUtil.amcIp;
    	logger.info("开始向"+amcIp+"发送报文："+xml);
    	
    	
    	int port=CommonUtil.parseInteger(SystemConfigUtil.amcPort);
    	try {
    		DatagramSocket ds=new DatagramSocket();
    		InetAddress add=InetAddress.getByName(amcIp);
    		//InetAddress add=InetAddress.getLocalHost();
			DatagramPacket dp=new DatagramPacket(xml.getBytes(),xml.getBytes().length,add,port);
		    ds.send(dp);
		    ds.close();
    	} catch (Exception ex) {
			logger.error("发送报文失败",ex);
			return false;
		}
    	logger.info("发送报文结束");
    	return true;
    }
	
	
    /*
     * 采用udp方式发送报文
     */
    public static boolean sendWebxml(String remoteURI) {
    	String xml=getXml("WEB");
    	if (xml.contains("${IP}")) {
			String str = "";
			if (remoteURI != null) {
				if (remoteURI.startsWith("https")) {
					str = remoteURI.substring(8);
				} else {
					str = remoteURI.substring(7);
				}
				if(str.contains("/")) {
				str = str.substring(0, str.indexOf("/"));
				
				str = getRemoteIp(str);}
				if(str.contains(":")) {
					str=str.substring(0,str.indexOf(":"));
				}
			} else {
				logger.info("weburl为空");
				return false;
			}
			xml = xml.replace("${IP}", str);
		}
    	logger.info("开始发送报文："+xml);
    	String amcIp=SystemConfigUtil.amcIp;
    	int port=CommonUtil.parseInteger(SystemConfigUtil.amcPort);
    	try {
    		DatagramSocket ds=new DatagramSocket();
    		InetAddress add=InetAddress.getByName(amcIp);
			DatagramPacket dp=new DatagramPacket(xml.getBytes(),xml.getBytes().length,add,port);
		    ds.send(dp);
		    ds.close();
    	} catch (Exception ex) {
			logger.error("发送报文失败",ex);
			return false;
		}
    	logger.info("发送报文结束");
    	return true;
    }
    
    /*
     * 获取本机ip地址
     */
    public static String getLocalIp() {
		String net = null;
		String local = null;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					InetAddress ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {
						net = ip.getHostAddress();
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {
						local = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if (net == null) {
			if (local != null) {
				return local;
			}
		} else {
			return net;
		}
		return null;
	}
    
    private static String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
	private static Pattern pat = null;
	private static String getRemoteIp(String hostname) {
		if (hostname == null) 
			return null;
		if (pat == null)
			pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(hostname);
		if (mat.find()) {
			return hostname;
		} else {
			try {
				return InetAddress.getByName(hostname).getHostAddress();
			} catch (UnknownHostException e) {
				return e.toString();
			}
		}
	}
    
    /*
     * 加载需要发送的心跳报文
     */
    private static String getXml(String type)  {

    	String xml="<?xml version=\"1.0\" encoding=\"GBK\" ?> " + 
    			"<APPROOT type=\"BAMC_USABILITY_REPORT\" ver=\"2.0\" from =\"F-MIMS\" to=\"F-AMC\" mode=\"asy\">" + 
    			"  <PUBLIC>" + 
    			"    <APPSNAME>F-MIMS</APPSNAME>" + 
    			"    <TYPE>02</TYPE>" + 
    			"    <IP>${IP}</IP>" + 
    			"  </PUBLIC>" + 
    			"  <PRIVATE>" + 
    			"  <ROWSET>" + 
    			"    <ROW>" + 
    			"      <OCCUREDAPPSNAME>F-MIMS</OCCUREDAPPSNAME>" + 
    			"      <OCCUREDIP>${IP}</OCCUREDIP>" + 
    			"      <MODULECODE>RelSceneServer</MODULECODE>" + 
    			"      <SUBMODULECODE>"+type+"</SUBMODULECODE>" + 
    			"      <STATUS>0</STATUS>" + 
    			"      <MSG>OK</MSG>" + 
    			"    </ROW>" + 
    			"  </ROWSET>" + 
    			"  </PRIVATE>" + 
    			"</APPROOT>";
    	return xml;
    	
    }
}
