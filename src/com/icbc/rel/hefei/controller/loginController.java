package com.icbc.rel.hefei.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;
import com.icbc.rel.hefei.service.sys.SysPublicNumberInfoService;
import com.icbc.rel.hefei.util.Md5Util;
import com.icbc.rel.hefei.util.SessionUtil;
import com.icbc.rel.hefei.util.VerifyImage;

/**
 * @Description: ��¼�ӿ�
 * @author helei
 * @date 2019��2��12��
 */

@Controller
public class loginController {
	
	private static final Logger logger = Logger.getLogger(loginController.class);
	
	@Autowired
	private SysPublicNumberInfoService sysService;
	/*
	 * ��¼ҳ��
	 */
	@RequestMapping(value="/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}
	
	
	/*
	 * ��ѯ��¼�û���Ϣ
	 */
	@RequestMapping(value="/ad/checkUser",method=RequestMethod.POST)
	@ResponseBody
	public Msg checkUser(String account,String psd,HttpServletRequest request) {
		try {
			if (psd.indexOf(" ") != -1) {
				psd = psd.replaceAll(" ", "+");
			}
			String uniqueId = (String) request.getSession().getAttribute(
					"UNIQUE_ID");
			System.out.println("��ȡUNIQUE_ID:"+uniqueId);
			String verify = (String) request.getSession().getAttribute(
					"RANDOM_NUMBER");
			String str = VerifyImage.decode(uniqueId, verify, psd, 0);
			logger.info("psd:"+psd+" ���ܺ�"+str);
			psd=Md5Util.EncoderByMd5(str);
			logger.info("md5:"+psd);
			SysPublicNumberInfo info=sysService.getPublicNumberInfo(account, psd);
			logger.info("md5:"+account);
			if(info!=null) {
				SessionUtil.setAdminSession(request.getSession(),info.getPublicNumberId(),info.getPublicNumberName());
				return new Msg(1,"��¼�ɹ�");
			}
			
		} catch (Exception e) {
			logger.info("Exception:",e);
			
		}
		return new Msg(-1,"�û��������벻����");
	}
	
	

}
