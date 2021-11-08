package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;



import dao.BoardDao;
import dao.MemberDao;
import dao.ProductDao;
import domain.Product;
import domain.ProductDate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;


public class HomeController implements Initializable{

	//�������� �۾����ϰ� fxml�� ���� �Է��ؼ� �� �ٲٱ�
	//���� home.fxml�� fx:id�� �־��ֱ� 
	@FXML
	private Label lbltotalmember;
	@FXML
	private Label lbltotalboard;
	@FXML
	private Label lbltotalproduct;
	//�� ��Ʈ
	@FXML
	private LineChart LC;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		// ��ü ��� ��, �Խ��� ��, ��ǰ ��
		lbltotalmember.setText(MemberDao.getMemberDao().membercount()+""); 
		//���� �ֱ� (membercount �޼ҵ忡�� ���� ���� 1�̶� +""�� ��Ʈ��ȭ)
		lbltotalmember.setAlignment(Pos.CENTER); //��� ����
		
		lbltotalboard.setText(BoardDao.getBoardDao().boardcount()+""); 
		//���� �ֱ� (boardcount �޼ҵ忡�� ���� ���� 1�̶� +""�� ��Ʈ��ȭ)
		lbltotalboard.setAlignment(Pos.CENTER); //��� ����
		
		lbltotalproduct.setText(ProductDao.getProductDao().productcount()+""); 
		//���� �ֱ� (productcount �޼ҵ忡�� ���� ���� 1�̶� +""�� ��Ʈ��ȭ)
		lbltotalproduct.setAlignment(Pos.CENTER); //��� ����
		
		//�� ��Ʈ (�迭)
		//1. [�迭����]
		XYChart.Series series = new XYChart.Series<>(); //�ڵ��ϼ��ϸ� ���׸�<x,y>����µ� ���׸� ��������
		//XYChart.Series ���� = new XYChart.Series<>(); //��Ʈ(x, y)�� ���� ������ �� �ִ� �迭 ���� �����ϱ�
		
		
		//2. [�迭�̸�]
		//�迭��.setName("�迭�̸�")	
		series.setName("��� ��");
		
		
		//3. [�����ͻ���]
			//��ǰ �� ������
			ObservableList<Product> products = ProductDao.getProductDao().productlist();
			
			//��¥�� ���� //��¥���� �����ؾ� �ϴϱ� �ݺ���������
			//��ǰ ��� ��¥ = ������ //��ǰ�� = ������
			ArrayList<ProductDate> dates = new ArrayList<>(); //arraylist�� �޸� �����ϱ� ������ �̰� ��.
					//domain ProductDate
			for(Product product : products) {
				String date = product.getP_date().split(" ")[0]; //���� �������� �ɰ��� 0��° �ε��� ���� date������ ���� �ν��Ͻ�ȭ
				Boolean datecheck = true;
				for(int i =0; i<dates.size(); i++) {
					if(dates.get(i).getDate().equals(date)) { //��¥�� �Ȱ��ٸ�
						dates.get(i).setCount(dates.get(i).getCount() +1); 
						//��¥�鳢�� ī��Ʈ�� ���� �ߺ��̸� count +1 �ƴϸ� �״�� ����Ʈ�� �߰�
						break;
					}
				}
				if(datecheck) dates.add(new ProductDate(date, 1));	
			}
			//System.out.println(dates.toString()); date�� �� �������� 
			
			for(ProductDate date : dates) { 
				XYChart.Data data = new XYChart.Data<>(
						date.getDate()+"", date.getCount());
				series.getData().add(data);//����Ʈ�� ��Ʈ�� �߰�
			}
			
		//���� ��Ʈ�� ���ֱ�
		//��Ʈ��.getData().add(�迭��);
		LC.getData().add(series);
		
		
		//getData().add(~~~) �迭�� �� �߰�
		//  x, y�࿡ ���ֱ� XYChart.Data<>(x��, y��);
		//XYChart.Data data1 = new XYChart.Data<>("1",30);
//		series.getData().add(new XYChart.Data<>("1",30)); 
//		series.getData().add(new XYChart.Data<>("2",40));
//		series.getData().add(new XYChart.Data<>("3",50));
		
	}
}
