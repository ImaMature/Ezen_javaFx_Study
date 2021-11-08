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
	private LineChart LC; //
	
	@FXML
	private Label lblincrease; // 제품등록추세 accordion 증가 레이블값
	
	@FXML
	private Label lbldecrease; // 제품등록추세 accordion 감소 레이블값
	
	@FXML
	private BarChart BC;
	
	@FXML
	private Label lblcategory;
	
	@FXML
	private PieChart PC;
	
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
//		//1. [계열생성]
		// 예시 
//		XYChart.Series series = new XYChart.Series<>(); //자동완성하면 제네릭<x,y>생기는데 제네릭 지워도됨
//		XYChart.Series 변수 = new XYChart.Series<>(); //차트(x, y)에 값을 저장할 수 있는 계열 변수 선언하기
//		
//		//2. [계열이름]
//		계열명.setName("계열이름")	
//		series.setName("등록 수");
//		
//		//3. [데이터생성]
//		XYChart.Data data1 =  new XYChart.Data<>("1" , 30);//
		
		//4. [계열에 데이터 넣기]
//		series.getData().add( data1 );
		
		//5. [계열을 차트에 넣기]
//		lc.getData().add(series);		
		
//		선차트에 날짜별 제품수 표시
//			//제품 다 빼오기 (모든 제품리스트)
//			ObservableList<Product> products = ProductDao.getProductDao().productlist();
//			
//			//날짜로 구분 //날짜별로 생성해야 하니까 반복문돌리기
//			//제품 등록 날짜 = 가로축 //제품수 = 세로축
//			//날짜별 개수를 저장하는 리스트
//			ArrayList<ProductDate> dates = new ArrayList<>(); 
//					//domain ProductDate
//				//arraylist가 메모리 관리하기 쉬워서 이거 씀. 
//			for(Product product : products) { // 모든 제품 하나씩 객체 꺼내기
//				String date = product.getP_date().split(" ")[0]; //띄어쓰기 기준으로 쪼개서 0번째 인덱스 값을 date변수에 저장 인스턴스화
//										//P_date는 product테이블에서 가져온 제품 등록한 날짜
//				Boolean datecheck = true; //중복체크
//				for(int i =0; i<dates.size(); i++) {
//					if(dates.get(i).getDate().equals(date)) { //만약에 날짜별 개수 리스트에 동일한 날짜가 있으면
//						dates.get(i).setCount(dates.get(i).getCount() +1); 
//						//날짜들끼리 카운트를 센거 중복이면 count +1 아니면 그대로 리스트에 추가
//						datecheck = false; break;
//					}
//				}
//				if(datecheck) dates.add(new ProductDate(date, 1));//동일한 날짜가 없으면 리스트에 추가
//			}
//			//System.out.println(dates.toString()); date값 잘 나오는지 
//			
//			//3. 계열에 값넣기
//			for(ProductDate date : dates) { //날짜별 개수 리스트에서 하나씩 객체 꺼내기
//				XYChart.Data data = new XYChart.Data<>(
//						date.getDate()+"", date.getCount()); // 객체를 값에 넣기
//				series.getData().add(data);//계열에 값넣기
//			}
//			
//		//4. 실제 차트에 계열 넣기
//		//차트명.getData().add(계열명);
//		LC.getData().add(series);
		//getData().add(~~~) 계열에 값 추가
		
		
		
		//번외
		//  x, y축에 값넣기 XYChart.Data<>(x축, y축);
		//XYChart.Data data1 = new XYChart.Data<>("1",30);
