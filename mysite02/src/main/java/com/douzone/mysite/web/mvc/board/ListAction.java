package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String p = request.getParameter("page");
		int page = 1;
		if(p != null) {
			page = Integer.parseInt(p);
		}
		List<BoardVo> list = new BoardDao().findAll(page);
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		int totalPage = new BoardDao().totalPage();
		request.setAttribute("totalPage", totalPage);
		MvcUtil.forward("board/list", request, response);
	}

}
