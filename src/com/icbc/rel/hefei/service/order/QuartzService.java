package com.icbc.rel.hefei.service.order;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuartzService {
	
	private static SchedulerFactory schedulerFactory=new StdSchedulerFactory();
	private static Logger logger = Logger.getLogger(QuartzService.class);
	
/*   public static void main(String [] str) throws Exception {
	  
	   File file=new File("src/quartz_jobs.xml");
	   UpdateXml("SendOrderMessageJobTrigger","0 1 17 * * ?");
   }*/
	
	/*
	 * 修改任务执行时间
	 */
	public static void ModifyJobTime(String triggerName,String cron) {
		try {
			logger.info("正在修改配置文件");
			//修改定时任务时间
			Scheduler sched=schedulerFactory.getScheduler();
			TriggerKey triggerkey=TriggerKey.triggerKey(triggerName, "DEFAULT");
			CronTrigger trigger=(CronTrigger)sched.getTrigger(triggerkey);
			if(trigger==null) {
				return;
			}
			String oldtime=trigger.getCronExpression();
			if(!oldtime.equalsIgnoreCase(cron)) {
				TriggerBuilder<Trigger> triggerBuilder=TriggerBuilder.newTrigger();
				triggerBuilder.withIdentity(triggerName, "DEFAULT");
				triggerBuilder.startNow();
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
				trigger=(CronTrigger)triggerBuilder.build();
				sched.rescheduleJob(triggerkey, trigger);
			}
			
			//修改配置文件，防止服务器重启后配置失效
			UpdateXml(triggerName,cron);
			logger.info("修改配置文件成功");
		}catch(Exception ex) {
			logger.error("更新定时任务计划失败",ex);
		}
		
	}
	
	public static void UpdateXml(String triggerName,String cron) throws Exception
	{
		String path="";
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document document=builder.parse(new File(path));
		NodeList list=document.getElementsByTagName("cron");
		for(int i=0;i<list.getLength();i++) {
			Element son=(Element)list.item(i);
			for(Node node= son.getFirstChild();node!=null;node=(Node) node.getNextSibling()) {
				if(node.getNodeType()==Node.ELEMENT_NODE) {
					String name=node.getNodeName();
					String value=node.getFirstChild().getNodeValue();
					if(name.equals("name")  ) {
						if(!triggerName.equals(value)) {
							break;
						}
					}
					if(name.equals("cron-expression")) {
						node.setTextContent(cron);
					}
				}
			}
		}
		
		TransformerFactory factory2=TransformerFactory.newInstance();
		Transformer former=factory2.newTransformer();
		former.transform(new DOMSource(document), new StreamResult(new File(path)));
		
	}

}
