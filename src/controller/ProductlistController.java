package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.MemberDao;
import dao.ProductDao;
import domain.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class ProductlistController implements Initializable{ //화면 로드[열렸을 때] 되었을때 초기값 인터페이스


	@FXML
    private Button btnactivation;
    
    @FXML
    void activation(ActionEvent event) {
    	btnactivation.setText(product.getActivation()); // 1. 선택 제품의 상태가 버튼 텍스트에 표시
    	int pa = product.getP_activation(); // 다음꺼 	// 2. 선택 제품의 상태를 가져오기 
    	int ch = pa+1; //클릭할때마다 1씩 증가				// 3. 상태 변경 [ 1-> 2 , 2->3 , 3-> 1 ]
    	if (ch>3) ch = 1; //초기화
    	
    	
    	if(ch == 1) {
    		ProductDao.getProductDao().activationupdate(1, product.getP_no());
    		producttableload();
    		btnactivation.setText("판매중");
    	}
    	if(ch == 2) {
    		ProductDao.getProductDao().activationupdate(2, product.getP_no());
	    	producttableload();
			btnactivation.setText("거래중");
    	}
    	if(ch == 3) {
    		ProductDao.getProductDao().activationupdate(3, product.getP_no());
	    	producttableload();
			btnactivation.setText("품절");
		}
    	
    	// product 클래스의 생성자의 activation의 if절과 같이 보기
    	
    	
    }
	
    private void producttableload() {
    	//1. DB에서 제품목록 가져오기
    			ObservableList<Product> products = ProductDao.getProductDao().productlist();
    			
    			//2. 제품목록을 테이블 뷰에 연결
    			productlist.setItems(products);
    			//3. 테이블뷰에 열 를 하나씩 가져와서 리스트내 객체에 필드와 연결 
    			TableColumn tc = productlist.getColumns().get(0);
    			tc.setCellValueFactory(new PropertyValueFactory<>("p_name"));
    			
    			tc = productlist.getColumns().get(1);
    			tc.setCellValueFactory(new PropertyValueFactory<>("p_category"));
    			
    			tc = productlist.getColumns().get(2);
    			tc.setCellValueFactory(new PropertyValueFactory<>("p_price"));
    			
    			tc = productlist.getColumns().get(3);
    			tc.setCellValueFactory(new PropertyValueFactory<>("activation")); 
    			//문자로 꺼내오기 Product클래스에 필드 선언하고 생성자에 if 절로 조건 생성
    			//그래서 p_activation에서 activation으로 바꿈
    			
    			tc = productlist.getColumns().get(4);
    			tc.setCellValueFactory(new PropertyValueFactory<>("p_date"));
    			
    			// 테이블뷰에서 클릭했을때 아이템 가져오기 
    						// 1. 테이블뷰에 클릭 이벤트 
    						//productlist.setOnMouseClicked( e -> { 정의 } );
    			//3. 테이블 뷰에 리스트 설정 static으로 쓴 변수 사용함
    			productlist.setOnMouseClicked(e -> { //e -> {정의}
    				// 2. 클릭 이벤트가 마우스 클릭과 같으면 
    				if(e.getButton().equals(MouseButton.PRIMARY)) { //클릭 되었다면
    					// 3.테이블뷰에서 클릭한 모델의 아이템[ 객체 ]
    					product = productlist.getSelectionModel().getSelectedItem(); 
    					// 4. 선택된 객체내 이미지경로 가져오기 
    					Image image = new Image(product.getP_img());
    					pimg.setImage(image);
    					// 5. 그외
    					lblpname.setText(product.getP_name());
    					lblpcontents.setText(product.getP_contents());
    					// 천단위 쉼표 문자열 만들기 [ String.format("%,d", 값 )  ]
    					lblpprice.setText(String.format("%,d", product.getP_price()));
    					
    						//회원번호 -> 회원 id // 코드가 길어져서 한번 빼와서 writer에 저장함
    					String writer = MemberDao.getMemberDao().midcheck(product.getM_no());
    					lblmid.setText(writer);
    					if( MainpageController.getinstance().getloginid().equals(  writer )) {
							// 로그인 아이디가 등록한 아이디와 동일하면 
							btndelete.setVisible(true);
							btnupdate.setVisible(true);
							btnactivation.setVisible(true);
						}else {
							btndelete.setVisible(false);
							btnupdate.setVisible(false);
							btnactivation.setVisible(false);
						}
						// 버튼 텍스트 
						btnactivation.setText( product.getActivation() );
    				}
    			});
	}
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	//버튼 숨기기
    	btndelete.setVisible(false);
    	btnupdate.setVisible(false);
    	btnactivation.setVisible(false);
    	producttableload();
    	
    }
    public static Product product; // 다른 페이지에 쓰기 위해서 static으로 만듦 메소드영역에 저장되어 전역변수로 사용가능 다른클래스에서도 static불러낼수있음

	@FXML
    private Button btndelete;

    @FXML
    private Button btnregister;

    @FXML
    private Button btnupdate;

    @FXML
    private Label lblpcontents;

    @FXML
    private Label lblpname;

    @FXML
    private Label lblpprice;

    @FXML
    private Label lblmid;

    @FXML
    private ImageView pimg;

    @FXML
    private TableView <Product> productlist;//제네릭에 넣어주기

    @FXML
    void delete(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setHeaderText("제품을 삭제 하시겠습니까?");
    	Optional<ButtonType> optional = alert.showAndWait();
    	if(optional.get() == ButtonType.OK) {
    		ProductDao.getProductDao().delete(product.getP_no());
    			Alert alert2 = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("삭제 되었습니다.");
    			MainpageController.getinstance().loadpage("productlist");
    		
    	}
    }

    @FXML
    void register(ActionEvent event) {
    	MainpageController.getinstance().loadpage("productregister");
    }

    @FXML
    void update(ActionEvent event) {
    	MainpageController.getinstance().loadpage("productupdate");
    }
}
