package controller;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.BoardDao;
import domain.Board;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class BoardlistController implements Initializable{
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//1. DAO ȣ��
		ObservableList<Board> boards = BoardDao.getBoardDao().boardlist();
		
		//2. ���̺� ���� �ʵ� ��������
		//���� ���̺� �信 0��° �÷���(No.)�� tc�� �����ϱ�
		TableColumn tc = boardlist.getColumns().get(0);
		//tc�� b_no��� �����ϱ�
		tc.setCellValueFactory(new PropertyValueFactory<>("b_no"));
		
			tc = boardlist.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory<>("b_title"));
			
			tc = boardlist.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("b_write"));
			
			tc = boardlist.getColumns().get(3);
			tc.setCellValueFactory(new PropertyValueFactory<>("b_date"));
			
			tc = boardlist.getColumns().get(4);
			tc.setCellValueFactory(new PropertyValueFactory<>("b_view"));
		
		//3.
		boardlist.setItems(boards);
	}
	
	
	  @FXML
	    private TableView<Board> boardlist;
	  				//Table�� ���� ��ü�� Ŭ������ : ���׸�
	  
	    @FXML
	    private Button btnwrite;

	    @FXML
	    void write(ActionEvent event) {
	    	MainpageController.getinstance().loadpage("boardwrite");
	    }
}
