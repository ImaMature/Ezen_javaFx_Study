package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SignupController {
	
	 @FXML
	    private Label btnback;

	    @FXML
	    private BorderPane mainboardpane;

	    @FXML
	    private AnchorPane signuppane;

	    @FXML
	    void back(MouseEvent event) {
	    	LoginController.getinstance().loadpage("login");
	    }

	    @FXML
	    void signup(MouseEvent event) {

	    }

}
