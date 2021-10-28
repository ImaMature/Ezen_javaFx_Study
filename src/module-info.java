module JavaFx_Study {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens Day01 to javafx.graphics, javafx.fxml;
	opens app to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml;
	
}
