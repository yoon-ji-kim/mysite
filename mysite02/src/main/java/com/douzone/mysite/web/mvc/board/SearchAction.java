package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class SearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		BoardVo vo = new BoardDao().findByNo(no);
		request.setAttribute("vo", vo);
		cookie(request,response,no);
		MvcUtil.forward("board/view", request, response);
	}

	private void cookie(HttpServletRequest request, HttpServletResponse response,int no) {
		boolean isCookie=false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			//쿠키가 있다면
			for(Cookie cookie : cookies) {
				if ("visitCount".equals(cookie.getName())) {
					isCookie=true;
				}
			}
		}
		if(isCookie) {
			Cookie cookie = new Cookie("visitCount", String.valueOf(no));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24 * 60 * 60);  //1day
			response.addCookie(cookie);
			
			new BoardDao().updateHit(no);		
		}
	}

}
