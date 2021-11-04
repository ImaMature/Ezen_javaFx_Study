package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.MemberDao;
import dao.ProductDao;
import domain.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class ProductlistController implements Initializable{ //화면 로드[열렸을 때] 되었을때 초기값 인터페이스

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//1. DB에서 제품목록 가져오기
		ObservableList<Product> products = ProductDao.getProductDao().productlist();
		
		//2. 제품목록을 테이블 뷰에 연결
		productlist.setItems(products);
		
		TableColumn tc = productlist.getColumns().get(0);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_name"));
		
		tc = productlist.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_category"));
		
		tc = productlist.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_price"));
		
		tc = productlist.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_activation"));
		
		tc = productlist.getColumns().get(4);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_date"));
		
		//3. 테이블 뷰에 리스트 설정
		productlist.setOnMouseClicked(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				// 3.테이블뷰에서 클릭한 모델의 아이템[ 객체 ]
				product = productlist.getSelectionModel().getSelectedItem();
				// 4. 선택된 객체내 이미지경로 가져오기 
				Image image = new Image(product.getP_img());
				pimg.setImage(image);
				// 5. 그외
				lblpname.setText(product.getP_name());
				lblpcontents.setText(product.getP_contents());
				// 천단위 쉼표 문자열 만들기 [ String.format("%,d", 값 )  ]
				lblpprice.setText(String.format("%,d", product.getP_price()));
				
					//회원번호 -> 회원 id // 코드가 길어져서 한번 빼와서 writer에 저장함
				String writer = MemberDao.getMemberDao().midcheck(product.getM_no());
				lblmid.setText(writer);
				System.out.println(writer);
				if(MainpageController.getinstance().getloginid().equals(writer)) {
					//로그인한 아이디가 동일하면 버튼이 보이고
					btndelete.setVisible(true);
					btnupdate.setVisible(true);
				}else {
					//동일하지 않으면 버튼 숨기기
					btndelete.setVisible(false);
					btnupdate.setVisible(false);
				}
			}
		});
	}

	//외부에서 선언 (다른데서 쓸 필요없어서 static 안쓰고 private함)
	private Product product;
	
    @FXML
    private Button btndelete;

    @FXML
    private Button btnregister;

    @FXML
    private Button btnupdate;

    @FXML
    private Label lblpcontents;

    @FXML
    private Label lblpname;

    @FXML
    private Label lblpprice;

    @FXML
    private Label lblmid;

    @FXML
    private ImageView pimg;

    @FXML
    private TableView <Product> productlist;//제네릭에 넣어주기

    @FXML
    void delete(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setHeaderText("제품을 삭제 하시겠습니까?");
    	Optional<ButtonType> optional = alert.showAndWait();
    	if(optional.get() == ButtonType.OK) {
    		ProductDao.getProductDao().delete(product.getP_no());
    			Alert alert2 = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("삭제 되었습니다.");
    			MainpageController.getinstance().loadpage("productlist");
    		
    	}
    }

    @FXML
    void register(ActionEvent event) {
    	MainpageController.getinstance().loadpage("productregister");
    }

    @FXML
    void update(ActionEvent event) {

    }
}
