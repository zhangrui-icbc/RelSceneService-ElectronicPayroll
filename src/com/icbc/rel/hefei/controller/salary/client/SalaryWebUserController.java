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
 * 工资excel
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
	 * 跳转工资条登录页面
	 * @return
	 */
	@RequestMapping("/salaryWebUser/jumpLogin")
	public String jumpSalary(HttpServletRequest request){
		logger.info("进入客户端跳转工资条登录页面=========>>>>>>>");
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
		String mpid = sysActivityInfo.getMpId();
		if(sceneSwitch.getStatus()==1) {
			logger.info("工资单场景全部可见");
		    return  "salary/client/login";	
		}else if(sceneSwitch.getStatus()==-1){
			logger.info("工资单场景已关闭");
			return  "empty";
		}else {
			List<String> idList = new ArrayList<String>();
			idList.add("10087544");
			idList.add("10103441");
			logger.info("工资单场景部分可见");
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
	public String jumpDetail( Model model,String id)
	{
		model.addAttribute("id",id);
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
	public String jumpReDetail( Model model,String id)
	{
		model.addAttribute("id",id);
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
    	logger.info("登录接口访问-------------------");
    	//sid   accesstime= 
    	String sid= request.getSession().getId();
    	logger.info("11sid:"+sid+",accesstime:"+System.currentTimeMillis());
    	UserDetailInfo userinfo= new UserDetailInfo();
    	String username="";
    	String IMUserId=SessionUtil.getImUserId(request.getSession()); //正确的获取openid方式
//    	String IMUserId="123";
    	logger.info("22sid:"+sid+",accesstime:"+System.currentTimeMillis()+",IMUserId:"+IMUserId);
    	if(StringUtils.isEmpty(IMUserId)) {
			logger.error("session失效,获取IMUserId失败！");
			return AjaxResult.error("页面失效,请重新进入！");
    	}
    	logger.info("用户的openid为："+IMUserId);
		//拉取用户详情
    	try {
    		userinfo = ImUserService.FetchUserInfo(IMUserId);
    		logger.info("33sid:"+sid+",accesstime:"+System.currentTimeMillis()+",userinfo:"+userinfo.toString());
    		username = userinfo.getMobileNo();
    		if(StringUtils.isEmpty(username)) {
    			logger.error("拉取用户详情失败！");
    			return AjaxResult.error("拉取用户详情失败！");
    		}	
		} catch (Exception e) {
			logger.error("拉取用户详情失败！");
			return AjaxResult.error("拉取用户详情失败！");
		}
		logger.info("拉取用户详情为："+JSON.toJSON(userinfo));
		String  companyId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID);
		logger.info("44sid:"+sid+",accesstime:"+System.currentTimeMillis()+",companyId:"+companyId);
    	if(StringUtils.isEmpty(password)) {
    		logger.error("密码为空,请检查后重新输入");
    		return AjaxResult.error("密码为空,请检查后重新输入");
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
     * 密码修改
     */
    @RequestMapping(value="/salaryWebUser/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request,String newPassword1,String newPassword2){
    	logger.info("进入客户端跳转密码修改=========>>>>>>>");
    	 if(!newPassword1.equals(newPassword2)) {
    		logger.error("新密码两次输入不一致!,请检查后重新输入");
    		return AjaxResult.error("新密码两次输入不一致!,请检查后重新输入");
    	}else {
    		SalaryUser user =  (SalaryUser) request.getSession().getAttribute("user");
			if(user.getPassword().equals(newPassword1)) {
				logger.error("新密码不能与旧密码一致,请重新输入");
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
