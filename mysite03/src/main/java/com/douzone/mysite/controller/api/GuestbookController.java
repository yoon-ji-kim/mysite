package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@RestController("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping("")
	public JsonResult list(@RequestParam Long sno) {
		List<GuestbookVo> list = guestbookService.getMessageList(sno);
		return JsonResult.success(list);
	}
	
	@PostMapping("")
	public JsonResult add(@RequestBody GuestbookVo vo) {
		guestbookService.addMessage(vo);
		System.out.println(vo);
		return JsonResult.success(vo);
	}
	
	@DeleteMapping("{no}/{password}")
	public JsonResult delete(@PathVariable Long no, @PathVariable String password) {
		int result = guestbookService.deleteMessage(no, password);
		return JsonResult.success(result);
	}
}
