package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardDao {

	//1. 필드 기본 세팅
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	
	//2. 객체
	private static BoardDao boardDao = new BoardDao();
	
	//기본 세팅
	public BoardDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234");
			System.out.println("DB연동성공");
		} catch (Exception e) {}
		System.out.println("DB연동실패");
	}
	
	//객체 반환 메소드
	public static BoardDao getBoardDao() {return boardDao;}
	
	//3. 메소드 [Create Reading Update Delete]
		//3-1) 게시물 등록 메소드
	public boolean write (Board board) {
		String sql = "insert into board(b_title, b_contents, b_write) values(?, ?, ?)";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, board.getB_title());
			preparedStatement.setString(2, board.getB_contents());
			preparedStatement.setString(3, board.getB_write());
			
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {}
		return false;
	}
		//3-2) 게시물 조회 메소드 (테이블뷰는 ObservableList만 받음)
		public ObservableList<Board> boardlist() {
			//0. 리스트 선언
			ObservableList<Board> boards = FXCollections.observableArrayList();
			
			//1. 조건 없이 모두 가져오기
			String sql = "select*from board order by b_no desc";
			try {
				preparedStatement = connection.prepareStatement(sql);
				resultSet = preparedStatement.executeQuery();
				//2. 검색된 [쿼리] 레코드의 하나씩 객체화
				while(resultSet.next()) {
					//쿼리결과 내 레코드가 없을 때 까지 반복
					Board board = new Board(resultSet.getInt(1), 
											resultSet.getString(2), 
											resultSet.getString(3), 
											resultSet.getString(4), 
											resultSet.getString(5), 
											resultSet.getInt(6));
					boards.add(board);
				}
			} catch (Exception e) {}
			return boards;
			
			
		}
	
		//3-3) 게시물 삭제 메소드
		public boolean delete (int b_no) {
			String sql = "delete from board where b_no = ?";
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, b_no);
				preparedStatement.executeUpdate();
				return true;
			} catch (Exception e) {	}
			return false;
		}
		
		//3-4) 게시물 수정 메소드
		//3-5) 게시물 개별조회 메소드
		//3-6) 게시물 조회수 증감 메소드
		public boolean viewupdate(int b_no) {
			String sql = "update board set b_view = b_view+1 where b_no=?";
			
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, b_no);
				preparedStatement.executeUpdate();
				return true;
			}catch(Exception e) {}
			return false;
		}
	
	
}
