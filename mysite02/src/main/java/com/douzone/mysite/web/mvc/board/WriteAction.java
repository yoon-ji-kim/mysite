package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		BoardVo vo = new BoardVo();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String gNo = request.getParameter("gno");
		Long oNo = Long.parseLong(request.getParameter("ono"));
		Long depth = Long.parseLong(request.getParameter("depth"));
		Long userNo = authUser.getNo();
		if(gNo == null || gNo.equals("")) {
			vo.setGroupNo(new BoardDao().findMaxNo()+1);
			vo.setDepth(depth);
			vo.setOrderNo(oNo);
		}else {
			new BoardDao().updateOno(oNo, Long.parseLong(gNo));
			vo.setGroupNo(Long.parseLong(gNo));
			vo.setDepth(depth+1);			
			vo.setOrderNo(oNo+1);
		}
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(userNo);
		new BoardDao().insert(vo);
		MvcUtil.redirect(request.getContextPath()+"/board", request, response);
	}

}
