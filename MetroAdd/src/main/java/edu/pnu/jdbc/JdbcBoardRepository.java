package edu.pnu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;

@Repository
public class JdbcBoardRepository { //인터페이스 만들어서 하셈. 원래 JPA 상속받던 인터페이스로 가능?

	private static final String DB_URL = "jdbc:mysql://localhost:3306/metro";
	private static final String DB_USER = "metro";
	private static final String DB_PASSWORD = "abcd";

	// java 에서 쓰던 JDBC 작업이랑 스프링부트에서 쓰는거랑 다른점?

	// 좋아요 수 많은수대로 게시글 정렬후 가져오기
	public List<Board> findBystationcodeOrderByLikesCountDesc(int stationcode) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Board> boards = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String sql = "SELECT * FROM board WHERE station_code = ? ORDER BY likes_count DESC";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, stationcode);
			rs = ps.executeQuery();

			while (rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setAuthor(rs.getString("author"));
				board.setContent(rs.getString("content"));
				board.setStationcode(rs.getInt("station_code"));
				board.setLikesCount(rs.getInt("likes_count"));
				board.setImage(rs.getString("image"));
				boards.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boards;
	}

	// 작성자 기준. 로그인시 이용. 게시글 모두 찾아오기.
	public List<Board> findByAuthor(String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Board> boards = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String sql = "SELECT * FROM board WHERE author = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setAuthor(rs.getString("author"));
				board.setContent(rs.getString("content"));
				board.setStationcode(rs.getInt("station_code"));
				board.setLikesCount(rs.getInt("likes_count"));
				board.setImage(rs.getString("image"));
				boards.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boards;
	}

	// -------------------------- 위 까지가 수업때 배운방식이고, 아래는 try-with-resources
	// 구문으로--------

	// 모든 게시글을 찾아 반환 (모든걸 찾을 필요가 있나? JPA에서 막 썼던 부분인것 같음)
	public List<Board> findAll() {
		List<Board> boards = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "SELECT * FROM board";
			try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Board board = new Board();
					board.setId(rs.getInt("id"));
					board.setAuthor(rs.getString("author"));
					board.setContent(rs.getString("content"));
					board.setAuthor(rs.getString("author"));
					board.setStationcode(rs.getInt("station_code"));
					board.setLikesCount(rs.getInt("likes_count"));
					board.setImage(rs.getString("image"));
					boards.add(board);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boards;
	}

	// id 기준으로 게시글 찾기
	public Optional<Board> findById(int id) {
		Board board = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "SELECT * FROM board WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						board = new Board();
						board.setId(rs.getInt("id"));
						board.setTitle(rs.getString("title"));
						board.setContent(rs.getString("content"));
						board.setAuthor(rs.getString("author"));
						board.setStationcode(rs.getInt("station_code"));
						board.setLikesCount(rs.getInt("likes_count"));
						board.setImage(rs.getString("image"));

					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(board);
	}

	// 게시글 저장관련 부분
	public Board save(Board board) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "INSERT INTO board (title, content, author, ...) VALUES (?, ?, ?, ...)";
			try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, board.getTitle());
				ps.setString(2, board.getContent());
				ps.setString(3, board.getAuthor());
				ps.setString(4, board.getStationcode().toString());
				ps.setString(5, board.getLikesCount().toString());
				ps.setString(6, board.getImage());
				ps.executeUpdate();
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						board.setId(generatedKeys.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return board;
	}

	// 게시글 삭제버튼 작동
	public void deleteById(int id) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "DELETE FROM board WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ------------------------ 이 부분은 JDBCTemplate 라는걸 이용하면 간단하게 된단다---------

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 딱히 더 간단해 보이지는 않는것 같은데...
	public List<Board> findAllByLikes_MemberOrderByLikes_BoardId(Member member) {
		String sql = "SELECT * FROM board WHERE author = ? ORDER BY id ASC";

		return jdbcTemplate.query(sql, new RowMapper<Board>() {
			@Override
			public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setAuthor(rs.getString("author"));
				board.setContent(rs.getString("content"));
				board.setStationcode(rs.getInt("station_code"));
				board.setLikesCount(rs.getInt("likes_count"));
				board.setImage(rs.getString("image"));
				return board;
			}
		}, member.getUsername());
	}

	// 이거 아래꺼는 MyBoard 관련. 좋아요를 기준으로 게시글 찾기
//	public List<Board> findAllByLikes_MemberOrderByLikes_BoardId(Member member) {
//		List<Board> boards = new ArrayList<>();
//		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//			// 좋아요 넣어야됑
//			String sql = "SELECT * FROM board WHERE author = ? ORDER BY id ASC";
//			try (PreparedStatement ps = conn.prepareStatement(sql)) {
//				ps.setString(1, member.getUsername());
//				try (ResultSet rs = ps.executeQuery()) {
//					while (rs.next()) {
//						Board board = new Board();
//						board.setId(rs.getInt("id"));
//						board.setAuthor(rs.getString("author"));
//						board.setContent(rs.getString("content"));
//						board.setStationcode(rs.getInt("station_code"));
//						board.setLikesCount(rs.getInt("likes_count"));
//						board.setImage(rs.getString("image"));
//						boards.add(board);
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return boards;
//	}

}