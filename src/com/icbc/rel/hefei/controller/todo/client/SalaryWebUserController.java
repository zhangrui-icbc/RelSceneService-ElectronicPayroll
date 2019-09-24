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

import com.icbc.rel.hefei.entity.UserDetailInfo;
import com.icbc.rel.hefei.entity.todo.client.SalaryUser;
import com.icbc.rel.hefei.entity.todo.salary.AjaxResult;
import com.icbc.rel.hefei.service.rel.ImUserService;
import com.icbc.rel.hefei.service.todo.client.service.SalaryWebUserService;
import com.icbc.rel.hefei.service.todo.salary.service.SalaryUserService;
import com.icbc.rel.hefei.util.PasswordCheck;
import com.icbc.rel.hefei.util.SessionParamConstant;
import com.icbc.rel.hefei.util.SessionUtil;

/**
 * 
 * @author ft
 * 工资excel
 *
 */
@RequestMapping("/salaryWebUser")
@Controller
public class SalaryWebUserController {
	@Autowired
	private SalaryWebUserService salaryWebUserService;
	
	@Autowired
	private SalaryUserService salaryUserService;
	
	/**
	 * 跳转工资条登录页面
	 * @return
	 */
	@RequestMapping("/jumpLogin")
	public String jumpSalary(HttpServletRequest request)
	{
		String openId = (String) request.getSession().getAttribute(SessionParamConstant.SESSION_PARAM_USERKEY);
		SalaryUser user = salaryWebUserService.getUserByOpenId(openId);
		request.getSession().setAttribute("user", user);
	    return  "todo/client/login";
	}
	
	/**
	 * 工资条查询页
	 * @return
	 */
	@RequestMapping("/query")
	public String query()
	{
	    return  "todo/client/query";
	}
	/**
	 * 工资条详情页
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
	 * 报销单查询页
	 * @return
	 */
	@RequestMapping("/queryRe")
	public String re()
	{
	    return  "todo/client/queryRe";
	}
	
	/**
	 * 报销详情页
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
	 *首页
	 * @return
	 */
	@RequestMapping("/jumpIndex")
	public String jumpIndex()
	{
	    return  "todo/client/index";
	}
	
	/**
	 * 工资条汇总页
	 * @return
	 */
	@RequestMapping("/jumpSummary")
	public String summary()
	{
	    return  "todo/client/summary";
	}
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request, String password){
//    	String IMUserId=SessionUtil.getImUserId(request.getSession());
    	String IMUserId="123";
		//拉取用户详情
		UserDetailInfo userinfo=ImUserService.FetchUserInfo(IMUserId);
		String username = userinfo.getMobileNo();
		String  companyId = salaryUserService.getCompanyIdByMobile(username);
		request.getSession().setAttribute(SessionParamConstant.SESSION_PARAM_COMPANYID, companyId);
    	if(StringUtils.isEmpty(password)) {
    		return AjaxResult.error("密码为空,请检查后重新输入");
    	}else {
    		AjaxResult ajaxResult = salaryWebUserService.login(username,password,IMUserId);
    		int code = (int) ajaxResult.get("code");
    		if(code!=0) {
    			return ajaxResult;
    		}else {
    			request.getSession().setAttribute(SessionParamConstant.SESSION_PARAM_USERKEY, IMUserId);
    			salaryWebUserService.saveUserKey(username,IMUserId);
    			request.getSession().setAttribute("user", ajaxResult.get("data"));
    			return ajaxResult;
    		}
    	}
    }
    
    /**
     * 密码修改
     */
    @RequestMapping(value="/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request,String newPassword1,String newPassword2){
    	 if(!newPassword1.equals(newPassword2)) {
    		return AjaxResult.error("新密码两次输入不一致!,请检查后重新输入");
    	}else if(PasswordCheck.checkPassword(newPassword1).equals("error")) {
    		return AjaxResult.error("请输入正确的密码格式!");
    	}else {
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
	@RequestMapping("/jumpIndex1")
	public String jumpIndex1()
	{
	    return  "todo/client/index";
	}
	/**
	 *纯首页
	 * @return
	 */
	@RequestMapping("/jumpLogin1")
	public String jumpLogin1()
	{
	    return  "todo/client/login";
	}
    
}
