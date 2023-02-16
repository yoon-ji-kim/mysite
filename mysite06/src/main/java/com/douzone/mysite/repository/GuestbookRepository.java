package com.douzone.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;

	public void insert(GuestbookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
	}

	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public String findPasswordByno(Long no) {
		return sqlSession.selectOne("guestbook.findPasswordByNo", no);
	}

	public void deleteByNoAndPassword(Long no, String password) {
		Map<String, Object> map = Map.of("no", no, "password", password);
//		Map<String, Object> map = new HashMap<>();
//		map.put("no", no);
//		map.put("password", password);
		sqlSession.delete("guestbook.deleteByNoAndPassword", map);
	}
}
