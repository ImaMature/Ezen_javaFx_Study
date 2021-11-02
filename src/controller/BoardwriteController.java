package controller;

import dao.BoardDao;
import domain.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BoardwriteController {

	   @FXML
	    private Button btncancle;

	    @FXML
	    private Button btnwrite;

	    @FXML
	    private TextArea txtcontents;

	    @FXML
	    private TextField txttitle;

	    @FXML
	    void cancel(MouseEvent event) {
	    	MainpageController.getinstance().loadpage("boardlist");
	    }
	    @FXML
	    void write(MouseEvent event) {
	    	Board board = new Board(txttitle.getText(), txtcontents.getText(), MainpageController.getinstance().getloginid());
	    	
	    	
	    	boolean result = BoardDao.getBoardDao().write(board);
	    	
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	
	    	if(result) {
	    		alert.setHeaderText("�Խù� ��� ����");
	    		alert.showAndWait();
	    		MainpageController.getinstance().loadpage("boardlst");
	    	}else {
	    		alert.setHeaderText("�Խù� ��� ���� [�����ڿ��� ����]");
	    		alert.showAndWait();
	    	}
	    }
}
