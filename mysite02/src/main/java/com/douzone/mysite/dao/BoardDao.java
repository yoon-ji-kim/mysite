package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	private int pageLength = 5;
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.10.123:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
	
	public Long findMaxNo() {
		Long result = 1L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select ifnull(max(g_no), 0) from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long max = rs.getLong(1);
				if(max != null) result = max;
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs !=null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "insert into board"
					+ " values (null, ?, ?, 0, now(), ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getGroupNo());
			pstmt.setLong(4, vo.getOrderNo());
			pstmt.setLong(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int totalPage() {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int totalRow= rs.getInt(1);
				result = totalRow % pageLength ==0 ? totalRow/pageLength : (totalRow/pageLength)+1;
			}
			if(result == 0) result =1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<BoardVo> findAll(int page) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int start= (page-1)*pageLength;

		try {
			conn = getConnection();
			String sql = "select a.no, a.title, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name"
						+ " from board a"
						+ " join user b"
						+ " on a.user_no = b.no"
						+ " order by g_no desc, o_no"
						+ " limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, pageLength);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getInt(3));
				vo.setRegDate(rs.getString(4));
				vo.setGroupNo(rs.getLong(5));
				vo.setOrderNo(rs.getLong(6));
				vo.setDepth(rs.getLong(7));
				vo.setUserNo(rs.getLong(8));
				vo.setName(rs.getString(9));
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void deleteByNoandUserNo(int no, Long userNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "delete from board"
					+ " where no = ? and user_no =?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			pstmt.setLong(2, userNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public BoardVo findByNo(int no) {
		BoardVo result = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select *"
					+ " from board"
					+ " where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
				result.setHit(rs.getInt(4));
				result.setRegDate(rs.getString(5));
				result.setGroupNo(rs.getLong(6));
				result.setOrderNo(rs.getLong(7));
				result.setDepth(rs.getLong(8));
				result.setUserNo(rs.getLong(9));
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs !=null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update board"
					+ " set title = ?, contents =?"
					+ " where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int findHitByNo(int no) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select hit"
					+ " from board"
					+ " where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs !=null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void updateHit(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int hit = findHitByNo(no);
		hit++;
		try {
			conn = getConnection();
			String sql = "update board"
					+ " set hit = ?"
					+ " where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hit);
			pstmt.setInt(2, no);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateOno(Long oNo, Long gNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "update board"
					+ " set o_no = o_no+1"
					+ " where g_no =?"
					+ " and o_no > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, gNo);
			pstmt.setLong(2, oNo);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<BoardVo> findByKeyword(String keyword) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select a.no, a.title, a.hit ,a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name"
					+ " from board a"
					+ " join user b"
					+ " on a.user_no = b.no"
					+ " where title like ?"
					+ " order by g_no desc, o_no"
					+ " limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, 0);
			pstmt.setInt(3, pageLength);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getInt(3));
				vo.setRegDate(rs.getString(4));
				vo.setGroupNo(rs.getLong(5));
				vo.setOrderNo(rs.getLong(6));
				vo.setDepth(rs.getLong(7));
				vo.setUserNo(rs.getLong(8));
				vo.setName(rs.getString(9));
				result.add(vo);				
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs !=null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
