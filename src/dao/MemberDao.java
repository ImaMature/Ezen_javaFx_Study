package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class MemberDao {
	
	public static void main(String[] args) {

		try {
			//1. mysql ����̺� Ȯ��
			Class.forName("com.mysql.cj.jdbc.Driver"); // forName�� ������ ����ó���� �� // ""�ȿ� �ִ°Ŵ� ȸ�縶�� �޶� �ܿ� �ʿ� X
				System.out.println("1. ����̺� �������� ����"); // print��� ����? Ȯ����
			//2. DB����
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234"); //local���� PC "DB������(�⺻root)", "DB�н�����"
				System.out.println("2. DB ���� ����");
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DB ���� ����");
		}
		
	}
	
	

}
