package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Appstart extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
		Scene scene = new Scene(parent);
		stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Trip to there");
			
			Image image = new Image("D:/work0928/JavaFx_Study/src/fxml/login_airplane.png");
			stage.getIcons().add(image);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
