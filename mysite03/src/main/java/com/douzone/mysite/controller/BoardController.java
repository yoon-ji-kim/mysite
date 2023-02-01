package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/")
	public String index(Model model) {
															//page, keyword
		Map<String, Object> map = boardService.getContentsList(1, "");
		model.addAttribute("map", map);
//		model.addAllAttributes(map); //안에서 map을 풀어서 jsp에서는 map.list 말고 list로 접근가능
		return "board/index";
	}
}
