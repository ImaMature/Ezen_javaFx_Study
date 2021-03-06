package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import domain.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController implements Initializable{

		private static LoginController instance;
		
		public LoginController() {
			instance = this;
		}
		public static LoginController getinstance() {
			return instance;
		}
		// instance = new
		
		// 4. 로그인시 입력된 아이디 반환 
		public String getid() {
			return txtid.getText();
			
		}
		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			lblconfirm.setText("");
		}
	
	  @FXML
	    private Label btnfindid;

	    @FXML
	    private Label btnfindpassword;

	    @FXML
	    private Button btnlogin;

	    @FXML
	    private Button btnsignup;

	    @FXML
	    private Label lblconfirm;

	    @FXML
	    private AnchorPane loginpane;

	    @FXML
	    private BorderPane mainboardpane;

	    @FXML
	    private TextField txtid;

	    @FXML
	    private PasswordField txtpassword;

	    @FXML
	    void findid(MouseEvent event) {
	    	loadpage("findid");
	    }

	    @FXML
	    void findpassword(MouseEvent event) {
	    	loadpage("findpassword");
	    }

	    @FXML
	    void login(ActionEvent event) {
	    	//1. Dao객체 메소드 호출
	    	boolean result = MemberDao.getMemberDao().login(txtid.getText(), txtpassword.getText());
	    	
	    	//2. txt 입력된 아이디와 패스워드 회원과 동일하면 로그인 성공
	    	if(result) {
	    		lblconfirm.setText("로그인 성공");
	    		
	    		MemberDao.getMemberDao().pointupdate(txtid.getText(),10); // pointupdate메소드로 인수전달
	    		
	    		btnlogin.getScene().getWindow().hide();
	    		Stage stage = new Stage();
	    		try {
	    			Parent parent = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
	    			Scene scene = new Scene(parent);
	    			stage.setScene(scene);
	    				stage.setResizable(false);
	    				stage.setTitle("Trip To There");
	    				
	    				Image image = new Image("C:\\Users\\505\\git\\Ezen_javaFx_Study\\src\\fxml\\login_airplane.png");
	    				stage.getIcons().add(image);
	    			stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}else {
	    		lblconfirm.setText("동일한 회원정보가 없습니다.");
	    	}
	    	
//	    	if(txtid.getText().equals("admin") && txtpassword.getText().equals("1234")) {
//	    		lblconfirm.setText("로그인 성공");
//	    	}else {
//	    		lblconfirm.setText("동일한 회원정보가 없습니다.");
//	    	}
	    }

	    @FXML
	    void signup(MouseEvent event) {

	    	loadpage("signup"); //signup.fxml을 열겠다는 뜻
	    }

	    public void loadpage( String page ) {
	    	try {
	    		Parent parent = FXMLLoader.load(
	    				getClass().getResource("/fxml/"+page+".fxml"));
	    		mainboardpane.setCenter(parent);
	    	}
	    	catch (Exception e) {}
	    }
	  
		
}
