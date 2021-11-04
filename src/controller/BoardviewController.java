package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.BoardDao;
import dao.MemberDao;
import domain.Board;
import domain.Reply;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BoardviewController implements Initializable{
	Board board = BoardlistController.board; //board를 전역변수로 설정

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//테이블값 로드 메소드 호출 
		replytableload();
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
		lblview.setText("조회수 : " + board.getB_view() + 1);
		
		
		
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
	    private Button btnupdate;
	    
	    @FXML
	    private Button btnreplywrite;

	    @FXML
	    private TableView<Reply> replylist;

	    //테이블 값 로드 메소드 [메소드화하는 이유 새로고침을 해서 반복사용 가능하게 하기위해]
	    public void replytableload() {
	    	ObservableList<Reply> replys = BoardDao.getBoardDao().replylist(board.getB_no());
	    	
	    	TableColumn tc = replylist.getColumns().get(0); // 테이블의 첫번째 열의 값을 가져와서
	    	tc.setCellValueFactory(new PropertyValueFactory<>("r_no")); // 해당 셀의 팩토리값을 리스트내에 존재하는 객체 필드로 집어 넣은것
	    	
	    	tc = replylist.getColumns().get(1);
	    	tc.setCellValueFactory(new PropertyValueFactory<>("r_write"));
	    	
	    	tc = replylist.getColumns().get(2);
	    	tc.setCellValueFactory(new PropertyValueFactory<>("r_contents"));
	    	
	    	tc = replylist.getColumns().get(3);
	    	tc.setCellValueFactory(new PropertyValueFactory<>("r_date"));
	    	
	    	replylist.setItems(replys);
	    }
	    
	    @FXML
	    private TextArea txtreply;

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
	    Reply reply = new Reply(
	    		txtreply.getText(), 
	    		MainpageController.getinstance().getloginid(), 
	    		board.getB_no()
	    		);
	    
	    	//DB처리
	    	boolean result = BoardDao.getBoardDao().replywrite(reply);
	    	
		    if(result) {
		    	Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setHeaderText("댓글등록");
		    	alert.showAndWait();
		    	
		    	replytableload();
		    	txtreply.setText(" ");
		    }else {
		    	System.out.println(" 실패 ");
		    	System.out.println( reply.getR_contents() );
		    	System.out.println( reply.getR_write() );
		    	System.out.println( reply.getB_no() );
		    	
		    }
	    }
	    
	    boolean upcheck = true; // 업데이트 버튼 스위치 변수 true냐 false냐

	    @FXML
	    void update(ActionEvent event) {
	    	//title과 contents가 editable, disable 꺼져있는 상태가 전제임
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	if(upcheck) { // true일때 여기로
		    	alert.setHeaderText("내용 수정 후 다시 버튼 클릭 시 수정완료가 됩니다.");
		    	alert.showAndWait();
		    	
		    	txttitle.setEditable(true);
		    	txtcontents.setEditable(true);
		    	upcheck =false;
	    	}
	    	else { //false 일때 여기로
	    		BoardDao.getBoardDao().update(board.getB_no(), txttitle.getText(), txtcontents.getText());
	    		
	    		alert.setHeaderText("게시물이 수정되었습니다.");
	    		alert.showAndWait();
	    		
	    		upcheck = true;
	    		txttitle.setEditable(false);
	    		txtcontents.setEditable(false);
	    	}
	    }
}
