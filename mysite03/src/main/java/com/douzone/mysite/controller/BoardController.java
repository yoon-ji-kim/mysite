package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="",  method = RequestMethod.GET)
	public String index(@RequestParam(value="page")int page, @RequestParam(value="keyword") String keyword ,Model model) {
															//page, keyword
		Map<String, Object> map = boardService.getContentsList(page, keyword);
//		model.addAttribute("map", map);
		model.addAllAttributes(map); //안에서 map을 풀어서 jsp에서는 map.list 말고 list로 접근가능
		return "board/list";
	}
	
	@RequestMapping(value="",  method = RequestMethod.POST)
	public String keyword(@RequestParam(value="page")int page, @RequestParam(value="kwd") String keyword ,Model model) {
															//page, keyword
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		model.addAllAttributes(map); //안에서 map을 풀어서 jsp에서는 map.list 말고 list로 접근가능
		return "board/list";
	}
	
	@RequestMapping("search")
	public String search(@RequestParam(value = "no")Long no, Model model) {
		BoardVo vo =boardService.getContents(no);
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update(HttpSession session,@RequestParam(value="no") Long no, Model model) {
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		BoardVo vo = boardService.getContetnts(no, authUser.getNo());
		model.addAttribute("vo", vo);
		return "board/update";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpSession session,BoardVo vo,@RequestParam(value="no") Long no, Model model) {
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		boardService.updateContents(vo);
		model.addAttribute("vo", vo);
		return "redirect:/board/search?no="+no;
	}
	
	@RequestMapping(value = "writeform", method = RequestMethod.POST)
	public String replywrite(BoardVo vo, Model model) {
		model.addAttribute("vo",vo);
		return "board/write";
	}
	
	@RequestMapping(value= "write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String write(HttpSession session,BoardVo vo) {		
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		vo.setUserNo(authUser.getNo());
		boardService.addContents(vo);
		return "redirect:/board?page=1&keyword";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session,@PathVariable("no")Long no) {
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board?page=1&keyword";
	}
}
