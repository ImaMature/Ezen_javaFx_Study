package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import domain.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SignupController implements Initializable{
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // �α��� ���� ���̺��� �ʱⰪ�� ���ַ��� �ϴ� ��
		lblconfirm.setText("");
	}
	
	  @FXML
	    private Button btinsignup;

	    @FXML
	    private Label btnback;

	    @FXML
	    private Label lblconfirm;

	    @FXML
	    private AnchorPane signuppane;

	    @FXML
	    private TextField txtemail;

	    @FXML
	    private TextField txtid;

	    @FXML
	    private TextField txtname;

	    @FXML
	    private PasswordField txtpassword;

	    @FXML
	    private PasswordField txtpasswordconfirm;

	  
	    @FXML
	    void signup(ActionEvent event) {
	    	//0 ��Ʈ�� ȣ��� => fx:id ȣ�� [ ��ü]
	    	
	    	//1. ��ȿ�� �˻�
	    		//1. ���̵� ���� üũ
	    		if(txtid.getText().length() < 4 || txtid.getText().length() > 13) {
	    			lblconfirm.setText("���̵�� 4 ~ 12 ���ڸ� �����մϴ�."); //���̺� ���� ����
	    			return; // �޼ҵ� ��
	    		}
	    		//2. �н����� ���� üũ
	    		if(txtpassword.getText().length() < 4 || txtpassword.getText().length() > 8) {
	    			lblconfirm.setText("��й�ȣ�� 4 ~ 8 ���ڸ� �����մϴ�."); // ���̺� ���� ����
	    			return; // �޼ҵ� ��
	    		}
	    		//3. �н����� ���� üũ
	    		if(!txtpassword.getText().equals(txtpasswordconfirm.getText())) {
	    			//!:���� [�ݴ�]
	    			lblconfirm.setText("��й�ȣ�� �������� �ʽ��ϴ�.");
	    			return; // �޼ҵ� ��
	    		}
	    		
	    		//4. �̸����� üũ
	    		if(txtname.getText().length() < 2) {
	    			lblconfirm.setText("�̸��� 2���� �̻����θ� �����մϴ�.");
	    			return;
	    		}
	    		//5. �̸��� ���� üũ
	    		if(txtemail.getText().length() < 5 || !txtemail.getText().contains("@")) {
	    			lblconfirm.setText("�̸��� �������� �Է����ּ���.");
	    			return;
	    		}
	    		
	    	//2. �ߺ�üũ
	    	//3. ��üȭ
	    	Member member = new Member(txtid.getText(), txtpassword.getText(), txtname.getText(), txtemail.getText());
	    	
	    	//4. ���� Ȥ�� DBó�� (�޼ҵ�ȭ)
	    	boolean result = MemberDao.getMemberDao().signup(member);// MemberDao�� signup�޼ҵ忡 member�� ������(ȣ��)
	    	if(result) { //true �� ���Ͻ�
	    		
	    	//5. �޽��� â ���� ������ ��ȯ
	    	lblconfirm.setText("�������ּż� �����մϴ�."); // �󺧹ٲٴ°�
	    	
		    	Alert alert = new Alert( AlertType.INFORMATION); // ���â ����, AlertType.information�� ��� �����ܶ���
		    	alert.setContentText("Trip To There ������ ���ϵ帳�ϴ�. [����Ʈ 1000 ����]"); // �޽��� ����
		    	alert.setHeaderText("ȸ�����Լ���"); // �޽��� ����
		    	alert.setTitle("�˸�"); // �޽��� â ����
		    	alert.showAndWait();// �޽��� ���� ��ư ��������� ���
		    	LoginController.getinstance().loadpage("login");
		    	
	    	
	    	}else { // false�� ���Ͻ�
	    		lblconfirm.setText("ȸ������ ���� [ �����ڿ��� ���� ]");
	    	}
	    }

	    @FXML
	    void back(MouseEvent event) {
	    	LoginController.getinstance().loadpage("login");
	    }
	    
}
