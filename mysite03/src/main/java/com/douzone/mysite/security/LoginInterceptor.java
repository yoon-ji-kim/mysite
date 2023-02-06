package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {
	//UserVo authUser= new UserService().getUser(vo);
	//DI 주의!
	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		UserVo authUser= userService.getUser(vo);
		if(authUser == null) {
			//패스워드나 email 틀림
			request.setAttribute("email", vo.getEmail());
			//포워드
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response);
			return false;
		}
		//authUser null 아님, 인증 됐음
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		//메인으로
		response.sendRedirect(request.getContextPath());
		return false;
	}

}
