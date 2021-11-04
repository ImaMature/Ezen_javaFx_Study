package controller;

import java.io.File;

import domain.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ProductregisterController {

		@FXML
	    private Button btncancel;

	    @FXML
	    private Button btnimgadd;

	    @FXML
	    private Button btnregister;

	    @FXML
	    private ToggleGroup category;

	    @FXML
	    private Label lblimgpath;

	    @FXML
	    private RadioButton opt_1;

	    @FXML
	    private RadioButton opt_2;

	    @FXML
	    private RadioButton opt_3;

	    @FXML
	    private RadioButton opt_4;

	    @FXML
	    private ImageView pimg;

	    @FXML
	    private TextArea txtpcontents;

	    @FXML
	    private TextField txtpname;

	    @FXML
	    private TextField txtpprice;

	    @FXML
	    void cancel(ActionEvent event) {
	    	MainpageController.getinstance().loadpage("productlist");
	    }


	    @FXML 
	    //제품등록 메소드
	    void register(ActionEvent event) {
	    	//유효성검사 (제품명 길이나 숫자가 -거나 이런거 생략함)
	    	String pname = txtpname.getText(); //제품 이름 갖고옴
	    	String pcontents = txtpcontents.getText(); //제품 설명 갖고옴
	    	int pprice = Integer.parseInt(txtpprice.getText()); // 정수형으로 형변환함
	    	String pcategory = ""; // 먼저 공백을 넣어둘거임
	    	
	    		if(opt_1.isSelected()) //1번째 라디오버튼을 클릭했을때(isSelected), 
	    		{ pcategory="의류";}	// pcategory에 의류를 저장
	    		
	    		if(opt_2.isSelected()) {
	    			pcategory="신발";
	    		}
	    		if(opt_3.isSelected() ) {
	    			pcategory="가방";
	    		}
	    		if(opt_4.isSelected()) {
	    			pcategory="ACC";
	    		}
	    		int m_no = 0;
	    	//객체화 [상태 초기값을 1로 정함 ]
	    	Product product = new Product(pname, pimage, pcontents, pcategory, pprice, 1, m_no); //등록시 생성자 받아옴
	    	
	    }
	    
	    //파일 경로 찾기
	    private String pimage; // 파일 경로 저장할 변수
	    private Stage stage; //파일 경로 찾을 화면
	    
	    
	    @FXML
	    //imgadd(이미지 등록)버튼 눌렀을때
	    void imgadd(ActionEvent event) {
	    	
	    	//1. 파일 선택 클래스
	    	FileChooser fileChooser = new FileChooser(); //파일 선택시 경로 저장
	    	//2. 파일 스테이지 설정 getExtensionFilters => 선택한 파일의 필터 지정( png, jpg, gif 만 선택할 수 있도록 )
	    	fileChooser.getExtensionFilters().add(new ExtensionFilter("그림파일 : Image File", "*png", "*jpg", "*gif")); //
	    	//3. 스테이지 실행
	    	File file = fileChooser.showOpenDialog(stage);
	    }
}
