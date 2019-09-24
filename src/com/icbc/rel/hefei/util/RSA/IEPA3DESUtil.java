package com.icbc.rel.hefei.util.RSA;

import java.util.Date;

public class IEPA3DESUtil {

	/***
	 * 输入原文,密钥文件名,返回3des加密后的base64字串
	 * 内部加密方式：1--ECB;2--CBC;3--CFB;4--OFB
	 ***/
	public static String encrypt3DES(String old, String keyfileName, int method)
			throws Exception {
		byte[] abc = old.getBytes("UTF-8");
		byte[] abd = new byte[(abc.length / 8) * 8 + 8];
		
		/*
		 * in - byte[] 需要加密的明文或解密的密文 len - int 需要加密或者解密的字符长度，该长度必须小于输入参数in的字节数
		 * out - byte[] 加解密后的字符串 module - int 加解密的方式：0加密，1解密 method -
		 * 内部加密方式：1--ECB;2--CBC;3--CFB;4--OFB
		 *  destype - 内部算法方式：0 加加加，1 加解加
		 * keyFile - String加解密的方式：密钥文件的路径
		 */
		int ret = com.icbc.crypto.utils.TripleDesCryptFileInputKey
				.IcbcTripleDes(abc, abc.length, abd, 0, method, 1, keyfileName);
		if (ret <= 0) {
			throw new Exception(new StringBuffer(
					"加解密出错,请对照返回值分析原因,IEPA3DESTool.Encrypt3DES返回值").append(ret)
					.toString());
		}
		return com.icbc.crypto.utils.Base64.icbcbase64encode(abd).trim();
	}
	
	/***
	 * 输入原文,密钥文件名,返回3des加密后的base64字串
	 * 内部加密方式：1--ECB;2--CBC;3--CFB;4--OFB
	 ***/
	public static String encrypt3DESWithout8Bytes(String old, String keyfileName, int method)
			throws Exception {
		byte[] abc = old.getBytes();
		byte[] abd = new byte[abc.length];
		/*
		 * in - byte[] 需要加密的明文或解密的密文 len - int 需要加密或者解密的字符长度，该长度必须小于输入参数in的字节数
		 * out - byte[] 加解密后的字符串 module - int 加解密的方式：0加密，1解密 method -
		 * 内部加密方式：1--ECB;2--CBC;3--CFB;4--OFB
		 *  destype - 内部算法方式：0 加加加，1 加解加
		 * keyFile - String加解密的方式：密钥文件的路径
		 */
		int ret = com.icbc.crypto.utils.TripleDesCryptFileInputKey
				.IcbcTripleDes(abc, abc.length, abd, 0, method, 1, keyfileName);
		if (ret <= 0) {
			throw new Exception(new StringBuffer(
					"加解密出错,请对照返回值分析原因,IEPA3DESTool.Encrypt3DES返回值").append(ret)
					.toString());
		}
		return com.icbc.crypto.utils.Base64.icbcbase64encode(abd).trim();
	}

  
    /***
     * 输入3des加密后的base64字串,密钥文件名,返回原文
     * 内部加密方式：1--ECB;2--CBC;3--CFB;4--OFB
     ***/
    public static String decrypt3DES(String base64old, String keyfileName, int method)  throws Exception
    {
    	byte[] abc = com.icbc.crypto.utils.Base64.icbcbase64decode(base64old);
        byte[] abd = new byte[abc.length];
        /*
        in - byte[] 需要加密的明文或解密的密文 
		len - int 需要加密或者解密的字符长度，该长度必须小于输入参数in的字节数 
		out - byte[] 加解密后的字符串 
		module - int 加解密的方式：0加密，1解密 
		method - 内部加密方式：1--ECB;2--CBC;3--CFB;4--OFB 
		destype - 内部算法方式：0 加加加，1 加解加 
		keyFile - String加解密的方式：密钥文件的路径

       */
        int ret = com.icbc.crypto.utils.TripleDesCryptFileInputKey.IcbcTripleDes(abc, abc.length, abd, 1,  method, 1,keyfileName);
        if (ret <= 0)
        {	
        	//增加日志
        	System.err.println("加解密出错,请对照返回值分析原因,IEPA3DESTool.Decrypt3DES返回值: " + String.valueOf(ret));
        	
            throw new Exception(new StringBuffer("加解密出错,请对照返回值分析原因,IEPA3DES.Decrypt3DES返回值").append(ret).toString());
        }
        return new String(abd,"utf-8").trim();
        
    }
    
    public static void main(String[] args) {
		String mpid="10003508";
		String timestamp=String.valueOf(new Date().getTime());//new Calendar().getTimeInMillis());
		
		String menuid="L0022";
		String TranData = "mpid="+mpid+"&timestamp="+timestamp+"&menuid="+menuid;
		
		String keyfile="D:/workspace/ICBCMPServer/CONFIG/ebank_app/icbcinbs/mims/server/security/ICBC_EMALL_1_00000_3DES_16";
		String encTranData ="";
		try {
			encTranData= encrypt3DES(TranData,keyfile,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(TranData);
		System.out.println(encTranData);
	}
}