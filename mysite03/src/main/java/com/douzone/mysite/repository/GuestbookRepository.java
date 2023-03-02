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
		return sqlSession.selectList("guestbook.findAllList");
	}
	public List<GuestbookVo> findAll(Long sno) {
		return sqlSession.selectList("guestbook.findAll", sno);
	}

	public String findPasswordByno(Long no) {
		return sqlSession.selectOne("guestbook.findPasswordByNo", no);
	}

	public int deleteByNoAndPassword(Long no, String password) {
		Map<String, Object> map = Map.of("no", no, "password", password);
		sqlSession.delete("guestbook.deleteByNoAndPassword", map);
		return 1;
	}
}
