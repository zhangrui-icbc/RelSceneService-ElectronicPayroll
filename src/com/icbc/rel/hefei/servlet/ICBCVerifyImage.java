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
			System.out.println("��ȡUNIQUE_ID:"+uniqueId);
			String verify = (String) arg0.getSession().getAttribute(
					"RANDOM_NUMBER");
			try {
				String str = VerifyImage.decode(uniqueId, verify, code, 0);
				logger.info("code:"+code+" ���ܺ�"+str);
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
		 * ��ǰ�з�֧�ְ�ȫ��������������ͷ��Ϣ���Լ�ǿ�Կ�վ�ű����˵ķ�������ʵ��ʹ������һЩ��Ҫע��ĵط���
		 * ��ʹ��X-XSS-Protectionʱ�����������վ���ܸ��ݷ�����Ҫ��ǿ�����û����
		 * ����Ϊ0ʱ��ǿ�ƽ��ã���ʱ����Υ��ͬԴ����������Դ��ű�������������ʹ��
		 * ����Ϊ1ʱ��ǿ�����ã�IE8�����Ѿ���Ĭ��������XSS���ܣ��������
		 * ��������mode=blockʱ����IE��⵽XSS������������ҳ��������ǽű����ֵĻ��ƣ��������
		 * ���Ͻ�������Ϊ��X-XSS-Protection: 1;mode=block
		 * ��ʹ��X-Content-Type-Optionsʱ����������ݷ�����Ҫ�����û�����������ͼ��
		 * ���Ӷ����һЩ�����������Ⱦ������©���Ĺ���
		 * ����Ϊnosniffʱ�������������������ͣ���Ӧ�ı�����������Я��Content-Type��ָ���������ͣ�����������޷���ȷ������Դ����
		 * ���Ͻ�������Ϊ��X-Content-Type-Options: nosniff
		 * �����������������У�/ICBCMPServer/servlet
		 * /ICBCVerifyImage���ص�����ȱ����Content-Typeͷ���·��ص�ͼƬ�޷������룬 ������Content-Type:
		 * image/pngʱ����֤�������ȷ���룬����������X-Content-Type-Optionsͷ�йء�
		 * �밴������������֯�޸ģ��뿭�����˰����䵽��ȫ�ʲ�����ʾ�������ز���ִ�С�
		 */
		arg1.setContentType("image/png");

		ImageIO.write(VerifyImage.getVerifyImage(randomNumber, BackgroundFile),
				"png", arg1.getOutputStream());
	}

}
