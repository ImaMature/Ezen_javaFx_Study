package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dao.ProductDao;
import domain.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ProductupdateController implements Initializable{
	Product product = ProductlistController.product; // 1.테이블뷰에서 클릭된 객체
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtpname.setText(product.getP_name());		//클릭된 제품명
		txtpcontents.setText(product.getP_contents()); //클릭된 제품내용
		txtpprice.setText(product.getP_price() + ""); //클릭된 제품가격 + 스트링화
			Image image = new Image(product.getP_img()); //클릭된이미지를 이미지로 image변수저장
		pimg.setImage(image); //pimg라는 이미 씬빌더에서 만들어놓은 이미지 변수에 저장
		lblimgpath.setText(product.getP_img());
		pimage = product.getP_img(); //ProductregisterController에 경로 저장된 변수
		
		if(product.getP_category().equals("의류")) {opt_1.setSelected(true);}
		if(product.getP_category().equals("신발")) {opt_2.setSelected(true);}
		if(product.getP_category().equals("가방")) {opt_3.setSelected(true);}
		if(product.getP_category().equals("ACC")) {opt_4.setSelected(true);}
		
	}
    @FXML
    private Button btncancel;

    @FXML
    private Button btnimgadd;

    @FXML
    private Button btnupdate;

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
    private Stage stage;	// 파일 선택 스테이지(이걸 전제로 Filechooser가 사용가능)
    private String pimage;  // 파일 선택 경로
    
    @FXML
    void imgadd(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser(); //stage가 무조건 있어야함 윈도우 창 하나가 stage
    	fileChooser.getExtensionFilters().add(
    			new ExtensionFilter("그림파일만 가능", "*jpg", "*png", "*gif"));
    	File file = fileChooser.showOpenDialog(stage); // 3. 파일 선택을 해당 스테이지 열기
    		//4. 파일 선택된 경로 file에 저장
    	lblimgpath.setText("파일경로 : " + file.getPath());
    	pimage = file.toURI().toString(); //realpath(실제경로)를 pimage에 저장해서 객체화
    	Image image = new Image(pimage); //이미지 클래스에 저장된 pimage값 저장
    	pimg.setImage(image);
    }

    @FXML
    void update(ActionEvent event) {
    	String p_name = txtpname.getText();
    	String p_contents = txtpcontents.getText();
    	int p_price = Integer.parseInt(txtpprice.getText());
    	
    	String category = "";
    	if(opt_1.isSelected()) {category="의류";}
    	if(opt_2.isSelected()) {category="신발";}
    	if(opt_3.isSelected()) {category="가방";}
    	if(opt_4.isSelected()) {category="ACC";}
    	
    	Product product2 = new Product(product.getP_no(), p_name, pimage, p_contents, category, p_price, 0, "0", 0);
    	boolean result = ProductDao.getProductDao().update(product2);
    	if(result) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setHeaderText("제품수정 완료"); alert.showAndWait();
    		MainpageController.getinstance().loadpage("productlist");
    	}
    }

}
