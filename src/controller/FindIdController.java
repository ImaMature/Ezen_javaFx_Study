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

public class FindIdController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblconfirm.setText(" "); // 라벨값(빨간글씨) 공백으로 초기화
		
	}
	
	@FXML
    private Label btnback;

    @FXML
    private Button btnfindid;

    @FXML
    private AnchorPane findidpane;

    @FXML
    private Label lblconfirm;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtname;

    @FXML
    void back(MouseEvent event) {
    	LoginController.getinstance().loadpage("login");
    }

    @FXML
    void findid(ActionEvent event) {
    	String result = MemberDao.getMemberDao().findid(txtname.getText(), txtemail.getText());
    	
    	if(result != null) {
    		lblconfirm.setText("회원님의 아이디 : " + result);
    	}else {
    		lblconfirm.setText("일치하는 아이디가 없습니다.");
    	}
    }
}