//		series.getData().add(new XYChart.Data<>("1",30)); 
//		series.getData().add(new XYChart.Data<>("2",40));
//		series.getData().add(new XYChart.Data<>("3",50));
		
		//2***. 선차트 다른방법 (이걸 자주쓰라고 하심)
			//2-1) 계열 생성
		XYChart.Series series = new XYChart.Series<>(); 
		//2-2) 계열 이름 설정
		series.setName("제품 수"); 
		//2-3) 띄어쓰기 기준으로 p_date를 쪼개고 1번째를 가져오기
			
			//DB : 쿼리 [검색결가] -> 리스트내 객체를 하나씩 꺼내기
			// 중복체크할 필요 없어서 스킵함
		
			//ArrayList 객체화 
			ArrayList<ProductDate> productDates= ProductDao.getProductDao().productdatelist();
			for(ProductDate productDate : productDates) {
				//2-4)계열설정
					//값 설정
					XYChart.Data data = new XYChart.Data<>(productDate.getDate(), productDate.getCount());
					//series.getData().add(new XYChart.Data<>(productDate.getDate(), productDate.getCount()));
					//선그래프 값에 숫자 넣기
						AnchorPane anchorPane = new AnchorPane(); // 컨테이너만들기
						
							Label label = new Label(productDate.getCount()+"");  //레이블 추가해서 string화
							//라벨에 안쪽에 여백(padding)넣기
							label.setPadding(new Insets(2)); //setPadding: 안쪽여백, new Insets(여백수치)
							anchorPane.getChildren().add(label); // Children -> 포함하는거(자식으로 두는거인가봄) // 컨테이너 레이블 넣기
					data.setNode(anchorPane); //값에 컨테이너 넣기
					//값에 노드(Node)설정 [ //data.setNode(컨테이너); ]
					series.getData().add(data);
					
			}
		//**y축 설정	
		LC.getYAxis().setAutoRanging(false); // y축 자동설정 끄기
		/*
		 * 차트 y축 고정으로 설정하는법
		 * 1. 차트명.getYAxis().setAutoRanging(false); 로 y축 자동설정 끄기
		 * 2. 해당 fxml 파일에서 밑에 Axis찾기해서 upperBound, lowerBound입력
		 * <xAxis>
                    <CategoryAxis side="BOTTOM" />
           </xAxis>
           <yAxis>
                    <upperBound = "최댓값(숫자)" lowerBound = '최솟값(숫자,음수도됨)' NumberAxis side="LEFT" />
            </yAxis>
		 */
		//2-5) 계열을 차트에 넣기
		LC.getData().add(series); 
		
//==================================================================================================================================		
		//3. 막대차트 (Barchart) Map 사용
		
		//3-1) 계열 생성
		XYChart.Series series2 = new XYChart.Series<>(); 
		//3-2) 계열 이름
		series2.setName("상의"); 
		//3-3) fxml에 설정(fxid)넣기
		//직접설정하기
		//3-4) 계열에 값넣기 (Map써서)
		
		HashMap<String, Integer> hashMap = ProductDao.getProductDao().productcategorylist();
		// Map 컬렉션<key , value> 제네릭 2개 필요
		
		String maxcategorykey = " ";
		int max = 0;
		for(String key : hashMap.keySet()) { // keySet. 모든 값을 가져오기
			if(hashMap.get(key) > max) {
				max = hashMap.get(key);
				maxcategorykey = key;
			}
			//map에 전체 기록 가져와서 반복문
			XYChart.Data data = new XYChart.Data<>(key, hashMap.get(key));
			series2.getData().add(data);
		}
		
		//3-5) 차트에 계열 넣기
		BC.getData().add(series2);
		lblcategory.setText(maxcategorykey);
		
	
		//레이블 증감 설정
		if(productDates.get(productDates.size()-1).getCount() > productDates.get(productDates.size()-2).getCount()) {
			lbldecrease.setVisible(false);//감소 레이블 숨기기
		}else {
			lblincrease.setVisible(false); // 증가 레이블 숨기기
		}
		
//==================================================================================================================================
		//4. 원형차트
			//xy좌표가 없다.
			//ObservableList 사용
		
		//4-1) 차트생성
		ObservableList<Product> products = ProductDao.productDao.productlist();
		ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList();
		
		
//		//4-2) 차트값 임의로 설정하기
//		observableList.add(new PieChart.Data("상의", 10)); //메소드가 아니라서 PieChart().Data가 아님
//		observableList.add(new PieChart.Data("하의", 5));
//		observableList.add(new PieChart.Data("신발", 15));
		
		for(Product product : products) {
			observableList.add(new PieChart.Data(product.getActivation(), 1)); //메소드가 아니라서 PieChart().Data가 아님
		}
		//설정한 값 저장
		PC.setData(observableList);
	}
	
	/* 모든 레코드 검색 [ select 필드명 from 테이블 ]
	 * 		select * p_date from product
	 * 
	 * 3. 그룹 [ group by 필드명 ]
		   group by select substring_index(product.p_date, " ", 1) from product
		   //product 테이블로부터 날짜를 기준으로 그룹한다.

	 * 4. 그룹조건(Mysql사용) [ having : 그룹 내 조건, where : 그룹 외 조건 ]
		 * select
				SUBSTRING_INDEX (product.p_date, " ", 1), 
				 //* substring_index(테이블명(생략가능).필드명, "자르는 기준", 가져올번호)
	    		count(*) // 문법
		   from 
				product
		   group by
				SUBSTRING_INDEX (product.p_date, " ", 1)
	    
	 */
	
}
