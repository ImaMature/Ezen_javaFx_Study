package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BoardviewController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	   @FXML
	    private Button btncancel;
	   	
	    @FXML
	    private Button btndelete;

	    @FXML
	    private Button btnreplywrite;

	    @FXML
	    private Button btnupdate;

	    @FXML
	    private TableView<?> replylist;

	    @FXML
	    private TextArea txtcontents;

	    @FXML
	    private TextField txttitle;

	    @FXML
	    void cancel(ActionEvent event) {
	    	MainpageController.getinstance().loadpage("boardlist");
	    }

	    @FXML
	    void delete(ActionEvent event) {

	    }

	    @FXML
	    void replywrite(ActionEvent event) {

	    }

	    @FXML
	    void update(ActionEvent event) {

	    }
}
