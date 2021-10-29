package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import domain.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SignupController implements Initializable{
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 로그인 실패 레이블의 초기값을 없애려고 하는 것
		lblconfirm.setText("");
	}
	
	  @FXML
	    private Button btinsignup;

	    @FXML
	    private Label btnback;

	    @FXML
	    private Label lblconfirm;

	    @FXML
	    private AnchorPane signuppane;

	    @FXML
	    private TextField txtemail;

	    @FXML
	    private TextField txtid;

	    @FXML
	    private TextField txtname;

	    @FXML
	    private PasswordField txtpassword;

	    @FXML
	    private PasswordField txtpasswordconfirm;

	  
	    @FXML
	    void signup(ActionEvent event) {
	    	//0 컨트롤 호출시 => fx:id 호출 [ 객체]
	    	
	    	//1. 유효성 검사
	    		//1. 아이디 길이 체크
	    		if(txtid.getText().length() < 4 || txtid.getText().length() > 13) {
	    			lblconfirm.setText("아이디는 4 ~ 12 글자만 가능합니다."); //레이블 내용 변경
	    			return; // 메소드 끝
	    		}
	    		//2. 패스워드 길이 체크
	    		if(txtpassword.getText().length() < 4 || txtpassword.getText().length() > 8) {
	    			lblconfirm.setText("비밀번호는 4 ~ 8 글자만 가능합니다."); // 레이블 내용 변경
	    			return; // 메소드 끝
	    		}
	    		//3. 패스워드 동일 체크
	    		if(!txtpassword.getText().equals(txtpasswordconfirm.getText())) {
	    			//!:부정 [반대]
	    			lblconfirm.setText("비밀번호가 동일하지 않습니다.");
	    			return; // 메소드 끝
	    		}
	    		
	    		//4. 이름길이 체크
	    		if(txtname.getText().length() < 2) {
	    			lblconfirm.setText("이름은 2글자 이상으로만 가능합니다.");
	    			return;
	    		}
	    		//5. 이메일 길이 체크
	    		if(txtemail.getText().length() < 5 || !txtemail.getText().contains("@")) {
	    			lblconfirm.setText("이메일 형식으로 입력해주세요.");
	    			return;
	    		}
	    		
	    	//2. 중복체크
	    	//3. 객체화
	    	Member member = new Member(txtid.getText(), txtpassword.getText(), txtname.getText(), txtemail.getText());
	    	
	    	//4. 파일 혹은 DB처리 (메소드화)
	    	boolean result = MemberDao.getMemberDao().signup(member);// MemberDao에 signup메소드에 member를 던져줌(호출)
	    	if(result) { //true 값 리턴시
	    		
	    	//5. 메시지 창 띄우고 페이지 전환
	    	lblconfirm.setText("가입해주셔서 감사합니다."); // 라벨바꾸는거
	    	
		    	Alert alert = new Alert( AlertType.INFORMATION); // 경고창 띄우기, AlertType.information은 경고 아이콘띄우기
		    	alert.setContentText("Trip To There 가입을 축하드립니다. [포인트 1000 지급]"); // 메시지 내용
		    	alert.setHeaderText("회원가입성공"); // 메시지 제목
		    	alert.setTitle("알림"); // 메시지 창 제목
		    	alert.showAndWait();// 메시지 띄우고 버튼 누르기까지 대기
		    	LoginController.getinstance().loadpage("login");
		    	
	    	
	    	}else { // false값 리턴시
	    		lblconfirm.setText("회원가입 실패 [ 관리자에게 문의 ]");
	    	}
	    }

	    @FXML
	    void back(MouseEvent event) {
	    	LoginController.getinstance().loadpage("login");
	    }
	    
}
