package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Board;
import domain.Reply;
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
		} catch (Exception e) {System.out.println("DB연동실패");}
		
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
			String sql = "select*from board order by b_no asc"; // 오름차순 order by 컬럼명 asc // 내림차순은 desc
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
				return boards;
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
		public boolean update(int b_no, String b_title, String b_contents) { 
			// 여기 메소드 인수의 인덱스 순서는 업데이트의 boardao.getboarddao().update(해당 메소드 호출자)와 같아야함
			String sql = "update board set b_title = ?, b_contents = ? where b_no=?";
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, b_title);
				preparedStatement.setString(2, b_contents);
				preparedStatement.setInt(3, b_no);
				preparedStatement.executeUpdate();
				return true;
			} catch (Exception e) {System.out.println(e.getMessage());}
				return false;
		}
		
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
	
	
		//Reply에 관련된 Dao도 여기에 씀
		//3-7) 댓글 등록 메소드
		public boolean replywrite(Reply reply) {
			String sql = "insert into reply(r_contents, r_write, b_no) values(?,?,?)"; 
			try{
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, reply.getR_contents());
				preparedStatement.setString(2, reply.getR_write());
				preparedStatement.setInt(3, reply.getB_no());
				preparedStatement.executeUpdate();
				return true;
			}catch(Exception e) {}
			return false;
			
		}
		
		//3-8) 댓글 출력 메소드 (테이블뷰가 ObservableList밖에 못받아서 이거 씀)
		public ObservableList<Reply> replylist (int b_no){
			ObservableList<Reply> replys = FXCollections.observableArrayList();
			
			String sql = "select * from reply where b_no=? order by r_no desc"; //reply테이블에 b_no에
				try {
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, b_no);
					resultSet = preparedStatement.executeQuery();
					
					while(resultSet.next()) { // 다음 레코드가 없을 때까지 (한줄씩)
						Reply reply = new Reply(resultSet.getInt(1), // 
								resultSet.getString(2), 
								resultSet.getString(3), 
								resultSet.getString(4), 
								resultSet.getInt(5));
						replys.add(reply);
						}
					return replys;
				}catch (Exception e) {}
				return replys;
		}
		
		//3-9) 로그인된 회원의 게시물 출력
		
				public ObservableList<Board> myboardlist(String id) {
					//0. 리스트 선언
					ObservableList<Board> boards = FXCollections.observableArrayList();
					
					//1. 조건 없이 모두 가져오기 
													//b_write board테이블의 회원 이름
					String sql = "select*from board where b_write =? order by b_no asc"; // 오름차순 order by 컬럼명 asc // 내림차순은 desc
					try {								//전달받은 id값과 b_write값이 같다면
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, id);
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
						return boards;
					} catch (Exception e) {}
					return boards;
					
					
				}
}
