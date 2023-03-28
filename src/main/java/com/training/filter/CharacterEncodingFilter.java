package com.training.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CharacterEncodingFilter implements Filter {

	private Map<String, String> contentType;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		contentType = new HashMap<>();
		contentType.put(".jpg", "image/jpeg");
		contentType.put(".js", "application/javascript");
		contentType.put(".css", "text/css");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String lastUriExtension = getLastFileExtension(request.getServletPath());
		servletRequest.setCharacterEncoding("UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		if (contentType.containsKey(lastUriExtension)) {
			String mime = contentType.get(lastUriExtension);
			servletResponse.setContentType(mime);
		} else {
			servletResponse.setContentType("text/html");
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		contentType.clear();
	}

	private String getLastFileExtension(String uri) {
		int start = uri.lastIndexOf(".");
		return uri.substring(start);
	}
}
