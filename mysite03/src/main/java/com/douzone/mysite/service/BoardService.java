package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트 수
	
	public void addContents(BoardVo vo) { //게시물 작성
	}
	
	public BoardVo getContents(Long no) {
		//view 보여주기
		return null;
	}
	
	public BoardVo getContetnts(Long no, Long userNo) {
		//수정 폼 보여줄때 보안을 위해 no와 authUser의 no and 조건 2개 넣기
		//service 단 전에는 access control넣기
		return null;
	}
	
	public void updateContents(BoardVo vo) {
		//수정
	}
	
	public void deleteContents(Long no, Long userNo) {
		//삭제, no와 authUser No 조건 두개
	}
	
	public Map<String, Object> getContentsList(int page, String keyword) {
		//조회 
		//동적 쿼리로 keyword 비었으면 검색 없이 
		//keyword 검색시 page  /board?page=2&keyword="" 따라다녀야함
		//page- limit keyword - where
		//페이지 개수 계산
		//1. view에서 게시판 리스트를 렌더링하기 위한 데이터 값 계산
		int totalCount = boardRepository.getTotalCount(keyword);
			// 페이징 계산
		int beginPage = 0;
		int prePage = 0;//있으면 ~ 없으면 -1
		int nextPage = 0;
		//어디까지 그릴지
		int endPage = 0;
		//2. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(page, keyword, LIST_SIZE);
		//3. 리스트 정보 맵에 저장
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("totalCount", totalCount);
		result.put("beginPage", beginPage);
		result.put("prePage", prePage);
		result.put("nextPage", nextPage);
		result.put("endPage", endPage);
		
		return result;
	}
}
