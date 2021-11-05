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

	//1. �ʵ� �⺻ ����
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	
	//2. ��ü
	private static BoardDao boardDao = new BoardDao();
	
	//�⺻ ����
	public BoardDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234");
			System.out.println("DB��������");
		} catch (Exception e) {System.out.println("DB��������");}
		
	}
	
	//��ü ��ȯ �޼ҵ�
	public static BoardDao getBoardDao() {return boardDao;}
	
	//3. �޼ҵ� [Create Reading Update Delete]
		//3-1) �Խù� ��� �޼ҵ�
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
		//3-2) �Խù� ��ȸ �޼ҵ� (���̺��� ObservableList�� ����)
		public ObservableList<Board> boardlist() {
			//0. ����Ʈ ����
			ObservableList<Board> boards = FXCollections.observableArrayList();
			
			//1. ���� ���� ��� ��������
			String sql = "select*from board order by b_no asc"; // �������� order by �÷��� asc // ���������� desc
			try {
				preparedStatement = connection.prepareStatement(sql);
				resultSet = preparedStatement.executeQuery();
				//2. �˻��� [����] ���ڵ��� �ϳ��� ��üȭ
				while(resultSet.next()) {
					//������� �� ���ڵ尡 ���� �� ���� �ݺ�
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
	
		//3-3) �Խù� ���� �޼ҵ�
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
		
		//3-4) �Խù� ���� �޼ҵ�
		public boolean update(int b_no, String b_title, String b_contents) { 
			// ���� �޼ҵ� �μ��� �ε��� ������ ������Ʈ�� boardao.getboarddao().update(�ش� �޼ҵ� ȣ����)�� ���ƾ���
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
		
		//3-5) �Խù� ������ȸ �޼ҵ�
		//3-6) �Խù� ��ȸ�� ���� �޼ҵ�
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
	
	
		//Reply�� ���õ� Dao�� ���⿡ ��
		//3-7) ��� ��� �޼ҵ�
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
		
		//3-8) ��� ��� �޼ҵ� (���̺�䰡 ObservableList�ۿ� ���޾Ƽ� �̰� ��)
		public ObservableList<Reply> replylist (int b_no){
			ObservableList<Reply> replys = FXCollections.observableArrayList();
			
			String sql = "select * from reply where b_no=? order by r_no desc"; //reply���̺� b_no��
				try {
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, b_no);
					resultSet = preparedStatement.executeQuery();
					
					while(resultSet.next()) { // ���� ���ڵ尡 ���� ������ (���پ�)
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
		
		//3-9) �α��ε� ȸ���� �Խù� ���
		
				public ObservableList<Board> myboardlist(String id) {
					//0. ����Ʈ ����
					ObservableList<Board> boards = FXCollections.observableArrayList();
					
					//1. ���� ���� ��� �������� 
													//b_write board���̺��� ȸ�� �̸�
					String sql = "select*from board where b_write =? order by b_no asc"; // �������� order by �÷��� asc // ���������� desc
					try {								//���޹��� id���� b_write���� ���ٸ�
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, id);
						resultSet = preparedStatement.executeQuery();
						//2. �˻��� [����] ���ڵ��� �ϳ��� ��üȭ
						while(resultSet.next()) {
							//������� �� ���ڵ尡 ���� �� ���� �ݺ�
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
