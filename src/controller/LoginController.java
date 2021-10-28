package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class LoginController implements Initializable{

		private static LoginController instance;
		
		public LoginController() {
			instance = this;
		}
		public static LoginController getinstance() {
			return instance;
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

	    }

	    @FXML
	    void findpassword(MouseEvent event) {

	    }

	    @FXML
	    void login(ActionEvent event) {
	    	if(txtid.getText().equals("admin") && txtpassword.getText().equals("1234")) {
	    		lblconfirm.setText("로그인 성공");
	    	}else {
	    		lblconfirm.setText("동일한 회원정보가 없습니다.");
	    	}
	    }

	    @FXML
	    void signup(MouseEvent event) {

	    	loadpage("signup");
	    }

		public void loadpage(String page) {
			try {
				Parent parent = FXMLLoader.load(getClass().getResource("/fxml/"+page+".fxml"));
				mainboardpane.setCenter(parent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
