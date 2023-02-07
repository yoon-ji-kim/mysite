package com.douzone.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String index(Model model) {
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		//spring-servlet.xml에 string 처리하는 messageConverter 설정되어있음 
		return "HelloWorld";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02(String name) {
		//한글안되서 명시적으로 messageConverter 설정해주기
		//관례상 되어 있지 않을 것을 설정을 통해 바꾸기
		return "안녕하세요" +name;
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		//json 설정
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		
		return vo;
	}
}
