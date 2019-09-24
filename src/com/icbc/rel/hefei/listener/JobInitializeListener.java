package com.icbc.rel.hefei.listener;



/**
 * Created by ILNIQ on 2017/6/19.
 */



import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icbc.rel.hefei.util.SystemConfigUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class JobInitializeListener implements ServletContextListener {

    private Scheduler scheduler;
    private static Logger logger = Logger.getLogger(JobInitializeListener.class);

    public void contextInitialized(ServletContextEvent sce) {
    	
    	/*logger.info("开启报文接收服务");//用于测试接收心跳报文
    	new Thread() {
    		@Override
    		public void run() {
    			
    				logger.info("我进来了");
    				@SuppressWarnings("resource")
					DatagramSocket ds;
					try {
						ds = new DatagramSocket(8080);
					
    				while(true) {
    					try {
    				    byte[] buf=new byte[2048];
    					DatagramPacket dp=new DatagramPacket(buf,buf.length);
    					ds.setSoTimeout(2000);
    			        ds.receive(dp);
    			    byte[] data=dp.getData();
    			    String msg=new String(data,0,dp.getLength());
    			    logger.info("接收到报文"+msg);
    				
    				
    			}catch(Exception ex) {
    				logger.info("接收报文出错");
    			}
    				}
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		}
    	}.start();*/
        
        //启动定时器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        if (null != scheduler) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }




}
