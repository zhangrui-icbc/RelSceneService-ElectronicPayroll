package com.icbc.rel.hefei.job;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.icbc.rel.hefei.util.SocketUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;

/*
 * �����Լ�أ���ʱ��Mac���������ģ�����udp����
 */
public class MonitoringJob implements Job{

	private Logger logger = Logger.getLogger(MonitoringJob.class);

    public void execute(JobExecutionContext context) {
        //logger.info("��������ʼ");
        try {
        	String remoteURI=SystemConfigUtil.webUrl;
    		SocketUtil.sendAppxml();
    		SocketUtil.sendWebxml(remoteURI);
        } catch (Exception e) {
            logger.error("���������쳣",e);
        }
    }
    
	
}
