module JavaFx_Study {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires mail;
	requires java.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens Day01 to javafx.graphics, javafx.fxml;
	opens Day02 to javafx.graphics, javafx.fxml;
	opens app to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml;
	opens dao to java.sql; //자바 클래스에 sql 연결
}
