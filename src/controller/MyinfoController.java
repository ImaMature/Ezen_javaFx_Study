package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.BoardDao;
import dao.MemberDao;
import dao.ProductDao;
import domain.Board;
import domain.Member;
import domain.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyinfoController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
										//
		
		
		//1. 로그인된 아이디의 DB에서 회원정보 찾기
		String loginid = MainpageController.getinstance().getloginid();
		Member member = MemberDao.getMemberDao().getmember(loginid);
//		
		//3-1. 테이블뷰에 내가 쓴 글 가져오기 (예매한목록이나 테이블 띄울때)
			//위에있으면 loginid가 안먹혀서 밑으로 내림
			ObservableList<Board> boards = BoardDao.getBoardDao().myboardlist(loginid);
			myboardlist.setItems(boards);
			TableColumn tc = myboardlist.getColumns().get(0);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_no")); //여기에있는건 sql과 같아야됨
				 tc = myboardlist.getColumns().get(1);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_title"));
				 tc = myboardlist.getColumns().get(2);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_date"));
				 tc = myboardlist.getColumns().get(3);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_view"));
//			
		//3-2. 내가 등록한 제품 가져오기	MemberDao의 10~11번 참고
			int m_no = MemberDao.getMemberDao().mnocheck(loginid);
			ObservableList<Product> products = ProductDao.getProductDao().myproductlist(m_no);
			
			myproductlist.setItems(products);
			
			tc = myproductlist.getColumns().get(0); //보드이름 안바꿔서 오류났었음
				tc.setCellValueFactory(new PropertyValueFactory<>("p_name"));
			tc = myproductlist.getColumns().get(1);
				tc.setCellValueFactory(new PropertyValueFactory<>("p_category"));
			tc = myproductlist.getColumns().get(2);
				tc.setCellValueFactory(new PropertyValueFactory<>("p_price"));
			tc = myproductlist.getColumns().get(3);
				tc.setCellValueFactory(new PropertyValueFactory<>("activation"));
			tc = myproductlist.getColumns().get(4);
				tc.setCellValueFactory(new PropertyValueFactory<>("p_date"));
				
		//2. DB에서 찾은 회원정보 레이블에 넣기
		lblid.setText(member.getM_id());
		lblname.setText(member.getM_name());
		lblemail.setText(member.getM_email());
		lblpoint.setText(member.getM_point()+"점"); // ""을 붙여줘서 int -> String으로 자동형변환
	}
	
		@FXML
	    private Button btndelete;
		
	    @FXML
	    private Button btnupdate;

	    @FXML
	    private Label lblemail;

	    @FXML
	    private Label lblid;
	    
	    @FXML
	    private Label lblpoint;
	    
	    @FXML
	    private Label lblname;
	    
	    @FXML
	    private TableView<Board> myboardlist;

	    @FXML
	    private TableView<Product> myproductlist;


	    @FXML
	    void delete(ActionEvent event) { // 회원탈퇴
	    	//1. 메시지창 띄우기
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("알림");
	    	alert.setHeaderText("정말 회원탈퇴 하시겠습니까?");
	    	
	    	Optional<ButtonType> optional = alert.showAndWait();
	    	if(optional.get() == ButtonType.OK) { // ok버튼을 누르면
	    		//회원 탈퇴 DB에서 진행
	    		boolean result = MemberDao.getMemberDao().delete(lblid.getText());
	    		//MemberDao클래스의 delete메소드 실행
	    		//.getMemberDao는 해당 메소드를 실행하기 위해 생성한 객체 메소드
	    		
	    		
	    		Alert alert2 = new Alert(AlertType.INFORMATION);
	    		
	    		
	    		if(result) {
	    			alert2.setHeaderText("회원탈퇴 되었습니다.");
	    			alert2.setTitle("알림");
	    			alert2.showAndWait();
	    			//로그아웃
	    			//1. main 스테이지 숨기기
	        		btndelete.getScene().getWindow().hide();
	        		
	        		//2. 스테이지 열기
	        		Stage stage = new Stage();
	        		try{
	        		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
	        		Scene scene = new Scene(parent);
	        		stage.setScene(scene);
	        			stage.setResizable(false);
	        			stage.setTitle("Trip To There");
	        			
	        			Image image = new Image("C:\\Users\\505\\git\\Ezen_javaFx_Study\\src\\fxml\\login_airplane.png");
	        			stage.getIcons().add(image);
	        		stage.show();
	        		}catch(Exception e) {
	        			
	        		}
	        		
	    		}else {
	    			alert2.setHeaderText("DB오류 [관리자에게 문의");
	    			alert2.setTitle("알림");
	    			alert2.showAndWait();
	    		}
	    		
	    	}
	    }

	    @FXML
	    void update(ActionEvent event) {
	    	MainpageController.getinstance().loadpage("myinfoupdate");
	    	
	    }
}
