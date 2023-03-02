package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList() {
		//방명록 첫 페이지
		return guestbookRepository.findAll();
	}
	public List<GuestbookVo> getMessageList(Long sno) {
		//방명록 첫 페이지
		return guestbookRepository.findAll(sno);
	}
	
	public int deleteMessage(Long no, String password) {
		//방명록 삭제
		String pwd = guestbookRepository.findPasswordByno(no);
		if(password.equals(pwd)) {
			guestbookRepository.deleteByNoAndPassword(no, password);
			return 1;
		}
		return -1;
	}
	
	public void addMessage(GuestbookVo vo) {
		//방명록 추가
		guestbookRepository.insert(vo);
	}
}
