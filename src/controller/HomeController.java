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

	//씬빌더로 작업안하고 fxml에 직접 입력해서 값 바꾸기
	//먼저 home.fxml에 fx:id값 넣어주기 
	@FXML
	private Label lbltotalmember;
	@FXML
	private Label lbltotalboard;
	@FXML
	private Label lbltotalproduct;
	//선 차트
	@FXML
	private LineChart LC;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		// 전체 멤버 수, 게시판 수, 제품 수
		lbltotalmember.setText(MemberDao.getMemberDao().membercount()+""); 
		//내용 넣기 (membercount 메소드에서 넣은 값이 1이라서 +""로 스트링화)
		lbltotalmember.setAlignment(Pos.CENTER); //가운데 정렬
		
		lbltotalboard.setText(BoardDao.getBoardDao().boardcount()+""); 
		//내용 넣기 (boardcount 메소드에서 넣은 값이 1이라서 +""로 스트링화)
		lbltotalboard.setAlignment(Pos.CENTER); //가운데 정렬
		
		lbltotalproduct.setText(ProductDao.getProductDao().productcount()+""); 
		//내용 넣기 (productcount 메소드에서 넣은 값이 1이라서 +""로 스트링화)
		lbltotalproduct.setAlignment(Pos.CENTER); //가운데 정렬
		
		//선 차트 (계열)
		//1. [계열생성]
		XYChart.Series series = new XYChart.Series<>(); //자동완성하면 제네릭<x,y>생기는데 제네릭 지워도됨
		//XYChart.Series 변수 = new XYChart.Series<>(); //차트(x, y)에 값을 저장할 수 있는 계열 변수 선언하기
		
		
		//2. [계열이름]
		//계열명.setName("계열이름")	
		series.setName("등록 수");
		
		
		//3. [데이터생성]
			//제품 다 빼오기
			ObservableList<Product> products = ProductDao.getProductDao().productlist();
			
			//날짜로 구분 //날짜별로 생성해야 하니까 반복문돌리기
			//제품 등록 날짜 = 가로축 //제품수 = 세로축
			ArrayList<ProductDate> dates = new ArrayList<>(); //arraylist가 메모리 관리하기 쉬워서 이거 씀.
					//domain ProductDate
			for(Product product : products) {
				String date = product.getP_date().split(" ")[0]; //띄어쓰기 기준으로 쪼개서 0번째 인덱스 값을 date변수에 저장 인스턴스화
				Boolean datecheck = true;
				for(int i =0; i<dates.size(); i++) {
					if(dates.get(i).getDate().equals(date)) { //날짜가 똑같다면
						dates.get(i).setCount(dates.get(i).getCount() +1); 
						//날짜들끼리 카운트를 센거 중복이면 count +1 아니면 그대로 리스트에 추가
						break;
					}
				}
				if(datecheck) dates.add(new ProductDate(date, 1));	
			}
			//System.out.println(dates.toString()); date값 잘 나오는지 
			
			for(ProductDate date : dates) { 
				XYChart.Data data = new XYChart.Data<>(
						date.getDate()+"", date.getCount());
				series.getData().add(data);//리스트를 차트에 추가
			}
			
		//실제 차트에 값넣기
		//차트명.getData().add(계열명);
		LC.getData().add(series);
		
		
		//getData().add(~~~) 계열에 값 추가
		//  x, y축에 값넣기 XYChart.Data<>(x축, y축);
		//XYChart.Data data1 = new XYChart.Data<>("1",30);
//		series.getData().add(new XYChart.Data<>("1",30)); 
//		series.getData().add(new XYChart.Data<>("2",40));
//		series.getData().add(new XYChart.Data<>("3",50));
		
	}
}
