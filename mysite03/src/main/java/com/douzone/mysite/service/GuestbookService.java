package com.douzone.mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	public List<GuestbookVo> getMessageList() {
		//방명록 첫 페이지
		return null;
	}
	
	public void deleteMessage(Long no, String password) {
		//방명록 삭제
	}
	
	public void addMessage(GuestbookVo vo) {
		//방명록 추가
	}
}
