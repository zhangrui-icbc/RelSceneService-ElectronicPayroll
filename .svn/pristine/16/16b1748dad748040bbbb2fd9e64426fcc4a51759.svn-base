package com.icbc.rel.hefei.util;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/*
 * 记录session的相关参数
 */
public class SessionUtil {
	private static final Logger logger = Logger.getLogger(SessionUtil.class);

	private static final String adminId = "adminId";// 平台管理员id
	private static final String adminName = "adminName";// 平台管理员名称
	private static final String loginType = "loginType";// 1-平台管理员 2-公众号管理员
	private static final String mpId = "mpId";// 公众号id
	private static final String mpName = "mpName";// 公众号名称
	private static final String imUserId = "imUserId";// 粉丝id
	private static final String customerType = "customerType";// 用户实名标志
	private static final String nickname = "nickname";// 用户昵称
	private static final String mobileno = "mobileno";// 用户手机号

	/*
	 * 保存平台管理员用户名和id至session
	 */
	public static boolean setAdminSession(HttpSession session, String id, String name) {
		try {
			session.setAttribute(adminId, id);
			session.setAttribute(adminName, name);
			session.setAttribute(loginType, 1);
		} catch (Exception e) {
			logger.error("登录系统后保存用户id至session失败", e);
			return false;
		}
		return true;
	}

	/*
	 * 保存公众号管理员用户名和id至session
	 */
	public static boolean setMpSession(HttpSession session, String id, String name) {
		try {
			session.setAttribute(mpId, id);
			session.setAttribute(mpName, name);
			session.setAttribute(loginType, 2);
		} catch (Exception e) {
			logger.error("跳转系统后保存用户id至session失败", e);
			return false;
		}
		return true;
	}

	/*
	 * 获取session中的AdminId
	 */
	public static String getAdminId(HttpSession session) {
		String id = "";
		try {
			id = (String) session.getAttribute(adminId);
		} catch (Exception ex) {
			logger.error("session获取id报错", ex);
			id = "";
		}
		return id;
	}

	/*
	 * 获取session中的AdminName
	 */
	public static String getAdminName(HttpSession session) {
		String name = "";
		try {
			name = (String) session.getAttribute(adminName);
		} catch (Exception ex) {
			logger.error("session获取name报错", ex);
			name = "";
		}
		return name;
	}

	/*
	 * 获取session中的mpId
	 */
	public static String getMpId(HttpSession session) {
		String id = "";
		try {
			id = (String) session.getAttribute(mpId);
		} catch (Exception ex) {
			logger.error("session获取id报错", ex);
			id = "";
		}
		return id;
	}

	/*
	 * 获取session中的mpName
	 */
	public static String getMpName(HttpSession session) {
		String name = "";
		try {
			name = (String) session.getAttribute(mpName);
		} catch (Exception ex) {
			logger.error("session获取name报错", ex);
			name = "";
		}
		return name;
	}

	/*
	 * 保存ImUserId
	 */
	public static boolean setImUserId(HttpSession session, String ImUserId) {
		try {
			session.setAttribute(imUserId, ImUserId);
		} catch (Exception e) {
			logger.error("保存ImUserId至session失败", e);
			return false;
		}
		return true;
	}

	/*
	 * 读取ImUserId
	 */
	public static String getImUserId(HttpSession session) {

		String relid = "123";
		try {
			relid = (String) session.getAttribute(imUserId);
		} catch (Exception ex) {
			logger.error("session获取name报错", ex);

		}
		if (relid == null) {
			relid = "123";
		}
		return relid;
	}

	/*
	 * 保存用户实名标志、昵称、手机号
	 */
	public static boolean setUserInfo(HttpSession session, String customertype, String nickName, String mobileNo) {
		try {
			session.setAttribute(customerType, customertype);
			session.setAttribute(nickname, nickName);
			session.setAttribute(mobileno, mobileNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("保存用户信息至session失败", e);
			return false;
		}
		return true;
	}

	/*
	 * 获取session中的用户实名标志
	 */
	public static String getCustomerType(HttpSession session) {
		String customertype = "";
		try {
			customertype = (String) session.getAttribute(customerType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("session获取用户实名标志报错", e);
			customertype = "";
		}
		return customertype;
	}

	/*
	 * 获取session中的用户昵称
	 */
	public static String getNickName(HttpSession session) {
		String nickName = "";
		try {
			nickName = (String) session.getAttribute(nickname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("session获取用户实名标志报错", e);
			nickName = "";
		}
		return nickName;
	}

	/*
	 * 获取session中的用户手机号
	 */
	public static String getMobileNo(HttpSession session) {
		String mobileNo = "";
		try {
			mobileNo = (String) session.getAttribute(mobileno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("session获取用户实名标志报错", e);
			mobileNo = "";
		}
		return mobileNo;
	}
}
