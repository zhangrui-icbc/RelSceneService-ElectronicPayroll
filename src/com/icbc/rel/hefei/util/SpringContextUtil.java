package com.icbc.rel.hefei.util;
/**
 * @Description: appli
 * @author xujunjie
 * @date 2018��2��1��
 */
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


public class SpringContextUtil implements ApplicationContextAware {

private static ApplicationContext applicationContext; // SpringӦ�������Ļ���

// �������������ϼ���@Overrideע�⣬ԭ���Ǽ̳�ApplicationContextAware�ӿ��Ǳ���ʵ�ֵķ���

public static ApplicationContext getApplicationContext() {
return applicationContext;
}

public static Object getBean(String name) throws BeansException {
return applicationContext.getBean(name);
}

public static Object getBean(String name, Class requiredType)
throws BeansException {
return applicationContext.getBean(name, requiredType);
}

public static boolean containsBean(String name) {
return applicationContext.containsBean(name);
}

public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
return applicationContext.isSingleton(name);
}

public static Class getType(String name) throws NoSuchBeanDefinitionException {
return applicationContext.getType(name);
}

public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
return applicationContext.getAliases(name);
}

public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	applicationContext=arg0;
	
}
}