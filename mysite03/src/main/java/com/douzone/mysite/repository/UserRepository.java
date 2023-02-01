package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(UserVo vo) {
		sqlSession.insert("user.insert", vo);
	}

	public UserVo findByEmailAndPassword(String email, String password) {
			 // 결과맵핑					// sqlId, 파라미터맵핑
//		return sqlSession.selectOne("user.findByEmailAndPassword", vo);
		// Map으로 써보기 (예제) map<string, (string, int여기올 수 있는게 여러개라서 object)>
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}
	
	public UserVo findByNo(Long userNo) {
		return sqlSession.selectOne("user.findByNo", userNo);
	}

	public void update(UserVo vo) {
		//동적 쿼리
		sqlSession.update("update", vo);
	}
}
