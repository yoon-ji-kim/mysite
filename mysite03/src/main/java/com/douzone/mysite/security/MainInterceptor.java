package com.douzone.mysite.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

public class MainInterceptor implements HandlerInterceptor {
	@Autowired
	private SiteService siteService;
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo vo = siteService.getSite();
		servletContext.setAttribute("site", vo);
		return true;
	}

}
