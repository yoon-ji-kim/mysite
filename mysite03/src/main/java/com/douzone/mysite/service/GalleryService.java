package com.douzone.mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	public List<GalleryVo> getImages() {
		return null;
	}
	
	public void removeImage(Long no) {
		//관리자일 경우만 삭제
	}
	
	public void addImage(GalleryVo vo) {
	}
}
