package com.icbc.rel.hefei.util.RSA;

import java.util.Date;

import org.apache.log4j.Logger;

import com.icbc.crypto.utils.RSA;
import com.icbc.crypto.utils.Base64;


public class RSAEncrypt {
	
	private static final Logger logger = Logger.getLogger(RSAEncrypt.class);
	/**
	 * 私钥加密
	 * @param data 待加密数据
	 * @param path 私钥密钥文件路径
	 * @return byte[] 加密数据
	 * @throws Exception 
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String path) throws Exception {
		String base64Text = Base64.icbcbase64encode(data);
		return RSA.icbcRsaPriEn(base64Text.getBytes(), path);
	}
	
	/**
	 * 公钥加密
	 * @param data 待加密数据
	 * @param path 公钥密钥文件路径
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String path) throws Exception {
		String base64Text = Base64.icbcbase64encode(data);
		return RSA.icbcRsaPubEn(base64Text.getBytes(), path);
	}
	
	/**
	 * 私钥解密
	 * @param data 待解密数据
	 * @param path 私钥存放路径
	 * @return byte[] 解密数据
	 * @throws Exception 
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String path) throws Exception {
		byte[] plainText  = RSA.icbcRsaPriDe(data, path);
		return Base64.icbcbase64decode(new String(plainText));
	}
	
	/**
	 * 公钥解密
	 * @param data 待解密数据
	 * @param path 公钥存放路径
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String path) throws Exception {
		byte[] plainText = RSA.icbcRsaPubDe(data, path);
		return Base64.icbcbase64decode(new String(plainText));
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		//boolean isCreate = RSA.icbcRsaGenkey(1024, "D:\\3des\\zh_pub.key","D:\\3des\\zh_priv.key");
		
		//System.out.println(isCreate);
		
		
		
		String str = "北京分行";
		
//		byte[] cipherText = RSAEncrypt.encryptByPublicKey(str.getBytes(), "D:\\3des\\zh_pub.key");
//		System.out.println();
//		byte[] plainText = RSAEncrypt.decryptByPrivateKey(cipherText, "D:\\3des\\zh_priv.key");
		
		byte[] cipherText = RSAEncrypt.encryptByPrivateKey(str.getBytes(), "D:\\3des\\zh_priv.key");
		System.out.println();
		byte[] plainText = RSAEncrypt.decryptByPublicKey(cipherText, "D:\\3des\\zh_pub.key");
		
		System.out.println(new String(plainText));
		
		System.out.println(String.valueOf(new Date().getTime()));
		
	}
}
