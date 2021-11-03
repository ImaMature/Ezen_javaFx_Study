package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.BoardDao;
import dao.MemberDao;
import domain.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BoardviewController implements Initializable{
	Board board = BoardlistController.board; //board를 전역변수로 설정

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//조회수 증감 메소드 호출
			BoardDao.getBoardDao().viewupdate(board.getB_no());
			
			//개별 조회
		txttitle.setText(board.getB_title());
		txtcontents.setText(board.getB_contents());
		lblwirter.setText("작성자 : " + board.getB_write() );
		lbldate.setText("작성일 : "+board.getB_date().split(" ")[0]);
		//date는 시간까지 나옴, 날짜만 빼오고 싶을때 쓰는거 split
		//split " " 공백을 기점으로 두조각으로 나뉘어서 인덱스 0 번째 값을 가져오기
		//2021-11-02 15:36:54 여기 공백을 기점으로 둘로나뉘어서 0번째 날짜만 빼옴
		lblview.setText("조회수 : " + board.getB_view());
		
		
		
		if(!MainpageController.getinstance().getloginid().equals(board.getB_write())) {
			//게시물 작성자와 로그인된 아이디가 다를 경우
				//버튼 숨기기
			btndelete.setVisible(false);
			btnupdate.setVisible(false);
		}
		
		
		
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
	    private Label lbldate;

	    @FXML
	    private Label lblview;

	    @FXML
	    private Label lblwirter;
	    
	    @FXML
	    void cancel(ActionEvent event) {
	    	MainpageController.getinstance().loadpage("boardlist");
	    }

	    @FXML
	    void delete(ActionEvent event) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setHeaderText("해당 게시물을 삭제하시겠습니까?");
	    	alert.setTitle("알림");
	    	Optional<ButtonType> optional = alert.showAndWait();
	    	if(optional.get() == ButtonType.OK) {
	    		//삭제 진행
	    		boolean result = BoardDao.getBoardDao().delete(board.getB_no());
	    		if(result) {
	    			alert.setHeaderText("게시물이 삭제되었습니다.");
	    			alert.setTitle("알림");
	    			alert.showAndWait();
	    			MainpageController.getinstance().loadpage("boardlist");
	    		}else {
	    			alert.setHeaderText("관리자에게 문의");
	    			alert.setTitle("알림");
	    			alert.showAndWait();
	    		}
	    	}
	    }

	    @FXML
	    void replywrite(ActionEvent event) {

	    }

	    @FXML
	    void update(ActionEvent event) {

	    }
}
