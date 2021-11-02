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
		
		//1. DAO ȣ��
		ObservableList<Board> boards = BoardDao.getBoardDao().boardlist();
		
		//2. ���̺� ���� �ʵ� ��������
		//���� ���̺� �信 0��° �÷���(No.)�� tc�� �����ϱ�
		TableColumn tc = boardlist.getColumns().get(0); // ���̺� ���� ù��° �ʵ�
		tc.setCellValueFactory(new PropertyValueFactory<>("b_no")); // ��ü �� �ʵ��. domain��Ű�� BoardŬ���� ����
		
			tc = boardlist.getColumns().get(1); // ���̺� ���� 2��° �ʵ�
			tc.setCellValueFactory(new PropertyValueFactory<>("b_title"));
			
			tc = boardlist.getColumns().get(2); // ���̺� ���� 3��° �ʵ�
			tc.setCellValueFactory(new PropertyValueFactory<>("b_write"));
			
			tc = boardlist.getColumns().get(3); // ���̺� ���� 4��° �ʵ�
			tc.setCellValueFactory(new PropertyValueFactory<>("b_date"));
			
			tc = boardlist.getColumns().get(4); // ���̺� ���� 5��° �ʵ�
			tc.setCellValueFactory(new PropertyValueFactory<>("b_view"));
		
		//3. ���̺� �信 ����Ʈ ����
		boardlist.setItems(boards);
		
		//4. Ŭ���� �������� ������ ������ ��ȯ
		//�μ� -> ���� : �͸�޼ҵ�(���ٽ�)[1ȸ�� �޼ҵ�] , ��ȯŸ�� ����, public����
		boardlist.setOnMouseClicked(e -> {
			
												//e��� �̺�Ʈ�� Ŭ���̸�  
			if(e.getButton().equals(MouseButton.PRIMARY)) {//PRIMARY:�⺻ Ŭ��
				board = boardlist.getSelectionModel().getSelectedItem();//���õ� �������� board�� ���
											//���̺� �信 ���õ� ���� ������[��ü]
				//System.out.println(board.toString()); // Ŀ�´�Ƽ�� �Խñ� ������ �������� Ȯ���ϴ� ��
				MainpageController.getinstance().loadpage("boardview");
				
				
				//��ȸ�� ����
			}
		});
				
	}
	
	//���� Ŭ������ ��üȭ (��� �ֵ� �������)
	public static Board board;
	
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
