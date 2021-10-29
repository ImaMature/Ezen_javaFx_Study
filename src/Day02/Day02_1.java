package Day02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Day02_1 extends Application{
					// 1. javafx로부터 상속받기
	
	@Override	//2. start 메소드 라이딩
	public void start(Stage stage) throws Exception {
				// 3.stage 이름 정하기
		
			//6.씬빌더 파일 가져오기(동일한 패키지내에 없을경우)
			//Parent parent = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
			// 동일한 패키지내에 있을경우
			Parent parent = FXMLLoader.load(getClass().getResource("test1.fxml"));
			
			//6-2. 씬에 넣기
			Scene scene = new Scene(parent);
			
			//6-3. 스테이지에 씬넣기
			stage.setScene(scene);
		stage.show();	//4. stage 열기
	}
	
	public static void main(String[] args) {
		launch(args); //5. start 메소드 호출
	}

}
