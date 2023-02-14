package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileuploadService fileuploadService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String mainUpdate(SiteVo vo,@RequestParam("file")MultipartFile file) {
		String url = fileuploadService.restore(file);
		//파일 업로드
		if(!file.isEmpty()) {
			vo.setProfile(url);			
		}
		//동적
		SiteVo site=  applicationContext.getBean(SiteVo.class);
		siteService.updateSite(vo);
		servletContext.setAttribute("sitevo", vo);
		
//		site.setTitle(vo.getTitle());
//		site.setProfile(vo.getProfile());
//		site.setWelcome(vo.getWelcome());
//		site.setDescription(vo.getDescription());
		//					 원본을 site에 copy
		BeanUtils.copyProperties(vo, site);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
