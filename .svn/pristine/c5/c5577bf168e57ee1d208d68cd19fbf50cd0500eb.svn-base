package com.icbc.rel.hefei.util.RSA;

import org.apache.log4j.Logger;

import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;
import com.icbc.crypto.utils.Base64;

public class RSA {
	private static final Logger logger = Logger.getLogger(RSA.class);

	/**
	 * 用于RSA的加密和解密
	 *	需要传入的参数：
	 *	type:1-公钥加密;2-公钥解密;3-私钥加密;4-私钥解密
	 *	keyFilePath:密钥文件
	 *	cipherField:密文
	 *	plainField：明文
	 * 	明文和密文都需要传入，一个是输入，另一个输出
	 *	返回：正常返0，异常返-1
	 */
	public static String decrypt(String abstractText) {
		String[] plainFields = null;
		String[] cipherFields = null;
		try {
			// 内部加密方式：1-公钥加密;2-公钥解密;3-私钥加密;4-私钥解密
			String type = "4";
			
			// 密钥文件
			String key="";
			
			//测试
			//key = "C:/Users/kfzx-lianxq/Desktop/加解密方案/ICBC_MIMS_2_00000_RSA_2048_PRIV.key";
			key = SystemConfigUtil.rsaPrivPath;
			//key = "D:/RelSceneServiceFile/security/ICBC_MIMS_2_00000_RSA_2048_PUB.key";
			
			// 密文
			String cipher = abstractText;
			// 明文
			String plain = "";
			return RSA_Handler(type, key, plain, cipher);
			
			
			
	}catch(Exception ex) {
		logger.error("解密摘要失败：",ex);
		return null;
	}
		
	}
	
	private static String RSA_Handler(String type,String key,String plain,String cipher) throws Exception
	{
		// 内部加密方式：1-公钥加密;2-公钥解密;3-私钥加密;4-私钥解密
		switch(Integer.parseInt(type)){
			case 1: //1-公钥加密;
				logger.info("1-公钥加密=> before encryptByPublicKey:"+ plain);
				try{
				cipher = Base64.icbcbase64encode(RSAEncrypt.encryptByPublicKey(plain.getBytes(), key)).replaceAll("\r","").replaceAll("\n", "");
				}catch(Exception e){
					throw new Exception("encryptError"); 
				}
				logger.info("1-公钥加密=> after encryptByPublicKey:"+ cipher);
				if(cipher==null||cipher.equalsIgnoreCase(""))
					throw new Exception("encryptError"); 
				return cipher;
		case 2://2-公钥解密;
				logger.info("2-公钥解密=> before decryptByPublicKey:"+ cipher);
				try{
				plain = new String(RSAEncrypt.decryptByPublicKey(Base64.icbcbase64decode(cipher), key));
				}catch(Exception e){
					throw new Exception("decryptError"); 
				}
				logger.info("2-公钥解密=> after decryptByPublicKey:"+ plain);
				
				if(plain==null||plain.equalsIgnoreCase(""))
					throw new Exception("decryptError"); 
				return plain;
			case 3://3-私钥加密;
				logger.info("3-私钥加密=> before encryptByPrivateKey:"+ plain);
				try{
				cipher = Base64.icbcbase64encode(RSAEncrypt.encryptByPrivateKey(plain.getBytes(), key)).replaceAll("\r","").replaceAll("\n", "");
				}catch(Exception e){
					throw new Exception("encryptError"); 
				}
				logger.info("3-私钥加密=> after encryptByPrivateKey:"+ cipher);
				
				if(cipher==null||cipher.equalsIgnoreCase(""))
					throw new Exception("encryptError"); 
				return cipher;
			case 4://4-私钥解密
				logger.info("4-私钥解密=> before decryptByPrivateKey:"+ cipher);
				try{
				plain = new String(RSAEncrypt.decryptByPrivateKey(Base64.icbcbase64decode(cipher), key));
				}catch(Exception e){
					throw new Exception("decryptError"); 
				}
				logger.info("4-私钥解密=> after decryptByPrivateKey:"+ plain);
				
				if(plain==null||plain.equalsIgnoreCase(""))
					throw new Exception("decryptError"); 
				return plain;
			default:
				throw new Exception("errortype"); 
		}
	}
}
