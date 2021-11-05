package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.BoardDao;
import dao.MemberDao;
import dao.ProductDao;
import domain.Board;
import domain.Member;
import domain.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyinfoController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
										//
		
		
		//1. �α��ε� ���̵��� DB���� ȸ������ ã��
		String loginid = MainpageController.getinstance().getloginid();
		Member member = MemberDao.getMemberDao().getmember(loginid);
//		
		//3-1. ���̺�信 ���� �� �� �������� (�����Ѹ���̳� ���̺� ��ﶧ)
			//���������� loginid�� �ȸ����� ������ ����
			ObservableList<Board> boards = BoardDao.getBoardDao().myboardlist(loginid);
			myboardlist.setItems(boards);
			TableColumn tc = myboardlist.getColumns().get(0);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_no")); //���⿡�ִ°� sql�� ���ƾߵ�
				 tc = myboardlist.getColumns().get(1);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_title"));
				 tc = myboardlist.getColumns().get(2);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_date"));
				 tc = myboardlist.getColumns().get(3);
				tc.setCellValueFactory(new PropertyValueFactory<>("b_view"));
//			
		//3-2. ���� ����� ��ǰ ��������	MemberDao�� 10~11�� ����
			int m_no = MemberDao.getMemberDao().mnocheck(loginid);
			ObservableList<Product> products = ProductDao.getProductDao().myproductlist(m_no);
			
			myproductlist.setItems(products);
			
			tc = myproductlist.getColumns().get(0); //�����̸� �ȹٲ㼭 ����������
				tc.setCellValueFactory(new PropertyValueFactory<>("p_name"));
			tc = myproductlist.getColumns().get(1);
				tc.setCellValueFactory(new PropertyValueFactory<>("p_category"));
			tc = myproductlist.getColumns().get(2);
				tc.setCellValueFactory(new PropertyValueFactory<>("p_price"));
			tc = myproductlist.getColumns().get(3);
				tc.setCellValueFactory(new PropertyValueFactory<>("activation"));
			tc = myproductlist.getColumns().get(4);
				tc.setCellValueFactory(new PropertyValueFactory<>("p_date"));
				
		//2. DB���� ã�� ȸ������ ���̺� �ֱ�
		lblid.setText(member.getM_id());
		lblname.setText(member.getM_name());
		lblemail.setText(member.getM_email());
		lblpoint.setText(member.getM_point()+"��"); // ""�� �ٿ��༭ int -> String���� �ڵ�����ȯ
	}
	
		@FXML
	    private Button btndelete;
		
	    @FXML
	    private Button btnupdate;

	    @FXML
	    private Label lblemail;

	    @FXML
	    private Label lblid;
	    
	    @FXML
	    private Label lblpoint;
	    
	    @FXML
	    private Label lblname;
	    
	    @FXML
	    private TableView<Board> myboardlist;

	    @FXML
	    private TableView<Product> myproductlist;


	    @FXML
	    void delete(ActionEvent event) { // ȸ��Ż��
	    	//1. �޽���â ����
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("�˸�");
	    	alert.setHeaderText("���� ȸ��Ż�� �Ͻðڽ��ϱ�?");
	    	
	    	Optional<ButtonType> optional = alert.showAndWait();
	    	if(optional.get() == ButtonType.OK) { // ok��ư�� ������
	    		//ȸ�� Ż�� DB���� ����
	    		boolean result = MemberDao.getMemberDao().delete(lblid.getText());
	    		//MemberDaoŬ������ delete�޼ҵ� ����
	    		//.getMemberDao�� �ش� �޼ҵ带 �����ϱ� ���� ������ ��ü �޼ҵ�
	    		
	    		
	    		Alert alert2 = new Alert(AlertType.INFORMATION);
	    		
	    		
	    		if(result) {
	    			alert2.setHeaderText("ȸ��Ż�� �Ǿ����ϴ�.");
	    			alert2.setTitle("�˸�");
	    			alert2.showAndWait();
	    			//�α׾ƿ�
	    			//1. main �������� �����
	        		btndelete.getScene().getWindow().hide();
	        		
	        		//2. �������� ����
	        		Stage stage = new Stage();
	        		try{
	        		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
	        		Scene scene = new Scene(parent);
	        		stage.setScene(scene);
	        			stage.setResizable(false);
	        			stage.setTitle("Trip To There");
	        			
	        			Image image = new Image("C:\\Users\\505\\git\\Ezen_javaFx_Study\\src\\fxml\\login_airplane.png");
	        			stage.getIcons().add(image);
	        		stage.show();
	        		}catch(Exception e) {
	        			
	        		}
	        		
	    		}else {
	    			alert2.setHeaderText("DB���� [�����ڿ��� ����");
	    			alert2.setTitle("�˸�");
	    			alert2.showAndWait();
	    		}
	    		
	    	}
	    }

	    @FXML
	    void update(ActionEvent event) {
	    	MainpageController.getinstance().loadpage("myinfoupdate");
	    	
	    }
}
