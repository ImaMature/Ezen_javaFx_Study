package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FindPasswordController implements Initializable{
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblconfirm.setText(" ");
	}
	
	 @FXML
	    private Label btnback;

	    @FXML
	    private Button btnfindpassword;

	    @FXML
	    private AnchorPane findpasswordpane;

	    @FXML
	    private Label lblconfirm;

	    @FXML
	    private TextField txtemail;

	    @FXML
	    private TextField txtid;

	    @FXML
	    void back(MouseEvent event) {
	     LoginController.getinstance().loadpage("login");
	    }

	    @FXML
	    void findpassword(ActionEvent event) {
	    	String result = MemberDao.getMemberDao().findpassword(txtid.getText(), txtemail.getText());
	    	if (result != null) {
	    		lblconfirm.setText("회원님의 비밀번호 : " + result);
	    	}else {
	    		lblconfirm.setText("일치하는 비밀번호가 없습니다.");
	    	}
	    }

}
