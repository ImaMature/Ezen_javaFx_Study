package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.MemberDao;
import domain.Member;
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

public class MyinfoController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		//1. �α��ε� ���̵��� DB���� ȸ������ ã��
		String loginid = MainpageController.getinstance().getloginid();
		Member member = MemberDao.getMemberDao().getmember(loginid);
		
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
