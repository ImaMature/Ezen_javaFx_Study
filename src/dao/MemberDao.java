package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Member;

public class MemberDao {
	
	// JDBC �ֿ� �������̽�, Ŭ����
		//1. Connection : DB ���� �������̽� [ DriverManager Ŭ���� ]
	
	//1. �ʵ� 
	private Connection connection;// ���� �������̽� ����
		//����Ŭ���� �� ��ü �����
	private PreparedStatement preparedStatement; // sql ���� �������̽� ����
	private ResultSet resultSet; // ����(�˻� ���) ���� �������̽� ����
	
	
	private static MemberDao memberDao = new MemberDao(); // 1.�����ڰ� ����Ǹ� MemberDao �޼ҵ� ����
	//2. ������
	public MemberDao() { //2.ȣ��޾Ƽ� memberDao�� ����
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("DB ���� ���� : " + e);
			}
		}
	//3. �޼ҵ�
	public static MemberDao getMemberDao() {return memberDao;} //3.����� memberDao ȣ��
		
		
		//��ɸ޼ҵ� �޼ҵ���� ����� �״��� ��Ʈ�ѷ��� ����
		//1.ȸ�� ���� �޼ҵ�
		public boolean signup (Member member) { // 1-1)���� : �μ��޾ƿͼ�
			//1)SQL �ۼ� [ SQL : DB ���۾� DML ] // DML�� ���۾� insert : ����
												//Ư���ʵ� : insert into ���̺�� (�ʵ��1, �ʵ��2, �ʵ��3) values(��1, ��2, ��3)
												//����ʵ� : insert into ���̺�� values(��1, ��2, ��3)
			String sql ="insert into member(m_id, m_password, m_name, m_email, m_point)"
					+ "values(?, ?, ?, ?, ?)";//1-3)���⿡ ���� (?: ���ϵ�ī��,�Ű����� ���� �� ����)
			
		try {	
			//2)SQL -> DB ���� [ PreparedStatement �������̽� : ����� DB�� SQL ���� ] connection�� ���ؼ� preparedStatement�� ����
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			//3.SQL ���� [ ? �� ������ �ֱ� ]
			preparedStatement.setString(1, member.getM_id()); // ù��° ? �� ������ �ֱ�
			preparedStatement.setString(2, member.getM_password());	//1-2)������ ������ ���⼭ ��ƿͼ�
			preparedStatement.setString(3, member.getM_name()); // 3��° ? �� �����ͳֱ�
			preparedStatement.setString(4, member.getM_email());
			preparedStatement.setInt(5, member.getM_point());
			
			//4.SQL ����
			preparedStatement.executeUpdate();
			
			//5.SQL ���
			return true; // DB ���� ���� �� true ��ȯ

		}
		catch(Exception e) {
			return false;
		}
		
		}
		//2.�α��� �޼ҵ�
		public boolean login(String id, String password) {
			
			//1. SQL �ۼ�
			String sql ="select*from member where m_id=? and m_password=?";
					
					//select : �˻�
					//Ư���ʵ� : select �ʵ�� from ���̺�� where ����
					//����ʵ� : select*from
					//� ���̺��� � �������� � �ʵ带 �����ð�����.
			
			//2. SQL -> DB ���� [������ ���� �߻� ]
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				
			//3. SQL ���� -> [ ?�� ���� �޼ҵ�� ���� �μ��� ���� ]
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, password);
				
			//4. SQL ���� [ Query : ���� (�˻� ���)
				ResultSet resultSet = preparedStatement.executeQuery();
				// �˻���� �����°� -> Resultset
				
			//5. SQL ��� [ �˻���� ���� ]
				if(resultSet.next()) { // ���� ����� ���� ������ ������ true ������ false
					return true; // ����� ���������� �־ �α��� ����
				}else {
					return false; // �α��� ����
				}
			} catch (Exception e) {
			
			}
			return false; // DB ����
			}
		//3.���̵�ã�� �޼ҵ�
		public String findid(String name, String email) {
			
			//1. SQL �ۼ�
			String sql = "select m_id from member where m_name=? and m_email=?";
			//2. SQL -> DB����
			try {
				preparedStatement = connection.prepareStatement(sql);
				//3. SQL ����
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, email);
				//4. SQL ����
				resultSet = preparedStatement.executeQuery();
				//5. SQL ���
				if(resultSet.next()) { // next ������ ã�°�
					//�˻������ ������
					return resultSet.getString(1);
				}else {
					return null; // �˻������ ������ null ��ȯ
				}
			} catch (Exception e) {
				return null; // DB ����
			}
		}
		
		//4.�н����� ã�� �޼ҵ�
		public String findpassword(String id, String email) {
			//1. sql �ۼ�
			String sql = "select m_password from member where m_id=? and m_email=?";
			//2. DB����
			try {
				preparedStatement = connection.prepareStatement(sql);
				//3. SQL����
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, email);
				//4. query
				resultSet = preparedStatement.executeQuery();
				//5. ���
				if(resultSet.next()) {
					return resultSet.getString(1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
			return null;
			
		}
		//5.ȸ������ �޼ҵ�
		//6.ȸ��Ż�� �޼ҵ�
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//DB ���� �׽�Ʈ
	/*
	public static void main(String[] args) {

		try {
			//1. mysql ����̺� Ȯ��
			Class.forName("com.mysql.cj.jdbc.Driver"); // forName�� ������ ����ó���� �� // ""�ȿ� �ִ°Ŵ� ȸ�縶�� �޶� �ܿ� �ʿ� X
				System.out.println("1. ����̺� �������� ����"); // print��� ����? Ȯ����
			//2. DB����
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234"); //URL : local���� PC "DB������(�⺻root)", "DB�н�����"
				System.out.println("2. DB ���� ����");
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DB ���� ����");
		}
		
	}
	
	*/

}
