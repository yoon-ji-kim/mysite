package com.douzone.mysite.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(@RequestParam(value="page", defaultValue = "1", required = false)int page, @RequestParam(value="keyword", required = false) String keyword ,Model model) {
															//page, keyword
		System.out.println("page"+page+", keyword"+keyword);
		Map<String, Object> map = boardService.getContentsList(page, keyword);
//		model.addAttribute("map", map);
		model.addAllAttributes(map); //안에서 map을 풀어서 jsp에서는 map.list 말고 list로 접근가능
		return "board/index";
	}
	
	@RequestMapping("search")
	public String search(@RequestParam(value = "no")Long no, Model model) {
		BoardVo vo =boardService.getContents(no);
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, @RequestParam(value="no") Long no, Model model) {
		BoardVo vo = boardService.getContetnts(no, authUser.getNo());
		model.addAttribute("vo", vo);
		return "board/update";
	}
	
	@Auth
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser,BoardVo vo,@RequestParam(value="no") Long no, Model model) {
		boardService.updateContents(vo);
		model.addAttribute("vo", vo);
		return "redirect:/board/search?no="+no;
	}
	
	@Auth
	@RequestMapping(value = "/writeform", method = RequestMethod.POST)
	public String replywrite(@ModelAttribute("boardVo")BoardVo vo, Model model) {
		return "board/write";
	}
	
//	@Auth(role="ADMIN")
	@Auth
	@RequestMapping(value= "/write", method = RequestMethod.GET)
	public String write(@ModelAttribute("boardVo") BoardVo vo) {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser,@ModelAttribute @Valid BoardVo vo, BindingResult result, Model model) {		
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "board/write";
		}
		vo.setUserNo(authUser.getNo());
		boardService.addContents(vo);
		return "redirect:/board?page=1&keyword";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser,@PathVariable("no")Long no) {

		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board?page=1&keyword";
	}
}
