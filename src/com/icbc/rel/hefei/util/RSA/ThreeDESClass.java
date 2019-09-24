package com.icbc.rel.hefei.util.RSA;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import com.icbc.crypto.utils.SHA1;
import com.icbc.crypto.utils.TripleDesCryptVarKey2;


public class ThreeDESClass {
	
	//字符编码
		public final static String ENCODING = "GBK";
		
		/**
		 * 字符串加密
		 * @param plainText 明文
		 * @return cipherText 密文
		 * @throws UnsupportedEncodingException 
		 */
		public static byte[] encrypt(byte[] plainText, byte[] cipherKey) throws UnsupportedEncodingException {
			byte[] cipherText = new byte[plainText.length];
			
			byte[] asciiCipherKey = TripleDesCryptVarKey2.Ascii2Text(cipherKey).getBytes(ENCODING);
			int ret = TripleDesCryptVarKey2.TripleDesCFB0(plainText, plainText.length, cipherText, 0, asciiCipherKey);
			return cipherText;
		}
		
		/**
		 * 字符串解密
		 * @param cipherText 密文
		 * @param cipherKey 密钥
		 * @return plainText 明文
		 * @throws UnsupportedEncodingException
		 */
		public static byte[] decrypt(byte[] cipherText, byte[] cipherKey) throws UnsupportedEncodingException {
			byte[] out = new byte[cipherText.length];
			byte[] asciiCipherKey = TripleDesCryptVarKey2.Ascii2Text(cipherKey).getBytes(ENCODING);
			int ret = TripleDesCryptVarKey2.TripleDesCFB0(cipherText, cipherText.length, out, 1, asciiCipherKey);
			return out;
		}
		
		
		/**
		 * 生成密钥 
		 * @param size 位数
		 * @return bytes[] 密钥
		 * @throws NoSuchAlgorithmException 
		 */
		public static byte[] createCipher(int size) throws NoSuchAlgorithmException {
			KeyGenerator kg = KeyGenerator.getInstance("3DES");
			kg.init(size);
			return kg.generateKey().getEncoded();
		}
		
		/**
		 * 摘要处理
		 * @param data 待摘要数据
		 * @return String 摘要字符串
		 */
		public static String shaHex(byte[] data) {
			SHA1 sha = new SHA1();
			return sha.getDigestOfString(data);
		}
		
		/**
		 * 验证
		 * @param data 待摘要数据
		 * @param messageDigest 摘要字符串
		 * @return  验证结果
		 */
		public static boolean validata(byte[] data, String messageDigest) {
			SHA1 sha = new SHA1();
			return messageDigest.equals(sha.getDigestOfString(data));
		}
		
		public static String decode3Des(String str,String filePath) throws Exception{
			String decode3DesStr = "";
			//decode3DesStr = IcbcTripleDesDecrypt.TripleDesDecrypt(str, filePath);
			decode3DesStr =IEPA3DESUtil.decrypt3DES(str,filePath,1);
			return decode3DesStr;
		}

		
		public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
			
			String text = "mpid=10003508&timestamp=1431657053756&menuid=L0022";//"测试字符串";
			String text2 = "测试字符串1";
			
			byte[] cipherKey = createCipher(112);

			
		//	byte[] cipherKey =  new String("67890567890").getBytes();
			
			//String hexString = "oX"
			
			byte[] cipherText = encrypt(text.getBytes(), cipherKey);
			
			
			//String base64Text = Base64.icbcbase64encode(cipherText);
			
			//cipherText = Base64.icbcbase64decode(base64Text);
			
			
			System.out.println(new String(decrypt(cipherText, cipherKey)));
			
			SHA1 sha = new SHA1();
			
			String zhaiyao = shaHex(text.getBytes());
			System.out.println(zhaiyao);
			
			
			boolean ret = validata(text2.getBytes(), zhaiyao);
			
			//System.out.println(ret);
			//byte[] sha1 = sha.getDigestOfBytes(cipherText);
		}

}
