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

	//1. �ʵ� 
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	//2. �ν��Ͻ� ��ȯ���ִ� ��ü
	public static ProductDao productDao = new ProductDao();
	
	public ProductDao(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/javafx?serverTimezone=UTC", "root", "1234");
			System.out.println("ProductDao DB ���� ����");
		} catch (Exception e) {
			System.out.println("ProductDao DB���� ����");
		}
	}
	//�޼ҵ� ��üȭ
	public static ProductDao getProductDao() { return productDao; }
	
	//��ǰ ���
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
	
	//2.��ǰ ��� (product���̺� ��ü�� ���� �������� �ϳ��ϳ��� ��üȭ�ؼ� �����ϱ�)
			//sql�� ������ ������ Ÿ��, �μ� ����� 1�� �� � ������ �ϰ� 2�϶� �ٸ� ��������
	public ObservableList<Product> productlist(){
		ObservableList<Product> products = FXCollections.observableArrayList();
											//javafx��ü�� ������� fxcollections�� ���
		String sql = "select * from product order by p_no desc";
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) { // �˻���� ���ڵ尡 ���� �� ���� ���ڵ� �ϳ��� ��üȭ
				//�ش� ���ڵ� ��üȭ
				Product product = new Product(resultSet.getInt(1),  //���̺��� �ڷ����� ������ �ڷ����� ���ƾߵ�
											resultSet.getString(2), 
											resultSet.getString(3), 
											resultSet.getString(4), 
											resultSet.getString(5),
											resultSet.getInt(6), 
											resultSet.getInt(7),
											resultSet.getString(8),
											resultSet.getInt(9));
				//��ü ����Ʈ ����
				products.add(product); //���������� �׷��� �ٸ�Ŭ�������� �긦 ȣ���Ϸ��� �޼ҵ�ȣ���ؾ��� products�� ����
			}
			return products;
		} catch (Exception e) {}
		return products;
	}
	
	//3. ��ǰ ���� (��ǰ ��ȣ�� �ش��ϴ� ��ǰ ����)
	public boolean delete(int p_no) {
		String sql = "delete from product where p_no =?"; //product���̺� p_no
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, p_no);
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {}
		return false;
	}
	
	//4. ��ǰ ����
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
			preparedStatement.executeUpdate(); //query �� select����,
			return true;
		}catch (Exception e) {
			return false; // Observerlist�� {}�ۿ� ������ return false�� ���ϴ�. 
		}
	}
	
	//5.
	public ObservableList<Product> myproductlist(int m_no){ //2���޼ҵ� �����ؿͼ� �μ� ����int m_no
		ObservableList<Product> products = FXCollections.observableArrayList();
											//javafx��ü�� ������� fxcollections�� ���
		String sql = "select * from product where m_no =? order by p_no desc";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, m_no); // ���� ���� �޾Ƽ� ?�� ���� ��
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) { // �˻���� ���ڵ尡 ���� �� ���� ���ڵ� �ϳ��� ��üȭ
				//�ش� ���ڵ� ��üȭ
				Product product = new Product(resultSet.getInt(1),  //���̺��� �ڷ����� ������ �ڷ����� ���ƾߵ�
											resultSet.getString(2), 
											resultSet.getString(3), 
											resultSet.getString(4), 
											resultSet.getString(5),
											resultSet.getInt(6), 
											resultSet.getInt(7),
											resultSet.getString(8),
											resultSet.getInt(9));
				//��ü ����Ʈ ����
				products.add(product); //���������� �׷��� �ٸ�Ŭ�������� �긦 ȣ���Ϸ��� �޼ҵ�ȣ���ؾ��� products�� ����
			}
			return products;
		} catch (Exception e) {}
		return products;
	}
	
	//6. ��ǰ���� Ȱ��ȭ ����
	public boolean activationupdate(int p_activation, int p_no) { //��ǰ��ȣ�� ������ ��� ������Ʈ�� �ϰڴ�
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
	
	//7. ��ü ��ǰ �� ��ȯ
	public int productcount() { //�޼ҵ尡 int�� ��ȯ���� int��
		String sql = "select count(*) from product"; 
		//count�Լ� �ش� �ʵ��� ���ڵ� ���� �� (����X) 
		//count(*) �ش� �ʵ��� ��� ���ڵ� ���� �� (����O)
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {			//select * from product�ϸ� count�� ���� ����
				return resultSet.getInt(1);	//count�� �ʵ�(��) 1���� ������ ������ 1
			}
		} catch (Exception e) {
			
		}
		return 0;
	}
	//select * from product group by p_date
		//��¥���� ��� �׷��ϱ�
	//select substring_index(product.p_date,' ',1) from product 
		//product ���̺��� p_date�ʵ� �ҷ�����(p_date(��¥)' '�� �������� 1��°�� ��������)
	//select substring_index(product.p_date,' ',1),count(*) from product
		//product ���̺��� p_date�ʵ� �ҷ����� (p_date(��¥)' '�� �������� 1��°�� ��������), �׸��� 2��° p_date�� ����� �� �ҷ����� 
	//select substring_index(product.p_date,' ',1),count(*) from product group by substring_index(product.p_date, ' ', 1)
		//��¥, ��ǰ �� �������� (�� �� product ���̺���) (��¥�� ''�� �������� ��������) p_date�� �������� �׷�Ȱ� ������(��¥�� ''�� �������� ������ ))
		/*MySQL ���� ���
		 * substring_index(product.p_date,' ',1)  | Count(*) (��¥���� ����� ��ǰ�� ���� ����) //count �� productdate �����ο� �ʵ�� ������
		 							2021-11-04   |		2
		 							2021-11-05   |		2
		 							2021-11-08   |		2

		*/
	//8. ��¥�� ��ǰ �� ��ȯ
	public ArrayList<ProductDate> productdatelist(){
		ArrayList <ProductDate> productdates = new ArrayList<>();
		String sql = "select substring_index(product.p_date,' ',1),count(*) from product group by substring_index(product.p_date, ' ', 1)";
		//select SUBSTRING_INDEX (product.p_date, ' ', 1) from product
		//* substring_index(���̺��.�ʵ��, '�ڸ��� ����', �����ù�ȣ)
									//��������ǥ�� �ڸ��� ���� �����ϱ�
		//2021-11-08 11:34:30 > 1 : 2021-11-08, 2: 11:34:30
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ProductDate date = new ProductDate(
						resultSet.getString(1), //1��° ��(�ʵ�,�÷�) �𸣰����� String sql�� ���� "~" ������� sql�� �� �Ǵ��ϱ�
						resultSet.getInt(2));	//2���� ��(�ʵ�, �÷�)
				productdates.add(date);
			}
		} catch (Exception e) {}
		return productdates;
		
	}
	
	
	
	//9. ī�װ��� ��ǰ �� ��ȯ
	public HashMap<String, Integer> productcategorylist(){
		HashMap<String, Integer> hashMap = new HashMap<>(); // HomeController�� �ִ� hashMap���� �ٸ� ���ο� ��ü
		String sql = "select p_category, count(*) from product group by p_category";
			//�׷�� ī�װ��� �������� ���δ�Ʈ ���̺��� ī�װ���, ��ǰ���� �˻��ϱ�
								//category�� domain/product�� String p_catogory �ʵ�� ����
								//count�� productDate�� int count �ʵ�� ����
		try {
			preparedStatement= connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//�˻��� ���ڵ带 map �÷��ǿ� �ֱ� [x��(key : ī�װ�), y��(value : ����)]
				hashMap.put(resultSet.getString(1), resultSet.getInt(2)); //Map�� ���� ������ ���������� ����
			}
			return hashMap;
			
		} catch (Exception e) {	}
		return hashMap;
	}
	
	//10. 
}
