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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class BoardlistController implements Initializable{
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//1. DAO 호출
		ObservableList<Board> boards = BoardDao.getBoardDao().boardlist();
		
		//2. 테이블 뷰의 필드 가져오기
		//현재 테이블 뷰에 0번째 컬럼값(No.)을 tc에 저장하기
		TableColumn tc = boardlist.getColumns().get(0); // 테이블 뷰의 첫번째 필드
		tc.setCellValueFactory(new PropertyValueFactory<>("b_no")); // 객체 내 필드명. domain패키지 Board클래스 참고
		
			tc = boardlist.getColumns().get(1); // 테이블 뷰의 2번째 필드
			tc.setCellValueFactory(new PropertyValueFactory<>("b_title"));
			
			tc = boardlist.getColumns().get(2); // 테이블 뷰의 3번째 필드
			tc.setCellValueFactory(new PropertyValueFactory<>("b_write"));
			
			tc = boardlist.getColumns().get(3); // 테이블 뷰의 4번째 필드
			tc.setCellValueFactory(new PropertyValueFactory<>("b_date"));
			
			tc = boardlist.getColumns().get(4); // 테이블 뷰의 5번째 필드
			tc.setCellValueFactory(new PropertyValueFactory<>("b_view"));
		
		//3. 테이블 뷰에 리스트 설정
		boardlist.setItems(boards);
		
		//4. 클릭한 아이템을 가지고 페이지 전환
		//인수 -> 정의 : 익명메소드(람다식)[1회성 메소드] , 반환타입 없고, public없고
		boardlist.setOnMouseClicked(e -> {
			
												//e라는 이벤트가 클릭이면  
			if(e.getButton().equals(MouseButton.PRIMARY)) {//PRIMARY:기본 클릭
				board = boardlist.getSelectionModel().getSelectedItem();//선택된 아이템을 board에 담기
											//테이블 뷰에 선택된 모델의 아이템[객체]
				//System.out.println(board.toString()); // 커뮤니티에 게시글 누르면 눌리는지 확인하는 법
				MainpageController.getinstance().loadpage("boardview");
				
				
				//조회수 증가
			}
		});
				
	}
	
	//현재 클래스의 객체화 (어디에 둬도 상관없음)
	public static Board board;
	
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
