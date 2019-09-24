package com.icbc.rel.hefei.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.icbc.rel.hefei.entity.SysParaInfo;
import com.icbc.rel.hefei.service.sys.SysParaInfoService;

/**
 *
 * @see 系统配置信息
 * @desc 配置信息系统参数
 */
public class SystemConfigUtil {
	private static final Logger logger = Logger.getLogger(SystemConfigUtil.class);
	private static SysParaInfoService paraSerice = (SysParaInfoService) SpringContextUtil.getApplicationContext()
			.getBean(SysParaInfoService.class);

	/**
	 * 文件服务器参数配置
	 */

	public static String picPath;// 图片上传存储目录
	public static String apiUrl;// 调用开放平台接口的地址
	public static String amcIp;// AMC应用监控服务器IP
	public static String amcPort;// AMC应用监控服务器端口
	public static String tempPath;// 存放临时文件的目录
	public static String domainName;// 项目部署的域名
	public static String rootPath;// 项目根目录
	public static boolean isDebug;// 是否为调试状态
	public static String webUrl;// weburl
	public static String thirdDesPath;// 秘钥文件
	public static String rsaPrivPath;// 私钥文件
	public static String rsaPubPath;// 报文路径
	public static String hadoopAddress;// hadoop服务器地址，用来上传图片
	public static Properties properties = new Properties();

	public static String markPlatUrl;// 营销平台的域名

	/**
	 * 获得配置参数
	 * 
	 * @param key
	 *            配置key
	 * @return
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 初始化配置信息
	 */
	static {
		try {
			logger.info("开始初始化配置文件");
			properties.load(SystemConfigUtil.class.getResourceAsStream("/properties/default.properties"));
			rootPath = properties.getProperty("rootPath");
			tempPath = properties.getProperty("tempPath");
			picPath = rootPath + properties.getProperty("picPath");
			isDebug =true ;
			thirdDesPath = rootPath + File.separator + properties.getProperty("thirdDesPath");
			rsaPrivPath = rootPath + File.separator + properties.getProperty("rsaPrivPath");
			rsaPubPath = rootPath + File.separator + properties.getProperty("rsaPubPath");
			logger.info("tempPath：" + tempPath);
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
					logger.info("markPlatUrl：" + markPlatUrl);
					break;
				case 7:
					hadoopAddress = para.getValue();
					logger.info("hadoopAddress：" + hadoopAddress);
					break;
				}
			}
			logger.info("完成初始化配置文件");
		} catch (Exception e) {
			logger.error("加载配置信息出错：", e);

		}
	}
}