package com.douzone.mysite.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String join(@ModelAttribute("userVo")UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(@ModelAttribute@Valid UserVo vo, BindingResult result,Model model) {
									//validation 값 리턴해줌
		if(result.hasErrors()) {
			//validation에 error가 있다면
//			List<ObjectError> list = result.getAllErrors(); //에러 가져오기
//			for (ObjectError error : list) {
//				System.out.println(error);
//			}
//			model.addAttribute("userVo", vo);  --> @ModelAttribute로 대체
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(HttpSession session ,UserVo vo, Model model) {
		UserVo authUser= userService.getUser(vo);
		if(authUser == null) {
			//패스워드나 email 틀림
			model.addAttribute("email", vo.getEmail());
			return "user/login";
		}
		session.setAttribute("authUser", authUser);
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	
	@Auth //interceptor
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
						//argument resolver
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		// ---------------------------------------
		
		UserVo userVo = userService.getUser(authUser.getNo());
		
		model.addAttribute("userVo", userVo);
		return "user/update";
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(HttpSession session, UserVo vo) {
		// AccessControl
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		// ---------------------------------------
		
		vo.setNo(authUser.getNo());
		userService.updateUser(vo);
		authUser.setName(vo.getName());
		return "redirect:/user/update";
	}
}
