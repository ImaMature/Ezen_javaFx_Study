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
		
		//1. DAO 호출
		ObservableList<Board> boards = BoardDao.getBoardDao().boardlist();
		
		//2. 테이블 뷰의 필드 가져오기
		//현재 테이블 뷰에 0번째 컬럼값(No.)을 tc에 저장하기
		TableColumn tc = boardlist.getColumns().get(0);
		//tc로 b_no라고 설정하기
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
	  				//Table에 넣을 객체의 클래스명 : 제네릭
	  
	    @FXML
	    private Button btnwrite;

	    @FXML
	    void write(ActionEvent event) {
	    	MainpageController.getinstance().loadpage("boardwrite");
	    }
}
