package com.icbc.rel.hefei.controller.todo.client;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icbc.rel.hefei.entity.SceneSwitch;
import com.icbc.rel.hefei.entity.SysActivityInfo;
import com.icbc.rel.hefei.entity.SysPublicNumberInfo;
import com.icbc.rel.hefei.entity.UserDetailInfo;
import com.icbc.rel.hefei.entity.todo.client.SalaryUser;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.service.rel.ImUserService;
import com.icbc.rel.hefei.service.sys.SceneSwitchService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.service.sys.SysPublicNumberInfoService;
import com.icbc.rel.hefei.service.todo.client.service.SalaryWebUserService;
import com.icbc.rel.hefei.service.todo.salary.service.SalaryUserService;
import com.icbc.rel.hefei.util.EnumUtil;
import com.icbc.rel.hefei.util.PasswordCheck;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;

/**
 * 
 * @author ft
 * ����excel
 *
 */
@RequestMapping("/salaryWebUser")
@Controller
public class SalaryWebUserController {
	@Autowired
	private SalaryWebUserService salaryWebUserService;
	
	@Autowired
	private SalaryUserService salaryUserService;
	
	@Autowired
	private SysActivityService sysActivityService;
	@Autowired
	private SysPublicNumberInfoService sysPublicNumberInfoService;
	@Autowired
	private SceneSwitchService sceneSwitchService;
	
	/**
	 * ��ת��������¼ҳ��
	 * @return
	 */
	@RequestMapping("/jumpLogin")
	public String jumpSalary(HttpServletRequest request)
	{
		//TODO ���ӵ����ж� 
		/*�ͻ��˵�ͨ����������activityuid��ȡ��Ӧ��mpidȻ��ͨ��syspublicnumberinfo���ű��ҵ��ù��ںŵĻ����ţ�������ű��ﻹû�л�����������ݣ�������һ����ȡ���ں���Ϣ�Ľӿ�����ȡ������(�����жϲ���û�л�����,��Ϊpc���Ѿ��жϺ���)*/
		String activityUid = request.getParameter("activityUid");
		request.getSession().setAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID, activityUid);
		SysActivityInfo sysActivityInfo = sysActivityService.getSceneByUid(activityUid);
		SysPublicNumberInfo  sysPublicNumberInfo = sysPublicNumberInfoService.getPublicNumberInfoByMpid(sysActivityInfo.getMpId());
		SceneSwitch sceneSwitch =  sceneSwitchService.selectByScene("salary");
		String  visibleareas = sceneSwitch.getVisibleAreas();
//		if(!visibleareas.contains(sysPublicNumberInfo.getStru_ID())) {
//			return  "empty";
//		}else {
			String openId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_USERKEY);
			SalaryUser user = salaryWebUserService.getUserByOpenId(openId);
			request.getSession().setAttribute("user", user);
		    return  "todo/client/login";	
//		}

	}
	
	/**
	 * ��������ѯҳ
	 * @return
	 */
	@RequestMapping("/query")
	public String query()
	{
	    return  "todo/client/query";
	}
	/**
	 * ����������ҳ
	 * @return
	 */
	@RequestMapping("/jumpDetail")
	public String jumpDetail( Model model,String salaryId,@DateTimeFormat(pattern = "yyyy-MM-dd")String issueTime,String  userId)
	{
		model.addAttribute("salaryId",salaryId);
		model.addAttribute("issueTime",issueTime);
		model.addAttribute("userId",userId);
	    return  "todo/client/detail";
	}
	
	/**
	 * ��������ѯҳ
	 * @return
	 */
	@RequestMapping("/queryRe")
	public String re()
	{
	    return  "todo/client/queryRe";
	}
	
	/**
	 * ��������ҳ
	 * @return
	 */
	@RequestMapping("/jumpReDetail")
	public String jumpReDetail( Model model,String salaryId,@DateTimeFormat(pattern = "yyyy-MM-dd")String issueTime,String  userId)
	{
		model.addAttribute("salaryId",salaryId);
		model.addAttribute("issueTime",issueTime);
		model.addAttribute("userId",userId);
	    return  "todo/client/detailRe";
	}
	/**
	 *��ҳ
	 * @return
	 */
	@RequestMapping("/jumpIndex")
	public String jumpIndex()
	{
	    return  "todo/client/index";
	}
	
	/**
	 * ����������ҳ
	 * @return
	 */
	@RequestMapping("/jumpSummary")
	public String summary()
	{
	    return  "todo/client/summary";
	}
	
	/**
	 * ��¼
	 * @param username
	 * @param password
	 * @return
	 */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request, String password){
//    	String IMUserId=SessionUtil.getImUserId(request.getSession()); //��ȷ�Ļ�ȡopenid��ʽ
    	String IMUserId="123";
		//��ȡ�û�����
		UserDetailInfo userinfo=ImUserService.FetchUserInfo(IMUserId);
		String username = userinfo.getMobileNo();
		//TODO �ⲽ�費��,��һ����ɾ���˲�������,����������,����
		String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
    	if(StringUtils.isEmpty(password)) {
    		return AjaxResult.error("����Ϊ��,�������������");
    	}else {
    		//TODO IMUserId��openid����  �Ƿ�Ҫ��һ����˾id����(����id)
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
    @RequestMapping(value="/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request,String newPassword1,String newPassword2){
    	 if(!newPassword1.equals(newPassword2)) {
    		return AjaxResult.error("�������������벻һ��!,�������������");
    	}else if(PasswordCheck.checkPassword(newPassword1).equals("error")) {
    		return AjaxResult.error("��������ȷ�������ʽ!");
    	}else {
    		SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
			if(user.getPassword().equals(newPassword1)) {
				return AjaxResult.error("�����벻���������һ��,����������");
			}
			return salaryWebUserService.resetPassword(user.getMobile(),newPassword1);
    	}
    }
    
    /**
	 *����ҳ
	 * @return
	 */
	@RequestMapping("/jumpIndex1")
	public String jumpIndex1()
	{
	    return  "todo/client/index";
	}
	/**
	 *����ҳ
	 * @return
	 */
	@RequestMapping("/jumpLogin1")
	public String jumpLogin1()
	{
	    return  "todo/client/login";
	}
    
}
