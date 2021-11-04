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
	Board board = BoardlistController.board; //board�� ���������� ����

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//���̺� �ε� �޼ҵ� ȣ�� 
		replytableload();
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
		lblview.setText("��ȸ�� : " + board.getB_view() + 1);
		
		
		
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
	    private Button btnupdate;
	    
	    @FXML
	    private Button btnreplywrite;

	    @FXML
	    private TableView<Reply> replylist;

	    //���̺� �� �ε� �޼ҵ� [�޼ҵ�ȭ�ϴ� ���� ���ΰ�ħ�� �ؼ� �ݺ���� �����ϰ� �ϱ�����]
	    public void replytableload() {
	    	ObservableList<Reply> replys = BoardDao.getBoardDao().replylist(board.getB_no());
	    	
	    	TableColumn tc = replylist.getColumns().get(0); // ���̺��� ù��° ���� ���� �����ͼ�
	    	tc.setCellValueFactory(new PropertyValueFactory<>("r_no")); // �ش� ���� ���丮���� ����Ʈ���� �����ϴ� ��ü �ʵ�� ���� ������
	    	
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
	    Reply reply = new Reply(
	    		txtreply.getText(), 
	    		MainpageController.getinstance().getloginid(), 
	    		board.getB_no()
	    		);
	    
	    	//DBó��
	    	boolean result = BoardDao.getBoardDao().replywrite(reply);
	    	
		    if(result) {
		    	Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setHeaderText("��۵��");
		    	alert.showAndWait();
		    	
		    	replytableload();
		    	txtreply.setText(" ");
		    }else {
		    	System.out.println(" ���� ");
		    	System.out.println( reply.getR_contents() );
		    	System.out.println( reply.getR_write() );
		    	System.out.println( reply.getB_no() );
		    	
		    }
	    }
	    
	    boolean upcheck = true; // ������Ʈ ��ư ����ġ ���� true�� false��

	    @FXML
	    void update(ActionEvent event) {
	    	//title�� contents�� editable, disable �����ִ� ���°� ������
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	if(upcheck) { // true�϶� �����
		    	alert.setHeaderText("���� ���� �� �ٽ� ��ư Ŭ�� �� �����Ϸᰡ �˴ϴ�.");
		    	alert.showAndWait();
		    	
		    	txttitle.setEditable(true);
		    	txtcontents.setEditable(true);
		    	upcheck =false;
	    	}
	    	else { //false �϶� �����
	    		BoardDao.getBoardDao().update(board.getB_no(), txttitle.getText(), txtcontents.getText());
	    		
	    		alert.setHeaderText("�Խù��� �����Ǿ����ϴ�.");
	    		alert.showAndWait();
	    		
	    		upcheck = true;
	    		txttitle.setEditable(false);
	    		txtcontents.setEditable(false);
	    	}
	    }
}
