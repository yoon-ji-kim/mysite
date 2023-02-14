package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAllByPageAndKeyword(int page, String keyword, int size){
		Map<String, Object> map = new HashMap<>();
		map.put("startOffset", (page-1)*size);
		map.put("size", size);
		map.put("keyword", keyword);
		return sqlSession.selectList("board.findAllByPageAndKeyword", map);
	}

	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.getTotalCount", keyword);
	}

	public BoardVo findBoardVoByNo(Long no) {
		return sqlSession.selectOne("board.findBoardVoByNo", no);
	}

	public BoardVo findBoardVoByNoandUserNo(Long no, Long userNo) {
		Map<String , Object> map = Map.of("no", no, "userNo", userNo);
		return sqlSession.selectOne("board.findBoardVoByNoandUserNo", map);
	}

	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}

	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}

	public Long getMaxGroupNo() {
		return sqlSession.selectOne("board.findMaxNo");
	}

	public void updateOrderNo(Long orderNo, Long groupNo) {
		Map<String, Long> map = Map.of("o_no", orderNo, "g_no", groupNo);
		sqlSession.update("board.updateGroupNo", map);
	}

	public void delete(Long no, Long userNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("userNo", userNo);
		sqlSession.delete("board.delete", map);
	}
}
