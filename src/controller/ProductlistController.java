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

public class ProductlistController implements Initializable{ //ȭ�� �ε�[������ ��] �Ǿ����� �ʱⰪ �������̽�

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//1. DB���� ��ǰ��� ��������
		ObservableList<Product> products = ProductDao.getProductDao().productlist();
		
		//2. ��ǰ����� ���̺� �信 ����
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
		
		//3. ���̺� �信 ����Ʈ ����
		productlist.setOnMouseClicked(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				// 3.���̺�信�� Ŭ���� ���� ������[ ��ü ]
				product = productlist.getSelectionModel().getSelectedItem();
				// 4. ���õ� ��ü�� �̹������ �������� 
				Image image = new Image(product.getP_img());
				pimg.setImage(image);
				// 5. �׿�
				lblpname.setText(product.getP_name());
				lblpcontents.setText(product.getP_contents());
				// õ���� ��ǥ ���ڿ� ����� [ String.format("%,d", �� )  ]
				lblpprice.setText(String.format("%,d", product.getP_price()));
				
					//ȸ����ȣ -> ȸ�� id // �ڵ尡 ������� �ѹ� ���ͼ� writer�� ������
				String writer = MemberDao.getMemberDao().midcheck(product.getM_no());
				lblmid.setText(writer);
				System.out.println(writer);
				if(MainpageController.getinstance().getloginid().equals(writer)) {
					//�α����� ���̵� �����ϸ� ��ư�� ���̰�
					btndelete.setVisible(true);
					btnupdate.setVisible(true);
				}else {
					//�������� ������ ��ư �����
					btndelete.setVisible(false);
					btnupdate.setVisible(false);
				}
			}
		});
	}

	//�ܺο��� ���� (�ٸ����� �� �ʿ��� static �Ⱦ��� private��)
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
    private TableView <Product> productlist;//���׸��� �־��ֱ�

    @FXML
    void delete(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setHeaderText("��ǰ�� ���� �Ͻðڽ��ϱ�?");
    	Optional<ButtonType> optional = alert.showAndWait();
    	if(optional.get() == ButtonType.OK) {
    		ProductDao.getProductDao().delete(product.getP_no());
    			Alert alert2 = new Alert(AlertType.INFORMATION);
    			alert.setHeaderText("���� �Ǿ����ϴ�.");
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
