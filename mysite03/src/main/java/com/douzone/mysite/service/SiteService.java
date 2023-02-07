package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService {
	@Autowired
	private SiteRepository siteRepository;

	public SiteVo getSite() {
		return siteRepository.find();
	}
	
	public SiteVo updateSite(SiteVo vo) {
		SiteVo result = new SiteVo();
//		siteRepository.update(vo);
		return result;
	}
}
