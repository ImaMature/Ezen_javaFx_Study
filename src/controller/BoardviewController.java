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
	Board board = BoardlistController.board; //board�� ���������� ����

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//��ȸ�� ���� �޼ҵ� ȣ��
			BoardDao.getBoardDao().viewupdate(board.getB_no());
			
			//���� ��ȸ
		txttitle.setText(board.getB_title());
		txtcontents.setText(board.getB_contents());
		lblwirter.setText("�ۼ��� : " + board.getB_write() );
		lbldate.setText("�ۼ��� : "+board.getB_date().split(" ")[0]);
		//date�� �ð����� ����, ��¥�� ������ ������ ���°� split
		//split " " ������ �������� ���������� ����� �ε��� 0 ��° ���� ��������
		//2021-11-02 15:36:54 ���� ������ �������� �ѷγ���� 0��° ��¥�� ����
		lblview.setText("��ȸ�� : " + board.getB_view());
		
		
		
		if(!MainpageController.getinstance().getloginid().equals(board.getB_write())) {
			//�Խù� �ۼ��ڿ� �α��ε� ���̵� �ٸ� ���
				//��ư �����
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
	    	alert.setHeaderText("�ش� �Խù��� �����Ͻðڽ��ϱ�?");
	    	alert.setTitle("�˸�");
	    	Optional<ButtonType> optional = alert.showAndWait();
	    	if(optional.get() == ButtonType.OK) {
	    		//���� ����
	    		boolean result = BoardDao.getBoardDao().delete(board.getB_no());
	    		if(result) {
	    			alert.setHeaderText("�Խù��� �����Ǿ����ϴ�.");
	    			alert.setTitle("�˸�");
	    			alert.showAndWait();
	    			MainpageController.getinstance().loadpage("boardlist");
	    		}else {
	    			alert.setHeaderText("�����ڿ��� ����");
	    			alert.setTitle("�˸�");
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
