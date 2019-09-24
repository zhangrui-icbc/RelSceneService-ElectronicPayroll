package com.icbc.rel.hefei.util.RSA;

import org.apache.log4j.Logger;

import com.icbc.rel.hefei.util.SystemConfigUtil;

public class ThirDescUtil {
	private static final Logger logger = Logger.getLogger(ThirDescUtil.class);
	/*
	 * cipherText:密文
	 */
	public static String decrypt(String cipherText)
	{
		try {
			//操作类型 1-加密;2-解密;
			String type = "2";
			// 内部加密方式：1--ECB;2--CBC;3--CFB;4--OFB
			String encodetype = "1";
	
			// 密钥文件
			String keyFilePath = "";
			String keyFile = "";
			/*try {
				keyFilePath = (String) getParams().getValueAt("keyFilePath");
			} catch (Exception e) {
				keyFilePath = "";
				logger.error("keyFilePath is empty");
				throw new Exception("keyFilePathisEmpty"); 
			}
			try {
				keyFile = (String) Settings.getSettings().getValueAt(
					"ICBCINBSSettings." + keyFilePath);
			}catch (Exception e) {
				keyFile = "";
				logger.error("keyFile not exist");
				throw new Exception("keyFilenotexist"); 
			}*/
			
			//测试
			keyFile = SystemConfigUtil.thirdDesPath;
	
			// 密文
			String cipher = cipherText;
			
			// 明文

			String plain = "";
				logger.info("before 3des decode:"+cipher);	
				try{
					plain = IEPA3DESUtil.decrypt3DES(cipher, keyFile, Integer.parseInt(encodetype));
				}catch(Exception e) {
					throw new Exception("decodeError"); 
				}	
				logger.info("after 3des decode:"+plain);	
			
			return plain;
		}catch (Exception e) {
			return "";
		}	
	}
	
}
