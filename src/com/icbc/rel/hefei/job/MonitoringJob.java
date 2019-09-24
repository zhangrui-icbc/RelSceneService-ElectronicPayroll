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
 * 可用性监控，定时向Mac发送请求报文，采用udp传送
 */
public class MonitoringJob implements Job{

	private Logger logger = Logger.getLogger(MonitoringJob.class);

    public void execute(JobExecutionContext context) {
        //logger.info("心跳任务开始");
        try {
        	String remoteURI=SystemConfigUtil.webUrl;
    		SocketUtil.sendAppxml();
    		SocketUtil.sendWebxml(remoteURI);
        } catch (Exception e) {
            logger.error("心跳任务异常",e);
        }
    }
    
	
}
