package com.icbc.rel.hefei.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.icbc.rel.hefei.entity.SysParaInfo;
import com.icbc.rel.hefei.service.sys.SysParaInfoService;

/**
 *
 * @see ϵͳ������Ϣ
 * @desc ������Ϣϵͳ����
 */
public class SystemConfigUtil {
	private static final Logger logger = Logger.getLogger(SystemConfigUtil.class);
	private static SysParaInfoService paraSerice = (SysParaInfoService) SpringContextUtil.getApplicationContext()
			.getBean(SysParaInfoService.class);

	/**
	 * �ļ���������������
	 */

	public static String picPath;// ͼƬ�ϴ��洢Ŀ¼
	public static String apiUrl;// ���ÿ���ƽ̨�ӿڵĵ�ַ
	public static String amcIp;// AMCӦ�ü�ط�����IP
	public static String amcPort;// AMCӦ�ü�ط������˿�
	public static String tempPath;// �����ʱ�ļ���Ŀ¼
	public static String domainName;// ��Ŀ���������
	public static String rootPath;// ��Ŀ��Ŀ¼
	public static boolean isDebug;// �Ƿ�Ϊ����״̬
	public static String webUrl;// weburl
	public static String thirdDesPath;// ��Կ�ļ�
	public static String rsaPrivPath;// ˽Կ�ļ�
	public static String rsaPubPath;// ����·��
	public static String hadoopAddress;// hadoop��������ַ�������ϴ�ͼƬ
	public static Properties properties = new Properties();

	public static String markPlatUrl;// Ӫ��ƽ̨������

	/**
	 * ������ò���
	 * 
	 * @param key
	 *            ����key
	 * @return
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	static {
		try {
			logger.info("��ʼ��ʼ�������ļ�");
			properties.load(SystemConfigUtil.class.getResourceAsStream("/properties/default.properties"));
			rootPath = properties.getProperty("rootPath");
			tempPath = properties.getProperty("tempPath");
			picPath = rootPath + properties.getProperty("picPath");
			isDebug =true ;
			thirdDesPath = rootPath + File.separator + properties.getProperty("thirdDesPath");
			rsaPrivPath = rootPath + File.separator + properties.getProperty("rsaPrivPath");
			rsaPubPath = rootPath + File.separator + properties.getProperty("rsaPubPath");
			logger.info("tempPath��" + tempPath);
			List<SysParaInfo> paras = paraSerice.getSyspara();
			for (SysParaInfo para : paras) {
				switch (para.getKey()) {
				case 1:
					domainName = para.getValue();
					break;
				case 2:
					amcIp = para.getValue();
					break;
				case 3:
					amcPort = para.getValue();
					break;
				case 4:
					apiUrl = para.getValue();
					break;
				case 5:
					webUrl = para.getValue() + "RelSceneService/image/dc.png";
					break;
				case 6:
					markPlatUrl = para.getValue();
					logger.info("markPlatUrl��" + markPlatUrl);
					break;
				case 7:
					hadoopAddress = para.getValue();
					logger.info("hadoopAddress��" + hadoopAddress);
					break;
				}
			}
			logger.info("��ɳ�ʼ�������ļ�");
		} catch (Exception e) {
			logger.error("����������Ϣ����", e);

		}
	}
}