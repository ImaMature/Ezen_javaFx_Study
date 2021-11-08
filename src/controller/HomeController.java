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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


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
	private LineChart LC; //
	
	@FXML
	private Label lblincrease; // ��ǰ����߼� accordion ���� ���̺�
	
	@FXML
	private Label lbldecrease; // ��ǰ����߼� accordion ���� ���̺�
	
	@FXML
	private BarChart BC;
	
	@FXML
	private Label lblcategory;
	
	@FXML
	private PieChart PC;
	
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
//		//1. [�迭����]
		// ���� 
//		XYChart.Series series = new XYChart.Series<>(); //�ڵ��ϼ��ϸ� ���׸�<x,y>����µ� ���׸� ��������
//		XYChart.Series ���� = new XYChart.Series<>(); //��Ʈ(x, y)�� ���� ������ �� �ִ� �迭 ���� �����ϱ�
//		
//		//2. [�迭�̸�]
//		�迭��.setName("�迭�̸�")	
//		series.setName("��� ��");
//		
//		//3. [�����ͻ���]
//		XYChart.Data data1 =  new XYChart.Data<>("1" , 30);//
		
		//4. [�迭�� ������ �ֱ�]
//		series.getData().add( data1 );
		
		//5. [�迭�� ��Ʈ�� �ֱ�]
//		lc.getData().add(series);		
		
//		����Ʈ�� ��¥�� ��ǰ�� ǥ��
//			//��ǰ �� ������ (��� ��ǰ����Ʈ)
//			ObservableList<Product> products = ProductDao.getProductDao().productlist();
//			
//			//��¥�� ���� //��¥���� �����ؾ� �ϴϱ� �ݺ���������
//			//��ǰ ��� ��¥ = ������ //��ǰ�� = ������
//			//��¥�� ������ �����ϴ� ����Ʈ
//			ArrayList<ProductDate> dates = new ArrayList<>(); 
//					//domain ProductDate
//				//arraylist�� �޸� �����ϱ� ������ �̰� ��. 
//			for(Product product : products) { // ��� ��ǰ �ϳ��� ��ü ������
//				String date = product.getP_date().split(" ")[0]; //���� �������� �ɰ��� 0��° �ε��� ���� date������ ���� �ν��Ͻ�ȭ
//										//P_date�� product���̺��� ������ ��ǰ ����� ��¥
//				Boolean datecheck = true; //�ߺ�üũ
//				for(int i =0; i<dates.size(); i++) {
//					if(dates.get(i).getDate().equals(date)) { //���࿡ ��¥�� ���� ����Ʈ�� ������ ��¥�� ������
//						dates.get(i).setCount(dates.get(i).getCount() +1); 
//						//��¥�鳢�� ī��Ʈ�� ���� �ߺ��̸� count +1 �ƴϸ� �״�� ����Ʈ�� �߰�
//						datecheck = false; break;
//					}
//				}
//				if(datecheck) dates.add(new ProductDate(date, 1));//������ ��¥�� ������ ����Ʈ�� �߰�
//			}
//			//System.out.println(dates.toString()); date�� �� �������� 
//			
//			//3. �迭�� ���ֱ�
//			for(ProductDate date : dates) { //��¥�� ���� ����Ʈ���� �ϳ��� ��ü ������
//				XYChart.Data data = new XYChart.Data<>(
//						date.getDate()+"", date.getCount()); // ��ü�� ���� �ֱ�
//				series.getData().add(data);//�迭�� ���ֱ�
//			}
//			
//		//4. ���� ��Ʈ�� �迭 �ֱ�
//		//��Ʈ��.getData().add(�迭��);
//		LC.getData().add(series);
		//getData().add(~~~) �迭�� �� �߰�
		
		
		
		//����
		//  x, y�࿡ ���ֱ� XYChart.Data<>(x��, y��);
		//XYChart.Data data1 = new XYChart.Data<>("1",30);
