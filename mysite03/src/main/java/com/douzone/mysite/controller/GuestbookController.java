package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping("/add")
	public String add(GuestbookVo vo) {
		System.out.println(vo);
		guestbookService.addMessage(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") int no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delete(GuestbookVo vo) {
		guestbookService.deleteMessage(vo.getNo(), vo.getPassword());
		return "redirect:/guestbook";
	}
	
	@RequestMapping("/spa")
	public String indexSpa() {
		System.out.println("spa...............");
		return "guestbook/index-spa";
	}
}
