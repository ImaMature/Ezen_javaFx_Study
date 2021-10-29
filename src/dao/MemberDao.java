package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class MemberDao {
	
	public static void main(String[] args) {

		try {
			//1. mysql 드라이브 확인
			Class.forName("com.mysql.cj.jdbc.Driver"); // forName이 무조건 예외처리가 뜸 // ""안에 있는거는 회사마다 달라서 외울 필요 X
				System.out.println("1. 드라이브 가져오기 성공"); // print찍는 이유? 확인차
			//2. DB연동
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234"); //local현재 PC "DB계정명(기본root)", "DB패스워드"
				System.out.println("2. DB 연동 성공");
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DB 연동 실패");
		}
		
	}
	
	

}
