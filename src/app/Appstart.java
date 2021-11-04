package app;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Appstart extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		
		//외부 폰트 로드
		//Font.loadFont(getClass().getResourceAsStream("C:/Users/505/git/Ezen_javaFx_Study/src/font/SEBANGGothic.ttf"), 14);
		
		
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
		Scene scene = new Scene(parent);
		//외부 스타일 시트 적용(css)
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Trip To There");
			
			Image image = new Image("C:\\Users\\505\\git\\Ezen_javaFx_Study\\src\\fxml\\login_airplane.png");
			stage.getIcons().add(image);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}


/*1. db설계
 * 		게시물번호 int
 * 		게시물제목	string
 * 		게시물내용	string
 * 		게시물작성자 string	[로그인된 ID]
 * 		게시물작성일 string [자동 주입]
 * 		게시물조회수 int
 * 2. DTO설계
 * 3. DAO설계
 * -------------------------------
 * 4. FXML작성
 * 5. CONTROLLER 작성
 * 6. DAO 작성
 * 
 * DDL
 * 			create table DB명.테이블명
 * 			
 *  
 */
		