package com.icbc.rel.hefei.servlet;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.icbc.rel.hefei.util.SystemConfigUtil;
import com.icbc.rel.hefei.util.VerifyImage;

public class ICBCVerifyImage extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String BackgroundFile = "";
	private static final Logger logger = Logger.getLogger(ICBCVerifyImage.class);
	@Override
	public void init(ServletConfig config) throws ServletException {
		BackgroundFile = "verify_image.bg";
		logger.info("imgfile:"+BackgroundFile);
		String cacheDir = SystemConfigUtil.tempPath;
		logger.info("cacheDir:"+cacheDir);
		ImageIO.setCacheDirectory(new File(cacheDir));

		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		if (arg0.getParameter("Edit") != null) {
			String code = arg0.getParameter("Edit");
			if (code.indexOf(" ") != -1) {
				code = code.replaceAll(" ", "+");
			}
			String uniqueId = (String) arg0.getSession().getAttribute(
					"UNIQUE_ID");
			System.out.println("读取UNIQUE_ID:"+uniqueId);
			String verify = (String) arg0.getSession().getAttribute(
					"RANDOM_NUMBER");
			try {
				String str = VerifyImage.decode(uniqueId, verify, code, 0);
				logger.info("code:"+code+" 解密后："+str);
				if (verify.equals(str)) {
					arg1.getWriter().write("0");
				} else {
					arg1.getWriter().write("1");
				}
			} catch (Exception e) {
				e.printStackTrace();
				arg1.getWriter().write("1");
			}
			return;
		}
		String randomNumber = String.valueOf(System.nanoTime());
		randomNumber = randomNumber.substring(randomNumber.length() - 4,
				randomNumber.length());
		arg0.getSession().setAttribute("RANDOM_NUMBER", randomNumber);

		/*
		 * 此前研发支持安全组提出完善浏览器头信息，以加强对跨站脚本过滤的方法，在实际使用中有一些需要注意的地方：
		 * 当使用X-XSS-Protection时，浏览器防跨站功能根据服务器要求被强制启用或禁用
		 * 设置为0时，强制禁用，此时允许违反同源策略载入资源或脚本，不建议这样使用
		 * 设置为1时，强制启用，IE8本身已经是默认启用了XSS功能，建议采用
		 * 补充设置mode=block时，若IE检测到XSS，则阻拦整个页面而不仅是脚本部分的绘制，建议采用
		 * 综上建议设置为：X-XSS-Protection: 1;mode=block
		 * 当使用X-Content-Type-Options时，浏览器根据服务器要求启用或禁用内容类型检测
		 * ，从而规避一些利用浏览器渲染、载入漏洞的攻击
		 * 设置为nosniff时，浏览器不检查内容类型，相应的必须在请求中携带Content-Type来指定内容类型，否则浏览器无法正确载入资源内容
		 * 综上建议设置为：X-Content-Type-Options: nosniff
		 * 在你们遇到的问题中，/ICBCMPServer/servlet
		 * /ICBCVerifyImage返回的内容缺少了Content-Type头导致返回的图片无法被载入， 当增加Content-Type:
		 * image/png时，验证码可以正确载入，这个问题仅与X-Content-Type-Options头有关。
		 * 请按照如上描述组织修改，请凯冬将此案例充到安全资产库提示其他基地参照执行。
		 */
		arg1.setContentType("image/png");

		ImageIO.write(VerifyImage.getVerifyImage(randomNumber, BackgroundFile),
				"png", arg1.getOutputStream());
	}

}
