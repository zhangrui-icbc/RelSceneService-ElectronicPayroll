package com.icbc.rel.hefei.controller.salary.client;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icbc.rel.hefei.entity.SceneSwitch;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;
import com.icbc.rel.hefei.entity.UserDetailInfo;
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;
import com.icbc.rel.hefei.service.rel.ImUserService;
import com.icbc.rel.hefei.service.rel.PublicNumberInfoService;
import com.icbc.rel.hefei.service.salary.client.service.SalaryWebUserService;
import com.icbc.rel.hefei.service.sys.SceneSwitchService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;

/**
 * 
 * @author ft
 * ����excel
 *
 */
@RequestMapping("/com")
@Controller
public class SalaryWebUserController {
	private static Logger logger = Logger.getLogger(SalaryWebUserController.class);
	@Autowired
	private SalaryWebUserService salaryWebUserService;
	@Autowired
	private SysActivityService sysActivityService;
	@Autowired
	private SceneSwitchService sceneSwitchService;
	/**
	 * ��ת��������¼ҳ��
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpLogin")
	public String jumpSalary(HttpServletRequest request){
		logger.info("����ͻ�����ת��������¼ҳ��=========>>>>>>>");
		//TODO ���ӵ����ж� 
		/*�ͻ��˵�ͨ����������activityuid��ȡ��Ӧ��mpidȻ��ͨ��syspublicnumberinfo���ű��ҵ��ù��ںŵĻ����ţ�������ű��ﻹû�л�����������ݣ�������һ����ȡ���ں���Ϣ�Ľӿ�����ȡ������(�����жϲ���û�л�����,��Ϊpc���Ѿ��жϺ���)*/
		String activityUid = request.getParameter("activityUid");
		request.getSession().setAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID, activityUid);
		SysActivityInfo sysActivityInfo = sysActivityService.getSceneByUid(activityUid);
		//��ȡ�û�����
		SysPublicNumberInfo info = new SysPublicNumberInfo();
		info.setPublicNumberId(sysActivityInfo.getMpId());
		info = PublicNumberInfoService.FetchPubAddrInfo(info);
		SceneSwitch sceneSwitch =  sceneSwitchService.selectByScene("salary");
		String mpid = sysActivityInfo.getMpId();
		if(sceneSwitch.getStatus()==1) {
			logger.info("���ʵ�����ȫ���ɼ�");
		    return  "salary/client/login";	
		}else if(sceneSwitch.getStatus()==-1){
			logger.info("���ʵ������ѹر�");
			return  "empty";
		}else {
			List<String> idList = new ArrayList<String>();
			idList.add("10087544");
			idList.add("10103441");
			logger.info("���ʵ��������ֿɼ�");
			if(idList.contains(mpid)){
				return  "salary/client/login";
			}else if(!sceneSwitch.getVisibleAreas().contains(info.getStru_ID())) {
				return  "empty";
			}else {
			    return  "salary/client/login";	
			}
		}

	}
	
	/**
	 * ��������ѯҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/query")
	public String query()
	{
	    return  "salary/client/query";
	}
	/**
	 * ����������ҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpDetail")
	public String jumpDetail( Model model,String id)
	{
		model.addAttribute("id",id);
	    return  "salary/client/detail";
	}
	
	/**
	 * ��������ѯҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/queryRe")
	public String re()
	{
	    return  "salary/client/queryRe";
	}
	
	/**
	 * ��������ҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpReDetail")
	public String jumpReDetail( Model model,String id)
	{
		model.addAttribute("id",id);
		return  "salary/client/detailRe";
	}
	/**
	 *��ҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpIndex")
	public String jumpIndex()
	{
	    return  "salary/client/index";
	}
	
	/**
	 * ����������ҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpSummary")
	public String summary()
	{
	    return  "salary/client/summary";
	}
	
	/**
	 * ��¼
	 * @param username
	 * @param password
	 * @return
	 */
    @RequestMapping(value="/salaryWebUser/login",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request, String password){
    	logger.info("��¼�ӿڷ���-------------------");
    	//sid   accesstime= 
    	String sid= request.getSession().getId();
    	logger.info("11sid:"+sid+",accesstime:"+System.currentTimeMillis());
    	UserDetailInfo userinfo= new UserDetailInfo();
    	String username="";
    	String IMUserId=SessionUtil.getImUserId(request.getSession()); //��ȷ�Ļ�ȡopenid��ʽ
//    	String IMUserId="123";
    	logger.info("22sid:"+sid+",accesstime:"+System.currentTimeMillis()+",IMUserId:"+IMUserId);
    	if(StringUtils.isEmpty(IMUserId)) {
			logger.error("sessionʧЧ,��ȡIMUserIdʧ�ܣ�");
			return AjaxResult.error("ҳ��ʧЧ,�����½��룡");
    	}
    	logger.info("�û���openidΪ��"+IMUserId);
		//��ȡ�û�����
    	try {
    		userinfo = ImUserService.FetchUserInfo(IMUserId);
    		logger.info("33sid:"+sid+",accesstime:"+System.currentTimeMillis()+",userinfo:"+userinfo.toString());
    		username = userinfo.getMobileNo();
    		if(StringUtils.isEmpty(username)) {
    			logger.error("��ȡ�û�����ʧ�ܣ�");
    			return AjaxResult.error("��ȡ�û�����ʧ�ܣ�");
    		}	
		} catch (Exception e) {
			logger.error("��ȡ�û�����ʧ�ܣ�");
			return AjaxResult.error("��ȡ�û�����ʧ�ܣ�");
		}
		logger.info("��ȡ�û�����Ϊ��"+JSON.toJSON(userinfo));
		String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
		logger.info("44sid:"+sid+",accesstime:"+System.currentTimeMillis()+",companyId:"+companyId);
    	if(StringUtils.isEmpty(password)) {
    		logger.error("����Ϊ��,�������������");
    		return AjaxResult.error("����Ϊ��,�������������");
    	}else {
    		AjaxResult ajaxResult = salaryWebUserService.login(username,password,companyId,IMUserId);
    		int code = (int) ajaxResult.get("code");
    		if(code!=0) {
    			return ajaxResult;
    		}else {
    			request.getSession().setAttribute(SessionParamConstant.SESSION_PARAM_USERKEY, IMUserId);
    			request.getSession().setAttribute("user", ajaxResult.get("data"));
    			return ajaxResult;
    		}
    	}
    }
    
    /**
     * �����޸�
     */
    @RequestMapping(value="/salaryWebUser/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request,String newPassword1,String newPassword2){
    	logger.info("����ͻ�����ת�����޸�=========>>>>>>>");
    	 if(!newPassword1.equals(newPassword2)) {
    		logger.error("�������������벻һ��!,�������������");
    		return AjaxResult.error("�������������벻һ��!,�������������");
    	}else {
    		SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
			if(user.getPassword().equals(newPassword1)) {
				logger.error("�����벻���������һ��,����������");
				return AjaxResult.error("�����벻���������һ��,����������");
			}
			return salaryWebUserService.resetPassword(user.getMobile(),newPassword1);
    	}
    }
    
    /**
	 *����ҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpIndex1")
	public String jumpIndex1()
	{
	    return  "salary/client/index";
	}
	/**
	 *����ҳ
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpLogin1")
	public String jumpLogin1()
	{
	    return  "salary/client/login";
	}
    
}
