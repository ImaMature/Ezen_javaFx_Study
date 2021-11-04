package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import domain.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductDao {

	//1. �ʵ� 
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	//2. �ν��Ͻ� ��ȯ���ִ� ��ü
	private static ProductDao productDao = new ProductDao();
	
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
				products.add(product);
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
}
