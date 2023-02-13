package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	GalleryRepository galleryRepository;
	
	public List<GalleryVo> getImages() {
		return galleryRepository.findAll();
	}
	
	public void removeImage(Long no) {
		//관리자일 경우만 삭제
		galleryRepository.delete(no);
	}
	
	public void addImage(GalleryVo vo) {
		galleryRepository.insert(vo);
	}
}
