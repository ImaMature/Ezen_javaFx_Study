package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Product;
import domain.ProductDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductDao {

	//1. 필드 
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	//2. 인스턴스 반환해주는 객체
	public static ProductDao productDao = new ProductDao();
	
	public ProductDao(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234");
			System.out.println("ProductDao DB 연동 성공");
		} catch (Exception e) {
			System.out.println("ProductDao DB연동 실패");
		}
	}
	//메소드 객체화
	public static ProductDao getProductDao() { return productDao; }
	
	//제품 등록
	public boolean register (Product product) {
		String sql = "insert into product(p_name, p_img, p_contents, p_category, p_price, p_activation, m_no) values(?,?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, product.getP_name());
			preparedStatement.setString(2, product.getP_img());
			preparedStatement.setString(3, product.getP_contents());
			preparedStatement.setString(4, product.getP_category());
			preparedStatement.setInt(5, product.getP_price());
			preparedStatement.setInt(6, product.getP_activation());
			preparedStatement.setInt(7, product.getM_no());
			preparedStatement.executeUpdate();
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	//2.제품 목록 (product테이블 전체의 행의 구성들을 하나하나씩 객체화해서 저장하기)
			//sql을 여러개 조작할 타입, 인수 만들면 1일 때 어떤 동작을 하고 2일때 다른 동작을함
	public ObservableList<Product> productlist(){
		ObservableList<Product> products = FXCollections.observableArrayList();
											//javafx객체를 만들려면 fxcollections을 사용
		String sql = "select * from product order by p_no desc";
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) { // 검색결과 레코드가 없을 때 까지 레코드 하나씩 객체화
				//해당 레코드 객체화
				Product product = new Product(resultSet.getInt(1),  //테이블의 자료형과 빼오는 자료형이 같아야됨
											resultSet.getString(2), 
											resultSet.getString(3), 
											resultSet.getString(4), 
											resultSet.getString(5),
											resultSet.getInt(6), 
											resultSet.getInt(7),
											resultSet.getString(8),
											resultSet.getInt(9));
				//객체 리스트 저장
				products.add(product); //지역변수임 그래서 다른클래스에서 얘를 호출하려면 메소드호출해야지 products만 못씀
			}
			return products;
		} catch (Exception e) {}
		return products;
	}
	
	//3. 제품 삭제 (제품 번호의 해당하는 제품 삭제)
	public boolean delete(int p_no) {
		String sql = "delete from product where p_no =?"; //product테이블에 p_no
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, p_no);
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {}
		return false;
	}
	
	//4. 제품 수정
	public boolean update (Product product) {
		String sql = "update product set p_name=?, p_img=?, p_contents=?, p_category=?, p_price=? where p_no=?";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, product.getP_name());
			preparedStatement.setString(2, product.getP_img());
			preparedStatement.setString(3, product.getP_contents());
			preparedStatement.setString(4, product.getP_category());
			preparedStatement.setInt(5, product.getP_price());
			preparedStatement.setInt(6, product.getP_no());
			preparedStatement.executeUpdate(); //query 는 select때만,
			return true;
		}catch (Exception e) {
			return false; // Observerlist만 {}밖에 나오는 return false를 씁니다. 
		}
	}
	
	//5.
	public ObservableList<Product> myproductlist(int m_no){ //2번메소드 복사해와서 인수 기입int m_no
		ObservableList<Product> products = FXCollections.observableArrayList();
											//javafx객체를 만들려면 fxcollections을 사용
		String sql = "select * from product where m_no =? order by p_no desc";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, m_no); // 새로 기입 받아서 ?에 넣을 값
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) { // 검색결과 레코드가 없을 때 까지 레코드 하나씩 객체화
				//해당 레코드 객체화
				Product product = new Product(resultSet.getInt(1),  //테이블의 자료형과 빼오는 자료형이 같아야됨
											resultSet.getString(2), 
											resultSet.getString(3), 
											resultSet.getString(4), 
											resultSet.getString(5),
											resultSet.getInt(6), 
											resultSet.getInt(7),
											resultSet.getString(8),
											resultSet.getInt(9));
				//객체 리스트 저장
				products.add(product); //지역변수임 그래서 다른클래스에서 얘를 호출하려면 메소드호출해야지 products만 못씀
			}
			return products;
		} catch (Exception e) {}
		return products;
	}
	
	//6. 제품상태 활성화 변경
	public boolean activationupdate(int p_activation, int p_no) { //제품번호가 동일한 경우 업데이트를 하겠다
		String sql = "update product set p_activation=? where p_no=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, p_activation);
			preparedStatement.setInt(2, p_no);
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {	}
		return false; 
	}
	
	//7. 전체 제품 수 반환
	public int productcount() { //메소드가 int형 반환값도 int형
		String sql = "select count(*) from product"; 
		//count함수 해당 필드의 레코드 수를 셈 (공백X) 
		//count(*) 해당 필드의 모든 레코드 수를 셈 (공백O)
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {			//select * from product하면 count한 값만 나옴
				return resultSet.getInt(1);	//count의 필드(열) 1개만 나오기 때문에 1
			}
		} catch (Exception e) {
			
		}
		return 0;
	}
	//select * from product group by p_date
		//날짜별로 모두 그룹하기
	//select substring_index(product.p_date,' ',1) from product 
		//product 테이블에서 p_date필드 불러오기(p_date(날짜)' '을 기준으로 1번째만 나오도록)
	//select substring_index(product.p_date,' ',1),count(*) from product
		//product 테이블에서 p_date필드 불러오기 (p_date(날짜)' '을 기준으로 1번째만 나오도록), 그리고 2번째 p_date가 몇개인지 다 불러오기 
	//select substring_index(product.p_date,' ',1),count(*) from product group by substring_index(product.p_date, ' ', 1)
		//날짜, 제품 수 가져오기 (둘 다 product 테이블에서) (날짜의 ''을 기점으로 나누어진) p_date를 기준으로 그룹된걸 가져옴(날짜의 ''을 기점으로 나누기 ))
		/*MySQL 실행 결과
		 * substring_index(product.p_date,' ',1)  | Count(*) (날짜별로 등록한 제품의 수를 센것) //count 는 productdate 도메인에 필드로 생성함
		 							2021-11-04   |		2
		 							2021-11-05   |		2
		 							2021-11-08   |		2

		*/
	//8. 날짜별 제품 수 반환
	public ArrayList<ProductDate> productdatelist(){
		ArrayList <ProductDate> productdates = new ArrayList<>();
		String sql = "select substring_index(product.p_date,' ',1),count(*) from product group by substring_index(product.p_date, ' ', 1)";
		//select SUBSTRING_INDEX (product.p_date, ' ', 1) from product
		//* substring_index(테이블명.필드명, '자르는 기준', 가져올번호)
									//작은따옴표로 자르는 기준 설정하기
		//2021-11-08 11:34:30 > 1 : 2021-11-08, 2: 11:34:30
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ProductDate date = new ProductDate(
						resultSet.getString(1), //1번째 열(필드,컬럼) 모르겠으면 String sql의 뒤의 "~" 내용들을 sql에 찍어서 판단하기
						resultSet.getInt(2));	//2번재 열(필드, 컬럼)
				productdates.add(date);
			}
		} catch (Exception e) {}
		return productdates;
		
	}
	
	
	
	//9. 카테고리별 제품 수 반환
	public HashMap<String, Integer> productcategorylist(){
		HashMap<String, Integer> hashMap = new HashMap<>(); // HomeController에 있는 hashMap과는 다른 새로운 객체
		String sql = "select p_category, count(*) from product group by p_category";
			//그룹된 카테고리를 기준으로 프로덕트 테이블에서 카테고리와, 제품수를 검색하기
								//category는 domain/product에 String p_catogory 필드로 선언
								//count는 productDate에 int count 필드로 선언
		try {
			preparedStatement= connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//검색된 레코드를 map 컬렉션에 넣기 [x축(key : 카테고리), y축(value : 개수)]
				hashMap.put(resultSet.getString(1), resultSet.getInt(2)); //Map의 단점 순서가 정해져있지 않음
			}
			return hashMap;
			
		} catch (Exception e) {	}
		return hashMap;
	}
	
	//10. 
}
