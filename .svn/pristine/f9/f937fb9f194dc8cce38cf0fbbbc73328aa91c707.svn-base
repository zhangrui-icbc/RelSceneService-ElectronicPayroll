package com.icbc.rel.hefei.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.icbc.rel.hefei.controller.addSenceneController;

public class ICBCSM4UniqueId extends HttpServlet {
	private static final Logger logger = Logger.getLogger(ICBCSM4UniqueId.class);
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		String uniqueId = String.valueOf(System.nanoTime()).substring(0, 10);
		arg0.getSession().setAttribute("UNIQUE_ID", uniqueId);
		logger.info("±£¥ÊUNIQUE_ID:"+uniqueId);
		arg1.getWriter().write(uniqueId);
	}

}
