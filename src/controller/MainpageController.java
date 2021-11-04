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
		loadpage("home");// mainpage 실행 가운데 home.fxml 배치
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

    //선언
    public static MainpageController instance;
    //생성자
    public MainpageController() {
    	instance = this; // [ 현재 클래스의 모든 멤버 포함 ] 
    }
    
    //객체 변환
    public static MainpageController getinstance() {
    	return instance;
    }
    
    public String getloginid() {// 로그인 레이블아이디를 빼오는 메소드
    	return lblloginid.getText();
    }
    	 
    
    
    
    //가운데 pane 변경하는 메소드
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
    	alert.setContentText("로그아웃");
    	alert.setHeaderText("로그아웃 하시겠습니까?");
    	alert.setTitle("확인");
    	
    	//메시Optional<T>을 때 [ Optional 클래스 : null 
    	Optional<ButtonType> optional = alert.showAndWait();
    	if(optional.get() == ButtonType.OK) {
    		
    		
    		//1. main 스테이지 숨기기
    		btnlogout.getScene().getWindow().hide();
    		
    		//2. 스테이지 열기
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

    @FXML // mainpage에서 product버튼 누르면 productlist로 이동
    void product(ActionEvent event) {

    	loadpage("productlist"); // 내가 만든 fx파일명 loadpage메소드에있는 내용을 오버라이드해서 갖다가 쓰는거임
    }
    
	
}