//		series.getData().add(new XYChart.Data<>("1",30)); 
//		series.getData().add(new XYChart.Data<>("2",40));
//		series.getData().add(new XYChart.Data<>("3",50));
		
		//2***. ����Ʈ �ٸ���� (�̰� ���־���� �Ͻ�)
			//2-1) �迭 ����
		XYChart.Series series = new XYChart.Series<>(); 
		//2-2) �迭 �̸� ����
		series.setName("��ǰ ��"); 
		//2-3) ���� �������� p_date�� �ɰ��� 1��°�� ��������
			
			//DB : ���� [�˻��ᰡ] -> ����Ʈ�� ��ü�� �ϳ��� ������
			// �ߺ�üũ�� �ʿ� ��� ��ŵ��
		
			//ArrayList ��üȭ 
			ArrayList<ProductDate> productDates= ProductDao.getProductDao().productdatelist();
			for(ProductDate productDate : productDates) {
				//2-4)�迭����
					//�� ����
					XYChart.Data data = new XYChart.Data<>(productDate.getDate(), productDate.getCount());
					//series.getData().add(new XYChart.Data<>(productDate.getDate(), productDate.getCount()));
					//���׷��� ���� ���� �ֱ�
						AnchorPane anchorPane = new AnchorPane(); // �����̳ʸ����
						
							Label label = new Label(productDate.getCount()+"");  //���̺� �߰��ؼ� stringȭ
							//�󺧿� ���ʿ� ����(padding)�ֱ�
							label.setPadding(new Insets(2)); //setPadding: ���ʿ���, new Insets(�����ġ)
							anchorPane.getChildren().add(label); // Children -> �����ϴ°�(�ڽ����� �δ°��ΰ���) // �����̳� ���̺� �ֱ�
					data.setNode(anchorPane); //���� �����̳� �ֱ�
					//���� ���(Node)���� [ //data.setNode(�����̳�); ]
					series.getData().add(data);
					
			}
		//**y�� ����	
		LC.getYAxis().setAutoRanging(false); // y�� �ڵ����� ����
		/*
		 * ��Ʈ y�� �������� �����ϴ¹�
		 * 1. ��Ʈ��.getYAxis().setAutoRanging(false); �� y�� �ڵ����� ����
		 * 2. �ش� fxml ���Ͽ��� �ؿ� Axisã���ؼ� upperBound, lowerBound�Է�
		 * <xAxis>
                    <CategoryAxis side="BOTTOM" />
           </xAxis>
           <yAxis>
                    <upperBound = "�ִ�(����)" lowerBound = '�ּڰ�(����,��������)' NumberAxis side="LEFT" />
            </yAxis>
		 */
		//2-5) �迭�� ��Ʈ�� �ֱ�
		LC.getData().add(series); 
		
//==================================================================================================================================		
		//3. ������Ʈ (Barchart) Map ���
		
		//3-1) �迭 ����
		XYChart.Series series2 = new XYChart.Series<>(); 
		//3-2) �迭 �̸�
		series2.setName("����"); 
		//3-3) fxml�� ����(fxid)�ֱ�
		//���������ϱ�
		//3-4) �迭�� ���ֱ� (Map�Ἥ)
		
		HashMap<String, Integer> hashMap = ProductDao.getProductDao().productcategorylist();
		// Map �÷���<key , value> ���׸� 2�� �ʿ�
		
		String maxcategorykey = " ";
		int max = 0;
		for(String key : hashMap.keySet()) { // keySet. ��� ���� ��������
			if(hashMap.get(key) > max) {
				max = hashMap.get(key);
				maxcategorykey = key;
			}
			//map�� ��ü ��� �����ͼ� �ݺ���
			XYChart.Data data = new XYChart.Data<>(key, hashMap.get(key));
			series2.getData().add(data);
		}
		
		//3-5) ��Ʈ�� �迭 �ֱ�
		BC.getData().add(series2);
		lblcategory.setText(maxcategorykey);
		
	
		//���̺� ���� ����
		if(productDates.get(productDates.size()-1).getCount() > productDates.get(productDates.size()-2).getCount()) {
			lbldecrease.setVisible(false);//���� ���̺� �����
		}else {
			lblincrease.setVisible(false); // ���� ���̺� �����
		}
		
//==================================================================================================================================
		//4. ������Ʈ
			//xy��ǥ�� ����.
			//ObservableList ���
		
		//4-1) ��Ʈ����
		ObservableList<Product> products = ProductDao.productDao.productlist();
		ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList();
		
		
//		//4-2) ��Ʈ�� ���Ƿ� �����ϱ�
//		observableList.add(new PieChart.Data("����", 10)); //�޼ҵ尡 �ƴ϶� PieChart().Data�� �ƴ�
//		observableList.add(new PieChart.Data("����", 5));
//		observableList.add(new PieChart.Data("�Ź�", 15));
		
		for(Product product : products) {
			observableList.add(new PieChart.Data(product.getActivation(), 1)); //�޼ҵ尡 �ƴ϶� PieChart().Data�� �ƴ�
		}
		//������ �� ����
		PC.setData(observableList);
	}
	
	/* ��� ���ڵ� �˻� [ select �ʵ�� from ���̺� ]
	 * 		select * p_date from product
	 * 
	 * 3. �׷� [ group by �ʵ�� ]
		   group by select substring_index(product.p_date, " ", 1) from product
		   //product ���̺�κ��� ��¥�� �������� �׷��Ѵ�.

	 * 4. �׷�����(Mysql���) [ having : �׷� �� ����, where : �׷� �� ���� ]
		 * select
				SUBSTRING_INDEX (product.p_date, " ", 1), 
				 //* substring_index(���̺��(��������).�ʵ��, "�ڸ��� ����", �����ù�ȣ)
	    		count(*) // ����
		   from 
				product
		   group by
				SUBSTRING_INDEX (product.p_date, " ", 1)
	    
	 */
	
}
