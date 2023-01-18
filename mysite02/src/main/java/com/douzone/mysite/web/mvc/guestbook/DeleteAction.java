package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestbookDao;
import com.douzone.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no =Integer.parseInt(request.getParameter("no"));
		String password = request.getParameter("password");
		String result = new GuestbookDao().findPasswordByno(no);
		if(password.equals(result)){
			new GuestbookDao().deleteByNoAndPassword(no, password);
		}
		response.sendRedirect(request.getContextPath()+"/guestbook");
	}

}
