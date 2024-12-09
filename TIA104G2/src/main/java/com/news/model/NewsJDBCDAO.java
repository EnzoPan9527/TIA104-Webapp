package com.news.model;

import java.util.*;
import java.sql.*;

public class NewsJDBCDAO implements NewsDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/checkinout?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO news (news_title,description,post_time,create_time) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT news_id,news_title,description,post_time,create_time FROM news order by news_id";
	private static final String GET_ONE_STMT = 
		"SELECT news_id,news_title,description,post_time,create_time FROM news where news_id = ?";
	private static final String DELETE = 
		"DELETE FROM news where news_id = ?";
	private static final String UPDATE = 
		"UPDATE news set news_title=?, description=?, post_time=?, create_time=? where news_id = ?";

	@Override
	public void insert(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsVO.getNews_title());
			pstmt.setString(2, newsVO.getDescription());
			pstmt.setTimestamp(3, newsVO.getPost_time());
			pstmt.setTimestamp(4, newsVO.getCreate_time());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, newsVO.getNews_title());
			pstmt.setString(2, newsVO.getDescription());
			pstmt.setTimestamp(3, newsVO.getPost_time());
			pstmt.setTimestamp(4, newsVO.getCreate_time());
			pstmt.setInt(5, newsVO.getNews_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer news_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, news_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public NewsVO findByPrimaryKey(Integer news_id) {

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, news_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// newsVo 也稱為 Domain objects
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getInt("news_id"));
				newsVO.setNews_title(rs.getString("news_title"));
				newsVO.setDescription(rs.getString("description"));
				newsVO.setPost_time(rs.getTimestamp("post_time"));
				newsVO.setCreate_time(rs.getTimestamp("create_time"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// newsVO 也稱為 Domain objects
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getInt("news_id"));
				newsVO.setNews_title(rs.getString("news_title"));
				newsVO.setDescription(rs.getString("description"));
				newsVO.setPost_time(rs.getTimestamp("post_time"));
				newsVO.setCreate_time(rs.getTimestamp("create_time"));
				list.add(newsVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {

		NewsJDBCDAO dao = new NewsJDBCDAO();

		
		 //新增
		NewsVO newsVO1 = new NewsVO();
		newsVO1.setNews_title("速報");
		newsVO1.setDescription("南韓總統尹錫悅今天晚間發表緊急談話，宣布南韓實施緊急戒嚴令，並表示這是為剷除親北韓勢力，守護自由憲政體制。但依法規定若過半數國會議員要求解除，就必須立即解禁。");
		newsVO1.setPost_time(java.sql.Timestamp.valueOf("2024-12-03 23:00:00.0"));
		newsVO1.setCreate_time(java.sql.Timestamp.valueOf("2024-12-03 22:58:00.0"));
		dao.insert(newsVO1);

		// 修改
		NewsVO newsVO2 = new NewsVO();
		newsVO2.setNews_id(6);
		newsVO2.setNews_title("速報2");
		newsVO2.setDescription("南韓總統尹錫悅今天晚間發表緊急談話，宣布南韓實施緊急戒嚴令，並表示這是為剷除親北韓勢力，守護自由憲政體制。但依法規定若過半數國會議員要求解除，就必須立即解禁。2");
		newsVO2.setPost_time(java.sql.Timestamp.valueOf("2024-12-03 23:01:00.0"));
		newsVO2.setCreate_time(java.sql.Timestamp.valueOf("2024-12-03 22:59:00.0"));
		dao.update(newsVO2);

		// 刪除
		dao.delete(6);

		// 查詢
		NewsVO newsVO3 = dao.findByPrimaryKey(2);
		System.out.print(newsVO3.getNews_id() + ",");
		System.out.print(newsVO3.getNews_title() + ",");
		System.out.print(newsVO3.getDescription() + ",");
		System.out.print(newsVO3.getCreate_time() + ",");
		System.out.print(newsVO3.getPost_time() + ",");
		System.out.println("---------------------");

		// 查詢
		List<NewsVO> list = dao.getAll();
		for (NewsVO aNews : list) {
			System.out.print(aNews.getNews_id() + ",");
			System.out.print(aNews.getNews_title() + ",");
			System.out.print(aNews.getDescription() + ",");
			System.out.print(aNews.getPost_time() + ",");
			System.out.print(aNews.getCreate_time() + ",");
			System.out.println();
		}
	}
}