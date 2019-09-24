package com.icbc.rel.hefei.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.ibm.xylem.Logger;

import sun.misc.BASE64Decoder;

public class VerifyImage {
	private static double rotateAngle = 0.2;
	private static int fontColor = 80;
	private static int width = 80;
	private static int height = 28;
	private static String[] name = { "dialog.3", "serif.2", "monospaced.0" };

	public static BufferedImage getVerifyImage(String verifycode, String VerifyCodeBgPath) {

		// 生成随机类
		Random initRandom = new Random();
		Random random = new Random(initRandom.nextLong());

		Image im = null;
		URL url=VerifyImage.class.getResource(VerifyCodeBgPath);
		javax.swing.ImageIcon imgIcon = new javax.swing.ImageIcon(
				url);
		System.out.println(VerifyCodeBgPath);
				im = imgIcon.getImage();

		BufferedImage image = null;
		// StringBuffer bRand = new StringBuffer();
		// 字体
		// String[] name = {"Dialog","Serif","SansSerif"};

		// String[] name = {"serif.2"};
		int[] fontArr = { Font.BOLD };
		// int[] fontArr =
		// {Font.PLAIN,Font.BOLD,Font.ITALIC,Font.BOLD+Font.ITALIC};
		int fontALen = fontArr.length;
		int nameLen = name.length;
		AffineTransform affineTransform = new AffineTransform();
		// 字符写入图片并旋转
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
         
		// 获取图形上下文
		Graphics2D g = image.createGraphics();

		int xstart = random.nextInt(2);
		int ystart = random.nextInt(2);

		// g.drawImage(im,0,0,null);
		image.getGraphics().drawImage(im, xstart, ystart, width, height, null);

		// 随机产生背景
		// 设定字体
		g.setFont(new Font("Serif", Font.PLAIN, 14));
		// 边框
		g.setColor(new Color(0, 0, 0));
		g.drawLine(0, 0, 0, height);
		g.drawLine(0, 0, width, 0);
		g.drawLine(0, height - 1, width - 1, height - 1);
		g.drawLine(width - 1, 0, width - 1, height - 1);
		int len = verifycode.length();
		for (int i = 0; i < len; i++) {
			String rand = verifycode.substring(i, i + 1);
			// 将认证码显示到图象中
			int fontSize = 18 + random.nextInt(3);
			g.setColor(new Color(random.nextInt(fontColor), random
					.nextInt(fontColor), random.nextInt(fontColor)));
			// g.setColor(new Color(0x000000ff));
			// 设定字体
			int fontName = random.nextInt(nameLen);
			int fontA = random.nextInt(fontALen);
			g.setFont(new Font(name[fontName], fontArr[fontA], fontSize));
			double ang = (double) (15 + random
					.nextInt((int) (100 * rotateAngle))) / 100;
			if (random.nextInt(10) / 2 == 0)
				ang = -1 * ang;
			if (rand.equals("i") || rand.equals("j") || rand.equals("f")
					|| rand.equals("t")) {
				ang = ang / 8;
			}
			int r1 = random.nextInt(5);
			int h1 = random.nextInt(5);
			affineTransform.rotate(ang, 15 * i + 5 + r1 + (fontSize / 2),
					15 - h1 / 2);
			g.setTransform(affineTransform);
			g.drawString(rand, 15 * i + 5 + random.nextInt(2),
					18 + random.nextInt(2));
			affineTransform.rotate(-1 * ang, 15 * i + 5 + +r1 + (fontSize / 2),
					15 - h1 / 2);
			g.setTransform(affineTransform);
		}
		// 图象生效释放资源
		g.dispose();

		return image;
	}
	
	/**
	 * 解密密码(3Des),返回解密后的密码
	 * 
	 * @return String
	 * @param uniqueId
	 *            随机生成的唯一ID
	 * @param encVC
	 *            加密后的验证码
	 * @param encPass
	 *            加密后的密码
	 * @param boolean isVC 是否比对验证码
	 * @param int decodeType 解密类型 0：window版 1：mac版
	 */

	public static String decode(String uniqueId, String verifyCode, String encPass,
			int decodeType) {
		String sKey = uniqueId + verifyCode; // ID加验证码作为密钥

		String pData = getPlainDataAll(sKey.getBytes(), encPass, true,
				"IBMJCE", decodeType);
		return pData;
	}
	
	/**
	 * 
	 * @param int decodeType 解密类型 0：window版 1：mac版
	 */
	private static String getPlainDataAll(byte[] encKeyB, String encData,
			boolean isSHA, String provider, int decodeType) {
//		System.out
//				.println("password decode trace field getPlainDataAll decodeType="
//						+ decodeType);
		BASE64Decoder b64 = new BASE64Decoder();
		byte[] resByte = (byte[]) null;
		try {
			resByte = b64.decodeBuffer(encData);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 开始组织密钥
		MessageDigest alga;
		byte[] bKey = null;
		try {
			if (isSHA) {
				alga = MessageDigest.getInstance("SHA-1", provider);

				alga.update(encKeyB);
				bKey = alga.digest();
			} else
				bKey = encKeyB;
			byte[] keyByte = new byte[24];
//			System.out
//					.println("password decode trace field getPlainDataAll keyByte="
//							+ keyByte);
			if (0 == decodeType) {
				System.arraycopy(bKey, 0, keyByte, 0, bKey.length);
				System.arraycopy(bKey, 0, keyByte, 20, 4);
			} else if (1 == decodeType) {// mac版算法：unique+keypart（验证码的值），sha1处理，得到20位的S1值
											// ；获取S1的第10位到13位，保存为S2；3DES密钥的值为：M
											// = S2+S1；
				System.arraycopy(bKey, 0, keyByte, 4, bKey.length);
				System.arraycopy(bKey, 10, keyByte, 0, 4);
			}
			// 密钥组织完毕
			Key key = null;
			key = new SecretKeySpec(keyByte, "DESede");

//			System.out.println("password decode trace field getPlainDataAll 1");
			Cipher cipher;
			cipher = Cipher.getInstance("DESede", provider);

//			System.out.println("password decode trace field getPlainDataAll 2");
			cipher.init(Cipher.DECRYPT_MODE, key);

//			System.out.println("password decode trace field getPlainDataAll 3");
			byte[] clearByte = cipher.doFinal(resByte);
//			System.out.println("password decode trace field getPlainDataAll 4");

			return new String(clearByte);

		} catch (Exception e) {
			System.out
					.println("password decode trace field getPlainDataAll e = "
							+ e.getMessage());
			System.out.println("DESede error:" + e);

		}
		return null;
	}
	
	public static void main(String[] args) {
		/*String encPwd = "47wiI93QaE7UMs2PaM/sSg==";
		String uniqueId = "14104266742481";
		String encVerify = "OYjA0Z2dcnE=";
		System.out.println(decode(uniqueId, "1234", encVerify, 0));
		System.out.println(decode(uniqueId, "1234", encPwd, 0));*/
		

		System.out.println(decode("2059903041", "6838", "Ot7EMhOFlB8=", 0));
	}
}
