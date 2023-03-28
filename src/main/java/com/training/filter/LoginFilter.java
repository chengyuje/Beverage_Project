package com.training.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.training.model.BeverageMember;

public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession();
		BeverageMember beverageMember = (BeverageMember) session.getAttribute("beverageMember");

		if (beverageMember != null) {
			chain.doFilter(servletRequest, servletResponse);
		} else {
			String requestURI = httpRequest.getRequestURI();
			String action = httpRequest.getParameter("action");
			if (requestURI.endsWith("LoginDispatchAction.do") && "login".equals(action)) {
				chain.doFilter(servletRequest, servletResponse);
			} else if (requestURI.endsWith("FrontendDispatchAction.do")
						&& ("eachPageGoods".equals(action) || "searchGoods".equals(action))) {
				chain.doFilter(servletRequest, servletResponse);
			} else if (requestURI.endsWith("MemberDispatchAction.do")) {
				chain.doFilter(servletRequest, servletResponse);
			} else {
				httpResponse.sendRedirect("BeverageMemberLogin.jsp");
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
