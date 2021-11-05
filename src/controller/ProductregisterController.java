package controller;

import java.io.File;

import application.Main;
import dao.MemberDao;
import dao.ProductDao;
import domain.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

	    @FXML  //��ǰ��� �޼ҵ�
	    void register(ActionEvent event) {
	    	//��ȿ���˻� (��ǰ�� ���̳� ���ڰ� -�ų� �̷��� ������)
	    	String pname = txtpname.getText(); //��ǰ �̸� �����
	    	String pcontents = txtpcontents.getText(); //��ǰ ���� �����
	    	int p_price = Integer.parseInt(txtpprice.getText()); // ���������� ����ȯ��
	    	String pcategory = ""; // ���� ������ �־�Ѱ���
	    	
	    		if(opt_1.isSelected()) //1��° ������ư�� Ŭ��������(isSelected), 
	    		{ pcategory="�Ƿ�";}	// pcategory�� �Ƿ��� ����
	    		
	    		if(opt_2.isSelected()) {
	    			pcategory="�Ź�";
	    		}
	    		if(opt_3.isSelected() ) {
	    			pcategory="����";
	    		}
	    		if(opt_4.isSelected()) {
	    			pcategory="ACC";
	    		}
	    		
	    		//�α��ε� id�� ȸ����ȣ �˻� dbó��
	    		int m_no = MemberDao.getMemberDao().mnocheck(MainpageController.getinstance().getloginid());
	    		
	    		

	    	//��üȭ [���� �ʱⰪ�� 1�� ���� ]
	    	Product product = new Product(pname, pimage, pcontents, pcategory, p_price, 1, m_no); //��Ͻ� ������ �޾ƿ�
	    	
	    	//DBó��
	    	boolean result = ProductDao.getProductDao().register(product);
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	if(result) {
	    		alert.setHeaderText("��ǰ ��� ����");	alert.showAndWait();
	    		MainpageController.getinstance().loadpage("productlist");
	    	}
	    	
	    }
	    
	    //���� ��� ã��
	    private String pimage; // ���� ��� ������ ����
	    private Stage stage; //���� ��� ã�� ȭ��
	    
	    
	    @FXML
	    //imgadd(�̹��� ���)��ư ��������
	    void imgadd(ActionEvent event) {
	    	
	    	//1. ���� ���� Ŭ����
		    	//FileChooser => ������ ������ �� �ִ� ���������� ����� Ŭ����
	    	FileChooser fileChooser = new FileChooser(); //���� ���ý� ��� ����
	    	//2. ���� �������� ���� getExtensionFilters => ������ ������ ���� ����( png, jpg, gif �� ������ �� �ֵ��� )
	    	fileChooser.getExtensionFilters().add(new ExtensionFilter("�׸����� : Image File", "*png", "*jpg", "*gif")); //
	    	//3. �������� ����
	    	File file = fileChooser.showOpenDialog(stage);
	    		//* ������ ������ ����Ŭ������ ����
	    	lblimgpath.setText("���� ��� : " + file.getPath()); //�ش� ���̺� ���� ��ΰ� ������ ����
	    	
	    	pimage = file.toURI().toString();
	    	//System.out.println("URL ��� : " + file.getPath()); 
		    	//url ��� (���� ����� ���� ���) 
		    	//ex) URL ��� C:\Users\505\Downloads\Bluewhite.jpg
	    	//System.out.println("URI ��� : " + pimage); 
		    	//uri ��� (��ǻ�Ͱ� ������ �� �ִ� ���) 
		    	//ex) URI ��� : file:/C:/Users/505/Downloads/Bluewhite.jpg
	    	
	    	Image image = new Image(pimage);
	    	//Image image = new Image(file.getPath()); //���� �Ʒ��� �Ȱ���
	    	pimg.setImage(image);
	    }
}
