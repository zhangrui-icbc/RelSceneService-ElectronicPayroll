package com.icbc.rel.hefei.Filter;
 
import java.io.IOException;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
 
public class XssFilter implements Filter{
	FilterConfig fc = null;
	
	public void destroy() {
		fc = null;
	}
 
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain fc) throws IOException, ServletException {
		fc.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)req), resp);
	}
 
	public void init(FilterConfig fc) throws ServletException {
		this.fc = fc;
	}
}
 
