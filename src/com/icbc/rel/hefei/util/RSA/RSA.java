package com.icbc.rel.hefei.util.RSA;

import org.apache.log4j.Logger;

import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;
import com.icbc.crypto.utils.Base64;

public class RSA {
	private static final Logger logger = Logger.getLogger(RSA.class);

	/**
	 * ����RSA�ļ��ܺͽ���
	 *	��Ҫ����Ĳ�����
	 *	type:1-��Կ����;2-��Կ����;3-˽Կ����;4-˽Կ����
	 *	keyFilePath:��Կ�ļ�
	 *	cipherField:����
	 *	plainField������
	 * 	���ĺ����Ķ���Ҫ���룬һ�������룬��һ�����
	 *	���أ�������0���쳣��-1
	 */
	public static String decrypt(String abstractText) {
		String[] plainFields = null;
		String[] cipherFields = null;
		try {
			// �ڲ����ܷ�ʽ��1-��Կ����;2-��Կ����;3-˽Կ����;4-˽Կ����
			String type = "4";
			
			// ��Կ�ļ�
			String key="";
			
			//����
			//key = "C:/Users/kfzx-lianxq/Desktop/�ӽ��ܷ���/ICBC_MIMS_2_00000_RSA_2048_PRIV.key";
			key = SystemConfigUtil.rsaPrivPath;
			//key = "D:/RelSceneServiceFile/security/ICBC_MIMS_2_00000_RSA_2048_PUB.key";
			
			// ����
			String cipher = abstractText;
			// ����
			String plain = "";
			return RSA_Handler(type, key, plain, cipher);
			
			
			
	}catch(Exception ex) {
		logger.error("����ժҪʧ�ܣ�",ex);
		return null;
	}
		
	}
	
	private static String RSA_Handler(String type,String key,String plain,String cipher) throws Exception
	{
		// �ڲ����ܷ�ʽ��1-��Կ����;2-��Կ����;3-˽Կ����;4-˽Կ����
		switch(Integer.parseInt(type)){
			case 1: //1-��Կ����;
				logger.info("1-��Կ����=> before encryptByPublicKey:"+ plain);
				try{
				cipher = Base64.icbcbase64encode(RSAEncrypt.encryptByPublicKey(plain.getBytes(), key)).replaceAll("\r","").replaceAll("\n", "");
				}catch(Exception e){
					throw new Exception("encryptError"); 
				}
				logger.info("1-��Կ����=> after encryptByPublicKey:"+ cipher);
				if(cipher==null||cipher.equalsIgnoreCase(""))
					throw new Exception("encryptError"); 
				return cipher;
		case 2://2-��Կ����;
				logger.info("2-��Կ����=> before decryptByPublicKey:"+ cipher);
				try{
				plain = new String(RSAEncrypt.decryptByPublicKey(Base64.icbcbase64decode(cipher), key));
				}catch(Exception e){
					throw new Exception("decryptError"); 
				}
				logger.info("2-��Կ����=> after decryptByPublicKey:"+ plain);
				
				if(plain==null||plain.equalsIgnoreCase(""))
					throw new Exception("decryptError"); 
				return plain;
			case 3://3-˽Կ����;
				logger.info("3-˽Կ����=> before encryptByPrivateKey:"+ plain);
				try{
				cipher = Base64.icbcbase64encode(RSAEncrypt.encryptByPrivateKey(plain.getBytes(), key)).replaceAll("\r","").replaceAll("\n", "");
				}catch(Exception e){
					throw new Exception("encryptError"); 
				}
				logger.info("3-˽Կ����=> after encryptByPrivateKey:"+ cipher);
				
				if(cipher==null||cipher.equalsIgnoreCase(""))
					throw new Exception("encryptError"); 
				return cipher;
			case 4://4-˽Կ����
				logger.info("4-˽Կ����=> before decryptByPrivateKey:"+ cipher);
				try{
				plain = new String(RSAEncrypt.decryptByPrivateKey(Base64.icbcbase64decode(cipher), key));
				}catch(Exception e){
					throw new Exception("decryptError"); 
				}
				logger.info("4-˽Կ����=> after decryptByPrivateKey:"+ plain);
				
				if(plain==null||plain.equalsIgnoreCase(""))
					throw new Exception("decryptError"); 
				return plain;
			default:
				throw new Exception("errortype"); 
		}
	}
}
