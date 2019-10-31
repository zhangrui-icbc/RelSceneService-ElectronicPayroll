package com.icbc.rel.hefei.controller.salary.client;
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
import com.icbc.rel.hefei.entity.salary.AjaxResult;
import com.icbc.rel.hefei.entity.salary.client.SalaryUser;
import com.icbc.rel.hefei.service.rel.ImUserService;
import com.icbc.rel.hefei.service.rel.PublicNumberInfoService;
import com.icbc.rel.hefei.service.salary.client.service.SalaryWebUserService;
import com.icbc.rel.hefei.service.salary.service.SalaryUserService;
import com.icbc.rel.hefei.service.sys.SceneSwitchService;
import com.icbc.rel.hefei.service.sys.SysActivityService;
import com.icbc.rel.hefei.service.sys.SysPublicNumberInfoService;
import com.icbc.rel.hefei.util.EnumUtil;
import com.icbc.rel.hefei.util.PasswordCheck;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;

/**
 * 
 * @author ft
 * 工资excel
 *
 */
@RequestMapping("/com")
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
	 * 跳转工资条登录页面
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpLogin")
	public String jumpSalary(HttpServletRequest request)
	{
		//TODO 增加地区判断 
		/*客户端得通过活动链接里的activityuid获取对应的mpid然后通过syspublicnumberinfo那张表找到该公众号的机构号，如果这张表里还没有机构号这个数据，就再走一次拉取公众号信息的接口来获取机构号(个人判断不会没有机构号,因为pc端已经判断好了)*/
		String activityUid = request.getParameter("activityUid");
		request.getSession().setAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID, activityUid);
		SysActivityInfo sysActivityInfo = sysActivityService.getSceneByUid(activityUid);
		//拉取用户详情
		SysPublicNumberInfo info = new SysPublicNumberInfo();
		info.setPublicNumberId(sysActivityInfo.getMpId());
		info = PublicNumberInfoService.FetchPubAddrInfo(info);
		SceneSwitch sceneSwitch =  sceneSwitchService.selectByScene("salary");
		if(sceneSwitch.getStatus()==1) {
			String openId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_USERKEY);
			SalaryUser user = salaryWebUserService.getUserByOpenId(activityUid,openId);
			request.getSession().setAttribute("user", user);
		    return  "salary/client/login";	
		}else {
			if(!sceneSwitch.getVisibleAreas().contains(info.getStru_ID())) {
				return  "empty";
			}else {
				String openId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_USERKEY);
				SalaryUser user = salaryWebUserService.getUserByOpenId(activityUid,openId);
				request.getSession().setAttribute("user", user);
			    return  "salary/client/login";	
			}
		}

	}
	
	/**
	 * 工资条查询页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/query")
	public String query()
	{
	    return  "salary/client/query";
	}
	/**
	 * 工资条详情页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpDetail")
	public String jumpDetail( Model model,String salaryId,@DateTimeFormat(pattern = "yyyy-MM-dd")String issueTime,String  userId)
	{
		model.addAttribute("salaryId",salaryId);
		model.addAttribute("issueTime",issueTime);
		model.addAttribute("userId",userId);
	    return  "salary/client/detail";
	}
	
	/**
	 * 报销单查询页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/queryRe")
	public String re()
	{
	    return  "salary/client/queryRe";
	}
	
	/**
	 * 报销详情页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpReDetail")
	public String jumpReDetail( Model model,String salaryId,@DateTimeFormat(pattern = "yyyy-MM-dd")String issueTime,String  userId)
	{
		model.addAttribute("salaryId",salaryId);
		model.addAttribute("issueTime",issueTime);
		model.addAttribute("userId",userId);
	    return  "salary/client/detailRe";
	}
	/**
	 *首页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpIndex")
	public String jumpIndex()
	{
	    return  "salary/client/index";
	}
	
	/**
	 * 工资条汇总页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpSummary")
	public String summary()
	{
	    return  "salary/client/summary";
	}
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
    @RequestMapping(value="/salaryWebUser/login",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request, String password){
    	String IMUserId=SessionUtil.getImUserId(request.getSession()); //正确的获取openid方式
//    	String IMUserId="123";
		//拉取用户详情
		UserDetailInfo userinfo=ImUserService.FetchUserInfo(IMUserId);
		String username = userinfo.getMobileNo();
		//TODO 这步骤不对,万一场景删除了不存在了,就有问题了,待改
		String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
    	if(StringUtils.isEmpty(password)) {
    		return AjaxResult.error("密码为空,请检查后重新输入");
    	}else {
    		//TODO IMUserId即openid问题  是否要加一个公司id参数(场景id)
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
     * 密码修改
     */
    @RequestMapping(value="/salaryWebUser/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request,String newPassword1,String newPassword2){
    	 if(!newPassword1.equals(newPassword2)) {
    		return AjaxResult.error("新密码两次输入不一致!,请检查后重新输入");
    	}/*else if(PasswordCheck.checkPassword(newPassword1).equals("error")) {
    		return AjaxResult.error("请输入正确的密码格式!");
    	}*/else {
    		SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
			if(user.getPassword().equals(newPassword1)) {
				return AjaxResult.error("新密码不能与旧密码一致,请重新输入");
			}
			return salaryWebUserService.resetPassword(user.getMobile(),newPassword1);
    	}
    }
    
    /**
	 *纯首页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpIndex1")
	public String jumpIndex1()
	{
	    return  "salary/client/index";
	}
	/**
	 *纯首页
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpLogin1")
	public String jumpLogin1()
	{
	    return  "salary/client/login";
	}
    
}
