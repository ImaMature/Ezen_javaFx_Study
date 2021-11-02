package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Member;

public class MemberDao {
	
	// JDBC 주요 인터페이스, 클래스
		//1. Connection : DB 연결 인터페이스 [ DriverManager 클래스 ]
	
	//1. 필드 
	private Connection connection;// 연결 인터페이스 선언
		//현재클래스 내 객체 만들기
	private PreparedStatement preparedStatement; // sql 연결 인터페이스 선언
	private ResultSet resultSet; // 쿼리(검색 결과) 연결 인터페이스 선언
	
	
	private static MemberDao memberDao = new MemberDao(); // 1.생성자가 실행되며 MemberDao 메소드 실행
	//2. 생성자
	public MemberDao() { //2.호출받아서 memberDao에 저장
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("DB 연동 실패 : " + e);
			}
		}
	//3. 메소드 객체화
	public static MemberDao getMemberDao() {return memberDao;} //3.저장된 memberDao 호출
		
		
		//기능메소드 메소드부터 만들고 그다음 컨트롤러에 기입
		//1.회원 가입 메소드
		public boolean signup (Member member) { // 1-1)순서 : 인수받아와서
			//1)SQL 작성 [ SQL : DB 조작어 DML ] // DML은 조작어 insert : 삽입
												//특정필드 : insert into 테이블명 (필드명1, 필드명2, 필드명3) values(값1, 값2, 값3)
												//모든필드 : insert into 테이블명 values(값1, 값2, 값3)
			String sql ="insert into member(m_id, m_password, m_name, m_email, m_point)"
					+ "values(?, ?, ?, ?, ?)";//1-3)여기에 전달 (?: 와일드카드,매개변수 받을 수 있음)
			
		try {	
			//2)SQL -> DB 연결 [ PreparedStatement 인터페이스 : 연결된 DB에 SQL 조작 ] connection을 통해서 preparedStatement가 전달
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			//3.SQL 설정 [ ? 에 데이터 넣기 ]
			preparedStatement.setString(1, member.getM_id()); // 첫번째 ? 에 데이터 넣기
			preparedStatement.setString(2, member.getM_password());	//1-2)가져온 값들을 여기서 담아와서
			preparedStatement.setString(3, member.getM_name()); // 3번째 ? 에 데이터넣기
			preparedStatement.setString(4, member.getM_email());
			preparedStatement.setInt(5, member.getM_point());
			
			//4.SQL 실행
			preparedStatement.executeUpdate();
			
			//5.SQL 결과
			return true; // DB 저장 성공 시 true 반환

		}
		catch(Exception e) {
			return false;
		}
		
		}
		//2.로그인 메소드
		public boolean login(String id, String password) {
			
			//1. SQL 작성
			String sql ="select*from member where m_id=? and m_password=?";
					
					//select : 검색
					//특정필드 : select 필드명 from 테이블명 where 조건
					//모든필드 : select*from
					//어떤 테이블에서 어떤 조건으로 어떤 필드를 가져올것인지.
			
			//2. SQL -> DB 연결 [무조건 예외 발생 ]
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				
			//3. SQL 설정 -> [ ?에 현재 메소드로 들어온 인수를 대입 ]
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, password);
				
			//4. SQL 실행 [ Query : 쿼리 (검색 결과)
				ResultSet resultSet = preparedStatement.executeQuery();
				// 검색결과 빼오는것 -> Resultset
				
			//5. SQL 결과 [ 검색결과 연결 ]
				if(resultSet.next()) { // 쿼리 결과에 다음 내용이 있으면 true 없으면 false
					return true; // 결과에 다음내용이 있어서 로그인 성공
				}else {
					return false; // 로그인 실패
				}
			} catch (Exception e) {
			
			}
			return false; // DB 오류
			}
		//3.아이디찾기 메소드
		public String findid(String name, String email) {
			
			//1. SQL 작성
			String sql = "select m_id from member where m_name=? and m_email=?";
			//2. SQL -> DB연결
			try {
				preparedStatement = connection.prepareStatement(sql);
				//3. SQL 설정
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, email);
				//4. SQL 실행
				resultSet = preparedStatement.executeQuery();
				//5. SQL 결과
				if(resultSet.next()) { // next 다음거 찾는거
					//검색결과가 있으면
					return resultSet.getString(1);
				}else {
					return null; // 검색결과가 없으면 null 반환
				}
			} catch (Exception e) {
				return null; // DB 오류
			}
		}
		
		//4.패스워드 찾기 메소드
		public String findpassword(String id, String email) {
			//1. sql 작성
			String sql = "select m_password from member where m_id=? and m_email=?";
			//2. DB연결
			try {
				preparedStatement = connection.prepareStatement(sql);
				//3. SQL설정
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, email);
				//4. query
				resultSet = preparedStatement.executeQuery();
				//5. 결과
				if(resultSet.next()) {
					return resultSet.getString(1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
			return null;
			
		}
		//5.회원수정 메소드
		
		
		//6.회원탈퇴 메소드
		public boolean delete (String loginid) {
			String sql = "delete from member where m_id=?";
						//delete from 테이블명 (전체삭제)
						//delete from 테이블명 where 조건
			
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, loginid); // where 뒤의 객체 순서대로 숫자를 집어넣음
				preparedStatement.executeUpdate();
				return true; // 탈퇴 성공시
			} catch (Exception e) {
				System.out.println(e);
			}
			return false; // DB 오류
			
			
		}
	
		//7.회원조회 메소드 [ 회원 아이디를 인수로 받아 회원정보 반환 ]
		public Member getmember (String loginid) {
			
			//1. SQL 작성
			String sql = "select*from member where m_id=?";
			//2. SQL 연결
			try {
				preparedStatement = connection.prepareStatement(sql);
				//3. SQL 설정
				preparedStatement.setString(1, loginid);
				//4. SQL 실행
				resultSet = preparedStatement.executeQuery();
				//5. SQL 결과
				if(resultSet.next()) {
					//패스워드를 제외한 회원정보 반환
					Member member = new Member(resultSet.getString(2), "", 
							resultSet.getString(4),resultSet.getString(5), 
							resultSet.getInt(6));
					return member;
					
				}else {
					return null; // 회원 정보가 없을 경우
				}
				
			} catch (Exception e) {	}
			return null; // db 오류
		}
		
		//8. 아이디 체크 메소드
		public boolean idcheck(String id) {
			String sql = "select m_id from member where m_id=?";
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) { return true;} // 현재 아이디가 존재하면
				else {return false;}	// 현재아이디가 존재하지 않으면
				
			}catch ( Exception e) {
				return false; //DB오류
			}
		}
	
		//9. 포인트 증감 메소드
		public boolean pointupdate(String id, int point) {
			String sql = "update member set m_point = m_point+? where m_id = ?"; 
			// update는 sql 문법
			// update 테이블명 set 변경할필드명 = 변경할 값 where 조건
			
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, point);
				preparedStatement.setString(2, id);
				preparedStatement.executeUpdate();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//DB 연동 테스트
	/*
	public static void main(String[] args) {

		try {
			//1. mysql 드라이브 확인
			Class.forName("com.mysql.cj.jdbc.Driver"); // forName이 무조건 예외처리가 뜸 // ""안에 있는거는 회사마다 달라서 외울 필요 X
				System.out.println("1. 드라이브 가져오기 성공"); // print찍는 이유? 확인차
			//2. DB연동
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234"); //URL : local현재 PC "DB계정명(기본root)", "DB패스워드"
				System.out.println("2. DB 연동 성공");
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DB 연동 실패");
		}
		
	}
	
	*/

}
