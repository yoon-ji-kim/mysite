package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		UserVo authUser =  new UserDao().findByEmailAndPassword(vo);
		
		if(authUser == null) {
			//로그인 정보 틀림
			request.setAttribute("email", email);
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		/* login 처리 */
		HttpSession session =  request.getSession(true);   //false 없으면 만들지 않음, true 없으면 만듦
		session.setAttribute("authUser", authUser);
		MvcUtil.redirect(request.getContextPath(), request, response);
	}

}
