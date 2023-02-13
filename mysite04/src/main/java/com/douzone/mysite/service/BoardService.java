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
	
	public void addContents(BoardVo vo) { 
		if(vo.getGroupNo() == null) { //게시물 작성
			Long max = boardRepository.getMaxGroupNo();
			vo.setGroupNo(max+1);			
		}else {	//댓글 작성
			vo.setDepth(vo.getDepth()+1);
			boardRepository.updateOrderNo(vo.getOrderNo(),vo.getGroupNo());
			vo.setOrderNo(vo.getOrderNo()+1);
		}
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
		//view 보여주기
		return boardRepository.findBoardVoByNo(no);
	}
	
	public BoardVo getContetnts(Long no, Long userNo) {
		//수정 폼 보여줄때 보안을 위해 no와 authUser의 no and 조건 2개 넣기
		return boardRepository.findBoardVoByNoandUserNo(no, userNo);
	}
	
	public void updateContents(BoardVo vo) {
		//수정
		boardRepository.update(vo);
	}
	
	public void deleteContents(Long no, Long userNo) {
		//삭제, no와 authUser No 조건 두개
		boardRepository.delete(no, userNo);
	}
	
	public Map<String, Object> getContentsList(int page, String keyword) {
		//조회 
		//keyword 검색시 page  /board?page=2&keyword="" 따라다녀야함
		//1. view에서 게시판 리스트를 렌더링하기 위한 데이터 값 계산
		int totalCount = boardRepository.getTotalCount(keyword);
		// 페이징 계산
		int totalPage = totalCount% LIST_SIZE ==0 ? totalCount/LIST_SIZE : (totalCount/LIST_SIZE)+1;
		int curBlock = ((page-1)/PAGE_SIZE)+1;
		int beginPage = (curBlock * PAGE_SIZE)-4;
		int prePage = page-1 <1 ? -1 : page-1;//있으면 ~ 없으면 -1
		//어디까지 그릴지
		int endPage = curBlock * PAGE_SIZE > totalPage ? totalPage : curBlock * PAGE_SIZE;
		int nextPage = page+1 > totalPage? -1 : page+1;
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
		result.put("keyword", keyword);
		result.put("curPage", page);
		return result;
	}
}
