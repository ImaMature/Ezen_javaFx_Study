package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainpageController implements Initializable {
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lblloginid.setText( LoginController.getinstance().getid() );
		loadpage("home");// mainpage ���� ��� home.fxml ��ġ
	}
	@FXML
    private Label btnback;
	
    @FXML
    private Button btnchatting;

    @FXML
    private Button btncommunity;

    @FXML
    private ImageView btnhome;

    @FXML
    private Button btnlogout;

    @FXML
    private Button btnmyinfo;

    @FXML
    private Button btnproduct;

    @FXML
    private AnchorPane cp;

    @FXML
    private Label lblloginid;

   
    @FXML
    private AnchorPane lp;

    @FXML
    private BorderPane mainpageborderpane;

    @FXML
    void back(MouseEvent event) {
    	//LoginController.getinstance().loadpage("login");
    }

    //����
    public static MainpageController instance;
    //������
    public MainpageController() {
    	instance = this; // [ ���� Ŭ������ ��� ��� ���� ] 
    }
    
    //��ü ��ȯ
    public static MainpageController getinstance() {
    	return instance;
    }
    
    public String getloginid() {// �α��� ���̺���̵� ������ �޼ҵ�
    	return lblloginid.getText();
    }
    	 
    
    
    
    //��� pane �����ϴ� �޼ҵ�
    public void loadpage(String page) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fxml/"+page+".fxml"));
			mainpageborderpane.setCenter(parent);
		} catch (Exception e) {
			
		}
    }
    @FXML
    void chatting(ActionEvent event) {
    	loadpage("chatting");
    	
    }

    @FXML
    void community(ActionEvent event) {
    	loadpage("boardlist");
    }

    @FXML
    void home(MouseEvent event) {
    	loadpage("home");
    }

    @FXML
    void logout(ActionEvent event) {

    	Alert alert = new Alert( AlertType.CONFIRMATION);
    	alert.setContentText("�α׾ƿ�");
    	alert.setHeaderText("�α׾ƿ� �Ͻðڽ��ϱ�?");
    	alert.setTitle("Ȯ��");
    	
    	//�޽�Optional<T>�� �� [ Optional Ŭ���� : null 
    	Optional<ButtonType> optional = alert.showAndWait();
    	if(optional.get() == ButtonType.OK) {
    		
    		
    		//1. main �������� �����
    		btnlogout.getScene().getWindow().hide();
    		
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
    			System.out.println(e);
    		}
    	}
    }

    @FXML
    void myinfo(ActionEvent event) {
    	loadpage("myinfo");
    }

    @FXML // mainpage���� product��ư ������ productlist�� �̵�
    void product(ActionEvent event) {

    	loadpage("productlist"); // ���� ���� fx���ϸ� loadpage�޼ҵ忡�ִ� ������ �������̵��ؼ� ���ٰ� ���°���
    }
    
	
}
